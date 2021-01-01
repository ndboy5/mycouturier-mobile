package com.raidify.mobi.mycouturier.ui.measurement;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.raidify.mobi.mycouturier.ROOMDB.Repository;
import com.raidify.mobi.mycouturier.ROOMDB.RepositoryCallback;
import com.raidify.mobi.mycouturier.ROOMDB.model.Measurement;

import java.util.ArrayList;
import java.util.List;

public class MeasurementBookViewModel extends AndroidViewModel {
private MutableLiveData<List<Measurement>> measurementList;
private Repository repository;

    public MeasurementBookViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }


    //TODO: re-arrange the approach to List update to seperate concern
    public LiveData<List<Measurement>> getMeasurementList(){
        //update the measurement list from DB before sending to user TODO:
    return this.measurementList;
}

public void updateListFromLocalDB(){
         repository.getAllMeasurements(new RepositoryCallback() {
             @Override
             public void onComplete(List<Measurement> result) {
                 measurementList.setValue(result);
                  Log.i("NDBOY", "Size here is " + result.size());
             }
         }); //TODO: For test to see if it updates the UI
}

}//End class
