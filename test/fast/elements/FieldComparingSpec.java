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

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;

@RunWith(JDaveRunner.class)
public class FieldComparingSpec extends Specification<Field<?>> {
  public class Initialized {
    public void sortIsBasedOnFieldName() {
      Field<?> field1 = field("1");
      Field<?> field2 = field("2");
      specify(field1.compareTo(field2), must.equal(field1.getName().compareTo(field2.getName())));
      specify(field2.compareTo(field1), must.equal(field2.getName().compareTo(field1.getName())));
    };
  }

  private static Field<?> field(String name) {
    return new Field<Object>(name, null, null) {
    };
  }
}
