package com.pawel.santarius.sortedlinkedlist;

public interface ISortedLinkedList<T extends Comparable<T>> {
    void add(T value);
    void remove(T value);
    SortedLinkedList<T> remove(int index);
}
