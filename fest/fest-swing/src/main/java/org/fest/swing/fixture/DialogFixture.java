/*
 * Created on Oct 20, 2006
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
 * Copyright @2006 the original author or authors.
 */
package org.fest.swing.fixture;

import java.awt.Dialog;
import java.awt.Dimension;

import static org.fest.assertions.Assertions.assertThat;

import org.fest.swing.RobotFixture;


/**
 * Understands simulation of user events and state verification of a <code>{@link Dialog}</code>.
 *
 * @author Alex Ruiz
 */
public class DialogFixture extends AbstractWindowFixture<Dialog> {

  /**
   * Creates a new </code>{@link DialogFixture}</code>. This constructor creates a new <code>{@link RobotFixture}</code>
   * containing the current AWT hierarchy.
   * @param dialogName the name of the dialog to find using the given <code>RobotFixture</code>.
   * @see org.fest.swing.ComponentFinder#findByName(String, Class)
   */
  public DialogFixture(String dialogName) {
    super(dialogName, Dialog.class);
  }
  
  /**
   * Creates a new </code>{@link DialogFixture}</code>.
   * @param robot performs simulation of user events on a <code>Dialog</code>.
   * @param dialogName the name of the dialog to find using the given <code>RobotFixture</code>.
   * @see org.fest.swing.ComponentFinder#findByName(String, Class)
   */
  public DialogFixture(RobotFixture robot, String dialogName) {
    super(robot, dialogName, Dialog.class);
  }
  
  /**
   * Creates a new </code>{@link DialogFixture}</code>. This constructor creates a new <code>{@link RobotFixture}</code>
   * containing the current AWT hierarchy.
   * @param target the target dialog.
   */
  public DialogFixture(Dialog target) {
    super(target);
  }
  
  /**
   * Creates a new </code>{@link DialogFixture}</code>.
   * @param robot performs simulation of user events on the given dialog.
   * @param target the target dialog.
   */
  public DialogFixture(RobotFixture robot, Dialog target) {
    super(robot, target);
  }

  /** {@inheritDoc} */
  public final DialogFixture show() {
    doShow();
    return this;
  }
  
  /** {@inheritDoc} */
  public final DialogFixture show(Dimension size) {
    doShow(size);
    return this;
  }

  /** {@inheritDoc} */
  public final DialogFixture click() {
    doClick();
    return this;
  }

  /** {@inheritDoc} */
  public final DialogFixture focus() {
    doFocus();
    return this;
  }

  /** {@inheritDoc} */
  public final DialogFixture resizeWidthTo(int width) {
    doResizeWidthTo(width);
    return this;
  }

  /** {@inheritDoc} */
  public final DialogFixture resizeHeightTo(int height) {
    doResizeHeightTo(height);
    return this;
  }

  /** {@inheritDoc} */
  public final DialogFixture resizeTo(Dimension size) {
    doResizeTo(size);
    return this;
  }

  /** {@inheritDoc} */
  public final DialogFixture requireSize(Dimension size) {
    assertEqualSize(size);
    return this;
  }

  /** {@inheritDoc} */
  public final DialogFixture requireVisible() {
    assertIsVisible();
    return this;
  }
  
  /** {@inheritDoc} */
  public final DialogFixture requireNotVisible() {
    assertIsNotVisible();
    return this;
  }

  /** {@inheritDoc} */
  public final DialogFixture requireEnabled() {
    assertIsEnabled();
    return this;
  }
  
  /** {@inheritDoc} */  
  public final DialogFixture requireDisabled() {
    assertIsDisabled();
    return this;
  }

  /**
   * Asserts that the target dialog is modal.
   * @return a reference to this fixture.
   * @throws AssertionError if the target dialog is not modal.
   */
  public final DialogFixture requireModal() {
    assertThat(target.isModal()).isTrue();
    return this;
  }
}
