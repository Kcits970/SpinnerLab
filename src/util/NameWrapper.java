package util;

public class NameWrapper<E> {
    E object;
    String name;

    public NameWrapper(E object, String name) {
        this.object = object;
        this.name = name;
    }

    public E getObject() {return object;}

    @Override
    public String toString() {return name;}
}
