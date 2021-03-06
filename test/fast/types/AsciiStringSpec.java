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
package fast.types;

import java.nio.ByteBuffer;

import jdave.Block;
import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;

@RunWith(JDaveRunner.class)
public class AsciiStringSpec extends Specification<AsciiString> {
  public class Initialized {
    public void validWithStopBit() throws Exception {
      ByteBuffer buffer = AsciiString.TYPE.encode("Hello");
      specify(AsciiString.TYPE.decode(buffer), must.equal("Hello"));
    }

    public void throwsExceptionWhenEncodingStringThatContainsStopBit() {
      specify(new Block() {
        @Override
        public void run() throws Throwable {
          String withStopBit = new String(new byte[] { 0, 0, AsciiString.TYPE.addStopBit((byte) 0), 0 });
          AsciiString.TYPE.encode(withStopBit);
        }
      }, should.raise(IllegalArgumentException.class));
    }
  }
}
