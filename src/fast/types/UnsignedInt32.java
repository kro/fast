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
package fast.types;

import java.nio.ByteBuffer;

/** A 32-bit unsigned integer message element type as specified in FAST
 * Specification version 1.1, section 10.6.1.2. */
public class UnsignedInt32 extends Type<Long> {
  public static final UnsignedInt32 TYPE = new UnsignedInt32();
  private static final int MAX_U32_BYTES_IN_FAST = 5;

  @Override
  public ByteBuffer encode(Long value) {
    byte[] bytes = new byte[MAX_U32_BYTES_IN_FAST];
    int index = MAX_U32_BYTES_IN_FAST;
    do {
      index--;
      bytes[index] = (byte) (value & ALL_BUT_STOP_BIT_MASK);
      value = value >> 7;
    } while (value != 0);
    bytes[MAX_U32_BYTES_IN_FAST - 1] = addStopBit(bytes[MAX_U32_BYTES_IN_FAST - 1]);
    return ByteBuffer.wrap(bytes, index, MAX_U32_BYTES_IN_FAST - index);
  }

  @Override
  protected Decoder<Long> createDecoder() {
    return new Decoder<Long>() {
      long value = 0;

      @Override
      public Long getResult() {
        return value;
      }

      @Override
      public void process(byte b) {
        value = (value << 7) | b;
      }
    };
  }
}
