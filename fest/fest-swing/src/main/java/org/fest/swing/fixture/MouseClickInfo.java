/*
 * Created on Sep 21, 2007
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

import static org.fest.swing.MouseButton.LEFT_BUTTON;
import static org.fest.swing.MouseButton.MIDDLE_BUTTON;
import static org.fest.swing.MouseButton.RIGHT_BUTTON;

import static org.fest.util.Strings.concat;

import org.fest.swing.MouseButton;

/**
 * Understands information about clicking a mouse button.
 *
 * @author Alex Ruiz
 */
public final class MouseClickInfo {

  private final MouseButton button;
  private int times;

  /**
   * Creates a new <code>{@link MouseClickInfo}</code> that specifies that the left button should clicked once.
   * @return the created click info.
   */
  public static MouseClickInfo leftButton() {
    return button(LEFT_BUTTON);
  }
  
  /**
   * Creates a new <code>{@link MouseClickInfo}</code> that specifies that the middle button should clicked once.
   * @return the created click info.
   */
  public static MouseClickInfo middleButton() {
    return button(MIDDLE_BUTTON);
  }
  
  /**
   * Creates a new <code>{@link MouseClickInfo}</code> that specifies that the right button should clicked once.
   * @return the created click info.
   */
  public static MouseClickInfo rightButton() {
    return button(RIGHT_BUTTON);
  }
  
  /**
   * Creates a new <code>{@link MouseClickInfo}</code> that specifies that the given button should clicked once.
   * @param button the mouse button to click.
   * @return the created click info.
   */
  public static MouseClickInfo button(MouseButton button) {
    return new MouseClickInfo(button, 1);
  }
  
  private MouseClickInfo(MouseButton button, int times) {
    this.button = button;
    this.times = times;
  }

  MouseButton button() { return button; }
  
  int times() { return times; }
  
  /**
   * Specifies how many times the mouse button should be clicked.
   * @param times the specified number of times to click the mouse button.
   * @return this object.
   */
  public MouseClickInfo times(int times) { 
    this.times = times;
    return this;
  }

  /**
   * Returns a <code>String</code> representation of this object.
   * @return a <code>String</code> representation of this object.
   */
  @Override public String toString() {
    StringBuilder b = new StringBuilder();
    b.append(concat(getClass().getSimpleName(), "["));
    b.append(concat("button=", button, ","));
    b.append(concat("times=", String.valueOf(times), "]"));
    return b.toString();
  }
}
