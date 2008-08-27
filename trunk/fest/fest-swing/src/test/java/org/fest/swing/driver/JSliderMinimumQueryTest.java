/*
 * Created on Aug 12, 2008
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

import javax.swing.JSlider;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.TestGroups.EDT_ACTION;

/**
 * Tests for <code>{@link JSliderMinimumQuery}</code>.
 *
 * @author Yvonne Wang
 */
@Test(groups = EDT_ACTION)
public class JSliderMinimumQueryTest {

  private JSlider slider;
  private int minimum;
  private JSliderMinimumQuery query;

  @BeforeMethod public void setUp() {
    slider = createMock(JSlider.class);
    minimum = 8;
    query = new JSliderMinimumQuery(slider);
  }
  
  public void shouldReturnMinimumValueOfJSlider() {
    new EasyMockTemplate(slider) {
      protected void expectations() {
        expect(slider.getMinimum()).andReturn(minimum);
      }

      protected void codeToTest() {
        assertThat(query.executeInEDT()).isEqualTo(minimum);
      }
    }.run();
  }
}
