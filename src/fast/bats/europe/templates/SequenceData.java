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
package fast.bats.europe.templates;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import silvertip.PartialMessageException;
import fast.Dictionary;
import fast.Message;
import fast.Sequence;
import fast.bats.europe.Elements;
import fast.elements.PresenceMap;
import fast.soup.PacketType;
import fast.templates.MessageTemplate;
import fast.templates.SequenceTemplate;

public class SequenceData extends MessageTemplate {
  public static final MessageTemplate TEMPLATE = new SequenceData();
  
  private Map<String, SequenceTemplate> sequenceDataTemplates = new HashMap<String, SequenceTemplate>() {
    private static final long serialVersionUID = 1L;
    {
      put("A", AddOrder.TEMPLATE);
      put("d", AddOrder.TEMPLATE);
      put("a", AddOrderLongForm.TEMPLATE);
      put("X", OrderCancel.TEMPLATE);
      put("x", OrderCancelLongForm.TEMPLATE);
      put("E", OrderExecuted.TEMPLATE);
      put("e", OrderExecutedLongForm.TEMPLATE);
      put("P", Trade.TEMPLATE);
      put("p", TradeLongForm.TEMPLATE);
      put("r", Trade.TEMPLATE);
      put("B", TradeBreak.TEMPLATE);
    }
  };
  
  private SequenceData() {
    super(PacketType.SEQUENCE_DATA);
    add(Elements.TIME_SECONDS);
    add(Elements.TIME_MILLISECONDS);
    add(Elements.MESSAGE_TYPE);
  }
  
  @Override
  public Message decode(ByteBuffer buffer, PresenceMap pmap, Dictionary dictionary) throws PartialMessageException {
    Message message = super.decode(buffer, pmap, dictionary);
    message.addSequence(marketDataSequence(buffer, pmap, message, dictionary));
    return message;
  }

  private Sequence marketDataSequence(ByteBuffer buffer, PresenceMap pmap, Message message, Dictionary dictionary)
      throws PartialMessageException {
    SequenceTemplate template = sequenceDataTemplates.get(messageType(message));
    if (template == null)
      throw new RuntimeException("unknown message type: \"" + messageType(message) + "\"");
    return template.decode(buffer, pmap, dictionary);
  }

  private String messageType(Message message) {
    return message.<String> get(Elements.MESSAGE_TYPE);
  }
}
