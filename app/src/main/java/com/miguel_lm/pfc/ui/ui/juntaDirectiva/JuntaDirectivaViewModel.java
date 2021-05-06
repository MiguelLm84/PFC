package com.miguel_lm.pfc.ui.ui.juntaDirectiva;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class JuntaDirectivaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public JuntaDirectivaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Fragment Junta Directiva");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
