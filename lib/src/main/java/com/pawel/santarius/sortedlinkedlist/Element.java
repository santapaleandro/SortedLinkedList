package com.pawel.santarius.sortedlinkedlist;

import java.io.Serializable;

class Element<T> implements Serializable {
  T data;
  Element<T> next;
  Element<T> previous;

  public Element(T data) {
    this.data = data;
    this.next = null;
    this.previous = null;
  }
}
