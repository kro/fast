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
import java.util.ArrayList;
import java.util.List;

import fast.PresenceMap;

public class PresenceMapType extends Type<PresenceMap> {
  public static final PresenceMapType TYPE = new PresenceMapType();

  @Override
  protected fast.types.Type.Decoder<PresenceMap> createDecoder() {
    return new Decoder<PresenceMap>() {
      private List<Byte> bytes = new ArrayList<Byte>();

      @Override
      public PresenceMap getResult() {
        ByteBuffer buff = ByteBuffer.allocate(bytes.size());
        for (byte b : bytes)
          buff.put(b);
        return new PresenceMap(buff.array());
      }

      @Override
      public void process(byte b) {
        bytes.add(b);
      }
    };
  }

  @Override
  public ByteBuffer encode(PresenceMap value) {
    throw new UnsupportedOperationException();
  }
}
