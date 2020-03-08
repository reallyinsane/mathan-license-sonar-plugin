/*
 * mathan-license-sonar-plugin
 * Copyright (c) 2020 Matthias Hanisch
 * matthias@mathan.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.mathan.sonar.licenses.parser;

import io.mathan.sonar.licenses.License;
import io.mathan.sonar.licenses.report.XmlReportFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.sax.SAXSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import net.sf.saxon.om.TreeInfo;
import net.sf.saxon.trans.XPathException;
import net.sf.saxon.xpath.XPathFactoryImpl;
import org.sonar.api.config.Configuration;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.xml.sax.InputSource;

public class ReportParser {

  private static final Logger LOGGER = Loggers.get(ReportParser.class);
  private final Configuration configuration;
  private static XPathFactoryImpl xPathFactory;
  private static XPath xpath;

  static {
    xPathFactory = new XPathFactoryImpl();
    xpath = xPathFactory.newXPath();
  }

  public ReportParser(Configuration configuration) {
    this.configuration = configuration;
  }

  /**
   * Creates an Analysis based on one or more dependency-update-reports.
   */
  public Analysis parse(List<XmlReportFile> files) throws IOException, XPathExpressionException, XPathException {
    Analysis analysis = new Analysis();
    for (XmlReportFile file : files) {
      parse(analysis, file);
    }
    return analysis;
  }

  private void parse(Analysis analysis, XmlReportFile file) throws IOException, XPathException, XPathExpressionException {
    TreeInfo saxTree = getTree(file.getInputStream());
    XPathExpression xPathExpression = xpath.compile("//dependency");
    List nodes = (List) xPathExpression.evaluate(saxTree, XPathConstants.NODESET);
    Object node;
    for (int i = 0; i < nodes.size(); i++) {
      node = nodes.get(i);
      String groupId = (String) xpath.compile("groupId/text()").evaluate(node, XPathConstants.STRING);
      String artifactId = (String) xpath.compile("artifactId/text()").evaluate(node, XPathConstants.STRING);
      String version = (String) xpath.compile("version/text()").evaluate(node, XPathConstants.STRING);
      List<License> licenses = parseLicenses(node);
      Dependency dependency = new Dependency();
      dependency.setGroupId(groupId);
      dependency.setArtifactId(artifactId);
      dependency.setVersion(version);
      if (licenses.isEmpty()) {
        dependency.setLicense(License.UNKNOWN);
      } else {
        dependency.setLicense(licenses.get(0));
      }
      analysis.getDependencies().add(dependency);
    }
  }

  private List<License> parseLicenses(Object dependency) throws XPathExpressionException {
    List<License> licenses = new ArrayList<>();
    XPathExpression xPathExpression = xpath.compile("licenses/license");
    List nodes = (List) xPathExpression.evaluate(dependency, XPathConstants.NODESET);
    Object node;
    for (int i = 0; i < nodes.size(); i++) {
      node = nodes.get(i);
      String name = (String) xpath.compile("name/text()").evaluate(node, XPathConstants.STRING);
      String url = (String) xpath.compile("url/text()").evaluate(node, XPathConstants.STRING);
      License license = License.getLicense(name, url);
      licenses.add(license);
      if (License.UNKNOWN.equals(license)) {
        LOGGER.info(String.format("Unknon license name=%s url=%s", name, url));
      }
    }
    return licenses;
  }

  private static TreeInfo getTree(InputStream in) throws XPathException {
    InputSource inputSource = new InputSource(in);
    SAXSource saxSource = new SAXSource(inputSource);
    net.sf.saxon.Configuration configuration = xPathFactory.getConfiguration();
    return configuration.buildDocumentTree(saxSource);
  }


}
