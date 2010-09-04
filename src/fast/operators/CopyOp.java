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
package fast.operators;

/** A copy field operator as specified in FAST Specification version 1.1,
 * section 6.3.5. */
public class CopyOp<T> implements FieldOperator<T> {
  @Override
  public T apply(Visitor<T> visitor) {
    T result = null;
    if (visitor.isPresent()) {
      result = visitor.decode();
      visitor.setAsPreviousValue(result);
    } else {
      result = visitor.getPreviousValue();
    }
    return result;
  }
}