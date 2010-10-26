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
package fast.soup.templates;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;

import fast.Message;
import fast.soup.Elements;
import fast.soup.SoupTCP2Encoder;

@RunWith(JDaveRunner.class)
public class LoginRequestEncodingSpec extends Specification<LoginRequest> {
  private final String USERNAME = "foo";
  private final String PASSWORD = "bar";
  private final String SESSION = "7";
  private final String SEQUENCE_NUMBER = "1";

  public class Initialized {
    public void loginRequestEncode() {
      ByteBuffer buffer = new SoupTCP2Encoder().encode(loginMessage());
      specify(bufferToString(buffer), must.equal(expectedRequest()));
    }

    private Message loginMessage() {
      Message message = new Message(LoginRequest.TEMPLATE);
      message.set(Elements.USERNAME, USERNAME);
      message.set(Elements.PASSWORD, PASSWORD);
      message.set(Elements.SESSION, SESSION);
      message.set(Elements.SEQUENCE_NUMBER, SEQUENCE_NUMBER);
      return message;
    }

    private String expectedRequest() {
      return LoginRequest.TEMPLATE.getPacketType() 
        + USERNAME        + "   "
        + PASSWORD        + "       " 
        + SESSION         + "         "
        + SEQUENCE_NUMBER + "         "
        + "\n";
    }

    private String bufferToString(ByteBuffer buffer) {
      buffer.flip();
      byte[] bytes = new byte[buffer.limit()];
      buffer.get(bytes);
      return new String(bytes);
    }
  }
}
