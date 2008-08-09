/*
 * Created on Aug 8, 2008
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

import javax.swing.JComboBox;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link IsJComboBoxEditableTask}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class IsJComboBoxEditableTaskTest {

  private JComboBox comboBox; 
  
  @BeforeMethod public void setUp() {
    comboBox = createMock(JComboBox.class);
  }
  
  public void shouldReturnTrueIfJComboBoxIsEditable() {
    new EasyMockTemplate(comboBox) {
      protected void expectations() {
        expect(comboBox.isEditable()).andReturn(true);
      }

      protected void codeToTest() {
        assertThat(IsJComboBoxEditableTask.isEditable(comboBox)).isTrue();
      }
    }.run();
  }
  
  public void shouldReturnFalseIfJComboBoxIsNotEditable() {
    new EasyMockTemplate(comboBox) {
      protected void expectations() {
        expect(comboBox.isEditable()).andReturn(false);
      }

      protected void codeToTest() {
        assertThat(IsJComboBoxEditableTask.isEditable(comboBox)).isFalse();
      }
    }.run();
  }
}
