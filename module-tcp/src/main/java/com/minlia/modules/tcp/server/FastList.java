package com.minlia.modules.tcp.server;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * @author will
 */
public final class FastList<T> implements List<T>, RandomAccess, Serializable {
  private static final long serialVersionUID = -4598088075242913858L;
  private final Class<?> clazz;
  private T[] elementData;
  private int size;

  public FastList(Class<?> clazz) {
    this.elementData = (T[]) Array.newInstance(clazz, 32);
    this.clazz = clazz;
  }

  public FastList(Class<?> clazz, int capacity) {
    this.elementData = (T[]) Array.newInstance(clazz, capacity);
    this.clazz = clazz;
  }

  @Override
  public boolean add(T element) {
    if (this.size < this.elementData.length) {
      this.elementData[this.size++] = element;
    } else {
      int oldCapacity = this.elementData.length;
      int newCapacity = oldCapacity << 1;
      T[] newElementData = (T[]) Array.newInstance(this.clazz, newCapacity);
      System.arraycopy(this.elementData, 0, newElementData, 0, oldCapacity);
      newElementData[this.size++] = element;
      this.elementData = newElementData;
    }

    return true;
  }

  @Override
  public T get(int index) {
    return this.elementData[index];
  }

  public T removeLast() {
    T element = this.elementData[--this.size];
    this.elementData[this.size] = null;
    return element;
  }

  @Override
  public boolean remove(Object element) {
    for(int index = this.size - 1; index >= 0; --index) {
      if (element == this.elementData[index]) {
        int numMoved = this.size - index - 1;
        if (numMoved > 0) {
          System.arraycopy(this.elementData, index + 1, this.elementData, index, numMoved);
        }

        this.elementData[--this.size] = null;
        return true;
      }
    }

    return false;
  }

  @Override
  public void clear() {
    for(int i = 0; i < this.size; ++i) {
      this.elementData[i] = null;
    }

    this.size = 0;
  }

  @Override
  public int size() {
    return this.size;
  }

  @Override
  public boolean isEmpty() {
    return this.size == 0;
  }

  @Override
  public T set(int index, T element) {
    T old = this.elementData[index];
    this.elementData[index] = element;
    return old;
  }

  @Override
  public T remove(int index) {
    if (this.size == 0) {
      return null;
    } else {
      T old = this.elementData[index];
      int numMoved = this.size - index - 1;
      if (numMoved > 0) {
        System.arraycopy(this.elementData, index + 1, this.elementData, index, numMoved);
      }

      this.elementData[--this.size] = null;
      return old;
    }
  }

  @Override
  public boolean contains(Object o) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Iterator<T> iterator() {
    return new Iterator<T>() {
      private int index;

      @Override
      public boolean hasNext() {
        return this.index < FastList.this.size;
      }

      @Override
      public T next() {
        if (this.index < FastList.this.size) {
          return FastList.this.elementData[this.index++];
        } else {
          throw new NoSuchElementException("No more elements in FastList");
        }
      }
    };
  }

  @Override
  public Object[] toArray() {
    throw new UnsupportedOperationException();
  }

  @Override
  public <E> E[] toArray(E[] a) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(Collection<? extends T> c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(int index, Collection<? extends T> c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void add(int index, T element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int indexOf(Object o) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int lastIndexOf(Object o) {
    throw new UnsupportedOperationException();
  }

  @Override
  public ListIterator<T> listIterator() {
    throw new UnsupportedOperationException();
  }

  @Override
  public ListIterator<T> listIterator(int index) {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<T> subList(int fromIndex, int toIndex) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Object clone() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void forEach(Consumer<? super T> action) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Spliterator<T> spliterator() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean removeIf(Predicate<? super T> filter) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void replaceAll(UnaryOperator<T> operator) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void sort(Comparator<? super T> c) {
    throw new UnsupportedOperationException();
  }
}
