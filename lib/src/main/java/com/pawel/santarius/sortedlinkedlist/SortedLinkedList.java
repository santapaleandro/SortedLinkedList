package com.pawel.santarius.sortedlinkedlist;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;

public class SortedLinkedList <T extends Comparable<T>> implements Serializable, Iterable<T> {
    transient Element<T> head;
    transient Element<T> last;
    transient int size = 0;

    void add(T value) {
        Element<T> newElement = new Element<>(value);
        if (head == null){
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

    void add(ArrayList<T> array){
        if (array != null){
            for (T t : array) {
                add(t);
            }
        }
    }

    void add(LinkedList<T> linkedList){
        if (linkedList != null){
            for (T t : linkedList) {
                add(t);
            }
        }
    }

    void add(SortedLinkedList<T> list){
        if (list != null){
            for (int i = 0; i < list.size; i++){
                add(list.get(i));
            }
        }
    }

    void remove(Element<T> e){
        size--;
        if (size == 0){
            head = last = null;
        } else {
            if (e == head){
                head = e.next;
                e.next.previous = null;
            }
            else if (e == last) {
                last = e.previous;
                e.previous.next = null;
            }
            else {
                e.next.previous = e.previous;
                e.previous.next = e.next;
            }
        }
    }

    public void remove(int index){
        checkBoundsExclusive(index);
        Element<T> e = getElement(index);
        remove(e);
    }

    void removeFirst() {
        if (size == 0){
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

    void clear(){
        if (size > 0){
            head = null;
            last = null;
            size = 0;
        }
    }

    private Element<T> getElement(int n) {
       Element<T> e;
       if (n < size / 2) {
           e = head;
           while (n-- > 0)
               e = e.next;
       } else {
            e = last;
            while (++n < size)
                e = e.previous;
       }
       return e;
    }

    private void checkBoundsExclusive(int index){
        if (index < 0 ){
            throw new IndexOutOfBoundsException("Index: " + index + " can not be negative number.");
        }
        if (index >= size){
            throw new IndexOutOfBoundsException("Index: " + index + " exceeds the Size: " + size);
        }
    }

    public T get(int index){
        checkBoundsExclusive(index);
        return getElement(index).data;
    }

    public int indexOf(T value){
        Element<T> e = head;
        int index = 0;
        while (e != null){
            if (e.data == value){
                return index;
            }
            index++;
            e = e.next;
        }
        return -1;
    }

    public int lastIndexOf(T value){
        Element<T> e = last;
        int index = size;
        while (e != null){
            index--;
            if (e.data == value) {
                return index;
            }
            e = e.previous;
        }
        return -1;
    }

    public boolean contains(T value){
        Element<T> e = head;
        while (e != null){
            if (e.data.equals(value)){
                return true;
            }
            e = e.next;
        }
        return false;
    }

    public ArrayList<T> toArray(){
        ArrayList<T> array = new ArrayList<>(size);
        Element<T> e = head;
        for (int i = 0; i < size; i++){
            array.add(e.data);
            e = e.next;
        }
        return array;
    }

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

    private class SortedLinkedListIterator implements Iterator<T>{

        private Element<T> current;

        public SortedLinkedListIterator(){
            this.current = head;
        }
        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
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
     * Performs the given action for each element of the {@code Iterable}
     * until all elements have been processed or the action throws an
     * exception.  Actions are performed in the order of iteration, if that
     * order is specified.  Exceptions thrown by the action are relayed to the
     * caller.
     * <p>
     * The behavior of this method is unspecified if the action performs
     * side effects that modify the underlying source of elements, unless an
     * overriding class has specified a concurrent modification policy.
     *
     * @param action The action to be performed for each element
     * @throws NullPointerException if the specified action is null
     * @implSpec <p>The default implementation behaves as if:
     * <pre>{@code
     *     for (T t : this)
     *         action.accept(t);
     * }</pre>
     * @since 1.8
     */
    @Override
    public void forEach(Consumer action) {
        Iterable.super.forEach(action);
    }

    /**
     * Creates a {@link Spliterator} over the elements described by this
     * {@code Iterable}.
     *
     * @return a {@code Spliterator} over the elements described by this
     * {@code Iterable}.
     * @implSpec The default implementation creates an
     * <em><a href="../util/Spliterator.html#binding">early-binding</a></em>
     * spliterator from the iterable's {@code Iterator}.  The spliterator
     * inherits the <em>fail-fast</em> properties of the iterable's iterator.
     * @implNote The default implementation should usually be overridden.  The
     * spliterator returned by the default implementation has poor splitting
     * capabilities, is unsized, and does not report any spliterator
     * characteristics. Implementing classes can nearly always provide a
     * better implementation.
     * @since 1.8
     */
    @Override
    public Spliterator spliterator() {
        return Iterable.super.spliterator();
    }
}
