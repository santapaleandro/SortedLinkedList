package com.pawel.santarius.sortedlinkedlist;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.jetbrains.annotations.NotNull;

/**
 * SortedLinkedList is a custom implementation of a sorted linked list. This data structure
 * automatically maintains its elements in a sorted order according to their natural ordering or by
 * a Comparator provided at list creation time. This implementation permits all elements, including
 * null.
 *
 * <p>This class provides methods to add, remove, and query elements. It also supports various
 * utility methods such as getting the size, checking if an element exists, and converting the list
 * to an array.
 *
 * <p>Note that this implementation is not synchronized. If multiple threads access a sorted linked
 * list concurrently and at least one of the threads modifies the list structurally, it must be
 * synchronized externally.
 *
 * <p>The iterators provided by this class are fail-fast: if the list is structurally modified at
 * any time after the iterator is created, in any way except through the iterator's own remove
 * method, the iterator will throw a {@link ConcurrentModificationException}.
 *
 * @param <T> the type of elements held in this collection, which must be Comparable
 * @author Pawel Santarius
 * @version 1.0
 * @since 2023-10-28
 */
public class SortedLinkedList<T extends Comparable<T>> implements Serializable, Iterable<T> {
  transient Element<T> head;
  transient Element<T> last;
  transient int size = 0;

  /**
   * Adds a new element to the list. The element is inserted into the correct position to maintain
   * the sorted order.
   *
   * @param value the value to be added
   */
  void add(T value) {
    Element<T> newElement = new Element<>(value);
    if (head == null) {
      newElement.previous = null;
      head = newElement;
      last = newElement;
    } else if (head.data.compareTo(value) >= 0) {
      newElement.previous = null;
      newElement.next = head;
      head.previous = newElement;
      head = newElement;
    } else {
      Element<T> current = head;
      while (current.next != null && current.next.data.compareTo(value) < 0) {
        current = current.next;
      }
      newElement.next = current.next;
      if (current.next != null) {
        current.next.previous = newElement;
      }
      current.next = newElement;
      newElement.previous = current;
      if (newElement.next == null) {
        last = newElement;
      }
    }
    size++;
  }

  /**
   * Adds all elements from an ArrayList to the list. Each element is inserted into the correct
   * position to maintain the sorted order.
   *
   * @param array the ArrayList containing elements to be added
   */
  void add(ArrayList<T> array) {
    if (array != null) {
      for (T t : array) {
        add(t);
      }
    }
  }

  /**
   * Adds all elements from a LinkedList to the list. Each element is inserted into the correct
   * position to maintain the sorted order.
   *
   * @param linkedList the LinkedList containing elements to be added
   */
  void add(LinkedList<T> linkedList) {
    if (linkedList != null) {
      for (T t : linkedList) {
        add(t);
      }
    }
  }

  /**
   * Adds all elements from another SortedLinkedList to this list. Each element is inserted into the
   * correct position to maintain the sorted order.
   *
   * @param list the SortedLinkedList containing elements to be added
   */
  void add(SortedLinkedList<T> list) {
    if (list != null) {
      for (int i = 0; i < list.size; i++) {
        add(list.get(i));
      }
    }
  }

  /**
   * Removes the specified element from the doubly linked list. Adjusts head or last pointers if the
   * removed element is at either end of the list. If the element is in the middle, it connects the
   * previous and next elements directly. Assumes the list and the element are not null.
   *
   * @param e the element to be removed
   */
  void remove(Element<T> e) {
    size--;
    if (size == 0) {
      head = last = null;
    } else {
      if (e == head) {
        head = e.next;
        e.next.previous = null;
      } else if (e == last) {
        last = e.previous;
        e.previous.next = null;
      } else {
        e.next.previous = e.previous;
        e.previous.next = e.next;
      }
    }
  }

  /**
   * Removes the element at the specified position in this list. This method first checks if the
   * index is within the bounds of the list, then retrieves the element at that index, and finally
   * removes the element.
   *
   * @param index the index of the element to be removed
   * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size())
   */
  public void remove(int index) {
    checkBoundsExclusive(index);
    Element<T> e = getElement(index);
    remove(e);
  }

