package com.pawel.santarius.sortedlinkedlist;

import org.junit.Test;
import static org.junit.Assert.*;

public class SortedLinkedListTest {

    @Test public void addToEmpty() {
        SortedLinkedList<Integer> sortedLinkedList = new SortedLinkedList<>();
        sortedLinkedList.add(2);
        assertTrue(sortedLinkedList.size != 0);
    }

    @Test public void checkSize(){
        SortedLinkedList<Integer> sortedLinkedList = new SortedLinkedList<>();
        sortedLinkedList.add(2);
        sortedLinkedList.add(1);
        assertEquals(2, sortedLinkedList.size);
    }

    @Test public void checkPreviousOfHeadElement(){
        SortedLinkedList<Integer> sortedLinkedList = new SortedLinkedList<>();
        sortedLinkedList.add(2);
        sortedLinkedList.add(1);
        assertEquals(1, (int) sortedLinkedList.head.data);
        assertNull(sortedLinkedList.head.previous);
    }

    @Test public void checkNextOfLastElement(){
        SortedLinkedList<Integer> sortedLinkedList = new SortedLinkedList<>();
        sortedLinkedList.add(2);
        sortedLinkedList.add(1);
        assertNull(sortedLinkedList.last.next);
    }

    @Test public void checkNextOfHeadElement(){
        SortedLinkedList<Integer> sortedLinkedList = new SortedLinkedList<>();
        sortedLinkedList.add(2);
        sortedLinkedList.add(1);
        assertEquals(sortedLinkedList.get(1),sortedLinkedList.head.next.data);
    }

    @Test public void checkPreviousOfLastElement(){
        SortedLinkedList<Integer> sortedLinkedList = new SortedLinkedList<>();
        sortedLinkedList.add(2);
        sortedLinkedList.add(1);
        assertEquals(sortedLinkedList.head.data,sortedLinkedList.last.previous.data);
    }
}
