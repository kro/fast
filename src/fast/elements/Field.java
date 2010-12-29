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
import java.util.Arrays;

import fast.operators.FieldOperator;
import fast.types.Type;

abstract public class Field<T> extends AbstractElem<T> implements Comparable<Field<?>> {
  private final String name;
  private final Type<T> type;
  private int maxLength = Integer.MAX_VALUE;

  protected Field(String name, Type<T> type, FieldOperator<T> operator, int maxLength) {
    super(type, operator, maxLength);
    this.name = name;
    this.type = type;
    this.maxLength = maxLength;
  }

  protected Field(String name, Type<T> type, FieldOperator<T> operator) {
    this(name, type, operator, Integer.MAX_VALUE);
  }

  public String getName() {
    return name;
  }

  @Override
  public int compareTo(Field<?> field) {
    return getName().compareTo(field.getName());
  }

  /**
   * @return value as bytes padded to right if maxLenght is less than
   *         Integer.MAX_VALUE
   */
  public byte[] getBytes(T value) {
    byte[] valueAsBytes = type.getBytes(value);
    if (maxLength == Integer.MAX_VALUE)
      return valueAsBytes;
    return padded(valueAsBytes);
  }

  private byte[] padded(byte[] valueAsBytes) {
    ByteBuffer buffer = ByteBuffer.allocate(maxLength);
    Arrays.fill(buffer.array(), (byte) ' ');
    buffer.put(valueAsBytes);
    return buffer.array();
  }
}
