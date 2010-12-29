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

import fast.bats.europe.templates.SequenceData;
import jdave.Specification;
import jdave.junit4.JDaveRunner;
import org.junit.runner.RunWith;

@RunWith(JDaveRunner.class)
public class FastPitchMessageSpec extends Specification<FastPitchMessage> {
  public class OrderMessage {
    private FastPitchMessage msg;

    public FastPitchMessage create() {
      msg = new FastPitchMessage(SequenceData.TEMPLATE);
      msg.set(Elements.ORDER_ID_1, "2");
      msg.set(Elements.ORDER_ID_2, "M");
      msg.set(Elements.ORDER_ID_3, "1UOB");
      msg.set(Elements.ORDER_ID_4, 477395l);
      return msg;
    }

    public void shouldDecodeOrderId() {
      specify(msg.getOrderId(), must.equal("2M1UOB00A8CZ"));
    }
  }

  public class PriceMessage {
    private FastPitchMessage msg;

    public FastPitchMessage create() {
      msg = new FastPitchMessage(SequenceData.TEMPLATE);
      msg.set(Elements.PRICE_1, 12345l);
      msg.set(Elements.PRICE_2, "0120");
      return msg;
    }

    public void shouldDecodePrice() {
      specify(msg.getPrice(), must.equal("12345.012"));
    }
  }

  public class LongPriceMessage {
    private FastPitchMessage msg;

    public FastPitchMessage create() {
      msg = new FastPitchMessage(SequenceData.TEMPLATE);
      msg.set(Elements.LONG_PRICE_1, "000000");
      msg.set(Elements.LONG_PRICE_2, 18l);
      msg.set(Elements.LONG_PRICE_3, 6400l);
      msg.set(Elements.LONG_PRICE_4, "000");
      return msg;
    }

    public void shouldDecodePrice() {
      specify(msg.getLongPrice(), must.equal("18.64"));
    }
  }
}
