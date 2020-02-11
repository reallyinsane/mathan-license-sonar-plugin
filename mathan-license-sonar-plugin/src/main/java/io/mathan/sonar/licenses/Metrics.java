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

import io.mathan.sonar.licenses.parser.Analysis;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.sonar.api.batch.fs.InputComponent;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.config.Configuration;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metric.ValueType;

public final class Metrics implements org.sonar.api.measures.Metrics {

  private static final String DOMAIN = "Licenses";

  static final String KEY_COMPLIANT = "metrics.licenses.compliant";
  static final String KEY_NON_COMPLIANT = "metrics.licenses.non-compliant";
  static final String KEY_UNKNOWN = "metrics.licenses.unknown";
  static final String KEY_LICENSE_PREFIX = "metrics.licenses.";

  private static final Metric<Integer> COMPLIANT = new Metric.Builder(Metrics.KEY_COMPLIANT, "Compliant", ValueType.INT)
      .setDescription("Dependencies with compliant licenses")
      .setDirection(Metric.DIRECTION_WORST)
      .setQualitative(false)
      .setDomain(Metrics.DOMAIN)
      .setBestValue(0.0d)
      .create();

  private static final Metric<Integer> NON_COMPLIANT = new Metric.Builder(Metrics.KEY_NON_COMPLIANT, "Non-compliant", ValueType.INT)
      .setDescription("Dependencies with non-compliant licenses")
      .setDirection(Metric.DIRECTION_WORST)
      .setQualitative(false)
      .setDomain(Metrics.DOMAIN)
      .setBestValue(0.0d)
      .create();

  private static final Metric<Integer> UNKNOWN = new Metric.Builder(Metrics.KEY_UNKNOWN, "Unknown", ValueType.INT)
      .setDescription("Dependencies with unknown licenses")
      .setDirection(Metric.DIRECTION_WORST)
      .setQualitative(false)
      .setDomain(Metrics.DOMAIN)
      .setBestValue(0.0d)
      .create();

  private static final Map<License, Metric<Integer>> LICENSES_METRICS = new HashMap<>();
  static final List<String> METRICS_KEYS = new ArrayList<>();

  static {
    METRICS_KEYS.add(KEY_COMPLIANT);
    METRICS_KEYS.add(KEY_NON_COMPLIANT);
    METRICS_KEYS.add(KEY_UNKNOWN);
    for (License license : License.values()) {
      if (UNKNOWN.equals(license)) {
        continue;
      }
      String key = KEY_LICENSE_PREFIX + license.name();
      Metric<Integer> metric = new Metric.Builder(key, license.getTitle(), ValueType.INT)
          .setDescription(license.name())
          .setDirection(Metric.DIRECTION_NONE)
          .setDomain(Metrics.DOMAIN)
          .create();
      LICENSES_METRICS.put(license, metric);
      METRICS_KEYS.add(key);
    }
  }

  private final Configuration configuration;
  private static final List<License> licensesCompliant = new ArrayList<>();
  private static final List<License> licensesNonCompliant = new ArrayList<>();

  public Metrics(Configuration configuration) {
    this.configuration = configuration;
    COMPLIANT.setHidden(configuration.getBoolean(Constants.CONFIG_HIDE_COMPLIANT).orElse(Constants.CONFIG_HIDE_COMPLIANT_DEFAULT));
    NON_COMPLIANT.setHidden(configuration.getBoolean(Constants.CONFIG_HIDE_NON_COMPLIANT).orElse(Constants.CONFIG_HIDE_NON_COMPLIANT_DEFAULT));
    UNKNOWN.setHidden(configuration.getBoolean(Constants.CONFIG_HIDE_UNKNOWN).orElse(Constants.CONFIG_HIDE_UNKNOWN_DEFAULT));
    for (License license : License.values()) {
      if (License.UNKNOWN.equals(license)) {
        continue;
      } else {
        boolean compliant = configuration.getBoolean(license.getConfig()).orElse(Boolean.valueOf(license.getDefaultValue()));
        if (compliant) {
          licensesCompliant.add(license);
        } else {
          licensesNonCompliant.add(license);
        }
      }
    }
  }

  /**
   * Calculates all metrics provided by this Sonar-Plugin based on the given Analysis.
   */
  static void calculateMetricsModule(SensorContext context, Analysis analysis) {
    calculateMetrics(context, context.fileSystem().inputFile(context.fileSystem().predicates().hasRelativePath("pom.xml")), analysis);
  }


  private static void calculateMetrics(SensorContext context, InputComponent inputComponent, Analysis analysis) {
    calculateCompliant(context, inputComponent, analysis);
    calculateNonCompliant(context, inputComponent, analysis);
    calculateUnknown(context, inputComponent, analysis);
    calculateLicense(context, inputComponent, analysis);
  }

  private static void calculateLicense(SensorContext context, InputComponent inputComponent, Analysis analysis) {
    for (License license : License.values()) {
      if (UNKNOWN.equals(license)) {
        continue;
      }
      int count = Math.toIntExact(analysis.getDependencies().stream().filter(dependency -> license.equals(dependency.getLicense())).count());
      if (count > 0) {
        context.<Integer>newMeasure().forMetric(LICENSES_METRICS.get(license)).on(inputComponent).withValue(count).save();
      }
    }
  }

  private static void calculateCompliant(SensorContext context, InputComponent inputComponent, Analysis analysis) {
    int count = Math.toIntExact(analysis.getDependencies().stream().filter(dependency -> licensesCompliant.contains(dependency.getLicense())).count());
    context.<Integer>newMeasure().forMetric(Metrics.COMPLIANT).on(inputComponent).withValue(count).save();
  }

  private static void calculateNonCompliant(SensorContext context, InputComponent inputComponent, Analysis analysis) {
    int count = Math.toIntExact(analysis.getDependencies().stream().filter(dependency -> licensesNonCompliant.contains(dependency.getLicense())).count());
    context.<Integer>newMeasure().forMetric(Metrics.NON_COMPLIANT).on(inputComponent).withValue(count).save();
  }

  private static void calculateUnknown(SensorContext context, InputComponent inputComponent, Analysis analysis) {
    int count = Math.toIntExact(analysis.getDependencies().stream().filter(dependency -> License.UNKNOWN.equals(dependency.getLicense())).count());
    context.<Integer>newMeasure().forMetric(Metrics.UNKNOWN).on(inputComponent).withValue(count).save();
  }


  @Override
  public List<Metric> getMetrics() {
    List<Metric> metrics = new ArrayList<>();
    metrics.add(Metrics.COMPLIANT);
    metrics.add(Metrics.NON_COMPLIANT);
    metrics.add(Metrics.UNKNOWN);
    for (License license : License.values()) {
      if (UNKNOWN.equals(license)) {
        continue;
      }
      metrics.add(LICENSES_METRICS.get(license));
    }
    return metrics;
  }

}