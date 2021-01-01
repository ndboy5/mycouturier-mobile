package com.raidify.mobi.mycouturier.ROOMDB;


import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.raidify.mobi.mycouturier.ROOMDB.model.MeasureEntry;
import com.raidify.mobi.mycouturier.ROOMDB.model.Measurement;

import java.util.ArrayList;
import java.util.List;


public class Repository {
    //Declare DAOs
    private MeasurementDAO measurementDAO;
    private MeasureEntryDAO measureEntryDAO;

    //Declare variables
    private MutableLiveData<List<Measurement>> allMeasurements = new MutableLiveData<>();
    private List<MeasureEntry> measureEntries;
    //Single measurement
    private Measurement measurement;
    private Long measurementId; //To hold return value after insertion into DB

    //TODO: Develop search feature for measurements saved on Device or cloud
    private MutableLiveData<List<Measurement>> searchResults = new MutableLiveData<>();

    //Note: Application level scope used to get new Database Instance
    public Repository(Application application){
        //get DB instance
        MCDatabase mcDatabase = MCDatabase.getDatabase(application);
        //initialize the DAOs
        measurementDAO = mcDatabase.measurementDAO();
        measureEntryDAO =  mcDatabase.measureEntryDAO();
        //initialize the list arrays
        measureEntries = new ArrayList<>();
    }

    //Return a list of all measurement in the Local DB
    public void getAllMeasurements(final RepositoryCallback callback){
        //TODO: Error handling. Surround all database calls with try-catch block
      MCDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    /* background thread */
                    List<Measurement> data = measurementDAO.getAllMeasurements();
                    callback.onComplete(data);

//        Log.i("NDBOY", "The real measurement size is: " + data.size());
                } catch (Exception e) {
                    Log.i("NDBOY", "error on getting data from repo: "+ e);
                }
            }
        });
//        Log.i("NDBOY", "The real measurement size is: " + this.allMeasurements.getValue().size());
         //TODO: The allMeasurements returns a null value. This data gets lost here
    }

    public Long insertMeasurement(Measurement measurement){
        MCDatabase.databaseWriteExecutor.execute(() -> {
           this.measurementId = measurementDAO.insertMeasurement(measurement);
        });

        return this.measurementId;
    }

    private void deleteMeasurement(int measurementID){
        MCDatabase.databaseWriteExecutor.execute(()->{
            measurementDAO.deleteMeasurement(measurementID);
        });
    }

    //Returns a list of all body measure entries belonging to a measurement Record from the Local DB
    public List<MeasureEntry> getMeasureEntriesBy(int measurementId){
        MCDatabase.databaseWriteExecutor.execute(() ->{
            measureEntries =   measureEntryDAO.getMeasureEntries(measurementId);
        });
        return measureEntries;
    }

    public void insertMeasureEntry(MeasureEntry measureEntry){
        MCDatabase.databaseWriteExecutor.execute(() -> {
            measureEntryDAO.insertEntry(measureEntry);
        });
    }

    private void deleteMeasureEntry(int measurementID){
        MCDatabase.databaseWriteExecutor.execute(()->{
            measureEntryDAO.deleteMeasurement(measurementID);
        });
    }

    //TODO: Get measurement records for self (belonging to self)
    //TODO: Get favourite measurements
}
