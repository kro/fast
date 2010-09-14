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
import fast.templates.MessageTemplate;

/** Template for Login Request message as specified in Soup TCP 2.0, section
 * 2.3.1. */
public class LoginRequest extends MessageTemplate {
  public static final LoginAccepted TEMPLATE = new LoginAccepted();

  public LoginRequest() {
    add(Elements.USERNAME);
    add(Elements.PASSWORD);
    add(Elements.SESSION);
    add(Elements.SEQUENCE_NUMBER);
  }
}