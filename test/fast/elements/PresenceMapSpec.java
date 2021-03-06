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
package fast.elements;

import jdave.Block;
import jdave.Specification;
import jdave.junit4.JDaveRunner;
import org.junit.runner.RunWith;

@RunWith(JDaveRunner.class)
public class PresenceMapSpec extends Specification<PresenceMap> {
  public class Initialized {
    public void validSingleByte() {
      byte[] pMapData = {(byte) 0xc0};
      PresenceMap pMap = new PresenceMap(pMapData);
      for (int i = 0; i < 7; i++) {
        specify(pMap.isPresent(i), must.equal(i == 0));
      }
    }

    public void shouldReturnFalseIfIndexOutOfBounds() {
      byte[] pMapData = {(byte) 0x80};
      PresenceMap pMap = new PresenceMap(pMapData);
      for (int i = 0; i < pMapData.length * 2; i++) {
        specify(pMap.isPresent(i), must.equal(false));
      }
    }

    public void validMultiByte() {
      byte[] pMapData = {(byte) 0x01, (byte) 0x84};
      PresenceMap pMap = new PresenceMap(pMapData);
      for (int i = 0; i < 14; i++) {
        specify(pMap.isPresent(i), must.equal(i == 6 || i == 11));
      }
    }
  }

  public class SingleBytePresenceMap {
    public PresenceMap create() {
      return new PresenceMap(new byte[]{(byte) 0x80});
    }

    public void shouldIndicatePresenceAfterSetting() {
      context.setPresent(0);
      for (int i = 0; i < 7; i++)
        specify(context.isPresent(i), must.equal(i == 0));
    }

    public void shouldThrowRuntimeExceptionIfIndexOutOfBounds() {
      specify(new Block() {
        @Override public void run() throws Throwable {
          context.setPresent(8);
        }
      }, must.raise(RuntimeException.class));
    }
  }

  public class TwoBytePresenceMap {
    public PresenceMap create() {
      return new PresenceMap(new byte[]{(byte) 0x0, (byte) 0x80});
    }

    public void hasPresentAfterSetting() {
      context.setPresent(13);
      for (int i = 0; i < 14; i++)
        specify(context.isPresent(i), must.equal(i == 13));
    }

    public void shouldThrowRuntimeExceptionIfIndexOutOfBounds() {
      specify(new Block() {
        @Override public void run() throws Throwable {
          context.setPresent(14);
        }
      }, must.raise(RuntimeException.class));
    }
  }
}
