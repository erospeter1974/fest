/*
 * Created on Jul 21, 2008
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
package org.fest.swing.task;

import javax.swing.JComboBox;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.edt.CheckThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.query.JComboBoxSelectedIndexQuery.selectedIndexOf;
import static org.fest.swing.testing.TestGroups.*;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link JComboBoxSetSelectedIndexTask}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class JComboBoxSelectItemAtIndexTaskTest {

  private Robot robot;
  private JComboBox comboBox;
  private int index;

  @BeforeClass public void setUpOnce() {
    CheckThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = RobotFixture.robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    comboBox = window.comboBox;
    index = 1;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldSetSelectedIndex() {
    assertThat(selectedIndexOf(comboBox)).isNotEqualTo(index);
    JComboBoxSetSelectedIndexTask.setSelectedIndex(comboBox, index);
    robot.waitForIdle();
    assertThat(selectedIndexOf(comboBox)).isEqualTo(index);
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JComboBox comboBox = new JComboBox(array("first", "second", "third"));

    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JComboBoxSelectItemAtIndexTaskTest.class);
      addComponents(comboBox);
    }
  }
}
