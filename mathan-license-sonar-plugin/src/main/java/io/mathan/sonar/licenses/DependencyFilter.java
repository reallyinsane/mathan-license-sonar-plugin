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

package io.mathan.sonar.licenses;

import io.mathan.sonar.licenses.filter.PatternArtifactFilter;
import io.mathan.sonar.licenses.parser.Dependency;
import java.util.Arrays;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.artifact.handler.DefaultArtifactHandler;
import org.apache.maven.artifact.resolver.filter.ArtifactFilter;
import org.sonar.api.batch.rule.Severity;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.config.Configuration;

public class DependencyFilter {

  private ArtifactFilter inclusions;
  private ArtifactFilter exclusions;

  private Severity severityNonCompliant;
  private Severity severityMissing;
  private Configuration configuration;

  private static DependencyFilter create(Configuration configuration, String inclusions, String exclusions, Severity severityNonCompliant, Severity severityMissing) {
    DependencyFilter filter = new DependencyFilter();
    filter.configuration = configuration;
    filter.inclusions = getIncludeFilter(inclusions);
    filter.exclusions = getIncludeFilter(exclusions);
    filter.severityNonCompliant = severityNonCompliant;
    filter.severityMissing = severityMissing;
    return filter;
  }


  public static DependencyFilter create(SensorContext context) {
    return create(
        context.config(),
        context.config().get(Constants.CONFIG_INCLUSIONS).orElse(":::::"),
        context.config().get(Constants.CONFIG_EXCLUSIONS).orElse(""),
        Severity.valueOf(context.config().get(Constants.CONFIG_SEVERITY_NON_COMPLIANT).orElse(Constants.CONFIG_SEVERITY_NON_COMPLIANT_DEFAULT)),
        Severity.valueOf(context.config().get(Constants.CONFIG_SEVERITY_MISSING).orElse(Constants.CONFIG_SEVERITY_MISSING_DEFAULT)));
  }

  public void setInclusions(String inclusions) {
    this.inclusions = getIncludeFilter(inclusions);
  }

  public void setExclusions(String exclusions) {
    this.exclusions = getIncludeFilter(exclusions);
  }

  private static ArtifactFilter getIncludeFilter(String pattern) {
    if (pattern.trim().isEmpty()) {
      return new ExcludeArtifactFilter();
    } else {
      return new PatternArtifactFilter(Arrays.asList(pattern.split(",")));
    }
  }

  public Severity severity(Dependency dependency) {
    Artifact artifact = asArtifact(dependency);
    if (inclusions.include(artifact) && !exclusions.include(artifact)) {
      if (License.UNKNOWN.equals(dependency.getLicense())) {
        return severityMissing;
      }
      if (nonCompliant(dependency.getLicense())) {
        return severityNonCompliant;
      }
    }
    return null;
  }

  private boolean nonCompliant(License license) {
    Boolean compliant = Boolean.valueOf(configuration.get(Constants.CONFIG_LICENSE_PREFIX + license.getId()).orElse(Boolean.toString(license.isCompliant())));
    return !compliant;
  }

  private static Artifact asArtifact(Dependency dependency) {
    return new DefaultArtifact(
        dependency.getGroupId(),
        dependency.getArtifactId(),
        dependency.getVersion(),
        null,
        null,
        null,
        new DefaultArtifactHandler());
  }

  static class ExcludeArtifactFilter implements ArtifactFilter {

    @Override
    public boolean include(Artifact artifact) {
      return false;
    }
  }
}
