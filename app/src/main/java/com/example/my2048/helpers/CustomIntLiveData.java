package com.example.my2048.helpers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import java.util.Objects;
import java.util.Optional;

public class CustomIntLiveData extends MutableLiveData<Integer> {
    public CustomIntLiveData() {
        setValue(0);
    }

    Integer previousValue;

    @Override
    public void postValue(Integer value) throws NullPointerException {
        if (value == null) {
            throw new NullPointerException("value can't be null");
        }
        previousValue = getValue();
        super.postValue(value);
    }

    @Override
    public void setValue(Integer value) throws NullPointerException {
        if (value == null) {
            throw new NullPointerException("value can't be null");
        }
        previousValue = getValue();
        super.setValue(value);
    }

    @NonNull
    @Override
    public Integer getValue() {
        return Optional.ofNullable(super.getValue()).orElse(0);
    }

    public void restorePreviousValue() {
        postValue(previousValue);
    }
}
