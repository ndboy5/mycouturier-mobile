package com.raidify.mobi.mycouturier.ui.measurement;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.raidify.mobi.mycouturier.model.Measurement;

import java.util.FormatFlagsConversionMismatchException;

public class FullBodyMaleViewModel extends ViewModel {
    private Measurement measurement = new Measurement();
    private String measureText = "0.0";
    private MutableLiveData<Float> currValue = new MutableLiveData<>();

    public void updateCurrValue(String value, int imageButtonId){

        Log.i("NDBOY", "you just clicked the button " + imageButtonId + " with value " + value );

        this.measureText = value;
        currValue.setValue((Float.valueOf(measureText)));
    }

    public MutableLiveData<Float> getCurrValue(){
        return  currValue;
    }

}