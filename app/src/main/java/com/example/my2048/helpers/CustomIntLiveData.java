package com.example.my2048.helpers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import java.util.Objects;

public class CustomIntLiveData extends MutableLiveData<Integer> {
    public CustomIntLiveData() {
        setValue(0);
    }

    @Override
    public void postValue(Integer value) throws NullPointerException {
        if (value == null) {
            throw new NullPointerException("value can't be null");
        }
        super.postValue(value);
    }

    @Override
    public void setValue(Integer value) throws NullPointerException {
        if (value == null) {
            throw new NullPointerException("value can't be null");
        }
        super.setValue(value);
    }

    @NonNull
    @Override
    public Integer getValue() {
        return Objects.requireNonNull(super.getValue());
    }
}
