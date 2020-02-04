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

package io.mathan.sonar.licenses.rule;

import io.mathan.sonar.licenses.Constants;
import javax.annotation.ParametersAreNonnullByDefault;
import org.sonar.api.rule.RuleStatus;
import org.sonar.api.rule.Severity;
import org.sonar.api.server.rule.RulesDefinition;

public class UsingNonCompliantLicenses implements RulesDefinition {

  @Override
  @ParametersAreNonnullByDefault
  public void define(Context context) {
    NewRepository repo = context.createRepository(Constants.REPOSITORY_KEY, Constants.LANGUAGE_KEY);
    repo.setName("UsingNonCompliantLicenses");
    NewRule rule = repo.createRule(Constants.RULE_KEY);
    rule.addTags("security", "vulnerability");
    rule.setName("Using non compliant licenses");
    rule.setSeverity(Severity.MAJOR);
    rule.setStatus(RuleStatus.READY);
    rule.addOwaspTop10(OwaspTop10.A9);

    String description = "TODO";
    rule.setHtmlDescription(description);
    repo.done();
  }

}