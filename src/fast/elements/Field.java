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
package fast.elements;

import fast.operators.FieldOperator;
import fast.types.Type;

abstract public class Field<T> extends AbstractElem<T> {
  private final String name;

  protected Field(String name, Type<T> type, FieldOperator<T> operator, int maxLength) {
    super(type, operator, maxLength);
    this.name = name;
  }

  protected Field(String name, Type<T> type, FieldOperator<T> operator) {
    this(name, type, operator, Integer.MAX_VALUE);
  }

  public String getName() {
    return name;
  }
}
