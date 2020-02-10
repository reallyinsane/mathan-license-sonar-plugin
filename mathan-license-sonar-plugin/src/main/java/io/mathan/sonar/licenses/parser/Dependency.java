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
import java.util.Objects;

public class Dependency {


  private String groupId;
  private String artifactId;
  private String version;
  private License license;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Dependency that = (Dependency) o;
    return groupId.equals(that.groupId)
        && artifactId.equals(that.artifactId)
        && version.equals(that.version);
  }

  public License getLicense() {
    return license;
  }

  public String getArtifactId() {
    return artifactId;
  }

  public String getGroupId() {
    return groupId;
  }

  public String getVersion() {
    return version;
  }

  public void setLicense(License license) {
    this.license = license;
  }

  public void setArtifactId(String artifactId) {
    this.artifactId = artifactId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupId, artifactId, version);
  }

  @Override
  public String toString() {
    return String.format("%s:%s:%s", groupId, artifactId, version);
  }

  /**
   * Creates a String identifying this dependency in format <i>groupId</i>:<i>artifactId</i>:<i>version</i>:<i>license</i>
   */
  public String toDataString() {
    return String.format("%s:%s:%s:%s", groupId, artifactId, version, getLicense());
  }
}
