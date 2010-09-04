/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fast.bats.europe.templates;

import fast.bats.europe.Elements;
import fast.templates.SequenceTemplate;

public class ExecutionId extends SequenceTemplate {
  public static final ExecutionId TEMPLATE = new ExecutionId();

  private ExecutionId() {
    add(Elements.EXECUTION_ID_1);
    add(Elements.EXECUTION_ID_2);
    add(Elements.EXECUTION_ID_3);
    add(Elements.EXECUTION_ID_4);
  }
}
