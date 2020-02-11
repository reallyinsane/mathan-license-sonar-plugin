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

import java.util.Arrays;
import java.util.List;
import org.sonar.api.PropertyType;
import org.sonar.api.batch.ScannerSide;
import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.rule.Severity;

@ScannerSide
public class Configuration {

  private Configuration() {
    // do nothing
  }

  static List<PropertyDefinition> getPropertyDefinitions() {
    return Arrays.asList(
        PropertyDefinition.builder(Constants.CONFIG_REPORT_PATH_PROPERTY)
            .subCategory("Paths")
            .name("Licenses report path")
            .description("path to the 'licenses.xml' file")
            .defaultValue(Constants.CONFIG_REPORT_PATH_DEFAULT)
            .build(),
        PropertyDefinition.builder(Constants.CONFIG_SEVERITY_NON_COMPLIANT)
            .subCategory(Constants.SUB_CATEGORY_SEVERITIES)
            .name("Non-compliant licenses")
            .description("Severity used for non-compliant licenses")
            .options(Severity.ALL)
            .defaultValue(Constants.CONFIG_SEVERITY_NON_COMPLIANT_DEFAULT)
            .type(PropertyType.SINGLE_SELECT_LIST)
            .index(1)
            .build(),
        PropertyDefinition.builder(Constants.CONFIG_SEVERITY_MISSING)
            .subCategory(Constants.SUB_CATEGORY_SEVERITIES)
            .name("Unknown licenses")
            .description("Severity used for unknown licenses")
            .options(Severity.ALL)
            .defaultValue(Constants.CONFIG_SEVERITY_MISSING_DEFAULT)
            .type(PropertyType.SINGLE_SELECT_LIST)
            .index(2)
            .build(),
        PropertyDefinition.builder(Constants.CONFIG_INCLUSIONS)
            .subCategory(Constants.SUB_CATEGORY_INCLUSIONS_EXCLUSIONS)
            .name("Inclusions")
            .description("Whitelist of dependencies to include in the analysis separated by comma. The filter syntax is"
                + " [groupId]:[artifactId]:[type]:[version] where each pattern segment is optional and supports full"
                + " and partial * wildcards. Ab empty pattern segment is treated as an implicit wildcard *.")
            .defaultValue("")
            .type(PropertyType.STRING)
            .index(1)
            .build(),
        PropertyDefinition.builder(Constants.CONFIG_EXCLUSIONS)
            .subCategory(Constants.SUB_CATEGORY_INCLUSIONS_EXCLUSIONS)
            .name("Exclusions")
            .description("Blacklist of dependencies to exclude in the analysis separated by comma. The filter syntax is"
                + " [groupId]:[artifactId]:[type]:[version] where each pattern segment is optional and supports full"
                + " and partial * wildcards. Ab empty pattern segment is treated as an implicit wildcard *.")
            .defaultValue("")
            .type(PropertyType.STRING)
            .index(2)
            .build(),
        PropertyDefinition.builder(License.AGPL_V3.getConfig())
            .subCategory(Constants.SUB_CATEGORY_LICENSES)
            .name(License.AGPL_V3.name())
            .description(License.AGPL_V3.name())
            .index(1)
            .type(PropertyType.BOOLEAN)
            .defaultValue(License.AGPL_V3.getDefaultValue())
            .build(),
        PropertyDefinition.builder(License.APACHE_V2.getConfig())
            .subCategory(Constants.SUB_CATEGORY_LICENSES)
            .name(License.APACHE_V2.name())
            .description(License.APACHE_V2.name())
            .index(2)
            .type(PropertyType.BOOLEAN)
            .defaultValue(License.APACHE_V2.getDefaultValue())
            .build(),
        PropertyDefinition.builder(License.BSD_2.getConfig())
            .subCategory(Constants.SUB_CATEGORY_LICENSES)
            .name(License.BSD_2.name())
            .description(License.BSD_2.name())
            .index(3)
            .type(PropertyType.BOOLEAN)
            .defaultValue(License.BSD_2.getDefaultValue())
            .build(),
        PropertyDefinition.builder(License.BSD_3.getConfig())
            .subCategory(Constants.SUB_CATEGORY_LICENSES)
            .name(License.BSD_3.name())
            .description(License.BSD_3.name())
            .index(4)
            .type(PropertyType.BOOLEAN)
            .defaultValue(License.BSD_3.getDefaultValue())
            .build(),
        PropertyDefinition.builder(License.CDDL_V1.getConfig())
            .subCategory(Constants.SUB_CATEGORY_LICENSES)
            .name(License.CDDL_V1.name())
            .description(License.CDDL_V1.name())
            .index(5)
            .type(PropertyType.BOOLEAN)
            .defaultValue(License.CDDL_V1.getDefaultValue())
            .build(),
        PropertyDefinition.builder(License.EPL_ONLY_V1.getConfig())
            .subCategory(Constants.SUB_CATEGORY_LICENSES)
            .name(License.EPL_ONLY_V1.name())
            .description(License.EPL_ONLY_V1.name())
            .index(6)
            .type(PropertyType.BOOLEAN)
            .defaultValue(License.EPL_ONLY_V1.getDefaultValue())
            .build(),
        PropertyDefinition.builder(License.EPL_ONLY_V2.getConfig())
            .subCategory(Constants.SUB_CATEGORY_LICENSES)
            .name(License.EPL_ONLY_V2.name())
            .description(License.EPL_ONLY_V2.name())
            .index(7)
            .type(PropertyType.BOOLEAN)
            .defaultValue(License.EPL_ONLY_V2.getDefaultValue())
            .build(),
        PropertyDefinition.builder(License.EPL_V1.getConfig())
            .subCategory(Constants.SUB_CATEGORY_LICENSES)
            .name(License.EPL_V1.name())
            .description(License.EPL_V1.name())
            .index(8)
            .type(PropertyType.BOOLEAN)
            .defaultValue(License.EPL_V1.getDefaultValue())
            .build(),
        PropertyDefinition.builder(License.EPL_V2.getConfig())
            .subCategory(Constants.SUB_CATEGORY_LICENSES)
            .name(License.EPL_V2.name())
            .description(License.EPL_V2.name())
            .index(9)
            .type(PropertyType.BOOLEAN)
            .defaultValue(License.EPL_V2.getDefaultValue())
            .build(),
        PropertyDefinition.builder(License.EUPL_V1_1.getConfig())
            .subCategory(Constants.SUB_CATEGORY_LICENSES)
            .name(License.EUPL_V1_1.name())
            .description(License.EUPL_V1_1.name())
            .index(10)
            .type(PropertyType.BOOLEAN)
            .defaultValue(License.EUPL_V1_1.getDefaultValue())
            .build(),
        PropertyDefinition.builder(License.GPL_V1.getConfig())
            .subCategory(Constants.SUB_CATEGORY_LICENSES)
            .name(License.GPL_V1.name())
            .description(License.GPL_V1.name())
            .index(11)
            .type(PropertyType.BOOLEAN)
            .defaultValue(License.GPL_V1.getDefaultValue())
            .build(),
        PropertyDefinition.builder(License.GPL_V2.getConfig())
            .subCategory(Constants.SUB_CATEGORY_LICENSES)
            .name(License.GPL_V2.name())
            .description(License.GPL_V2.name())
            .index(12)
            .type(PropertyType.BOOLEAN)
            .defaultValue(License.GPL_V2.getDefaultValue())
            .build(),
        PropertyDefinition.builder(License.GPL_V3.getConfig())
            .subCategory(Constants.SUB_CATEGORY_LICENSES)
            .name(License.GPL_V3.name())
            .description(License.GPL_V3.name())
            .index(13)
            .type(PropertyType.BOOLEAN)
            .defaultValue(License.GPL_V3.getDefaultValue())
            .build(),
        PropertyDefinition.builder(License.LGPL_V2_1.getConfig())
            .subCategory(Constants.SUB_CATEGORY_LICENSES)
            .name(License.LGPL_V2_1.name())
            .description(License.LGPL_V2_1.name())
            .index(14)
            .type(PropertyType.BOOLEAN)
            .defaultValue(License.LGPL_V2_1.getDefaultValue())
            .build(),
        PropertyDefinition.builder(License.LGPL_V3.getConfig())
            .subCategory(Constants.SUB_CATEGORY_LICENSES)
            .name(License.LGPL_V3.name())
            .description(License.LGPL_V3.name())
            .index(15)
            .type(PropertyType.BOOLEAN)
            .defaultValue(License.LGPL_V3.getDefaultValue())
            .build(),
        PropertyDefinition.builder(License.MIT.getConfig())
            .subCategory(Constants.SUB_CATEGORY_LICENSES)
            .name(License.MIT.name())
            .description(License.MIT.name())
            .index(16)
            .type(PropertyType.BOOLEAN)
            .defaultValue(License.MIT.getDefaultValue())
            .build(),
        PropertyDefinition.builder(Constants.CONFIG_HIDE_COMPLIANT)
            .subCategory(Constants.SUB_CATEGORY_APPEARANCE)
            .name("Hide compliant dependencies")
            .description("Flag indicating whether the number of dependencies with compliant licenses is hidden. (Change requires restart)")
            .type(PropertyType.BOOLEAN)
            .defaultValue(String.valueOf(Constants.CONFIG_HIDE_COMPLIANT_DEFAULT))
            .build(),
        PropertyDefinition.builder(Constants.CONFIG_HIDE_NON_COMPLIANT)
            .subCategory(Constants.SUB_CATEGORY_APPEARANCE)
            .name("Hide non-compliant dependencies")
            .description("Flag indicating whether the number of dependencies with non-compliant licenses is hidden. (Change requires restart)")
            .type(PropertyType.BOOLEAN)
            .defaultValue(String.valueOf(Constants.CONFIG_HIDE_NON_COMPLIANT_DEFAULT))
            .build(),
        PropertyDefinition.builder(Constants.CONFIG_HIDE_UNKNOWN)
            .subCategory(Constants.SUB_CATEGORY_APPEARANCE)
            .name("Hide dependencies without license")
            .description("Flag indicating whether the number of dependencies with unknown licenses is hidden. (Change requires restart)")
            .type(PropertyType.BOOLEAN)
            .defaultValue(String.valueOf(Constants.CONFIG_HIDE_UNKNOWN_DEFAULT))
            .build(),
        PropertyDefinition.builder(Constants.CONFIG_HIDE_LICENSES)
            .subCategory(Constants.SUB_CATEGORY_APPEARANCE)
            .name("Hide entries for each used license")
            .description("Flag indicating whether the entries for each used licenses are hidden. (Change requires restart)")
            .type(PropertyType.BOOLEAN)
            .defaultValue(String.valueOf(Constants.CONFIG_HIDE_LICENSES_DEFAULT))
            .build()

    );
  }
}