  /**
   * Removes the first element from the list. If the list is empty, this method throws a
   * NoSuchElementException. Adjusts the head of the list to the next element, if available, and
   * decrements the size. If the list only contains one element, it removes it and sets both head
   * and last to null.
   *
   * @throws NoSuchElementException if the list is empty
   */
  void removeFirst() {
    if (size == 0) {
      throw new NoSuchElementException();
    }
    if (head.next != null) {
      head.next.previous = null;
    } else {
      last = null;
    }
    head = head.next;
    size--;
  }

  /**
   * Removes the last element from the list. If the list is empty, this method throws a
   * NoSuchElementException. Adjusts the last of the list to its previous element, if available, and
   * decrements the size. If the list only contains one element, it removes it and sets both head
   * and last to null.
   *
   * @throws NoSuchElementException if the list is empty
   */
  void removeLast() {
    if (size == 0) {
      throw new NoSuchElementException();
    }
    if (last.previous != null) {
      last.previous.next = null;
    } else {
      head = null;
    }
    last = last.previous;
    size--;
  }

  /**
   * Clears the list, removing all elements. Sets both head and last pointers to null and resets the
   * size to 0. If the list is already empty, this method does nothing.
   */
  void clear() {
    if (size > 0) {
      head = null;
      last = null;
      size = 0;
    }
  }

  /**
   * Retrieves the element at the specified position in the list. This method optimizes retrieval by
   * iterating from either the head or the last element, depending on whether the index is in the
   * first or second half of the list.
   *
   * @param n the index of the element to retrieve
   * @return the element at the specified position in this list
   */
  private Element<T> getElement(int n) {
    Element<T> e;
    if (n < size / 2) {
      e = head;
      while (n-- > 0) e = e.next;
    } else {
      e = last;
      while (++n < size) e = e.previous;
    }
    return e;
  }

