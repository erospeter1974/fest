/*
 * Created on Aug 5, 2008
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
package org.fest.swing.task;

import javax.swing.text.JTextComponent;

import org.fest.swing.core.GuiTask;

/**
 * Understands a task that returns the text of a <code>{@link JTextComponent}</code>.
 *
 * @author Yvonne Wang
 */
public final class GetJTextComponentTextTask extends GuiTask<String> {
  private final JTextComponent textComponent;

  /**
   * Returns the text of the given <code>{@link JTextComponent}</code>. This action is executed in the event dispatch
   * thread.
   * @param textComponent the given <code>JTextComponent</code>.
   * @return the text of the given <code>JTextComponent</code>.
   */
  public static String textOf(JTextComponent textComponent) {
    return new GetJTextComponentTextTask(textComponent).run();
  }

  private GetJTextComponentTextTask(JTextComponent textComponent) {
    this.textComponent = textComponent;
  }

  /**
   * Returns the text in this task's <code>{@link JTextComponent}</code>. This action is executed in the event dispatch
   * thread.
   * @return the text in this task's <code>JTextComponent</code>.
   */
  protected String executeInEDT() {
    return textComponent.getText();
  }
}