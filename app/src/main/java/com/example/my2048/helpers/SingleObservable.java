package com.example.my2048.helpers;

import java.util.Observable;

public class SingleObservable<T> extends Observable {
    private T field;

    public SingleObservable(T field) {
        this.field = field;
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