  /**
   * Checks if the given index is within the valid range for existing elements in the list. The
   * valid range is exclusive, meaning the index can be from 0 to size - 1. Throws an
   * IndexOutOfBoundsException if the index is out of the valid range.
   *
   * @param index the index to check
   * @throws IndexOutOfBoundsException if index is negative or not less than the current size of the
   *     list
   */
  private void checkBoundsExclusive(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException("Index: " + index + " can not be negative number.");
    }
    if (index >= size) {
      throw new IndexOutOfBoundsException("Index: " + index + " exceeds the Size: " + size);
    }
  }

  /**
   * Retrieves the element data at the specified position in the list. This method first checks if
   * the index is within the bounds of the list and then returns the data of the element at that
   * index.
   *
   * @param index the index of the element whose data is to be returned
   * @return the data of the element at the specified position
   * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size)
   */
  public T get(int index) {
    checkBoundsExclusive(index);
    return getElement(index).data;
  }

  /**
   * Returns the index of the first occurrence of the specified element in this list, or -1 if this
   * list does not contain the element. More formally, returns the lowest index {@code i} such that
   * {@code value.equals(get(i))}, or -1 if there is no such index.
   *
   * @param value the value to search for
   * @return the index of the first occurrence of the specified element in this list, or -1 if this
   *     list does not contain the element. If the specified value is null, then the behavior of
   *     this method is unspecified and may vary depending on the implementation.
   */
  public int indexOf(T value) {
    Element<T> e = head;
    int index = 0;
    while (e != null) {
      if (e.data.equals(value)) {
        return index;
      }
      index++;
      e = e.next;
    }
    return -1;
  }

  /**
   * Returns the index of the last occurrence of the specified element in this list, or -1 if this
   * list does not contain the element. More formally, returns the highest index {@code i} such that
   * {@code value.equals(get(i))}, or -1 if there is no such index.
   *
   * @param value the value to search for
   * @return the index of the last occurrence of the specified element in this list, or -1 if this
   *     list does not contain the element. If the specified value is null, then the behavior of
   *     this method is unspecified and may vary depending on the implementation.
   */
  public int lastIndexOf(T value) {
    Element<T> e = last;
    int index = size;
    while (e != null) {
      index--;
      if (e.data.equals(value)) {
        return index;
      }
      e = e.previous;
    }
    return -1;
  }

  /**
   * Checks if the linked list contains a specified value.
   *
   * <p>Iterates through the list, comparing each element's data with the given value. The
   * comparison is equality-based, typically using the {@code equals} method.
   *
   * @param value The value to search for in the list.
   * @return {@code true} if the value is found, {@code false} otherwise.
   */
  public boolean contains(T value) {
    Element<T> e = head;
    while (e != null) {
      if (e.data.equals(value)) {
        return true;
      }
      e = e.next;
    }
    return false;
  }

  /**
   * Converts the sorted linked list to an {@link ArrayList}.
   *
   * <p>This method copies the elements of the linked list into an ArrayList, preserving the order
   * of elements. The generated ArrayList is a new copy, independent of the original list.
   *
   * @return an ArrayList containing all elements of the linked list.
   */
  public ArrayList<T> toArray() {
    ArrayList<T> array = new ArrayList<>(size);
    Element<T> e = head;
    for (int i = 0; i < size; i++) {
      array.add(e.data);
      e = e.next;
    }
    return array;
  }

  /**
   * Compares this sorted linked list with another object for equality. Two sorted linked lists are
   * considered equal if they have the same size and their corresponding elements are equal.
   *
   * @param obj the object to be compared for equality with this sorted linked list
   * @return {@code true} if the specified object is equal to this list, {@code false} otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;

    SortedLinkedList<?> other = (SortedLinkedList<?>) obj;

    if (this.size != other.size) return false;

    Element<T> currentA = this.head;
    Element<?> currentB = other.head;

    while (currentA != null && currentB != null) {
      if (!currentA.data.equals(currentB.data)) {
        return false;
      }
      currentA = currentA.next;
      currentB = currentB.next;
    }

    return currentA == null && currentB == null;
  }

  /**
   * Computes the hash code for this sorted linked list. The hash code is calculated using a formula
   * similar to that specified in {@link java.util.List#hashCode()}. The hash code is computed by
   * iterating through all elements in the list, applying a hash function, and combining the
   * results. This method ensures that lists with the same elements in the same order will have the
   * same hash code.
   *
   * @return the hash code value for this sorted linked list
   */
  @Override
  public int hashCode() {
    int result = 1;
    for (T element : this) {
      result = 31 * result + (element == null ? 0 : element.hashCode());
    }
    return result;
  }

  /**
   * Returns an iterator over elements of type {@code T}.
   *
   * @return an Iterator.
   */
  @Override
  @NotNull
  public Iterator<T> iterator() {
    return new SortedLinkedListIterator();
  }

  private class SortedLinkedListIterator implements Iterator<T> {

    private Element<T> current;

    public SortedLinkedListIterator() {
      this.current = head;
    }
    /**
     * Returns {@code true} if the iteration has more elements. (In other words, returns {@code
     * true} if {@link #next} would return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
      return current != null;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public T next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      T data = current.data;
      current = current.next;
      return data;
    }
  }

  /**
   * Performs the given action for each element of the linked list.
   *
   * <p>This method traverses the list from head to tail, applying the provided {@code Consumer}
   * action to each element's data. It's useful for iterating over the list with a lambda expression
   * or method reference.
   *
   * <p>This implementation overrides {@link Iterable#forEach(Consumer)} and provides a way to
   * access list elements in a functional style.
   *
   * @param action The action to be performed for each element, which must not be null.
   * @throws NullPointerException if the specified action is null.
   */
  @Override
  public void forEach(Consumer<? super T> action) {
    Objects.requireNonNull(action);
    for (Element<T> current = head; current != null; current = current.next) {
      action.accept(current.data);
    }
  }

  /**
   * Creates a {@link Spliterator} over the elements described by this {@code Iterable}.
   *
   * @return a {@code Spliterator} over the elements described by this {@code Iterable}.
   * @implSpec The default implementation creates an <em><a
   *     href="../util/Spliterator.html#binding">early-binding</a></em> spliterator from the
   *     iterable's {@code Iterator}. The spliterator inherits the <em>fail-fast</em> properties of
   *     the iterable's iterator.
   * @implNote The default implementation should usually be overridden. The spliterator returned by
   *     the default implementation has poor splitting capabilities, is unsized, and does not report
   *     any spliterator characteristics. Implementing classes can nearly always provide a better
   *     implementation.
   * @since 1.8
   */
  @Override
  public Spliterator<T> spliterator() {
    return Iterable.super.spliterator();
  }

  /**
   * Creates a sequential {@link Stream} with this sorted linked list as its source. This method
   * uses {@link StreamSupport} to create the stream from the list's spliterator, ensuring the
   * stream iterates through the elements in the order they appear in the list.
   *
   * @return a sequential {@code Stream} over the elements in this sorted linked list
   */
  public Stream<T> stream() {
    return StreamSupport.stream(this.spliterator(), false);
  }
}
