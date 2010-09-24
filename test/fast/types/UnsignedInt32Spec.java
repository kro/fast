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

import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;

import fast.AbstractFastSpec;

@RunWith(JDaveRunner.class)
public class UnsignedInt32Spec extends AbstractFastSpec<UnsignedInt32> {
  public class Initialized {
    public void validDecode() throws Exception {
      byte[] bytes = toByteArray(0x2, 0x61, 0x15, 0xee);
      ByteBuffer buffer = ByteBuffer.wrap(bytes);
      specify(UnsignedInt32.TYPE.decode(buffer), must.equal(5786350));
    }

    public void validEncode() {
      byte[] expectedBytes = toByteArray(0x1, 0x31, 0xba);
      ByteBuffer expected = ByteBuffer.wrap(expectedBytes);
      specify(UnsignedInt32.TYPE.encode(22714l), must.equal(expected));
    }

    public void validEncodeDecode() throws Exception {
      long value = 231343l;
      specify(UnsignedInt32.TYPE.decode(UnsignedInt32.TYPE.encode(value)), must.equal(value));
    }
  }
}
