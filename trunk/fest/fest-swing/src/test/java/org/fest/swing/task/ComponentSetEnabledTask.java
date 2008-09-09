/*
 * Created on Aug 10, 2008
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
package org.fest.swing.task;

import java.awt.Component;

import org.fest.swing.core.Condition;
import org.fest.swing.core.GuiTask;

import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.util.Strings.concat;

/**
 * Understands a task that enables or disables a <code>{@link Component}</code>.
 *
 * @author Alex Ruiz
 */
public class ComponentSetEnabledTask {

  public static void enable(Component component) {
    setEnabled(component, true);
  }

  public static void disable(Component component) {
    setEnabled(component, false);
  }

  public static void setEnabled(final Component component, final boolean enabled) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        component.setEnabled(enabled);
      }
    }, new Condition(concat("Component's 'enabled' property is ", enabled)) {
      public boolean test() {
        return component.isEnabled() == enabled;
      }
    });
  }
}
