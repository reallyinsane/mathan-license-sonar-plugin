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
import java.util.HashMap;
import java.util.Map;

public class LicenseMapper {

  private static Map<String, License> URL_MAP = new HashMap<>();
  private static Map<String, License> NAME_MAP = new HashMap<>();

  static {
    // names from license-maven-plugin
    NAME_MAP.put("GNU Affero General Public License (AGPL) version 3.0", License.AGPL_V3);
    NAME_MAP.put("Apache License version 2.0", License.APACHE_V2);
    NAME_MAP.put("BSD 2-Clause License", License.BSD_2);
    NAME_MAP.put("BSD 3-Clause License", License.BSD_3);
    NAME_MAP.put("COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Version 1.0", License.CDDL_V1);
    NAME_MAP.put("Eclipse Public License - v 1.0", License.EPL_ONLY_V1);
    NAME_MAP.put("Eclipse Public License - v 2.0", License.EPL_ONLY_V2);
    NAME_MAP.put("Eclipse Public + Distribution License - v 1.0", License.EPL_V1);
    NAME_MAP.put("Eclipse Public License - v 2.0 with Secondary License", License.EPL_V2);
    NAME_MAP.put("European Union Public License v1.1", License.EUPL_V1_1);
    NAME_MAP.put("GNU Free Documentation License (FDL) version 1.3", License.FDL_V1_3);
    NAME_MAP.put("GNU General Public License (GPL) version 1.0", License.GPL_V1);
    NAME_MAP.put("GNU General Public License (GPL) version 2.0", License.GPL_V2);
    NAME_MAP.put("GNU General Public License (GPL) version 3.0", License.GPL_V3);
    NAME_MAP.put("GNU General Lesser Public License (LGPL) version 2.1", License.LGPL_V2_1);
    NAME_MAP.put("GNU General Lesser Public License (LGPL) version 3.0", License.LGPL_V3);
    NAME_MAP.put("MIT-License", License.MIT);
    // additional names
    NAME_MAP.put("BSD", License.BSD_2);
    NAME_MAP.put("The BSD License", License.BSD_2);
    NAME_MAP.put("The Apache Software License, Version 2.0", License.APACHE_V2);
    NAME_MAP.put("Apache License, Version 2.0", License.APACHE_V2);
    NAME_MAP.put("Apache Public License 2.0", License.APACHE_V2);

    URL_MAP.put("https://www.apache.org/licenses/LICENSE-2.0.txt", License.APACHE_V2);
    URL_MAP.put("http://www.apache.org/licenses/LICENSE-2.0", License.APACHE_V2);
  }

  public static License getLicense(String name, String url) {
    if(URL_MAP.containsKey(url)) {
      return URL_MAP.get(url);
    }
    if(NAME_MAP.containsKey(name)) {
      return NAME_MAP.get(name);
    }
    return License.UNKNOWN;
  }

}
