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
package fast;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import fast.elements.Field;

public abstract class FieldContainer {
  protected Map<Field<?>, Object> values = new HashMap<Field<?>, Object>();

  public void set(Field<?> field, Object value) {
    values.put(field, value);
  }

  public Set<Field<?>> getFields() {
    return values.keySet();
  }
  
  @SuppressWarnings("unchecked")
  public <T> T get(Field<T> field) {
    return (T) values.get(field);
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder(" ");
    for (Field<?> field : values.keySet()) {
      stringBuilder.append(field.getName() + " = " + values.get(field) + " ");
    }
    return stringBuilder.toString();
  }
}
