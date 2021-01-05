package com.raidify.mobi.mycouturier.ui.measurement;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.raidify.mobi.mycouturier.ROOMDB.Repository;
import com.raidify.mobi.mycouturier.ROOMDB.RepositoryMeasureEntryCallback;
import com.raidify.mobi.mycouturier.ROOMDB.RepositoryMeasurementCallback;
import com.raidify.mobi.mycouturier.ROOMDB.model.MeasureEntry;
import com.raidify.mobi.mycouturier.ROOMDB.model.Measurement;

import java.util.List;

public class MeasurementBookViewModel extends AndroidViewModel {
private MutableLiveData<List<Measurement>> measurementList;
    private MutableLiveData<List<MeasureEntry>> measureEntries;
private Repository repository;

    public MeasurementBookViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
      //  loadMeasurementsFromLocalDB();
    }


public Measurement getMeasureRecordByRowId(int rowId){
        return measurementList.getValue().get(rowId);
}

    //TODO: re-arrange the approach to List update to seperate concern
    public LiveData<List<Measurement>> getMeasurementList(){
        //update the measurement list from DB before sending to user TODO:
        loadMeasurementsFromLocalDB();
    return this.measurementList;
}

    public LiveData<List<MeasureEntry>> getMeasureEntries(){
        //update the measure entries list from DB before sending to user TODO:
        return this.measureEntries;
    }

public void clearAllMeasurements(){
        repository.deleteAllMeasurement(); //Clears all measurements from Repo
}

private void loadMeasurementsFromLocalDB(){
        MutableLiveData<List<Measurement>> allMeasurementsLiveData = new MutableLiveData<>();
         repository.getAllMeasurements(new RepositoryMeasurementCallback() {
             @Override
             public void onComplete(List<Measurement> result) {
                 allMeasurementsLiveData.postValue(result);
                  Log.i("NDBOY", "Size here is " + allMeasurementsLiveData.getValue().size());
             }
         }); //TODO: For test to see if it updates the UI
    this.measurementList = allMeasurementsLiveData;
}

    public void loadMeasureEntriesFromDB(int measurementId){

        MutableLiveData<List<MeasureEntry>> allMeasureEntryLiveData = new MutableLiveData<>();
        repository.getMeasureEntriesById(measurementId, new RepositoryMeasureEntryCallback() {
            @Override
            public void onComplete(List<MeasureEntry> result) {
                allMeasureEntryLiveData.postValue(result);
//                Log.i("NDBOY", allMeasureEntryLiveData.getValue().size() + " entries loaded from local DB");
                Log.i("NDBOY", result.size() + " entries loaded from local DB");
            }
        });
        this.measureEntries = allMeasureEntryLiveData;

        Log.i("NDBOY", "entries of measurement id: " + measurementId + " loaded from DB");
    }
}//End class
