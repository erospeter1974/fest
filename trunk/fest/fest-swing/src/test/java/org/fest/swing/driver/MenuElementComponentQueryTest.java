/*
 * Created on Aug 22, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.driver;

import java.awt.Component;

import javax.swing.MenuElement;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.testing.TestGroups;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link MenuElementComponentQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = TestGroups.EDT_ACTION)
public class MenuElementComponentQueryTest {

  private MenuElement element;
  private Component component;
  private MenuElementComponentQuery query;
  
  @BeforeMethod public void setUp() {
    element = createMock(MenuElement.class);
    component = createMock(Component.class);
    query = new MenuElementComponentQuery(element);
  }
  
  public void shouldReturnComponentOfMenuElement() {
    new EasyMockTemplate(element) {
      protected void expectations() {
        expect(element.getComponent()).andReturn(component);
      }

      protected void codeToTest() {
        assertThat(query.executeInEDT()).isSameAs(component);
      }
    }.run();
  }
}
