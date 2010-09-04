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

import fast.Dictionary;
import fast.Message;
import fast.elements.PresenceMap;

import fast.soup.TemplateIdentifier;
import fast.soup.templates.Debug;
import fast.soup.templates.EndOfSession;
import fast.soup.templates.LoginAccepted;
import fast.soup.templates.LoginRejected;
import fast.soup.templates.ServerHeartbeat;

import fast.templates.MessageTemplate;

import fast.bats.europe.templates.SequenceData;

public class FastPitchMessageParser implements MessageParser<FastPitchMessage> {
  private final Dictionary dictionary = new Dictionary();

  private Map<String, MessageTemplate> decoders = new HashMap<String, MessageTemplate>() {
    private static final long serialVersionUID = 1L;
    {
      put(TemplateIdentifier.SEQUENCE_DATA, SequenceData.TEMPLATE);
      put(TemplateIdentifier.DEBUG, Debug.TEMPLATE);
      put(TemplateIdentifier.END_OF_SESSION, EndOfSession.TEMPLATE);
      put(TemplateIdentifier.LOGIN_ACCEPTED, LoginAccepted.TEMPLATE);
      put(TemplateIdentifier.LOGIN_REJECTED, LoginRejected.TEMPLATE);
      put(TemplateIdentifier.SERVER_HEARTBEAT, ServerHeartbeat.TEMPLATE);
    }
  };

  @Override
  public FastPitchMessage parse(ByteBuffer buffer) throws PartialMessageException {
    return new FastPitchMessage(decode(buffer));
  }

  private Message decode(ByteBuffer buffer) {
    PresenceMap pmap = PresenceMapFactory.create(buffer);
    String identifier = TemplateIdentifier.ELEM.decode(buffer, pmap, dictionary);
    MessageTemplate template = decoders.get(identifier);
    if (template == null)
      throw new RuntimeException("unknown template identifer: " + identifier);
    return template.decode(buffer, pmap, dictionary);
  }
}
