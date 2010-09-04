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
package fast.soup;

import fast.operators.DefaultOp;

public class TemplateIdentifier extends fast.elements.TemplateIdentifier {
  public static final TemplateIdentifier ELEM = new TemplateIdentifier();

  public static final String SEQUENCE_DATA = "S";
  public static final String SERVER_HEARTBEAT = "H";
  public static final String DEBUG = "+";
  public static final String LOGIN_ACCEPTED = "A";
  public static final String LOGIN_REJECTED = "J";
  public static final String END_OF_SESSION = "Z";

  private TemplateIdentifier() {
    super(new DefaultOp<String>(SEQUENCE_DATA));
  }
}
