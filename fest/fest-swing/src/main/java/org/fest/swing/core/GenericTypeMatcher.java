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
 * Copyright @2007-2008 the original author or authors.
 */
package org.fest.swing.core;

import java.awt.Component;

import org.fest.swing.annotation.RunsInCurrentThread;

/**
 * Understands a <code>{@link ComponentMatcher}</code> that matches a <code>{@link Component}</code> by type and some 
 * custom search criteria.
 * @param <T> the type of <code>Component</code> supported by this matcher. 
 *
 * @author Yvonne Wang
 */
public abstract class GenericTypeMatcher<T extends Component> extends AbstractComponentMatcher {

  /** Creates a new </code>{@link GenericTypeMatcher}</code>. The component to match does not have to be showing. */
  public GenericTypeMatcher() {
    
  }

  /**
   * Creates a new </code>{@link GenericTypeMatcher}</code>.
   * @param requireShowing indicates if the component to match should be showing or not.
   */
  public GenericTypeMatcher(boolean requireShowing) {
    super(requireShowing);
  }
  
  /**
   * Verifies that the given <code>{@link Component}</code>:
   * <ol>
   * <li>Is an instance of the generic type specified in this <code>{@link ComponentMatcher}</code></li> 
   * <li>Matches some search criteria</li>
   * </ol>
   * <p>
   * <b>Note:</b> This method is <b>not</b> executed in the event dispatch thread (EDT.) Clients are responsible for 
   * invoking this method in the EDT.
   * </p>
   * @param c the <code>Component</code> to verify. 
   * @return <code>true</code> if the given <code>Component</code> is an instance of the generic type of this matcher 
   * and matches some search criteria. Otherwise, <code>false</code>. 
   */
  @RunsInCurrentThread
  public final boolean matches(Component c) {
    if (c == null) return false;
    try {
      return (isShowingMatches(c)) && isMatching(cast(c));
    } catch(ClassCastException ignored) {
      return false;
    }
  }

  @SuppressWarnings("unchecked") 
  private T cast(Component c) { return (T)c; }

  /**
   * Verifies that the given component matches some search criteria.
   * <p>
   * <b>Note:</b> Implementations of this method should <b>not</b> use the event dispatch thread (EDT.) Clients are
   * responsible for invoking this method in the EDT.
   * </p>
   * @param component the <code>Component</code> to verify.
   * @return <code>true</code> if the given component matches the defined search criteria; otherwise, 
   * <code>false</code>.
   */
  @RunsInCurrentThread
  protected abstract boolean isMatching(T component);
}
