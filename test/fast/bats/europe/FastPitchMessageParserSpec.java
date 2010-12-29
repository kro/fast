/*
 *
 *  Copyright 2010 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package fast.bats.europe;

import fast.AbstractFastSpec;
import fast.Message;
import fast.elements.Elem;
import fast.elements.Field;
import fast.elements.PresenceMap;
import jdave.junit4.JDaveRunner;
import org.junit.runner.RunWith;
import silvertip.PartialMessageException;
import silvertip.GarbledMessageException;

import java.nio.ByteBuffer;

@RunWith(JDaveRunner.class)
public class FastPitchMessageParserSpec extends AbstractFastSpec<FastPitchMessageParser> {
  private static final int STOP_BIT = 0x80;
  private static final int P_WITH_STOP_BIT = 'p' | STOP_BIT;

  public class ParseTradeLongForm {
    public void decodeSimplestTradeLongFormMessage() throws Exception {
      byte[] pMap = constructPresenceMap(Elements.MESSAGE_TYPE);
      byte[] bytes = toByteArray(pMap[0], P_WITH_STOP_BIT);
      assertParsing(bytes, Elements.MESSAGE_TYPE, "p");
    }

    public void decodeTradeLongFormWithSymbol1() throws Exception {
      byte[] pMap = constructPresenceMap(Elements.MESSAGE_TYPE, Elements.SYMBOL_1);
      byte[] bytes = toByteArray(pMap[0], P_WITH_STOP_BIT, 'R', 'E', 'C', 'o' | STOP_BIT);
      assertParsing(bytes, Elements.SYMBOL_1, "RECo");

    }

    public void decodeTradeLongFormWithTimeMillis() throws Exception {
      byte[] pMap = constructPresenceMap(Elements.MESSAGE_TYPE, Elements.TIME_MILLISECONDS);
      byte[] bytes = toByteArray(pMap[0], 2, 44 | STOP_BIT, P_WITH_STOP_BIT);
      assertParsing(bytes, Elements.TIME_MILLISECONDS, (2 << 7) + 44l);

    }

    public void decodeTradeLongFormWithOrderId1() throws Exception {
      byte[] pMap = constructPresenceMap(Elements.MESSAGE_TYPE, Elements.ORDER_ID_1);
      byte[] bytes = toByteArray(pMap[0], P_WITH_STOP_BIT, 'C' | STOP_BIT);
      assertParsing(bytes, Elements.ORDER_ID_1, "C");
    }

    public void decodeTradeLongFormWithOrderId4() throws Exception {
      byte[] pMap = constructPresenceMap(Elements.ORDER_ID_4, Elements.MESSAGE_TYPE);
      byte[] bytes = toByteArray(pMap[0], P_WITH_STOP_BIT, 115, 61 | STOP_BIT);
      assertParsing(bytes, Elements.ORDER_ID_4, (115 << 7) + 61l);

    }


    public void decodeTradeLongFormWithLongShares() throws Exception {
      byte[] pMap = constructPresenceMap(Elements.MESSAGE_TYPE, Elements.LONG_SHARES);
      byte[] bytes = toByteArray(pMap[0], pMap[1], pMap[2], P_WITH_STOP_BIT, 62, 57, 32 | STOP_BIT);
      assertParsing(bytes, Elements.LONG_SHARES, ((62l << 7) << 7) + (57 << 7) + 32l);
    }

    private <T> void assertParsing(byte[] bytes, Field<T> field, T expected) throws Exception {
      Message parsedMessage = new FastPitchMessageParser().parse(ByteBuffer.wrap(bytes));
      specify(parsedMessage.get(field), must.equal(expected));
    }
  }

  private static byte[] constructPresenceMap(Elem... elems) throws PartialMessageException {
    byte[] emptyData = new byte[calculateNeededPresenceMapSize(elems)];
    stopWithStopBit(emptyData);
    PresenceMap presenceMap = PresenceMapFactory.create(ByteBuffer.wrap(emptyData));
    for (Elem elem : elems)
      presenceMap.setPresent(elem);
    byte[] bytes = presenceMap.getBytes();
    stopWithStopBit(bytes);
    return bytes;
  }

  private static int calculateNeededPresenceMapSize(Elem[] elems) throws PartialMessageException {
    PresenceMap slotsMap = PresenceMapFactory.create(ByteBuffer.wrap(toByteArray(STOP_BIT)));
    int maxSlot = 1;
    for (Elem elem : elems) {
      maxSlot = Math.max(maxSlot, slotsMap.getBinding(elem));
    }
    return (int) Math.round(Math.ceil(maxSlot / 7d));
  }

  private static void stopWithStopBit(byte[] bytes) {
    bytes[bytes.length - 1] |= STOP_BIT;
  }
}
