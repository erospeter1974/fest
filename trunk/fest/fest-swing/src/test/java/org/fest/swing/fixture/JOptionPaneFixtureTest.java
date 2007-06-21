/*
 * Created on Feb 13, 2007
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

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import abbot.tester.ComponentTester;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showOptionDialog;
import static org.fest.assertions.Assertions.assertThat;

import static org.fest.swing.RobotFixture.robotWithNewAwtHierarchy;

import static org.fest.util.Arrays.array;

import org.fest.swing.ComponentLookupException;
import org.fest.swing.GUITest;
import org.fest.swing.RobotFixture;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JOptionPaneFixture}</code>.
 *
 * @author Alex Ruiz
 */
@GUITest public class JOptionPaneFixtureTest {

  public static class CustomWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    final JButton button = new JButton("Click me");
    
    CustomWindow() {
      setLayout(new FlowLayout());
      add(button);
    }

    void setUpMessageWithTitle(String title) {
      setUpMessage("Information", title, INFORMATION_MESSAGE);
    }
    
    void setUpMessageWithText(String message) {
      setUpMessage(message, "Title", INFORMATION_MESSAGE);
    }
    
    void setUpMessageWithOptions(final Object[] options) {
      addMouseListenerToButton(new MouseAdapter() {
        @Override public void mouseClicked(MouseEvent e) {
          showOptionDialog(CustomWindow.this, "Message", "Title", YES_NO_OPTION, QUESTION_MESSAGE, null, options,
              options[0]); 
        }
      });
    }

    void setUpInputMessage() {
      addMouseListenerToButton(new MouseAdapter() {
        @Override public void mouseClicked(MouseEvent e) {
          showInputDialog(CustomWindow.this, "Message"); 
        }
      });
    }
    
    void setUpErrorMessage() { setUpMessage(ERROR_MESSAGE); }
    void setUpInformationMessage() { setUpMessage(INFORMATION_MESSAGE); }
    void setUpWarningMessage() { setUpMessage(WARNING_MESSAGE); }
    void setUpQuestionMessage() { setUpMessage(QUESTION_MESSAGE); }
    void setUpPlainMessage() { setUpMessage(PLAIN_MESSAGE); }

    private void setUpMessage(final int messageType) {
      setUpMessage("Text", "Title", messageType);
    }
    
    private void setUpMessage(final String text, final String title, final int messageType) {
      addMouseListenerToButton(new MouseAdapter() {
        @Override public void mouseClicked(MouseEvent e) {
          showMessageDialog(CustomWindow.this, text, title, messageType);
        }
      });
    }
    
    void setUpManuallyCreatedOptionPaneWithTitle(final String title) {
      addMouseListenerToButton(new MouseAdapter() {
        @Override public void mouseClicked(MouseEvent e) {
          JOptionPane optionPane = new JOptionPane("Manually Created");
          JDialog dialog = optionPane.createDialog(CustomWindow.this, title);
          dialog.setVisible(true);      
        }
      });
    }

    void addMouseListenerToButton(MouseListener l) {
      removeAllMouseListeners();
      button.addMouseListener(l);
    }
    
