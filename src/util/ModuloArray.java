package util;

public class ModuloArray<E> {
    int size;
    E[] array;

    public ModuloArray(int size) {
        this.size = size;
        array = (E[]) new Object[size];
    }

    public ModuloArray(E... elements) {
        this.size = elements.length;
        array = elements;
    }

    public E get(int index) {
        return array[Math.floorMod(index, size)];
    }

    public void set(int index, E element) {
        array[Math.floorMod(index, size)] = element;
    }
}
