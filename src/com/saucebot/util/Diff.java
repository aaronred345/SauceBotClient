package com.saucebot.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Diff<T> {

    private Set<T> added;
    private Set<T> removed;

    private Diff(final Collection<T> listA, final Collection<T> listB) {
        added = new HashSet<T>(listB);
        removed = new HashSet<T>(listA);

        added.removeAll(listA);
        removed.removeAll(listB);
    }

    public Set<T> getAdded() {
        return added;
    }

    public Set<T> getRemoved() {
        return removed;
    }

    public boolean isDifferent() {
        return !isSame();
    }

    public boolean isSame() {
        return added.isEmpty() && removed.isEmpty();
    }

    public static <T> Diff<T> calculate(final Collection<T> listA, final Collection<T> listB) {
        return new Diff<T>(listA, listB);
    }

}
