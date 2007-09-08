/*
 * Created on Sep 5, 2007
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
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import static org.fest.assertions.Assertions.assertThat;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link RobotFixture}</code>.
 *
 * @author Yvonne Wang
 */
public class RobotFixtureTest {

  private RobotFixture robot;
  private MyFrame frame;
  
  @BeforeTest public void setUp() {
    robot = RobotFixture.robotWithCurrentAwtHierarchy();
    frame = new MyFrame();
    frame.setTitle(getClass().getSimpleName());
    robot.showWindow(frame);
  }
  
  @AfterTest public void tearDown() {
    robot.cleanUp();
  }
  
  @Test public void shouldShowPopupMenuInTextFieldContainingPopupMenu() {
    JPopupMenu menu = robot.showPopupMenu(frame.textBoxWithPopupMenu);
    assertThat(menu).isSameAs(frame.popupMenu);
  }

  @Test(expectedExceptions = ComponentLookupException.class)
  public void shouldNotShowPopupMenuInTextFieldNotContainingPopupMenu() {
    robot.showPopupMenu(frame.textBox);
  }

  private static class MyFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private final JPopupMenu popupMenu = new JPopupMenu("Popup Menu");
    private final JTextField textBoxWithPopupMenu = new JTextField(20);
    private final JTextField textBox = new JTextField(20);
    
    MyFrame() {
      setLayout(new FlowLayout());
      add(textBoxWithPopupMenu);
      add(textBox);
      textBoxWithPopupMenu.setComponentPopupMenu(popupMenu);
      popupMenu.add(new JMenuItem("First"));
      popupMenu.add(new JMenuItem("Second"));
    }
  }
}
