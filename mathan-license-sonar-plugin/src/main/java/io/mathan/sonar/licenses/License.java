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

import flexjson.JSONDeserializer;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class License {

  public static final License UNKNOWN = getUnknown();
  private static List<License> licenses = null;

  private String id;
  private String name;
  private String description;
  private boolean compliant;
  private List<String> names;
  private List<String> urls;

  public static License getLicense(String name, String url) {
    for(License license : getLicenses()) {
      if(license.getNames().contains(name) || license.getUrls().contains(url)) {
        return license;
      }
    }
    return UNKNOWN;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isCompliant() {
    return compliant;
  }

  public void setCompliant(boolean compliant) {
    this.compliant = compliant;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public List<String> getNames() {
    if (names == null) {
      names = new ArrayList<>();
    }
    return names;
  }

  public void setNames(List<String> names) {
    this.names = names;
  }

  public List<String> getUrls() {
    if (urls == null) {
      urls = new ArrayList<>();
    }
    return urls;
  }

  public void setUrls(List<String> urls) {
    this.urls = urls;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    License license = (License) o;
    return id.equals(license.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  private static License getUnknown() {
    License license = new License();
    license.setId("UNKNOWN");
    return license;
  }

  public static List<License> getLicenses() {
    if (licenses == null) {
      licenses = new JSONDeserializer<List<License>>().use("values", License.class).deserialize(new InputStreamReader(License.class.getResourceAsStream("/licenses.json")));
    }
    return licenses;
  }
}
