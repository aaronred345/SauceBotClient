package com.saucebot.util;

import java.util.Arrays;
import java.util.TreeSet;

public class FixedSizePriorityQueue<T extends Comparable<? super T>> extends TreeSet<T> {

    private int capacity = 10;

    private int elementsLeft = 0;

    public FixedSizePriorityQueue(final int capacity) {
        super(new NaturalComparator<T>());
        this.capacity = capacity;
        this.elementsLeft = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public boolean add(final T element) {
        if (elementsLeft > 0) {
            if (super.add(element)) {
                elementsLeft--;
            } else {
                return false;
            }
        } else {
            if (comparator().compare(element, first()) > 0) {
                pollFirst();
                super.add(element);
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public int size() {
        return capacity - elementsLeft;
    }

    public boolean hasNext() {
        return elementsLeft < capacity;
    }

    public T next() {
        if (hasNext()) {
            elementsLeft++;
            return pollLast();
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return "MessageQueue[" + Arrays.toString(toArray()) + "]";
    }

}
