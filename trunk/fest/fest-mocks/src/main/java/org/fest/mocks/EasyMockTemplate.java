/*
 * Created on Apr 22, 2007
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Copyright @2007 the original author or authors.
 */
package org.fest.mocks;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;

/**
 * Understands a template for usage of <a href="http://www.easymock.org/" target="_blank">EasyMock</a> mocks.
 * 
 * @author Alex Ruiz
 */
public abstract class EasyMockTemplate {

  /** Mock objects managed by this template */
  private final List<Object> mocks = new ArrayList<Object>();
    
  /**
   * Constructor.
   * @param mocks the mocks for this template to manage.
   * @throws IllegalArgumentException if the list of mock objects is <code>null</code> or empty.
   * @throws IllegalArgumentException if any of the given mocks is <code>null</code>.
   * @throws IllegalArgumentException if any of the given mocks is not a mock.
   */ 
  public EasyMockTemplate(Object... mocks) {
    if (mocks == null) throw new IllegalArgumentException("The list of mock objects should not be null");
    if (mocks.length == 0) throw new IllegalArgumentException("The list of mock objects should not be empty");
    for (Object mock : mocks) {
      if (mock == null) throw new IllegalArgumentException("The list of mocks should not include null values");
      this.mocks.add(checkAndReturnMock(mock));
    }
  }
  
  private Object checkAndReturnMock(Object mock) {
    if (Enhancer.isEnhanced(mock.getClass())) return mock;
    if (Proxy.isProxyClass(mock.getClass())) return mock;
    throw new IllegalArgumentException(mock + " is not a mock");
  }
   
  /**
   * Encapsulates the common pattern followed when using EasyMock.
   * <ol>
   * <li>Set up expectations on the mock objects</li>
   * <li>Set the state of the mock controls to "replay"</li>
   * <li>Execute the code to test</li>
   * <li>Verify that the expectations were met</li>
   * </ol>
   * Steps 2 and 4 are considered invariant behavior while steps 1 and 3 should be implemented by subclasses of this 
   * template.
   */
  public final void run() {
    setUp();
    expectations();
    for (Object mock : mocks) replay(mock);
    codeToTest();
    for (Object mock : mocks) verify(mock);
  }

  /** Sets the expectations on the mock objects. */
  protected abstract void expectations();

  /** Executes the code that is under test. */
  protected abstract void codeToTest();

  /** Sets up the test fixture if necessary. */
  protected void setUp() {}

}
