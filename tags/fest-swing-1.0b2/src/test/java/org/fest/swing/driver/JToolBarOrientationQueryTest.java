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

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JToolBar;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.MethodInvocations;
import org.fest.swing.testing.TestWindow;

import static java.awt.BorderLayout.NORTH;
import static javax.swing.SwingConstants.HORIZONTAL;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JToolBarOrientationQuery}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = { GUI, EDT_ACTION })
public class JToolBarOrientationQueryTest {

  private static final int ORIENTATION = HORIZONTAL;

  private Robot robot;
  private MyToolBar toolBar;
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    toolBar = window.toolBar;
    robot.showWindow(window);
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnOrientationOfJToolBar() {
    toolBar.startRecording();
    assertThat(JToolBarOrientationQuery.orientationOf(toolBar)).isEqualTo(ORIENTATION);
    toolBar.requireInvoked("getOrientation");
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createNew() {
      return new MyWindow();
    }
    
    final MyToolBar toolBar = new MyToolBar();
    
    private MyWindow() {
      super(JToolBarOrientationQueryTest.class);
      setLayout(new BorderLayout());
      add(toolBar, NORTH);
    }
  }
  
  private static class MyToolBar extends JToolBar {
    private static final long serialVersionUID = 1L;

    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    MyToolBar() {
      super(ORIENTATION);
      add(new JButton("Click me"));
    }

    @Override public int getOrientation() {
      if (recording) methodInvocations.invoked("getOrientation");
      return super.getOrientation();
    }

    void startRecording() { recording = true; }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }

}