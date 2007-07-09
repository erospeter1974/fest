/*
 * Created on Jul 3, 2007
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
package org.fest.swing.fixture;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;

import abbot.tester.ComponentTester;
import static org.fest.assertions.Assertions.assertThat;

import static org.fest.swing.RobotFixture.robotWithNewAwtHierarchy;

import org.fest.swing.Condition;
import org.fest.swing.RobotFixture;
import static javax.swing.SwingUtilities.getWindowAncestor;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Understands test methods for subclasses of <code>{@link ComponentFixture}</code>.
 * @param <T> the type of component tested by this test class. 
 *
 * @author Alex Ruiz
 */
public abstract class ComponentFixtureTestCase<T extends Component> {

  protected static class MainWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    public final JButton button = new JButton("Some Button");

    private final ComponentTester tester;
    
    MainWindow() {
      setLayout(new FlowLayout());
      button.setName("button");
      add(button);
      setTitle("Testing with FEST");
      lookNative();
      tester = new ComponentTester();
    }
    
    private void lookNative() {
      try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception ignored) {}
    }
    
    public void clickButton() {
      tester.click(button);      
    }
  }

  private RobotFixture robot;
  private MainWindow window;
  private ComponentFixture<T> fixture;

  @BeforeMethod public final void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = new MainWindow();
    T target = createTarget();
    addToWindow(target);
    robot().showWindow(window, new Dimension(300, 200));
    fixture = createFixture(); 
    assertThat(fixture.target).isSameAs(target);
    afterSetUp();
    moveToUnblockMainWindow(target);
    giveFocusToButton();
  }

  private void addToWindow(T target) {
    if (addTargetToWindow()) window.add(target);
  }

  protected boolean addTargetToWindow() { return true; }
  
  private void moveToUnblockMainWindow(T target) {
    if (!targetBlocksMainWindow()) return;
    Rectangle mainWindowBounds = window.getBounds();
    Window targetWindow = windowAncestorOf(target);
    Rectangle targetBounds = targetWindow.getBounds();
    targetBounds.y = mainWindowBounds.y + mainWindowBounds.height + 10;
    targetWindow.setBounds(targetBounds);
  }

  private Window windowAncestorOf(T target) {
    if (target instanceof Window) return (Window)target;
    return getWindowAncestor(target);
  }
  
  protected abstract T createTarget();
  protected void afterSetUp() {}
  protected abstract ComponentFixture<T> createFixture();
  
  @Test public final void shouldClickComponent() {
    ComponentEvents events = ComponentEvents.attachTo(fixture.target);
    fixture.click();
    assertThat(events.clicked()).isTrue();
  }
  
  @Test public final void shouldGiveFocusToComponent() {
    T target = fixture.target;
    if (target.hasFocus()) giveFocusToButton();
    fixture.focus();
    assertThat(target.hasFocus()).isTrue();
  }

  private void giveFocusToButton() {
    if (targetBlocksMainWindow()) window.clickButton();
    giveFocusTo(window.button);
  }

  protected boolean targetBlocksMainWindow() { return false; }
  
  protected final void giveFocusTo(final Component c) {
    c.requestFocusInWindow();
    robot().wait(new Condition("component has focus") {
      public boolean test() {
        return c.hasFocus();
      }
    });
  }
  
  @Test public final void shouldPassIfComponentIsVisibleAndExpectingVisible() {
    fixture.target.setVisible(true);
    fixture.requireVisible();
  }
  
  @Test(expectedExceptions = AssertionError.class)
  public final void shouldFailIfComponentIsNotVisibleAndExpectingVisible() {
    fixture.target.setVisible(false);
    fixture.requireVisible();
  }
  
  @Test public final void shouldPassIfComponentIsNotVisibleAndExpectingNotVisible() {
    fixture.target.setVisible(false);
    fixture.requireNotVisible();
  }
  
  @Test(expectedExceptions = AssertionError.class)
  public final void shouldFailIfComponentIsVisibleAndExpectingNotVisible() {
    fixture.target.setVisible(true);
    fixture.requireNotVisible();
  }
  
  @Test public final void shouldPassIfComponentIsEnabledAndExpectingEnabled() {
    fixture.target.setEnabled(true);
    fixture.requireEnabled();
  }
  
  @Test(expectedExceptions = AssertionError.class)
  public final void shouldFailIfComponentIsDisabledAndExpectingEnabled() {
    fixture.target.setEnabled(false);
    fixture.requireEnabled();
  }
  
  @Test public final void shouldPassIfComponentIsDisabledAndExpectingDisabled() {
    fixture.target.setEnabled(false);
    fixture.requireDisabled();
  }
  
  @Test(expectedExceptions = AssertionError.class)
  public final void shouldFailIfComponentIsEnabledAndExpectingDisabled() {
    fixture.target.setEnabled(true);
    fixture.requireDisabled();
  }

  @AfterMethod public final void tearDown() {
    beforeTearDown();
    robot.cleanUp();
  }
  
  protected void beforeTearDown() {}
  
  protected final RobotFixture robot() { return robot; }
  protected final MainWindow window() { return window; }
  protected final ComponentFixture<T> fixture() { return fixture; }
}
