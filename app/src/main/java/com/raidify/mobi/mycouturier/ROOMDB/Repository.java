package com.raidify.mobi.mycouturier.ROOMDB;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.raidify.mobi.mycouturier.ROOMDB.model.MeasureEntry;
import com.raidify.mobi.mycouturier.ROOMDB.model.Measurement;

import java.util.List;

public class Repository {
    //Declare DAOs
    private MeasurementDAO measurementDAO;
    private MeasureEntryDAO measureEntryDAO;
    //Declare variables
    private List<Measurement> allMeasurements;
    //TODO: Develop search feature for measurements saved on Device or cloud
    private MutableLiveData<List<Measurement>> searchResults = new MutableLiveData<>();

    //Note: Application level scope used to get new Database Instance
    public Repository(Application application){
        //get DB instance
        MCDatabase mcDatabase = MCDatabase.getDatabase(application);
        //initialize the DAOs
        measurementDAO = mcDatabase.measurementDAO();
        measureEntryDAO =  mcDatabase.measureEntryDAO();
    }

    //Return a list of all measurement in the Local DB
    public List<Measurement> getAllMeasurements(){
        MCDatabase.databaseWriteExecutor.execute(() ->{
          allMeasurements =   measurementDAO.getAllMeasurements();
        });
        return allMeasurements;
    }

    public void insertMeasurement(Measurement measurement){
        MCDatabase.databaseWriteExecutor.execute(() -> {
            measurementDAO.insertMeasurement(measurement);
        });
    }

    private void deleteMeasurement(int measurementID){
        MCDatabase.databaseWriteExecutor.execute(()->{
            measurementDAO.deleteMeasurement(measurementID);
        });
    }

    //Returns a list of all body measure entries belonging to a measurement Record from the Local DB
    public List<Measurement> getMeasureEntriesBy(int measurementId){
        MCDatabase.databaseWriteExecutor.execute(() ->{
            allMeasurements =   measureEntryDAO.getMeasureEntries(measurementId);
        });
        return allMeasurements;
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
