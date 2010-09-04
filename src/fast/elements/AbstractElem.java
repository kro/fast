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

import java.nio.ByteBuffer;

import fast.Dictionary;
import fast.operators.FieldOperator;
import fast.types.Type;

/** An element in a FAST message, which can be of type Presence Map, Template
 * Identifier, or a field. */
public abstract class AbstractElem<T> implements Elem<T> {
  private final Type<T> type;
  private final FieldOperator<T> operator;
  private final int maxLength;

  protected AbstractElem(Type<T> type, FieldOperator<T> operator, int maxLength) {
    this.type = type;
    this.operator = operator;
    this.maxLength = maxLength;
  }

  protected AbstractElem(Type<T> type, FieldOperator<T> operator) {
    this(type, operator, Integer.MAX_VALUE);
  }

  public T decode(final ByteBuffer buffer, final PresenceMap pmap, final Dictionary dictionary) {   
    return operator.apply(new FieldOperator.Visitor<T>() {
      @Override public boolean isPresent() {
        return pmap.isPresent(AbstractElem.this);
      }
      
      @Override public T decode() {
        return type.decode(buffer, maxLength);
      }
      
      @Override public T getPreviousValue() {
        return (T) dictionary.get(AbstractElem.this);
      }
      
      @Override public void setAsPreviousValue(T value) {
        dictionary.put(AbstractElem.this, value);
      }
    });
  }
}
