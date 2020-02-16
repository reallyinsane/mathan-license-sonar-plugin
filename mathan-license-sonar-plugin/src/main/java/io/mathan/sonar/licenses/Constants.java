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

import org.sonar.api.rule.Severity;

public final class Constants {

  public static final String CONFIG_REPORT_PATH_PROPERTY = "sonar.licenses.reportPath";
  public static final String CONFIG_REPORT_PATH_DEFAULT = "target/generated-resources/licenses.xml";

  public static final String CONFIG_HIDE_COMPLIANT = "sonar.licenses.hide.compliant";
  public static final Boolean CONFIG_HIDE_COMPLIANT_DEFAULT = true;
  public static final String CONFIG_HIDE_NON_COMPLIANT = "sonar.licenses.hide.non-compliant";
  public static final Boolean CONFIG_HIDE_NON_COMPLIANT_DEFAULT = false;
  public static final String CONFIG_HIDE_UNKNOWN = "sonar.licenses.hide.missing";
  public static final Boolean CONFIG_HIDE_UNKNOWN_DEFAULT = false;
  public static final String CONFIG_HIDE_LICENSES = "sonar.licenses.hide.single";
  public static final Boolean CONFIG_HIDE_LICENSES_DEFAULT = true;

  public static final String CONFIG_SEVERITY_NON_COMPLIANT = "sonar.licenses.severity.non-compliant";
  public static final String CONFIG_SEVERITY_NON_COMPLIANT_DEFAULT = Severity.CRITICAL;
  public static final String CONFIG_SEVERITY_MISSING = "sonar.licenses.severity.missing";
  public static final String CONFIG_SEVERITY_MISSING_DEFAULT = Severity.BLOCKER;

  public static final String CONFIG_LICENSE_PREFIX = "sonar.licenses.compliant.";

  static final String CONFIG_INCLUSIONS = "sonar.licenses.inclusions";
  static final String CONFIG_EXCLUSIONS = "sonar.licenses.exclusions";

  static final String SUB_CATEGORY_LICENSES = "Licenses";
  static final String SUB_CATEGORY_INCLUSIONS_EXCLUSIONS = "Inclusions/Exclusions";
  static final String SUB_CATEGORY_APPEARANCE = "Appearance";
  static final String SUB_CATEGORY_SEVERITIES = "Severities";

  private Constants() {
  }

}
