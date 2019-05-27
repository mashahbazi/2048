package com.example.my2048;

import java.util.Observable;

class SingleObservable<T> extends Observable {
    private T field;

    SingleObservable(T field) {
        this.field = field;
    }

    T getField() {
        return field;
    }

    void setField(T field) {
        this.field = field;
        setChanged();
        notifyObservers(field);
    }
}
