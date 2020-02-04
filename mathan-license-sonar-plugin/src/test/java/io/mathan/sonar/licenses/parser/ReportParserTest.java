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
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.sonar.api.config.Configuration;
import org.sonar.api.config.Settings;
import org.sonar.api.config.internal.ConfigurationBridge;
import org.sonar.api.config.internal.MapSettings;

public class ReportParserTest {

  private static final String GROUP_ID = "io.mathan.test";

  @Test
  public void parseReport() throws Exception {
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream("report/licenses.xml");

    Settings settings = new MapSettings();
    Configuration configuration = new ConfigurationBridge(settings);
    Analysis analysis = new ReportParser(configuration).parse(Arrays.asList((XmlReportFile) () -> inputStream));


  }


  private static void verifyPresent(
      List<Dependency> dependencies, String artifactId,
      String version, String next, License license) {
    Dependency check = new Dependency();
    check.setArtifactId(artifactId);
    check.setLicense(license);
    check.setGroupId(ReportParserTest.GROUP_ID);
    check.setVersion(version);
    Assert.assertTrue(String.format("%s:%s:%s expected but not found", ReportParserTest.GROUP_ID, artifactId, version), dependencies.contains(check));
  }

}