    private void removeAllMouseListeners() {
      for (MouseListener l : button.getMouseListeners()) button.removeMouseListener(l);
    }
  }

  private CustomWindow window;
  private RobotFixture robot;
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = new CustomWindow();
    robot.showWindow(window);
  }
  
  @Test public void shouldFindOptionPane() throws Exception {
    showErrorMessage();
    JOptionPaneFixture fixture = fixture();
    assertThat(fixture.target).isNotNull();
    fixture.button().click();
  }
  
  @Test(dependsOnMethods = "shouldFindOptionPane")
  public void shouldFindButtonWithGivenTextInOptionPane() {
    showMessageWithOptions(array("First", "Second"));
    JOptionPaneFixture fixture = fixture();
    JButtonFixture button = fixture.buttonWithText("Second");
    assertThat(button).isNotNull();
    assertThat(button.target.getText()).isEqualTo("Second");
    fixture.button().click();
  }

  @Test(dependsOnMethods = "shouldFindOptionPane")
  public void shouldFindTextComponentInOptionPane() {
    window.setUpInputMessage();
    clickWindowButton();
    JOptionPaneFixture fixture = fixture();
    JTextComponentFixture textComponentFixture = fixture.textBox();
    assertThat(textComponentFixture).isNotNull();
    fixture.button().click();
  }

  @Test(dependsOnMethods = { "shouldFindOptionPane", "shouldFindTextComponentInOptionPane" },
        expectedExceptions = ComponentLookupException.class)
  public void shouldNotFindTextComponentInOptionPaneIfNotInputMessage() {
    showErrorMessage();
    JOptionPaneFixture fixture = fixture();
    fixture.textBox();
    fixture.button().click();
  }

  @Test(dependsOnMethods = "shouldFindOptionPane")
  public void shouldPassIfMatchingTitle() {
    showMessageWithTitle("Star Wars");
    JOptionPaneFixture fixture = fixture();
    fixture.requireTitle("Star Wars");
    fixture.button().click();
  }
  
  @Test(dependsOnMethods = "shouldFindOptionPane")
  public void shouldPassIfMatchingTitleWhenOptionPaneCreatedManually() {
    window.setUpManuallyCreatedOptionPaneWithTitle("Jedi");
    clickWindowButton();
    JOptionPaneFixture fixture = fixture();
    fixture.requireTitle("Jedi");
    fixture.button().click();
  }
  
  @Test(dependsOnMethods = { "shouldFindOptionPane", "shouldPassIfMatchingTitle" },
        expectedExceptions = AssertionError.class)
  public void shouldFailIfNotMatchingTitle() {
    showMessageWithTitle("Yoda");
    JOptionPaneFixture fixture = fixture();
    fixture.requireTitle("Darth Vader");
    fixture.button().click();
  }

  @Test(dependsOnMethods = "shouldFindOptionPane")
  public void shouldPassIfMatchingOptions() {
    showMessageWithOptions(array("First", "Second"));
    JOptionPaneFixture fixture = fixture();
    fixture.requireOptions(array("First", "Second"));
    fixture.button().click();
  }

  @Test(dependsOnMethods = { "shouldFindOptionPane", "shouldPassIfMatchingOptions" },
        expectedExceptions = AssertionError.class)
  public void shouldFailIfNotMatchingOptions() {
    showMessageWithOptions(array("First", "Second"));
    JOptionPaneFixture fixture = fixture();
    fixture.requireOptions(array("Third"));
    fixture.button().click();
  }

  @Test(dependsOnMethods = "shouldFindOptionPane")
  public void shouldPassIfMatchingMessage() {
    showMessageWithText("Leia");
    JOptionPaneFixture fixture = fixture();
    fixture.requireMessage("Leia");
    fixture.button().click();
  }
  
  @Test(dependsOnMethods = { "shouldFindOptionPane", "shouldPassIfMatchingMessage" },
        expectedExceptions = AssertionError.class)
  public void shouldFailIfNotMatchingMessage() {
    showMessageWithText("Palpatine");
    JOptionPaneFixture fixture = fixture();
    fixture.requireMessage("Anakin");
    fixture.button().click();
  }
  
  @Test(dependsOnMethods = "shouldFindOptionPane")
  public void shouldPassIfExpectedAndActualMessageTypeIsError() {
    showErrorMessage();
    JOptionPaneFixture fixture = fixture();
    fixture.requireErrorMessage();
    fixture.button().click();
  }

  @Test(dependsOnMethods = { "shouldFindOptionPane", "shouldPassIfExpectedAndActualMessageTypeIsError" },
        expectedExceptions = AssertionError.class)
  public void shouldFailIfExpectedMessageTypeIsErrorAndActualIsNot() {
    showInformationMessage();
    JOptionPaneFixture fixture = fixture();
    fixture.requireErrorMessage();
    fixture.button().click();
  }

  @Test(dependsOnMethods = "shouldFindOptionPane")
  public void shouldPassIfExpectedAndActualMessageTypeIsInformation() {
    showInformationMessage();
    JOptionPaneFixture fixture = fixture();
    fixture.requireInformationMessage();
    fixture.button().click();
  }

  @Test(dependsOnMethods = { "shouldFindOptionPane", "shouldPassIfExpectedAndActualMessageTypeIsInformation" },
        expectedExceptions = AssertionError.class)
  public void shouldFailIfExpectedMessageTypeIsInformationAndActualIsNot() {
    showErrorMessage();
    JOptionPaneFixture fixture = fixture();
    fixture.requireInformationMessage();
    fixture.button().click();
  }

  @Test(dependsOnMethods = "shouldFindOptionPane")
  public void shouldPassIfExpectedAndActualMessageTypeIsWarning() {
    showWarningMessage();
    JOptionPaneFixture fixture = fixture();
    fixture.requireWarningMessage();
    fixture.button().click();
  }
  
  @Test(dependsOnMethods = { "shouldFindOptionPane", "shouldPassIfExpectedAndActualMessageTypeIsWarning" },
        expectedExceptions = AssertionError.class)
  public void shouldFailIfExpectedMessageTypeIsWarningAndActualIsNot() {
    showErrorMessage();
    JOptionPaneFixture fixture = fixture();
    fixture.requireWarningMessage();
  }

  @Test(dependsOnMethods = "shouldFindOptionPane")
  public void shouldPassIfExpectedAndActualMessageTypeIsQuestion() {
    showQuestionMessage();
    JOptionPaneFixture fixture = fixture();
    fixture.requireQuestionMessage();
    fixture.button().click();
  }
  
  @Test(dependsOnMethods = { "shouldFindOptionPane", "shouldPassIfExpectedAndActualMessageTypeIsWarning" },
        expectedExceptions = AssertionError.class)
  public void shouldFailIfExpectedMessageTypeIsQuestionAndActualIsNot() {
    showErrorMessage();
    JOptionPaneFixture fixture = fixture();
    fixture.requireQuestionMessage();
    fixture.button().click();
  }

  @Test(dependsOnMethods = "shouldFindOptionPane")
  public void shouldPassIfExpectedAndActualMessageTypeIsPlain() {
    showPlainMessage();
    JOptionPaneFixture fixture = fixture();
    fixture.requirePlainMessage();
    fixture.button().click();
  }
  
  @Test(dependsOnMethods = { "shouldFindOptionPane", "shouldPassIfExpectedAndActualMessageTypeIsWarning" },
        expectedExceptions = AssertionError.class)
  public void shouldFailIfExpectedMessageTypeIsPlainAndActualIsNot() {
    showErrorMessage();
    JOptionPaneFixture fixture = fixture();
    fixture.requirePlainMessage();
    fixture.button().click();
  }
  
  private void showMessageWithTitle(String title) {
    window.setUpMessageWithTitle(title);
    clickWindowButton();
  }
  
  private void showMessageWithText(String text) {
    window.setUpMessageWithText(text);
    clickWindowButton();
  }
  
  private void showMessageWithOptions(Object[] options) {
    window.setUpMessageWithOptions(options);
    clickWindowButton();
  }
  
  private void showErrorMessage() {
    window.setUpErrorMessage();
    clickWindowButton();
  }
  
  private void showInformationMessage() {
    window.setUpInformationMessage();
    clickWindowButton();
  }

  private void showWarningMessage() {
    window.setUpWarningMessage();
    clickWindowButton();
  }

  private void showQuestionMessage() {
    window.setUpQuestionMessage();
    clickWindowButton();
  }

  private void showPlainMessage() {
    window.setUpPlainMessage();
    clickWindowButton();
  }

  private void clickWindowButton() {
    ComponentTester.getTester(window.button).actionClick(window.button);
  }
  
  private JOptionPaneFixture fixture() {
    return new JOptionPaneFixture(robot);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
}
