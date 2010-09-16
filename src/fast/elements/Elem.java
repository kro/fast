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
import fast.Encoder;

/** An element in a FAST message, which can be of type Presence Map, Template
 * Identifier, or a field. */
public interface Elem<T> {
  T decode(ByteBuffer buffer, PresenceMap pmap, Dictionary dictionary);
  void encode(ByteBuffer buffer, Object value, Encoder encoder);
}
