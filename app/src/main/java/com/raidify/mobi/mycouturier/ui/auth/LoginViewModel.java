package com.raidify.mobi.mycouturier.ui.auth;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.raidify.mobi.mycouturier.ROOMDB.Repository;
import com.raidify.mobi.mycouturier.ROOMDB.model.Account;
import com.raidify.mobi.mycouturier.api_server.APIServerSingleton;
import com.raidify.mobi.mycouturier.util.Constants;
import com.raidify.mobi.mycouturier.util.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel


    public LoginViewModel(Application application) {
        super(application);
    }


}