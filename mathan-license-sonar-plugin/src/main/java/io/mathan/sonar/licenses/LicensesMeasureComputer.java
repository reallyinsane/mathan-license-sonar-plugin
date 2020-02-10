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

import org.sonar.api.ce.measure.Component.Type;
import org.sonar.api.ce.measure.Measure;
import org.sonar.api.ce.measure.MeasureComputer;

/**
 * As the metrics are reported for the pom.xml only, aggregation has to be made with this {@link MeasureComputer}.
 */
public class LicensesMeasureComputer implements MeasureComputer {

  @Override
  public MeasureComputerDefinition define(MeasureComputerDefinitionContext defContext) {
    return defContext
        .newDefinitionBuilder()
        .setOutputMetrics(Metrics.METRICS_KEYS.toArray(new String[Metrics.METRICS_KEYS.size()]))
        .build();
  }

  @Override
  public void compute(MeasureComputerContext context) {
    if (context.getComponent().getType() != Type.FILE) {
      for (String key : Metrics.METRICS_KEYS) {
        computeCount(context, key);
      }
    }
  }

  private void computeCount(MeasureComputerContext context, String metric) {
    int count = 0;
    for (Measure measure : context.getChildrenMeasures(metric)) {
      count += measure.getIntValue();
    }
    if (count > 0) {
      context.addMeasure(metric, count);
    }
  }

}
