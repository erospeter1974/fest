/*
 * Created on Feb 23, 2008
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.core;

import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import static org.fest.swing.core.FocusOwnerFinder.focusOwner;

/**
 * Understands monitoring when a <code>{@link Component}</code> gets keyboard focus.
 *
 * @author Alex Ruiz
 */
final class FocusMonitor extends FocusAdapter {

  private volatile boolean focused = false;

  static FocusMonitor addFocusMonitorTo(Component c) {
    return new FocusMonitor(c);
  }

  private FocusMonitor(Component c) {
    c.addFocusListener(this);
    focused = focusOwner() == c;
  }

  @Override public void focusGained(FocusEvent e) {
    focused = true;
  }

  @Override public void focusLost(FocusEvent e) {
    focused = false;
  }

  boolean hasFocus() { return focused; }
}
