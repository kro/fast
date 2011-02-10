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

import fast.Message;
import fast.bats.europe.templates.SequenceData;
import fast.templates.MessageTemplate;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class FastPitchMessage extends Message {
  private static final int LONG_BASE_36_LEN = 6;

  public FastPitchMessage(MessageTemplate template) {
    super(template);
  }

  public String getSymbol() {
    StringBuffer buf = new StringBuffer();
    buf.append(symbol1());
    buf.append(symbol2());
    return buf.toString().trim();
  }

  private String symbol1() {
    return get(Elements.SYMBOL_1);
  }

  private String symbol2() {
    return get(Elements.SYMBOL_2);
  }

  public String getSide() {
    return get(Elements.SIDE).trim();
  }

  public String getOrderId() {
    StringBuffer buf = new StringBuffer();
    buf.append(orderId1());
    buf.append(orderId2());
    buf.append(orderId3());
    buf.append(orderId4());
    return buf.toString().trim();
  }

  private String orderId1() {
    return get(Elements.ORDER_ID_1);
  }

  private String orderId2() {
    return get(Elements.ORDER_ID_2);
  }

  private String orderId3() {
    return get(Elements.ORDER_ID_3);
  }

  private String orderId4() {
    long orderId4 = get(Elements.ORDER_ID_4);
    return encodeFixedWidthBase36String(orderId4);
  }

  public boolean hasPrice() {
    if (isLongForm())
      return get(Elements.LONG_PRICE_1) != null;
    return get(Elements.PRICE_1) != null;
  }

  public String getPrice() {
    if (isLongForm())
      return getLongFormPrice();
    return getShortFormPrice();
  }

  private boolean isLongForm() {
    return SequenceData.hasLongFormTemplate(this);
  }

  private String getShortFormPrice() {
    String price1 = String.format("%08d", get(Elements.PRICE_1));
    String price2 = get(Elements.PRICE_2);
    String price = price1 + price2;
    return price.substring(0, 6) + "." + price.substring(6, 10);
  }

  private String getLongFormPrice() {
    StringBuffer b = new StringBuffer("");
    b.append(longPrice1());
    b.append(longPrice2());
    b.append(".");
    b.append(longPrice3());
    b.append(longPrice4());
    return removeLeadingAndTrailingZeroes(b.toString()).trim();
  }

  private String longPrice1() {
    return String.format("%06d", Integer.valueOf(get(Elements.LONG_PRICE_1)));
  }

  private String longPrice2() {
    return String.format("%06d", get(Elements.LONG_PRICE_2));
  }

  private String longPrice3() {
    return String.format("%04d", get(Elements.LONG_PRICE_3));
  }

  private String longPrice4() {
    return get(Elements.LONG_PRICE_4);
  }

  public long getShares() {
    if (isLongForm())
      return get(Elements.LONG_SHARES);
    return get(Elements.SHARES);
  }

  public String getExecutionId() {
    StringBuffer buf = new StringBuffer();
    buf.append(executionId1());
    buf.append(executionId2());
    buf.append(executionId3());
    buf.append(executionId4());
    return buf.toString().trim();
  }

  private String executionId1() {
    return get(Elements.EXECUTION_ID_1);
  }

  private String executionId2() {
    return get(Elements.EXECUTION_ID_2);
  }

  private String executionId3() {
    return get(Elements.EXECUTION_ID_3);
  }

  private String executionId4() {
    long executionId4 = get(Elements.EXECUTION_ID_4);
    return encodeFixedWidthBase36String(executionId4);
  }

  public long getTimestamp() {
    return get(Elements.TIME_SECONDS) * 1000 + get(Elements.TIME_MILLISECONDS);
  }

  public DateTime getDateTime() {
    DateTimeZone timeZone = DateTimeZone.forID("Europe/London");
    DateTime midnight = new DateTime(timeZone).toDateMidnight().toDateTime();
    return midnight.plusMillis((int) getTimestamp());
  }

  @Override
  public String toString() {
    StringBuffer buf = new StringBuffer();
    buf.append(super.toString());
    if (hasPrice())
      buf.append(" ( price = " + getPrice() + ")");
    return buf.toString();
  }

  private static String encodeFixedWidthBase36String(long value) {
    byte[] bytes = new byte[LONG_BASE_36_LEN];
    for (int i = bytes.length - 1; i >= 0; i--) {
      int curLeastSignificantBase36int = (int) (value % 36);
      bytes[i] = (byte) printableChar(curLeastSignificantBase36int);
      value /= 36;
    }
    return new String(bytes);
  }

  private static int printableChar(int base36int) {
    if (base36int > 9) {
      int characterIndex = base36int - 10;
      return 'A' + characterIndex;
    } else {
      int numberIndex = base36int;
      return '0' + numberIndex;
    }
  }

  private static String removeLeadingAndTrailingZeroes(String s) {
    return s.replaceFirst("^0*", "").replaceFirst("0+\\z", "");
  }
}
