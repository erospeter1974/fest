/*
 * Created on Feb 23, 2008
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

import java.awt.Window;

/**
 * Understands activating a <code>{@link Window}</code>. "Activate" means that the given window gets the keyboard focus.
 *
 * @author Alex Ruiz
 */
class ActivateWindowTask implements Runnable {

  private final Window w;

  /**
   * Creates a new </code>{@link ActivateWindowTask}</code>.
   * @param w the <code>Window</code> to activate.
   */
  ActivateWindowTask(Window w) {
    this.w = w;
  }

  /** Activates this task's <code>{@link Window}</code> */
  public void run() {
    // FIXME figure out why two are sometimes needed
    w.toFront(); w.toFront();
  }
}
