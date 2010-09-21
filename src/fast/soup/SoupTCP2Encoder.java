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
package fast.soup;

import java.nio.ByteBuffer;

import fast.Encoder;
import fast.FieldContainer;
import fast.Message;
import fast.elements.Field;

public class SoupTCP2Encoder implements Encoder {
  @Override
  public ByteBuffer encode(Message message) {
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    message.encode(buffer, this);
    buffer.put((byte) '\n');
    return buffer;
  }

  @Override
  public void encode(FieldContainer container, Field<?> field, ByteBuffer buffer) {
    container.addBytes(field, buffer);
  }
}
