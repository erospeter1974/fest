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

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.core.*;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.testing.TestWindow;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.EventMode.*;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.JTabbedPaneSelectTabTask.setSelectedTab;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.util.Arrays.array;
import static org.fest.util.Strings.concat;

/**
 * Tests for <code>{@link JTabbedPaneDriver}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class JTabbedPaneDriverTest {

  private Robot robot;
  private JTabbedPane tabbedPane;
  private JTabbedPaneDriver driver;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    driver = new JTabbedPaneDriver(robot);
    MyWindow window = MyWindow.createNew();
    tabbedPane = window.tabbedPane;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldSetTabDirectlyIfLocationOfTabNotFound(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    setSelectedTab(tabbedPane, 0);
    robot.waitForIdle();
    final JTabbedPaneLocation location = createMock(JTabbedPaneLocation.class);
    final int index = 1;
    driver = new JTabbedPaneDriver(robot, location);
    new EasyMockTemplate(location) {
      protected void expectations() {
        expect(location.pointAt(tabbedPane, index)).andThrow(new LocationUnavailableException("Thrown on purpose"));
      }

      protected void codeToTest() {
        driver.selectTab(tabbedPane, index);
        assertThatSelectedTabIndexIsEqualTo(index);
      }
    }.run();
  }

  @Test(groups = GUI, dataProvider = "tabIndexProvider")
  public void shouldSelectTabWithGivenIndex(int index, EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.selectTab(tabbedPane, index);
    assertThatSelectedTabIndexIsEqualTo(index);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotSelectTabWithGivenIndexIfTabbedPaneIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearAndDisableTabbedPane();
    robot.waitForIdle();
    driver.selectTab(tabbedPane, 1);
    assertThatSelectedTabIndexIsEqualTo(0);
  }

  @Test(groups = GUI, dataProvider = "tabIndexProvider")
  public void shouldSetTabWithGivenIndexDirectly(int index, EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.setTabDirectly(tabbedPane, index);
    assertThatSelectedTabIndexIsEqualTo(index);
  }

  @DataProvider(name = "tabIndexProvider")
  public Object[][] tabIndices() {
    return new Object[][] {
        { 0, AWT },
        { 0, ROBOT },
        { 1, AWT },
        { 1, ROBOT }
    };
  }

  @Test(groups = GUI, dataProvider = "indexOutOfBoundsProvider")
  public void shouldThrowErrorIfIndexOutOfBounds(int index, EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    try {
      driver.selectTab(tabbedPane, index);
      fail();
    } catch (ActionFailedException expected) {
      assertThat(expected).message().isEqualTo(concat(
        "Index <", index, "> is not within the JTabbedPane bounds of <0> and <1> (inclusive)"));
    }
  }

  @DataProvider(name = "indexOutOfBoundsProvider")
  public Object[][] indicesOutOfBounds() {
    return new Object[][] {
        { -1, AWT },
        { -1, ROBOT },
        { 2, AWT },
        { 2, ROBOT }
    };
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldSelectFirstTab(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.selectTab(tabbedPane, "First");
    assertThatSelectedTabIndexIsEqualTo(0);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldSelectSecondTab(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.selectTab(tabbedPane, "Second");
    assertThatSelectedTabIndexIsEqualTo(1);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotSelectTabWithGivenTitleIfTabbedPaneIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearAndDisableTabbedPane();
    robot.waitForIdle();
    driver.selectTab(tabbedPane, "Second");
    assertThatSelectedTabIndexIsEqualTo(0);
  }

  private void assertThatSelectedTabIndexIsEqualTo(int expected) {
    int selectedIndex = selectedIndexIn(tabbedPane);
    assertThat(selectedIndex).isEqualTo(expected);
  }

  private static int selectedIndexIn(final JTabbedPane tabbedPane) {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() {
        return tabbedPane.getSelectedIndex();
      }
    });
  }

  public void shouldReturnTabTitles() {
    assertThat(driver.tabTitles(tabbedPane)).isEqualTo(array("First", "Second"));
  }

  private void clearAndDisableTabbedPane() {
    execute(new GuiTask() {
      protected void executeInEDT() {
        tabbedPane.setSelectedIndex(0);
        tabbedPane.setEnabled(false);
      }
    });
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JTabbedPane tabbedPane = new JTabbedPane();

    static MyWindow createNew() {
      return new MyWindow();
    }

    private MyWindow() {
      super(JTabbedPaneDriverTest.class);
      tabbedPane.addTab("First", new JPanel());
      tabbedPane.addTab("Second", new JPanel());
      add(tabbedPane);
      setPreferredSize(new Dimension(300, 200));
    }
  }
}