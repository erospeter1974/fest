/*
 * Created on Mar 16, 2008
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

import javax.swing.JPopupMenu;
import javax.swing.table.JTableHeader;

import org.fest.swing.core.MouseButton;
import org.fest.swing.core.Robot;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.LocationUnavailableException;

import static org.fest.swing.core.MouseButton.LEFT_BUTTON;

/**
 * Understands simulation of user input on a <code>{@link JTableHeader}</code>. Unlike
 * <code>JTableHeaderFixture</code>, this driver only focuses on behavior present only in
 * <code>{@link JTableHeader}</code>s. This class is intended for internal use only.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JTableHeaderDriver extends JComponentDriver {

  private final JTableHeaderLocation location = new JTableHeaderLocation();

  /**
   * Creates a new </code>{@link JTableHeaderDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public JTableHeaderDriver(Robot robot) {
    super(robot);
  }

  /**
   * Clicks the column under the given index.
   * @param tableHeader the target <code>JTableHeader</code>.
   * @param columnIndex the given index.
   * @throws LocationUnavailableException if the index is out of bounds.
   */
  public void clickColumn(JTableHeader tableHeader, int columnIndex) {
    clickColumn(tableHeader, columnIndex, LEFT_BUTTON, 1);
  }


  /**
   * Clicks the column under the given index using the given mouse button the given number of times.
   * @param tableHeader the target <code>JTableHeader</code>.
   * @param columnIndex the given index.
   * @param button the mouse button to use.
   * @param times the number of times to click.
   * @throws LocationUnavailableException if the index is out of bounds.
   */
  public void clickColumn(JTableHeader tableHeader, int columnIndex, MouseButton button, int times) {
    robot.click(tableHeader, location.pointAt(tableHeader, columnIndex), button, times);
  }

  /**
   * Clicks the column which name matches the given one.
   * @param tableHeader the target <code>JTableHeader</code>.
   * @param columnName the column name to match.
   * @throws LocationUnavailableException if a column with a matching name cannot be found.
   */
  public void clickColumn(JTableHeader tableHeader, String columnName) {
    clickColumn(tableHeader, columnName, LEFT_BUTTON, 1);
  }

  /**
   * Clicks the column which name matches the given one using the given mouse button the given number of times.
   * @param tableHeader the target <code>JTableHeader</code>.
   * @param columnName the column name to match.
   * @param button the mouse button to use.
   * @param times the number of times to click.
   * @throws LocationUnavailableException if a column with a matching name cannot be found.
   */
  public void clickColumn(JTableHeader tableHeader, String columnName, MouseButton button, int times) {
    robot.click(tableHeader, location.pointAt(tableHeader, columnName), button, times);
  }

  /**
   * Shows a pop-up menu at the given column.
   * @param tableHeader the target <code>JTableHeader</code>.
   * @param columnIndex the index of the column.
   * @return the displayed pop-up menu.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   */
  public JPopupMenu showPopupMenu(JTableHeader tableHeader, int columnIndex) {
    return robot.showPopupMenu(tableHeader, location.pointAt(tableHeader, columnIndex));
  }
  
  /**
   * Shows a pop-up menu at the given column.
   * @param tableHeader the target <code>JTableHeader</code>.
   * @param columnName the name of the column.
   * @return the displayed pop-up menu.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   */
  public JPopupMenu showPopupMenu(JTableHeader tableHeader, String columnName) {
    return robot.showPopupMenu(tableHeader, location.pointAt(tableHeader, columnName));
  }
}
