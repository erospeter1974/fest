/*
 * Created on Aug 10, 2008
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

import javax.swing.JScrollBar;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.TestGroups.EDT_QUERY;

/**
 * Tests for <code>{@link JScrollBarValueQuery}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = EDT_QUERY)
public class JScrollBarValueQueryTest {

  private JScrollBar scrollBar;
  private int value;
  private JScrollBarValueQuery query;

  @BeforeMethod public void setUp() {
    scrollBar = createMock(JScrollBar.class);
    value = 8;
    query = new JScrollBarValueQuery(scrollBar);
  }
  
  public void shouldReturnValueOfJScrollBar() {
    new EasyMockTemplate(scrollBar) {
      protected void expectations() {
        expect(scrollBar.getValue()).andReturn(value);
      }

      protected void codeToTest() {
        assertThat(query.executeInEDT()).isEqualTo(value);
      }
    }.run();
  }
}
