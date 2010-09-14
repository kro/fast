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

import org.junit.runner.RunWith;

import jdave.Block;
import jdave.junit4.JDaveRunner;
import fast.operators.FieldOperator.Visitor;

@RunWith(JDaveRunner.class)
public class NoneOpSpec extends AbstractFieldOperatorSpec<NoneOp<Object>> {
  @Override
  protected void fieldIsNotPresent(final Visitor<Object> visitor) {
    specify(new Block() {
      @Override
      public void run() throws Throwable {
        getOperator().apply(visitor);
      }
    }, should.raise(RuntimeException.class));
  }

  @Override
  protected NoneOp<Object> getOperator() {
    return new NoneOp<Object>();
  }
}
