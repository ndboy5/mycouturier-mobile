package com.raidify.mobi.mycouturier.controller;
/*
    This controller is used to handle all http requests from the API.
    It is a singleton class built in line with the recommendations of the Android team
    for singleton Volley Class and Dependency Injection
    see: https://developer.android.com/training/volley/requestqueue and https://developer.android.com/training/dependency-injection/manual
    //TODO: modify codee to use this class rather the APIServerSingleton
 */
import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyController extends Application {
    private final static String TAG = VolleyController.class.getSimpleName();
    private static VolleyController instance;
    private RequestQueue requestQueue;


    public static synchronized VolleyController getInstance() {
//        if (instance == null) {
//            instance = new VolleyController(context);
//        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }
    //Overloaded method to use in setting the default TAG
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG: tag);
        getRequestQueue().add(req);
    }
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public  void cancelPendingRequests(Object tag){
        if(requestQueue!= null){
            requestQueue.cancelAll(tag);
        }
    }
}