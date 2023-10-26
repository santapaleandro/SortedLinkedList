package com.pawel.santarius.sortedlinkedlist;

import java.io.Serializable;
import java.util.NoSuchElementException;

public class SortedLinkedList <T extends Comparable<T>> implements Serializable {
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

    void removeElement(Element<T> e){
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

    void remove(T value){

    }

    public void remove(int index){
        checkBoundsExclusive(index);
        Element<T> e = getElement(index);
        removeElement(e);
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

}
