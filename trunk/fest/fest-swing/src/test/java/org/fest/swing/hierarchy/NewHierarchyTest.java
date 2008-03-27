/*
 * Created on Nov 12, 2007
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
package org.fest.swing.hierarchy;

import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.listener.WeakEventListener;
import org.fest.swing.testing.TestFrame;
import org.fest.swing.testing.ToolkitStub;

import static java.awt.AWTEvent.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link NewHierarchy}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class NewHierarchyTest {

  private static final long EVENT_MASK = WINDOW_EVENT_MASK | COMPONENT_EVENT_MASK;
  
  private ToolkitStub toolkit;
  private WindowFilter filter;
  private CustomFrame frame;

  @BeforeMethod public void setUp() {
    toolkit = new ToolkitStub();
    frame = new CustomFrame(getClass());
    frame.display();
    filter = new WindowFilter();
  }

  @AfterMethod public void tearDown() {
    frame.destroy();
  }

  @Test public void shouldIgnoreExistingComponentsAndAddTransientWindowListenerToToolkit() {
    new NewHierarchy(toolkit, filter, true);
    assertThat(filter.isFiltered(frame)).isTrue();
    assertThatTransientWindowListenerWasAddedToToolkit();
  }

  @Test public void shouldNotIgnoreExistingComponentsAndAddTransientWindowListenerToToolkit() {
    new NewHierarchy(toolkit, filter, false);
    assertThat(filter.isFiltered(frame)).isFalse();
    assertThatTransientWindowListenerWasAddedToToolkit();
  }

  private void assertThatTransientWindowListenerWasAddedToToolkit() {
    List<WeakEventListener> eventListeners = toolkit.eventListenersUnderEventMask(EVENT_MASK, WeakEventListener.class);
    assertThat(eventListeners).hasSize(1);
    WeakEventListener weakEventListener = eventListeners.get(0);
    assertThat(weakEventListener.underlyingListener()).isInstanceOf(TransientWindowListener.class);
  }

  @Test(dependsOnMethods = "shouldIgnoreExistingComponentsAndAddTransientWindowListenerToToolkit")
  public void shouldReturnNoChildrenIfComponentIsFiltered() {
    NewHierarchy hierarchy = new NewHierarchy(toolkit, filter, true);
    assertThat(hierarchy.childrenOf(frame)).isEmpty();
  }

  @Test(dependsOnMethods = "shouldNotIgnoreExistingComponentsAndAddTransientWindowListenerToToolkit")
  public void shouldReturnUnfilteredChildrenOfUnfilteredComponent() {
    NewHierarchy hierarchy = new NewHierarchy(toolkit, filter, false);
    filter.filter(frame.textField);
    assertThat(hierarchy.childrenOf(frame.getContentPane())).containsOnly(frame.comboBox);
  }

  @Test(dependsOnMethods = "shouldIgnoreExistingComponentsAndAddTransientWindowListenerToToolkit")
  public void shouldNotContainFilteredComponent() {
    NewHierarchy hierarchy = new NewHierarchy(toolkit, filter, true);
    assertThat(hierarchy.contains(frame)).isFalse();
  }

  @Test(dependsOnMethods = "shouldNotIgnoreExistingComponentsAndAddTransientWindowListenerToToolkit")
  public void shouldContainUnfilteredComponent() {
    NewHierarchy hierarchy = new NewHierarchy(toolkit, filter, false);
    assertThat(hierarchy.contains(frame)).isTrue();
  }

  @Test(dependsOnMethods = "shouldIgnoreExistingComponentsAndAddTransientWindowListenerToToolkit")
  public void shouldNotContainFilteredWindowsInRootWindows() {
    NewHierarchy hierarchy = new NewHierarchy(toolkit, filter, true);
    assertThat(hierarchy.roots()).excludes(frame);
  }

  @Test(dependsOnMethods = "shouldNotIgnoreExistingComponentsAndAddTransientWindowListenerToToolkit")
  public void shouldContainUnfilteredWindowsInRootWindows() {
    NewHierarchy hierarchy = new NewHierarchy(toolkit, filter, false);
    assertThat(hierarchy.roots()).contains(frame);
  }

  // TODO Test method dispose(Window)

  private static class CustomFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    final JTextField textField = new JTextField(20);
    final JComboBox comboBox = new JComboBox(array("One", "Two"));

    public CustomFrame(Class<?> testClass) {
      super(testClass);
      add(textField);
      add(comboBox);
    }
  }
}