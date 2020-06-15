package com.minlia.module.nestedset.node;

import net.jcip.annotations.Immutable;

import java.io.Serializable;

@Immutable
class Key  implements Serializable {
    private static final Long serialVersionUID=4L;

    private final Class<?> clazz;
    private final Long id;

    public Key(Class<?> clazz, Long id) {
        this.clazz = clazz;
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.clazz != null ? this.clazz.hashCode() : 0);
        hash = 23 * hash + Integer.parseInt(this.id.toString());
        return hash;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Key)) {
            return false;
        }

        Key otherKey = (Key) other;

        return this.clazz.equals(otherKey.clazz)
                && this.id == otherKey.id;
    }

    @Override
    public String toString() {
        return "[Class: " + this.clazz.getName() + ", Id: " + this.id + "]";
    }
}
