/*
 * Created on Feb 25, 2008
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

import static javax.swing.SwingConstants.VERTICAL;
import static org.fest.assertions.Assertions.assertThat;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JSliderDriver}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class VerticalJSliderDriverTest extends JSliderDriverTestCase {

  int orientation() {
    return VERTICAL;
  }

  @Test public void shouldHaveGivenOrientation() {
    assertThat(slider().getOrientation()).isEqualTo(VERTICAL);
  }
}
