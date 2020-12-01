package com.raidify.mobi.mycouturier.ui.measurement;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.raidify.mobi.mycouturier.model.Measurement;

import java.util.FormatFlagsConversionMismatchException;

public class FullBodyMaleViewModel extends ViewModel {
    private Measurement measurement = new Measurement();
    private String measureText = "0.0";
    private MutableLiveData<Float> currValue = new MutableLiveData<>();

    public void setCurrValue(String value, int bodyPart){
        this.measureText = value;
        currValue.setValue((Float.valueOf(measureText)));
    }

    public MutableLiveData<Float> getCurrValue(){
        return  currValue;
    }

}