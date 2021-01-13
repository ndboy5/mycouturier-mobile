package com.raidify.mobi.mycouturier.ROOMDB;


import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.raidify.mobi.mycouturier.ROOMDB.model.Account;
import com.raidify.mobi.mycouturier.ROOMDB.model.MeasureEntry;
import com.raidify.mobi.mycouturier.ROOMDB.model.Measurement;
import com.raidify.mobi.mycouturier.api_server.APIServerSingleton;
import com.raidify.mobi.mycouturier.util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

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
    private Context context;



    //Note: Application level scope used to get new Database Instance
    public Repository(Application application){
        this.context = application;
        //get DB instance
        MCDatabase mcDatabase = MCDatabase.getDatabase(context);
        //initialize the DAOs
        measurementDAO = mcDatabase.measurementDAO();
        measureEntryDAO =  mcDatabase.measureEntryDAO();
        //initialize the list arrays
        measureEntries = new ArrayList<>();
    }



    public void makeAPIGETTestCall(){
        String url = "https://run.mocky.io/v3/6f35ea97-8f46-44ad-ac55-b1ddafe5aa48";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //NOTE: Volley responds on the main thread.
                      //  textView.setText("Response: " + response.toString());
                        Log.i("NDBOY", "JSON" + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });


        APIServerSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    //Return a list of all measurement in the Local DB
    public void getAllMeasurements(final RepositoryMeasurementCallback callback){
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
        //TODO: Remove. used for test only
     //  makeAPIGETTestCall();
    }
    // This method is called by the new measurement View model and used to create a new measurement
    public Long insertMeasurement(Measurement measurement, List<MeasureEntry> measureEntries){
        MCDatabase.databaseWriteExecutor.execute(() -> {
           this.measurementId = measurementDAO.insertMeasurement(measurement);
           //update the measureEntry list with new MeasureId and save on the Local DB
            measureEntries.forEach(measureEntry -> {
                measureEntry.setMeasurementId(this.measurementId);
            });
           measureEntryDAO.insertAll(measureEntries);
            Log.i("NDBOY", "Measurement" + measurement.getDescription() + " was inserted successfully with ID: " + this.measurementId);
        });

        return this.measurementId;
    }

    public void deleteMeasurement(int measurementID){
        MCDatabase.databaseWriteExecutor.execute(()->{
            measurementDAO.deleteMeasurement(measurementID);
        });
    }

    public void deleteAllMeasurement(){
        MCDatabase.databaseWriteExecutor.execute(()->{
            measurementDAO.deleteAllMeasurement();
        });
    }

    //Returns a list of all body measure entries belonging to a measurement Record from the Local DB
    public void getMeasureEntriesById(int measurementId, final RepositoryMeasureEntryCallback callback){
        MCDatabase.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    // List<MeasureEntry>  measureEntries = measureEntryDAO.getMeasureEntries();
                    List<MeasureEntry>  measureEntries = measureEntryDAO.getMeasureEntries(measurementId);
                    callback.onComplete(measureEntries);
                } catch (Exception error){
                    Log.i("NDBOY", "Error from repository: "+ error);
                }
            }
        }
        );
    }

    public void insertMeasureEntry(List<MeasureEntry> measureEntry){
        MCDatabase.databaseWriteExecutor.execute(() -> {
            measureEntryDAO.insertAll(measureEntry);
//            measureEntryDAO.insertEntry(measureEntry);
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
