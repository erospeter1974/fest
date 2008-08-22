/*
 * Created on Aug 7, 2008
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

import java.awt.Component;

import javax.swing.JList;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the <code>{@link Component}</code> used as
 * list renderer for a particular item in a <code>{@link JList}</code>.
 *
 * @author Alex Ruiz
 */
class JListCellRendererQuery extends GuiQuery<Component> {

  private final JList list;
  private final int index;

  static Component cellRendererIn(JList list, int index) {
    return execute(new JListCellRendererQuery(list, index));
  }

  JListCellRendererQuery(JList list, int index) {
    this.list = list;
    this.index = index;
  }

  protected Component executeInEDT() {
    Object element = list.getModel().getElementAt(index);
    return list.getCellRenderer().getListCellRendererComponent(list, element, index, true, true);
  }
}