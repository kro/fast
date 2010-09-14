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

import fast.soup.Elements;
import fast.soup.PacketType;
import fast.templates.MessageTemplate;

/** Template for Login Rejected message as specified in Soup TCP 2.0, section
 * 2.2.2. */
public class LoginRejected extends MessageTemplate {
  public static final LoginRejected TEMPLATE = new LoginRejected();

  private LoginRejected() {
    super(PacketType.LOGIN_REJECTED);
    add(Elements.REJECTED_REASON_CODE);
  }
}
