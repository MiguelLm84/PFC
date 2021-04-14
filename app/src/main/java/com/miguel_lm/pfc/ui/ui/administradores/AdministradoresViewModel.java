package com.miguel_lm.pfc.ui.ui.administradores;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AdministradoresViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AdministradoresViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is administradores fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}