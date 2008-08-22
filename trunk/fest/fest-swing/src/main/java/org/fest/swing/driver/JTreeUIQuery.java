package org.fest.swing.driver;

import javax.swing.JTree;
import javax.swing.plaf.TreeUI;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns a <code>{@link JTree}</code>'s current
 * UI.
 *
 * @author Yvonne Wang
 */
class JTreeUIQuery extends GuiQuery<TreeUI> {

  private final JTree tree;

  static TreeUI uiOf(final JTree tree) {
    return execute(new JTreeUIQuery(tree));
  }

  private JTreeUIQuery(JTree tree) {
    this.tree = tree;
  }

  protected TreeUI executeInEDT() throws Throwable {
    return tree.getUI();
  }
}