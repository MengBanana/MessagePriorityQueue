/**
 * PriorityQueue.java
 * This file contains the declarations for class template PriorityQueue<T>.
 *
 *  @author Xinmeng Zhang
 */
package edu.northeastern.cs_5004;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.AbstractQueue;

/**
 * This class implements a priority queue for Messages, using a separate
 * MessageQueue instance for messages of each priority. 
 */
public class PriorityQueue<T> extends AbstractQueue<T> {

	/**
	 * This class implements an iterator for a queue.
	 */
	class PriorityQueueIterator implements Iterator<T> {
		/** The current queue index */
		int queueIndex = 0;
		
		/** The current queue iterator */
		Iterator<T> queueItr = queues[0].iterator();
		
		/**
		 * Returns true if another element is available.
		 * 
		 * @return true if another element is available
		 */
		public boolean hasNext() {
			while (queueIndex < queues.length) {
				// return true if iterator for current queue has another message
				if (queueItr.hasNext()) {
					return true;
				}
				// else move to next queue
				if (++queueIndex < queues.length) {
					queueItr = queues[queueIndex].iterator();
				}
			}
			return false;
		}
		
		/**
		 * Returns the next element available or null if no more elements.
		 * 
		 * @return next element available or null if no elements avaialble 
		 */
		public T next() {
			return (hasNext() ? queueItr.next() : null);
		}	
	}

	/**
	 * The array of queues for each priority
	 */
	private Queue<T>[] queues;
			
	/**
	 * The priorities for the PriorityQueue
	 */
	public enum Priority{
		highest,
		high,
		low,
		lowest
	};

	/**
	 * Create a new priority queue
	 */
	@SuppressWarnings("unchecked")
	public PriorityQueue() {
		// allocate array for queues
		int nPriorities = Priority.values().length;
		queues = (Queue<T>[]) new Queue[nPriorities];

		// allocate message queue for each priority
		for (Priority p : Priority.values()) {
			queues[p.ordinal()] = new ArrayDeque<T>();
		}
	}
	
	/**
	 * Offer an item to the queue with the lowest priority
	 *
	 * @param item the item to enqueue
	 * @return true if the item is successfully offered to the queue
	 */
	@Override
	public boolean offer(T item) {
		return offer(item, Priority.lowest);
	}

	/**
	 * Offer a message to the queue with the given priority
	 *
	 * @param item the item to item
	 * @param priority the priority of the item
	 * @return true if the item is successfully offered to the queue
	 */
	public boolean offer(T item, Priority priority) {
		assert( item != null);

		// enqueue the item on the specified queue
		return queues[priority.ordinal()].offer(item);
	}
	
	/**
	 * Add an item to the queue with the lowest priority
	 *
	 * @param item the item to enqueue
	 * @return true if the item is successfully offered to the queue
	 * @throw NullPointerException if item is null
	 */
	@Override
	public boolean add(T item) {
		return add(item, Priority.lowest); // this function will throw NullPointerException
	}

	/**
	 * Add a message to the queue with the given priority
	 *
	 * @see java.util.Queue#add()
	 * @param item the item to item
	 * @param priority the priority of the item
	 * @return true if the item is successfully offered to the queue
	 * @throw NullPointerException if item is null
	 */
	public boolean add(T item, Priority priority) {
		if (item == null) {
			throw new NullPointerException("Can't add null\n");
		}
		//IllegalStateException - if the element cannot be added at this time due to capacity restrictions
		//ClassCastException - if the class of the specified element prevents it from being added to this queue
		//NullPointerException - if the specified element is null and this queue does not permit null elements
		//IllegalArgumentException - if some property of this element prevents it from being added to this queue

		// enqueue the item on the specified queue
		return offer(item, priority);
	}

	/**
	 * Remove and return the first element of the greatest priority in the queue.
	 *
	 * @return the first element with the greatest priority
	 */
	@Override
	public T poll() {
		T msg = null;

		// dequeue message from the first non-empty queue
		for (Priority p : Priority.values()) {
			msg =  queues[p.ordinal()].poll();
			// found message to poll
			if (msg != null) {
				break;
			}
		}

		// return the message if found
		return msg;
	}
	
	/**
	 * Remove and return the first element of the greatest priority in the queue.
	 *
	 * @see java.util.AbstractQueue#remove()
	 * @return the first element with the greatest priority
	 * @throw NoSuchElementException() if this queue is empty
	 */
	@Override
	public T remove() {
		T msg = poll();
		if (msg == null) {
			throw new NoSuchElementException("Can't remove when queue is empty\n");
		}
		// return the message if found
		return msg;
	}
	
	/**
	 * Retrieves, but does not remove, the first element of the greatest priority in the queue.
	 * 
	 * @see java.util.Queue#peek()
	 * @return the first element with the greatest priority
	 */
	@Override
	public T peek() {
		T msg = null;

		// dequeue message from the first non-empty queue
		for (Priority p : Priority.values()) {
			msg =  queues[p.ordinal()].peek();
			// found message to return
			if (msg != null) {
				break;
			}
		}

		// return the message if found
		return msg;
	}
	
	/**
	 * Retrieves, but does not remove, the first element of the greatest priority in the queue.
	 * 
	 * @see java.util.Queue#element()
	 * @return the first element with the greatest priority
	 * @throw NoSuchElementException() if this queue is empty
	 */
	@Override
	public T element() {
		T msg = peek();
		if (msg == null) {
			throw new NoSuchElementException("Queue is empty, can't retrieve element\n");
		}
		// return the message if found
		return msg;
	}
	
	/**
	 * Get the current number of elements in the queue with the specified priority.
	 *
	 * @param the number of elements for the specified priority
	 */
	public int size(Priority priority) {
		return queues[priority.ordinal()].size();
	}

	/**
	 * Get the current number of elements in the queue.
	 */
	@Override
	public int size() {
		// add the size of all the queues
		int nElements = 0;
		for (Priority p : Priority.values()) {
			nElements+= size(p);
		}
		return nElements;
	}
	
	/**
	 * Return an iterator for the queue.
	 * 
	 * @return an iterator for the queue
	 */
	public Iterator<T> iterator() {
		return new PriorityQueueIterator();
	}
	

	


}
