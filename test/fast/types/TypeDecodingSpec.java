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

import silvertip.PartialMessageException;

@RunWith(JDaveRunner.class)
public class TypeDecodingSpec extends Specification<Type<String>> {
  public class Initialized extends Type<String> {

    private String dataString;
    private ByteBuffer data;

    public Type<String> create() {
      dataString = "ABCDEFGHIJKLMN";
      data = ByteBuffer.wrap(dataString.getBytes());
      return this;
    }

    public void decodeWithMaxLimit() throws Exception {
      final int limit = dataString.length() - 5;
      String result = decode(data, limit);
      specify(result.length(), must.equal(limit));
      specify(result, must.equal(dataString.substring(0, limit)));
    }

    public void decodeWithoutMaxLimitAndStopBitThrowsException() {
      specify(new Block() {
        @Override
        public void run() throws Throwable {
          decode(data);
        }
      }, must.raise(PartialMessageException.class));
    }

    public void decodeWithStopBit() throws Exception {
      final byte[] dataArray = data.array();
      final int stopBitIndex = dataString.length() - 3;
      dataArray[stopBitIndex] = addStopBit(dataArray[stopBitIndex]);
      String result = decode(data);
      specify(result, must.equal(dataString.substring(0, stopBitIndex + 1)));
    }

    @Override
    protected fast.types.Type.Decoder<String> createDecoder() {
      return new Decoder<String>() {
        private String str = "";

        @Override
        public String getResult() {
          return str;
        }

        @Override
        public void process(byte b) {
          str += (char) b;
        }
      };
    }

    @Override
    public ByteBuffer encode(String value) {
      throw new RuntimeException("Only decode is tested");
    }

    @Override
    public byte[] getBytes(String value) {
      throw new RuntimeException("Only decode is tested");
    }
  }
}
