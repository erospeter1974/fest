/*
 * Created on Aug 6, 2007
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
package org.fest.swing;

import java.awt.Component;

/**
 * Understands a <code>{@link ComponentMatcher}</code> that matches a <code>{@link Component}</code> by type and some
 * custom search criteria.
 * @param <T> the type of <code>Component</code> supported by this matcher. 
 *
 * @author Yvonne Wang
 */
public abstract class TypedMatcher<T extends Component> implements ComponentMatcher {

  /**
   * Verifies that the given <code>{@link Component}</code> is of the specified type in this matcher and matches some 
   * search criteria.
   * @param c the <code>Component</code> to verify. 
   * @return <code>true</code> if the given <code>Component</code> matches some search criteria, otherwise 
   * <code>false</code>. 
   */
  @SuppressWarnings("unchecked") 
  public final boolean matches(Component c) {
    if (c == null) return false;
    try {
      return isMatching((T)c);
    } catch(ClassCastException ignored) {
      return false;
    }
  }

  /**
   * Verifies that the given component matches some search criteria.
   * @param component the <code>Component</code> to verify.
   * @return <code>true</code> if the given component matches the defined search criteria; otherwise <code>false</code>.
   */
  protected abstract boolean isMatching(T component);
}
