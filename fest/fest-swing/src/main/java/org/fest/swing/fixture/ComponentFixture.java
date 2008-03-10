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

import java.awt.Component;

import org.fest.swing.core.Robot;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.Settings;
import org.fest.swing.core.Timeout;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.WaitTimedOutError;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.format.Formatting.format;

/**
 * Understands simulation of user events on a <code>{@link Component}</code> and verification of the state of such
 * <code>{@link Component}</code>.
 * @param <T> the type of <code>Component</code> that this fixture can manage.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class ComponentFixture<T extends Component> {

  /** Performs simulation of user events on <code>{@link #target}</code> */
  public final Robot robot;

  /** This fixture's <code>{@link Component}</code>. */
  public final T target;

  /**
   * Creates a new <code>{@link ComponentFixture}</code>.
   * @param robot performs simulation of user events on a <code>Component</code>.
   * @param type the type of the <code>Component</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching component could not be found.
   * @throws ComponentLookupException if more than one matching component is found.
   */
  public ComponentFixture(Robot robot, Class<? extends T> type) {
    this(robot, robot.finder().findByType(type, requireShowing()));
  }

  /**
   * Creates a new <code>{@link ComponentFixture}</code>.
   * @param robot performs simulation of user events on a <code>Component</code>.
   * @param name the name of the <code>Component</code> to find using the given <code>RobotFixture</code>.
   * @param type the type of the <code>Component</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching component could not be found.
   * @throws ComponentLookupException if more than one matching component is found.
   */
  public ComponentFixture(Robot robot, String name, Class<? extends T> type) {
    this(robot, robot.finder().findByName(name, type, requireShowing()));
  }

  /**
   * Returns whether showing components are the only ones participating in a component lookup. The returned value is
   * obtained from <code>{@link Settings#componentLookupScope()}</code>
   * @return <code>true</code> if only showing components can participate in a component lookup, <code>false</code>
   * otherwise.
   */
  protected static boolean requireShowing() {
    return Settings.componentLookupScope().requireShowing();
  }

  /**
   * Creates a new <code>{@link ComponentFixture}</code>.
   * @param robot performs simulation of user events on the given <code>Component</code>.
   * @param target the <code>Component</code> to be managed by this fixture.
   */
  public ComponentFixture(Robot robot, T target) {
    this.robot = robot;
    this.target = target;
  }
  
  /**
   * Simulates a user clicking this fixture's <code>{@link Component}</code>.
   * @return this fixture.
   */
  protected abstract ComponentFixture<T> click();

  /**
   * Simulates a user clicking this fixture's <code>{@link Component}</code>.
   * @param button the button to click.
   * @return this fixture.
   */
  protected abstract ComponentFixture<T> click(MouseButton button);

  /**
   * Simulates a user clicking this fixture's <code>{@link Component}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  protected abstract ComponentFixture<T> click(MouseClickInfo mouseClickInfo);

  /**
   * Simulates a user double-clicking this fixture's <code>{@link Component}</code>.
   * @return this fixture.
   */
  protected abstract ComponentFixture<T> doubleClick();

  /**
   * Simulates a user right-clicking this fixture's <code>{@link Component}</code>.
   * @return this fixture.
   */
  protected abstract ComponentFixture<T> rightClick();

  /**
   * Gives input focus to this fixture's <code>{@link Component}</code>.
   * @return this fixture.
   */
  protected abstract ComponentFixture<T> focus();

  /**
   * Simulates a user pressing and releasing the given keys on this fixture's <code>{@link Component}</code> .
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  protected abstract ComponentFixture<T> pressAndReleaseKeys(int...keyCodes);

  /**
   * Simulates a user pressing given key on this fixture's <code>{@link Component}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  protected abstract ComponentFixture<T> pressKey(int keyCode);

  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link Component}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  protected abstract ComponentFixture<T> releaseKey(int keyCode);

  /**
   * Asserts that this fixture's <code>{@link Component}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError if the managed <code>Component</code> is enabled.
   */
  protected abstract ComponentFixture<T> requireDisabled();

  /**
   * Asserts that this fixture's <code>{@link Component}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError if the managed <code>Component</code> is disabled.
   */
  protected abstract ComponentFixture<T> requireEnabled();

  /**
   * Asserts that this fixture's <code>{@link Component}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws WaitTimedOutError if the managed <code>Component</code> is never enabled.
   */
  protected abstract ComponentFixture<T> requireEnabled(Timeout timeout);

  /**
   * Asserts that this fixture's <code>{@link Component}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>Component</code> is visible.
   */
  protected abstract ComponentFixture<T> requireNotVisible();

  /**
   * Asserts that this fixture's <code>{@link Component}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>Component</code> is not visible.
   */
  protected abstract ComponentFixture<T> requireVisible();

  /**
   * Returns this fixture's <code>{@link Component}</code> casted to the given subtype.
   * @param <C> enforces that the given type is a subtype of the managed <code>Component</code>.
   * @param type the type that the managed <code>Component</code> will be casted to.
   * @return this fixture's <code>Component</code> casted to the given subtype.
   * @throws AssertionError if this fixture's <code>Component</code> is not an instance of the given type.
   */
  public final <C extends T> C targetCastedTo(Class<C> type) {
    assertThat(target).as(format(target)).isInstanceOf(type);
    return type.cast(target);
  }
}