[![Build Status](https://travis-ci.org/reallyinsane/mathan-license-sonar-plugin.svg?branch=master)](https://travis-ci.org/reallyinsane/mathan-license-sonar-plugin)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/9b8454dfea424ebe98646cfc7151f02f)](https://www.codacy.com/manual/reallyinsane/mathan-license-sonar-plugin?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=reallyinsane/mathan-license-sonar-plugin&amp;utm_campaign=Badge_Grade)
<a href="https://opensource.org/licenses/Apache-2.0"><img src="https://img.shields.io/badge/license-apache2-blue.svg"></a>

# License Plugin for SonarQube 7.9

Integrates [license summary] from [license-maven-plugin] into SonarQube v7.9.

## About license summary

The [license-maven-plugin] has the goal *aggregate-download-licenses* which creates an overview about licenses of the dependencies of a Maven project. 


## Note

**This SonarQube plugin does not perform analysis**, rather, it reads existing license summary (licenses.xml). Please refer to [license-maven-plugin] for relevant documentation how to generate the reports.

## Metrics

The plugin keeps track of the following statistics:

Metric | Description 
-------|------------
Compliant | The number of dependencies with compliant licenses. (See configuration properties for definition of compliant/non-compliant licenses) 
Non-compliant | The number of dependencies with non-compliant licenses. (See configuration properties for definition of compliant/non-compliant licenses)  
Unknown | The number of dependencies with unknown licenses.
_License_ | The number of dependencies with this license. (See configuration properties for configuration enabling metrics for each license)
Compliance rating | The rating of the usage of compliant/non-compliant licenes. (See configuration properties for definition of rating for non-compliant and unknown licenses)

#### Compliance rating

The compliance rating is based on the usage of non-compliant or unknown licenses. If compliant licenses are used only the rating is ![a](a.png). If at least one non-compliant license
is found the rating is ![e](e.png) otherwise if at lease one license is unknown the rating is ![d](d.png).

## Installation

Copy the plugin (jar file) to $SONAR_INSTALL_DIR/extensions/plugins and restart SonarQube.

## Plugin Configuration

The [license-maven-plugin] will output a file named 'licenses.xml' when asked to output XML. The mathan-license-sonar-plugin reads an existing license summary XML report.

There is additional configuration available which enables to override the default mapping of non-compliant/unknown licenses to SonarQube severity. It is also possible to include or exclude certain
dependencies for the check.

### Artifact pattern syntax 
 
The filters defined are using a special artifact pattern syntax already known from Maven extended to allow a comma separated list of such patterns.
 
The pattern is defined like this: `[groupId]:[artifactId]:[type]:[version]:[scope]:[classifier]`. 

Each pattern segment is optional and supports full and partial * wildcards. An empty pattern segment is treated as an implicit wildcard. For example, `org.apache.*` would match all artifacts
whose group id started with `org.apache.`, and `:::*-SNAPSHOT` would match all snapshot artifacts.

### Configuration properties

This plugin offers various configuration options which are explained in the following categories. The settings can be found under Administration > Configuration > General Settings > Licenses.

#### Appearance

By default 3 metrics will be reported. With the following configuration metrics can be hidden. Changes to the setting in this category need a restart of
 SonarQube to take effect.
 
Property | Default
---------|--------
Hide compliant dependencies | true
Hide dependencies without license | false
Hide non-compliant dependencies | false
Hide entries for each used license | true 

#### Inclusions/ Exclusions

By default updates for all dependencies are reported. A whitelist filter and/or a blacklist filter can be used to include/exclude certain dependencies. These filter use the artifact pattern syntax
described above. Some common use cases for the filter are

- exclude SNAPSHOT dependencies (`:::*-SNAPSHOT`)
- exclude dependencies with scope test (`::::test`)
- include dependencies of own company only (e.g `com.mycompany.*`)

Property | Default
---------|--------
sonar.licenses.inclusions | `:::::` (include all)
sonar.licenses.exclusions | (none)

#### Compliant Licenses

Within this section the license can be defined as compliant/non-compliant. It can be useful to change these setting to e.g. also mark LGPL licenses as non-compliant. The default
configuration is like this.

##### Compliant
License       | Name                                                    | Compliant 
--------------|---------------------------------------------------------|----------
Apache 2.0    | Apache License version 2.0                              | true
BSD 2         | BSD 2-Clause License                                    | true
BSD 3         | BSD 3-Clause License                                    | true
CDDL 1.0      | Common development and distribution license version 1.0 | true
FDL 1.3       | GNU Free Documentation License (FDL) version 1.3        | true
LGPL 2.1      | GNU General Lesser Public License (LGPL) version 2.1    | true
LGPL 3.0      | GNU General Lesser Public License (LGPL) version 3.0    | true
MIT           | MIT-License                                             | true

##### Non-Compliant
License       | Name                                                    | Compliant 
--------------|---------------------------------------------------------|----------
AGPL 3.0      | GNU Affero General Public License (AGPL) version 3.0    | false
EPL /w D. 1.0 | Eclipse Public + Distribution License - v 1.0           | false
EPC 1.0       | Eclipse Public License - v 1.0                          | false
EPL /w S 2.0  | Eclipse Public License - v 2.0 with Secondary License   | false
EPL 2.0       | Eclipse Public License - v 2.0                          | false
EUPL 1.1      | European Union Public License v1.1                      | false
GPL 1.0       | GNU General Public License (GPL) version 1.0            | false
GPL 2.0       | GNU General Public License (GPL) version 2.0            | false
GPL 3.0       | GNU General Public License (GPL) version 3.0            | false

#### Severities

The severity for non-compliant and unknown licenses can be changed. By default non-compliant licenses are reported by issues with severity blocker. For unknown licenses the severity is critical.

[license summary]: http://www.mojohaus.org/license-maven-plugin/download-licenses-mojo.html
[license-maven-plugin]: https://github.com/mojohaus/license-maven-plugin
