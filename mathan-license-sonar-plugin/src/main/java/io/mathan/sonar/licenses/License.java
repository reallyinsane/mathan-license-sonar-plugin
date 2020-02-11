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

public enum License {
  AGPL_V3("AGPL 3.0", "sonar.licenses.compliant.agpl_v3", "false"),
  APACHE_V2("Apache 2.0", "sonar.licenses.compliant.apache_v2", "true"),
  BSD_2("BSD 2-Clause", "sonar.licenses.compliant.bsd_2", "true"),
  BSD_3("BSD 3-Clause", "sonar.licenses.compliant.bsd_3", "true"),
  CDDL_V1("CDDL 1.0", "sonar.licenses.compliant.cddl_v1", "true"),
  EPL_V1("EPL /w Distribution 1.0", "sonar.licenses.compliant.epl_v1", "false"),
  EPL_ONLY_V1("EPL 1.0", "sonar.licenses.compliant.epl_only_v1", "false"),
  EPL_V2("EPL /w Secondary 2.0", "sonar.licenses.compliant.epl_v2", "false"),
  EPL_ONLY_V2("EPL 2.0", "sonar.licenses.compliant.epl_only_v2", "false"),
  EUPL_V1_1("EUPL 1.1", "sonar.licenses.compliant.eupl_v1_1", "false"),
  FDL_V1_3("FDL 1.3", "sonar.licenses.compliant.fdl_v1_3", "true"),
  GPL_V1("GPL 1.0", "sonar.licenses.compliant.gpl_v1", "false"),
  GPL_V2("GPL 2.0", "sonar.licenses.compliant.gpl_v2", "false"),
  GPL_V3("GPL 3.0", "sonar.licenses.compliant.gpl_v3", "false"),
  LGPL_V2_1("LGPL 2.1", "sonar.licenses.compliant.lgpl_v2_1", "true"),
  LGPL_V3("LGPL 3.0", "sonar.licenses.compliant.lgpl_v3", "true"),
  MIT("MIT", "sonar.licenses.compliant.mit", "true"),
  UNKNOWN("Unknown", null, null);

  private final String title;
  private final String config;
  private final String defaultValue;

  private License(String title, String config, String defaultValue) {
    this.title = title;
    this.config = config;
    this.defaultValue = defaultValue;
  }

  public String getConfig() {
    return config;
  }

  public String getDefaultValue() {
    return defaultValue;
  }

  public String getTitle() {
    return title;
  }
}
