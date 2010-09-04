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

import java.util.HashMap;
import java.util.Map;

public class PresenceMap {
  private final byte[] pMapData;
  private final Map<Elem<?>, Integer> slots = new HashMap<Elem<?>, Integer>();

  public PresenceMap(byte[] pMapData) {
    this.pMapData = pMapData;
  }

  public boolean isPresent(int slot) {
    int offset = slot / 7;
    if (offset >= pMapData.length)
      return false;
    return (pMapData[offset] & (0x40 >> (slot % 7))) != 0;
  }

  public boolean isPresent(Elem<?> elem) {
    return isPresent(slots.get(elem));
  }
  
  public void bind(Elem<?> elem, int slot) {
    slots.put(elem, slot);
  }
}
