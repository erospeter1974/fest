/*
 * Created on Jun 22, 2008
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
package org.fest.swing.input;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.createMock;

/**
 * Tests for <code>{@link PushEventQueueTask}</code>.
 *
 * @author Alex Ruiz 
 */
@Test
public class PushEventQueueTaskTest {

  private Toolkit toolkit;
  private DragAwareEventQueue queue;
  private PushEventQueueTask task;
  
  @BeforeMethod public void setUp() {
    toolkit = createMock(Toolkit.class);
    queue = new DragAwareEventQueue(toolkit, 0l, createMock(AWTEventListener.class));
    task = new PushEventQueueTask(toolkit, queue);
  }
  
  public void shouldReplaceEventQueue() {
    final EventQueue eventQueue = createMock(EventQueue.class);
    new EasyMockTemplate(toolkit) {
      protected void expectations() {
        expect(toolkit.getSystemEventQueue()).andReturn(eventQueue);
        eventQueue.push(queue);
        expectLastCall().once();
      }

      protected void codeToTest() {
        task.run();
      }
    }.run();
  }
}
