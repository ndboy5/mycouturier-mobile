/**
 * This view model is used by the Measurements fragments for the creation of new Measurement Records
 */

package com.raidify.mobi.mycouturier.ui.measurement;

import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.raidify.mobi.mycouturier.R;
import com.raidify.mobi.mycouturier.ROOMDB.Repository;
import com.raidify.mobi.mycouturier.ROOMDB.model.Measurement;

import java.util.List;

public class NewMeasurementViewModel extends AndroidViewModel {
    private final MutableLiveData<Float> currEntry = new MutableLiveData<>();
    private final Measurement measurement = new Measurement();
    private List<Measurement> allMeasurements;
    private Repository repository;

    //Constructor
    public NewMeasurementViewModel (Application application) {
        super(application);
   //     Log.i("NDBOY", "It got this far");
        repository = new Repository(application);
        Log.i("NDBOY", "But couldn't get here");
        allMeasurements = repository.getAllMeasurements();
    }


    public LiveData<Float> getCurrEntry(){
        return currEntry;
    }

    public void setCurrEntry(Float m){
        this.currEntry.setValue(m);
    }

    public void setUpperBDPartEntry(String val, int buttonID) {

            setCurrEntry(Float.valueOf(val));
            switch (buttonID) {
                case R.id.imageButton1:
                    measurement.setShoulder(currEntry.getValue());
                    Log.i("NDBOY", String.valueOf(measurement.getShoulder()) +" "+ measurement.getDescription());
                    break;
                case R.id.imageButton2:
                    measurement.setWaist(currEntry.getValue());
                    Log.i("NDBOY", String.valueOf(measurement.getWaist()) +" "+ measurement.getDescription());
                    break;
                case R.id.imageButton3:
                    measurement.setInseam(currEntry.getValue());
                    Log.i("NDBOY", String.valueOf(measurement.getHip()) +" "+ measurement.getDescription());
            }
        }

    public void setLowerBDPartEntry(String val, int buttonID) {

        setCurrEntry(Float.valueOf(val));
        switch (buttonID) {
            case R.id.imageButton1:
                measurement.setShoulder(currEntry.getValue());
                Log.i("NDBOY", String.valueOf(measurement.getShoulder()) +" "+ measurement.getDescription());
                break;
            case R.id.imageButton2:
                measurement.setWaist(currEntry.getValue());
                Log.i("NDBOY", String.valueOf(measurement.getWaist()) +" "+ measurement.getDescription());
                break;
            case R.id.imageButton3:
                measurement.setInseam(currEntry.getValue());
                Log.i("NDBOY", String.valueOf(measurement.getHip()) +" "+ measurement.getDescription());
        }
    }

    public void setActiveBtnBackgroundColor(View view){
        ImageButton imageButton = (ImageButton) view;
        imageButton.setBackgroundResource(R.color.colorPrimaryDark); //TODO: Find a better color jor
     }


     //TODO: Resolve the error in this method
    public void resetActiveBtnBackgroundColor(View view){
        ImageButton imageButton = (ImageButton) view;
        imageButton.setBackgroundResource(R.color.WHITE); //TODO: Find a better color
    }


    public void setGender(String gender){
        measurement.setGender(gender);
    }

    public void setDescription(String text){
        measurement.setDescription(text);
    }

    public void setMeasurementUnit(int selectedUnit){
        switch (selectedUnit) {
            case R.id.cmUnit:
                measurement.setUnitOfMeasure("cm");
                break;
            case R.id.mmUnit:
                measurement.setUnitOfMeasure("mm");
                break;
            case R.id.inchUnit:
                measurement.setUnitOfMeasure("inch");
                break;

            default:
                measurement.setUnitOfMeasure("cm");
                break;
        }

    }

    public void saveMeasurementToLocalDB(){
    repository.insertMeasurement(this.measurement);
    Log.i("NDBOY", "measurement inserted successfully.");
    }

}
