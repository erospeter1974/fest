/*
 * Created on Oct 14, 2007
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
 * Copyright @2007-2008 the original author or authors.
 */
package org.fest.swing.monitor;

import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.*;
import static org.fest.assertions.Assertions.assertThat;

import java.awt.EventQueue;
import java.awt.Window;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.testing.TestWindow;
import org.fest.swing.testing.ToolkitStub;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link Context}</code>.
 *
 * @author Alex Ruiz
 */
public class ContextTest {

  private EventQueue eventQueue;
  private ToolkitStub toolkit;
  private WindowEventQueueMapping windowEventQueueMapping;
  private EventQueueMapping eventQueueMapping;
  private TestWindow frame;
  private Context context;

  @BeforeMethod public void setUp() {
    eventQueue = new EventQueue();
    toolkit = ToolkitStub.createNew(eventQueue);
    frame = new TestWindow(ContextTest.class);
    windowEventQueueMapping = createMock(WindowEventQueueMapping.class);
    eventQueueMapping = createMock(EventQueueMapping.class);
    createContext();
  }

  private void createContext() {
    new EasyMockTemplate(windowEventQueueMapping) {
      protected void expectations() {
        windowEventQueueMapping.addQueueFor(toolkit);
      }

      protected void codeToTest() {
        context = new Context(toolkit, windowEventQueueMapping, eventQueueMapping);
      }
    }.run();
    reset(windowEventQueueMapping);
  }

  @AfterMethod public void tearDown() {
    frame.destroy();
  }

  @Test public void shouldReturnRootWindows() {
    final TestWindow anotherFrame = new TestWindow(ContextTest.class);
    new EasyMockTemplate(windowEventQueueMapping) {
      protected void expectations() {
        expect(windowEventQueueMapping.windows()).andReturn(frameInList());
      }

      protected void codeToTest() {
        Collection<Window> rootWindows = context.rootWindows();
        assertThat(rootWindows).contains(frame);
        assertThat(rootWindows).contains(anotherFrame);
      }
    }.run();
  }

  private List<Window> frameInList() {
    List<Window> windows = new ArrayList<Window>();
    windows.add(frame);
    return windows;
  }

  @Test public void shouldReturnStoredQueue() {
    new EasyMockTemplate(eventQueueMapping) {
      protected void expectations() {
        expect(eventQueueMapping.storedQueueFor(frame)).andReturn(eventQueue);
      }

      protected void codeToTest() {
        EventQueue storedQueue = context.storedQueueFor(frame);
        assertThat(storedQueue).isSameAs(eventQueue);
      }
    }.run();
  }

  @Test public void shouldRemoveContext() {
    new EasyMockTemplate(windowEventQueueMapping) {
      protected void expectations() {
        windowEventQueueMapping.removeMappingFor(frame);
        expectLastCall().once();
      }

      protected void codeToTest() {
        context.removeContextFor(frame);
      }
    }.run();
  }

  @Test public void shouldAddContext() {
    new EasyMockTemplate(windowEventQueueMapping, eventQueueMapping) {
      protected void expectations() {
        windowEventQueueMapping.addQueueFor(frame);
        expectLastCall().once();
        eventQueueMapping.addQueueFor(frame);
        expectLastCall().once();
      }

      protected void codeToTest() {
        context.addContextFor(frame);
      }
    }.run();
  }

  @Test public void shouldReturnEventQueueForComponent() {
    new EasyMockTemplate(eventQueueMapping) {
      protected void expectations() {
        expect(eventQueueMapping.queueFor(frame)).andReturn(eventQueue);
      }

      protected void codeToTest() {
        EventQueue storedEventQueue = context.eventQueueFor(frame);
        assertThat(storedEventQueue).isSameAs(eventQueue);
      }
    }.run();
  }

  @Test public void shouldReturnAllEventQueues() {
    new EasyMockTemplate(windowEventQueueMapping, eventQueueMapping) {
      protected void expectations() {
        expect(windowEventQueueMapping.eventQueues()).andReturn(eventQueueInList());
        expect(eventQueueMapping.eventQueues()).andReturn(eventQueueInList());
      }

      protected void codeToTest() {
        Collection<EventQueue> allEventQueues = context.allEventQueues();
        assertThat(allEventQueues).containsOnly(eventQueue);
      }
    }.run();
  }

  private List<EventQueue> eventQueueInList() {
    final List<EventQueue> queues = new ArrayList<EventQueue>();
    queues.add(eventQueue);
    return queues;
  }
}
