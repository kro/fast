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
import fast.elements.Field;
import jdave.junit4.JDaveRunner;
import org.junit.runner.RunWith;
import silvertip.PartialMessageException;
import silvertip.GarbledMessageException;

import java.nio.ByteBuffer;

@RunWith(JDaveRunner.class)
public class FastPitchMessageParserSpec extends AbstractFastSpec<FastPitchMessageParser> {
    private static final int pCharWithStopBit = 240;

    public class ParseTradeLongForm {
        public void decodeSimplestTradeLongFormMessage() throws Exception {
            byte[] bytes = toByteArray(0xc0, pCharWithStopBit & 0x7f);
            assertParsing(bytes, Elements.MESSAGE_TYPE, "p");
        }

        public void decodeTradeLongFormWithSymbol1() throws Exception {
            byte[] bytes = toByteArray(0xc4, pCharWithStopBit, 82, 69, 67, 239);
            assertParsing(bytes, Elements.SYMBOL_1, "RECo");

        }
        public void decodeTradeLongFormWithTimeMillis() throws Exception {
            byte[] bytes = toByteArray(0xc1, 2, 172, pCharWithStopBit);
            assertParsing(bytes, Elements.TIME_MILLISECONDS, 300l);

        }
        public void decodeTradeLongFormWithOrderId1() throws Exception {
            byte[] bytes = toByteArray(0xc8, pCharWithStopBit, 195);
            assertParsing(bytes, Elements.ORDER_ID_1, "C");
        }
        public void decodeTradeLongFormWithOrderId4() throws Exception {
            byte[] bytes = toByteArray(0xe0, pCharWithStopBit, 115, 189);
            assertParsing(bytes, Elements.ORDER_ID_4, 14781l);

        }
        public void decodeTradeLongFormWithLongShares() throws Exception {
            byte[] bytes = toByteArray(0x40, 0x0, 0x81, pCharWithStopBit, 62, 57, 160);
            assertParsing(bytes, Elements.LONG_SHARES, Long.valueOf(1023136l));
        }

        private <T> void assertParsing(byte[] bytes, Field<T> field, T expected) 
              throws PartialMessageException, GarbledMessageException {
            Message parsedMessage = new FastPitchMessageParser().parse(ByteBuffer.wrap(bytes));
            specify(parsedMessage.get(field), must.equal(expected));
        }
    }
}
