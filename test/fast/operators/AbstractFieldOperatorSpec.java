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
package fast.operators;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;

import fast.operators.FieldOperator.Visitor;

@RunWith(JDaveRunner.class)
public abstract class AbstractFieldOperatorSpec<T extends FieldOperator<Object>> extends Specification<T> {
  public class Initialized {
    public void fieldIsPresent() {
      final Object expected = new Object();
      Object result = getOperator().apply(new Visitor<Object>() {
        private Object previousValue = null;

        @Override
        public Object decode() {
          return expected;
        }

        @Override
        public Object getPreviousValue() {
          return previousValue;
        }

        @Override
        public boolean isPresent() {
          return true;
        }

        @Override
        public void setAsPreviousValue(Object value) {
          previousValue = value;
        }
      });
      specify(result, must.equal(expected));
    }

    public void fieldIsNotPresent() {
      AbstractFieldOperatorSpec.this.fieldIsNotPresent(new Visitor<Object>() {
        private Object previousValue = null;

        @Override
        public Object decode() {
          throw new RuntimeException("Field is not present");
        }

        @Override
        public Object getPreviousValue() {
          return previousValue;
        }

        @Override
        public boolean isPresent() {
          return false;
        }

        @Override
        public void setAsPreviousValue(Object value) {
          previousValue = value;
        }
      });
    }
  }

  protected abstract T getOperator();

  protected abstract void fieldIsNotPresent(Visitor<Object> visitor);
}
