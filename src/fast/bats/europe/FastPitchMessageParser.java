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
import java.util.HashMap;
import java.util.Map;

import silvertip.MessageParser;
import silvertip.PartialMessageException;
import silvertip.GarbledMessageException;

import fast.Dictionary;
import fast.Message;
import fast.elements.PresenceMap;

import fast.soup.PacketType;
import fast.soup.templates.Debug;
import fast.soup.templates.EndOfSession;
import fast.soup.templates.LoginAccepted;
import fast.soup.templates.LoginRejected;
import fast.soup.templates.ServerHeartbeat;

import fast.templates.MessageTemplate;

import fast.bats.europe.templates.SequenceData;

public class FastPitchMessageParser implements MessageParser<Message> {
  private final Dictionary dictionary = new Dictionary();

  private Map<String, MessageTemplate> decoders = new HashMap<String, MessageTemplate>() {
    private static final long serialVersionUID = 1L;
    {
      put(PacketType.SEQUENCE_DATA, SequenceData.TEMPLATE);
      put(PacketType.DEBUG, Debug.TEMPLATE);
      put(PacketType.END_OF_SESSION, EndOfSession.TEMPLATE);
      put(PacketType.LOGIN_ACCEPTED, LoginAccepted.TEMPLATE);
      put(PacketType.LOGIN_REJECTED, LoginRejected.TEMPLATE);
      put(PacketType.SERVER_HEARTBEAT, ServerHeartbeat.TEMPLATE);
    }
  };

  @Override
  public Message parse(ByteBuffer buffer) throws PartialMessageException, GarbledMessageException {
    return decode(buffer);
  }

  private Message decode(ByteBuffer buffer) throws PartialMessageException, GarbledMessageException {
    PresenceMap pmap = PresenceMapFactory.create(buffer);
    String identifier = PacketType.ELEM.decode(buffer, pmap, dictionary);
    MessageTemplate template = decoders.get(identifier);
    if (template == null) {
      buffer.reset();
      byte[] messageData = new byte[buffer.limit() - buffer.position()];
      buffer.get(messageData);
      throw new GarbledMessageException("unknown template identifer: " + identifier, messageData);
    }
    return template.decode(buffer, pmap, dictionary);
  }
}
