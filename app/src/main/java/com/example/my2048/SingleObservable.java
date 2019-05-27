package com.example.my2048;

import java.util.Observable;

public class SingleObservable<T> extends Observable {
    T field;

    public SingleObservable(T field) {
        this.field = field;
    }

    public SingleObservable() {
    }

    public T getField() {
        return field;
    }

    public void setField(T field) {
        this.field = field;
        setChanged();
        notifyObservers(field);
    }
}
