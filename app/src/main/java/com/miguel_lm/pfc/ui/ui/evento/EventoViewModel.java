package com.miguel_lm.pfc.ui.ui.evento;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EventoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EventoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}