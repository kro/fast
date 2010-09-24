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
package fast.bats.europe;

import java.nio.ByteBuffer;

import silvertip.PartialMessageException;

import fast.elements.PresenceMap;
import fast.types.PresenceMapType;

public class PresenceMapFactory {
  public static PresenceMap create(ByteBuffer buffer) throws PartialMessageException {
    PresenceMap pmap = PresenceMapType.TYPE.decode(buffer);
    pmap.bind(Elements.MESSAGE_TYPE, 0);
    pmap.bind(Elements.ORDER_ID_4, 1);
    pmap.bind(Elements.PRICE_1, 2);
    pmap.bind(Elements.ORDER_ID_1, 3);
    pmap.bind(Elements.SYMBOL_1, 4);
    pmap.bind(Elements.ORDER_ID_3, 5);
    pmap.bind(Elements.TIME_MILLISECONDS, 6);
    pmap.bind(Elements.SHARES, 7);
    pmap.bind(Elements.SIDE, 8);
    pmap.bind(Elements.EXECUTION_ID_4, 9);
    pmap.bind(Elements.EXECUTION_ID_2, 10);
    pmap.bind(Elements.SYMBOL_2, 11);
    pmap.bind(Elements.EXECUTION_ID_1, 12);
    pmap.bind(Elements.EXECUTION_ID_3, 13);
    pmap.bind(Elements.TIME_SECONDS, 14);
    pmap.bind(Elements.PRICE_2, 15);
    pmap.bind(Elements.ORDER_ID_2, 16);
    pmap.bind(Elements.TEMPLATE_IDENTIFIER, 17);
    pmap.bind(Elements.DISPLAY, 18);
    pmap.bind(Elements.LONG_SHARES, 20);
    pmap.bind(Elements.LONG_PRICE_1, 21);
    pmap.bind(Elements.LONG_PRICE_2, 22);
    pmap.bind(Elements.LONG_PRICE_3, 23);
    pmap.bind(Elements.LONG_PRICE_4, 24);
    pmap.bind(fast.soup.Elements.TEXT, 19);
    pmap.bind(fast.soup.Elements.SESSION, 19);
    pmap.bind(fast.soup.Elements.REJECTED_REASON_CODE, 19);
    pmap.bind(fast.soup.Elements.SEQUENCE_NUMBER, 19);
    return pmap;
  }
}
