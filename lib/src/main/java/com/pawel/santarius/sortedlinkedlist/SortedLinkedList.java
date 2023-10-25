package com.pawel.santarius.sortedlinkedlist;

import java.io.Serializable;

public class SortedLinkedList <T extends Comparable<T>> implements Serializable {
    transient Element<T> head;
    transient Element<T> last;
    transient int size = 0;
    public void add(T value) {
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
}
