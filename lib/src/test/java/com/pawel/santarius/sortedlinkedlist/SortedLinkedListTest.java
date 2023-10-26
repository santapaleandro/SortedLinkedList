package com.pawel.santarius.sortedlinkedlist;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SortedLinkedListTest {

    private SortedLinkedList<Integer> sortedLinkedList;

    @Before public void init(){
        sortedLinkedList = new SortedLinkedList<>();
        sortedLinkedList.add(2);
    }

    @Test public void addToEmpty() {
        assertTrue(sortedLinkedList.size != 0);
    }

    @Test public void checkSize(){
        sortedLinkedList.add(1);
        assertEquals(2, sortedLinkedList.size);
    }

    @Test public void checkPreviousOfHeadElement(){
        sortedLinkedList.add(1);
        assertEquals(1, (int) sortedLinkedList.head.data);
        assertNull(sortedLinkedList.head.previous);
    }

    @Test public void checkNextOfLastElement(){
        sortedLinkedList.add(1);
        assertNull(sortedLinkedList.last.next);
    }

    @Test public void checkNextOfHeadElement(){
        sortedLinkedList.add(1);
        assertEquals(sortedLinkedList.get(1),sortedLinkedList.head.next.data);
    }

    @Test public void checkPreviousOfLastElement(){
        sortedLinkedList.add(1);
        assertEquals(sortedLinkedList.head.data,sortedLinkedList.last.previous.data);
    }

    @Test public void checkClear(){
        sortedLinkedList.add(1);
        assertEquals(sortedLinkedList.size, 2);
        sortedLinkedList.clear();
        assertEquals(sortedLinkedList.size, 0);
    }

    @Test public void removeElement(){
        sortedLinkedList.removeElement(new Element<>(2));
        assertEquals(0, sortedLinkedList.size);
    }

    @Test public void removeByIndex(){
        sortedLinkedList.remove(0);
        assertEquals(sortedLinkedList.size, 0);
    }

    @Test public void removeFirst(){
        sortedLinkedList.removeFirst();
        assertEquals(sortedLinkedList.size, 0);
    }

    @Test public void removeLast(){
        sortedLinkedList.removeLast();
        assertEquals(sortedLinkedList.size, 0);
    }

    @Test public void indexOf(){
        assertEquals(sortedLinkedList.indexOf(2), 0);
        sortedLinkedList.add(0);
        assertEquals(sortedLinkedList.indexOf(2), 1);
        sortedLinkedList.add(2);
        assertEquals(sortedLinkedList.indexOf(2), 1);
    }

    @Test public void lastIndexOf(){
        assertEquals(sortedLinkedList.lastIndexOf(2),0);
        sortedLinkedList.add(1);
        sortedLinkedList.add(3);
        sortedLinkedList.add(2);
        assertEquals(sortedLinkedList.lastIndexOf(2),2);
    }

    @Test public void contains(){
        assertTrue(sortedLinkedList.contains(2));
    }
}
