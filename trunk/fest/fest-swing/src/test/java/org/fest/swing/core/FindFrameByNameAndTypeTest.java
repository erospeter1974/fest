/*
 * Created on Feb 2, 2008
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
package org.fest.swing.core;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.finder.WindowFinder.findFrame;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=108">Bug 108</a>.
 *
 * @author Alex Ruiz
 */
@Test(groups = { GUI, BUG })
public class FindFrameByNameAndTypeTest {

  private MyFrame frame;
  private Robot robot;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    frame = new GuiQuery<MyFrame>() {
      protected MyFrame executeInEDT() {
        return new MyFrame();
      }
    }.run();
    robot.showWindow(frame);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldNotFindFrameWhenUsingWrongName() {
    try {
      findFrame("yourFrame").using(robot);
      fail("Should not find frame");
    } catch (WaitTimedOutError expected) {}
  }

  private static class MyFrame extends TestWindow {
    private static final long serialVersionUID = 1L;

    public MyFrame() {
      super(FindFrameByNameAndTypeTest.class);
      setName("myFrame");
    }
  }
}