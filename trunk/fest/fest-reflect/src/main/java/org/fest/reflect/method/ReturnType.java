/*
 * Created on Aug 17, 2007
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
 * Copyright @2007-2009 the original author or authors.
 */
package org.fest.reflect.method;

/**
 * Understands the return type of the method to invoke.
 * <p>
 * The following is an example of proper usage of this class:
 * <pre>
 *   // Equivalent to call 'person.setName("Luke")'
 *   {@link org.fest.reflect.core.Reflection#method(String) method}("setName").{@link MethodName#withParameterTypes(Class...) withParameterTypes}(String.class)
 *                    .{@link ParameterTypes#in(Object) in}(person)
 *                    .{@link Invoker#invoke(Object...) invoke}("Luke");
 * 
 *   // Equivalent to call 'person.concentrate()'
 *   {@link org.fest.reflect.core.Reflection#method(String) method}("concentrate").{@link MethodName#in(Object) in}(person).{@link Invoker#invoke(Object...) invoke}();
 *   
 *   // Equivalent to call 'person.getName()'
 *   String name = {@link org.fest.reflect.core.Reflection#method(String) method}("getName").{@link MethodName#withReturnType(Class) withReturnType}(String.class)
 *                                  .{@link ReturnType#in(Object) in}(person)
 *                                  .{@link Invoker#invoke(Object...) invoke}();   
 * </pre>
 * </p>
 * 
 * @param <T> the generic type of the method's return type. 
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class ReturnType<T> extends ReturnTypeTemplate<T> {
  
  ReturnType(Class<T> type, MethodName methodName) {
    super(type, methodName);
  }

  /**
   * Creates a new method invoker.
   * @param target the object containing the method to invoke.
   * @return the created method invoker.
   * @throws NullPointerException if the given target is <code>null</code>.
   */
  public Invoker<T> in(Object target) {
    return new Invoker<T>(methodName, target);
  }

  /**
   * Specifies the parameter types of the method to invoke. This method call is optional if the method to invoke does 
   * not take arguments.
   * @param parameterTypes the parameter types of the method to invoke.
   * @return the created parameter types holder.
   * @throws NullPointerException if the array of parameter types is <code>null</code>.
   */
  public ParameterTypes<T> withParameterTypes(Class<?>... parameterTypes) {
    return new ParameterTypes<T>(parameterTypes, methodName);
  }
}