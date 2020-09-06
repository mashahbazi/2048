package com.example.my2048.modules.mainactivity;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.my2048.helpers.CustomIntLiveData;
import com.example.my2048.helpers.SharedPreferencesHelper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


@RunWith(JUnit4.class)
public class MainActivityViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private MainActivityViewModel viewModel;
    private SharedPreferences mockedSharedPreference;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Context context = Mockito.mock(Context.class);
        this.mockedSharedPreference = Mockito.mock(SharedPreferences.class);
        SharedPreferencesHelper sharedPreferencesHelper = SharedPreferencesHelper.getInstance(context);
        sharedPreferencesHelper.mock(mockedSharedPreference);
        viewModel = new MainActivityViewModel(sharedPreferencesHelper);
    }

    @Test
    public void changeViewsTextTest() {
        CustomIntLiveData first = new CustomIntLiveData();
        first.setValue(10);
        CustomIntLiveData second = new CustomIntLiveData();
        second.setValue(20);
        viewModel.changeViewsText(first, second);
        Assert.assertNotNull(first.getValue());
        Assert.assertNotNull(second.getValue());
        Assert.assertEquals(first.getValue(), new Integer(20));
        Assert.assertEquals(second.getValue(), new Integer(10));
    }
}
