/**
 * PriorityQueue_test.java
 * This file contains tests for class PriorityQueue<T>.
 *
 *  @author Xinmeng Zhang, modified Prof. Gust's code
 */
package edu.northeastern.cs_5004;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.FixMethodOrder;  
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runners.MethodSorters;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * This class performs unit tests for the PriorityQueue classes.
 * Add tests for peek function
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class PriorityQueue_test {
	/**
	 * Unit tests for Message
	 */
	@Test
	public void test_0010_Message() {
		// test creation of message with null string
		Message msg = new Message();
		assertNotNull("Message created", msg);
		assertNull("Message string null", msg.getMessage());

		// test allocation of message with non-null string
		msg = new Message("Hello");
		assertNotNull("Message created", msg);
		assertNotNull("Message string", msg.getMessage());
		assertEquals("Message string", "Hello", msg.getMessage());
	}
	
	/**
	 * Unit tests for MessageQueue
	 * Test add remove element functions that may throw exceptions
	 */
	@Test
	public void test_0020_MessagePriorityQueue() {
		//// test empty queue
		// create a message queue
		PriorityQueue<Message> mpq = new PriorityQueue<Message>();
		assertNotNull("Priority queue createed", mpq);
		assertEquals("Priority queue empty", 0, mpq.size());
		
		// add null message to the queue, throw NullPointerException
		try {
			mpq.add(null);
		}
		catch(NullPointerException ex) {
			System.out.printf("NullPointerException caught: %s", ex.getMessage());
		}

		// remove message from empty queue, throw NoSuchElementException
		try {
			mpq.remove();
		}
		catch(NoSuchElementException ex) {
			System.out.printf("NoSuchElementException caught: %s", ex.getMessage());
		}
		
		// retrive element message from empty queue, throw NoSuchElementException
		try {
			mpq.element();
		}
		catch(NoSuchElementException ex) {
			System.out.printf("NoSuchElementException caught: %s", ex.getMessage());
		}

		//// test single message on queue
		// create message queue and add message
		mpq = new PriorityQueue<Message>();
		Message msg = new Message("0.0");
		mpq.add(msg, PriorityQueue.Priority.high);
		assertEquals("Prioity queue size", 1, mpq.size());
		
		// retrieve element message from queue
		assertEquals("Expected message polled", mpq.element(), msg);
		 
		// check size again after retrieve element, make sure nothing happened
		assertEquals("Prioity queue size", 1, mpq.size());
		assertEquals("Priority queue size: high",
					 1, mpq.size(PriorityQueue.Priority.high));

		// remove a message
		assertEquals("Expected message polled", mpq.remove(), msg);
		assertEquals("Priority queue empty", 0, mpq.size());

		// remove message from empty queue
		try {
			mpq.remove();
		}
		catch(NoSuchElementException ex) {
			System.out.printf("NoSuchElementException caught: %s", ex.getMessage());
		}
		assertEquals("Priority queue empty", 0, mpq.size());

		//// add 3 messages for each priority
		mpq = new PriorityQueue<Message>();
		for (PriorityQueue.Priority p : PriorityQueue.Priority.values()) {
			for (int i = 0; i < 3; i++) {
				msg = new Message(p + "." + i);
				mpq.add(msg, p);
			}
		}

		// ensure that each queue has 3 messages
		assertEquals("Expected #messages", 
					 12, mpq.size());
		assertEquals("Expected highest messages",
					 3, mpq.size(PriorityQueue.Priority.highest));
		assertEquals("Expected high messages",
				     3, mpq.size(PriorityQueue.Priority.high));
		assertEquals("Expected low messages",
				     3, mpq.size(PriorityQueue.Priority.low));
		assertEquals("Expected highest lowest",
				 	 3, mpq.size(PriorityQueue.Priority.lowest));

		// verify that the messages retrieve element and remove in the right order
		Message temp = new Message();
		for (PriorityQueue.Priority p : PriorityQueue.Priority.values()) {
			for (int i = 0; i < 3; i++) {
				temp = mpq.element();
				msg = mpq.remove();
				assertEquals(temp, msg);
				assertNotNull("Message not null", msg);
				assertEquals("Message poll order", 
						p + "." + i, msg.getMessage());
			}
		}
		assertEquals("Queue empty", 0, mpq.size());
	}
	
	/**
	 * Unit tests for MessageQueue
	 * Test offer poll peek size functions
	 */
	@Test
	public void test_0030_MessagePriorityQueue() {
		//// test empty queue
		// create a message queue
		PriorityQueue<Message> mpq = new PriorityQueue<Message>();
		assertNotNull("Priority queue createed", mpq);
		assertEquals("Priority queue empty", 0, mpq.size());

		// poll message from empty queue
		assertNull("Priority queue empty", mpq.poll());
		
		// peek message from empty queue
		assertNull("Priority queue empty", mpq.peek());

		//// test single message on queue
		// create message queue and offer message
		mpq = new PriorityQueue<Message>();
		Message msg = new Message("0.0");
		mpq.offer(msg, PriorityQueue.Priority.high);
		assertEquals("Prioity queue size", 1, mpq.size());
		
		// peek message from queue
		assertEquals("Expected message polled", mpq.peek(), msg);
		 
		// check size again after peek, make sure nothing happened
		assertEquals("Prioity queue size", 1, mpq.size());
		assertEquals("Priority queue size: high",
					 1, mpq.size(PriorityQueue.Priority.high));

		// poll a message
		assertEquals("Expected message polled", mpq.poll(), msg);
		assertEquals("Priority queue empty", 0, mpq.size());

		// poll message from empty queue
		assertNull("Priority queue empty", mpq.poll());
		assertEquals("Priority queue empty", 0, mpq.size());

		//// offer 3 messages for each priority
		mpq = new PriorityQueue<Message>();
		for (PriorityQueue.Priority p : PriorityQueue.Priority.values()) {
			for (int i = 0; i < 3; i++) {
				msg = new Message(p + "." + i);
				mpq.offer(msg, p);
			}
		}

		// ensure that each queue has 3 messages
		assertEquals("Expected #messages", 
					 12, mpq.size());
		assertEquals("Expected highest messages",
					 3, mpq.size(PriorityQueue.Priority.highest));
		assertEquals("Expected high messages",
				     3, mpq.size(PriorityQueue.Priority.high));
		assertEquals("Expected low messages",
				     3, mpq.size(PriorityQueue.Priority.low));
		assertEquals("Expected highest lowest",
				 	 3, mpq.size(PriorityQueue.Priority.lowest));

		// verify that the messages peek and poll in the right order
		Message temp = new Message();
		for (PriorityQueue.Priority p : PriorityQueue.Priority.values()) {
			for (int i = 0; i < 3; i++) {
				temp = mpq.peek();
				msg = mpq.poll();
				assertEquals(temp, msg);
				assertNotNull("Message not null", msg);
				assertEquals("Message poll order", 
						p + "." + i, msg.getMessage());
			}
		}
		assertEquals("Queue empty", 0, mpq.size());
	}
	
	/**
	 * Unit tests for MessageQueueIterator
	 */
	@Test
	public void test_0035_MessagePriorityQueueIterator() {
		//// Enqueue 3 messages for each priority
		PriorityQueue<Message> mpq = new PriorityQueue<Message>();
		for (PriorityQueue.Priority p : PriorityQueue.Priority.values()) {
			for (int i = 0; i < 3; i++) {
				Message msg = new Message(p + "." + i);
				mpq.offer(msg, p);
			}
		}
		
		// iterate over messages
		int priorityIndex = 0;
		int msgIndex = 0;
		PriorityQueue.Priority[] priorities = PriorityQueue.Priority.values();
		for (Message msg : mpq) {
			String msgStr = priorities[priorityIndex] + "." + msgIndex;
			assertEquals("PriorityQueue message", msgStr, msg.getMessage());
			if (++msgIndex == 3) {
				msgIndex = 0;
				priorityIndex++;
			}
		}
	}
	
	/**
	 * Run the tests in this class.
	 * 
	 * @param args the program arguments
	 */
	public static void main(String[] args) {
	    Result result = JUnitCore.runClasses(PriorityQueue_test.class);
	    
	    System.out.println("[Unit Test Results]");
	    System.out.println();
	    
	    if (result.getFailureCount() > 0) {
	    	System.out.println("Test failure details:");
		    for (Failure failure : result.getFailures()) {
		       System.out.println(failure.toString());
		    }
		    System.out.println();
	    }
	    
	    int passCount = result.getRunCount()-result.getFailureCount()-result.getIgnoreCount(); 
	    System.out.println("Test summary:");
	    System.out.println("* Total tests = " + result.getRunCount());
	    System.out.println("* Passed tests: " + passCount);
	    System.out.println("* Failed tests = " + result.getFailureCount());
	    System.out.println("* Inactive tests = " + result.getIgnoreCount());
	}
}
