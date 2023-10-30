package com.pawel.santarius.sortedlinkedlist;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class SortedLinkedListTest {

    private SortedLinkedList<Integer> sortedLinkedList;
    private SortedLinkedList<Integer> sortedLinkedList2;

    @Before public void init(){
        sortedLinkedList = new SortedLinkedList<>();
        sortedLinkedList.add(2);
        sortedLinkedList2 = new SortedLinkedList<>();
        sortedLinkedList2.add(2);
    }

    @Test public void equals(){
        assertEquals(sortedLinkedList, sortedLinkedList2);
    }

    @Test public void add() {
        assertTrue(sortedLinkedList.size != 0);
    }

    @Test public void addArrayList(){
        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(3);
        arr.add(4);
        arr.add(5);
        sortedLinkedList.add(arr);
        assertEquals(sortedLinkedList.size,4);
    }

    @Test public void addLinkedList(){
        LinkedList<Integer> arr = new LinkedList<>();
        arr.add(4);
        arr.add(3);
        arr.add(5);
        sortedLinkedList.add(arr);
        assertEquals(sortedLinkedList.size,4);
        assertEquals(sortedLinkedList.indexOf(3),1);
    }

    @Test public void addSortedLinkedList(){
        SortedLinkedList<Integer> arr = new SortedLinkedList<>();
        arr.add(4);
        arr.add(3);
        arr.add(5);
        sortedLinkedList.add(arr);
        assertEquals(sortedLinkedList.size,4);
        assertEquals(sortedLinkedList.indexOf(3),1);
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
        sortedLinkedList.remove(new Element<>(2));
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

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetIndexGreaterThanSize() {
        sortedLinkedList.get(3);
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
        sortedLinkedList.add(3);
        assertTrue(sortedLinkedList.contains(3));
    }

    @Test public void toArray(){
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(2);
        sortedLinkedList.add(1);
        assertEquals(sortedLinkedList.toArray(),arrayList);
    }

    @Test public void Iterator(){
        sortedLinkedList.add(5);
        sortedLinkedList.add(3);
        Iterator<Integer> iterator = sortedLinkedList.iterator();
        assertEquals(Integer.valueOf(2), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(Integer.valueOf(3), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(Integer.valueOf(5), iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test public void equalsFalse(){
        sortedLinkedList2.add(10);
        assertNotEquals(sortedLinkedList, sortedLinkedList2);
    }

    @Test public void forEachAddThree(){
        sortedLinkedList.add(10);
        sortedLinkedList.add(5);
        sortedLinkedList.stream();
    }


}
