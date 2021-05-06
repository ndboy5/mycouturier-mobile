package com.raidify.mobi.mycouturier.ui.home;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;

import com.raidify.mobi.mycouturier.util.SessionManager;

public class HomeViewModel extends AndroidViewModel {
SessionManager sessionManager;

    public HomeViewModel(Application application) {
        super(application);
        sessionManager= new SessionManager(application);
    }

    public boolean isLoggedIn(){
        Log.i("NDBOY", "Status of isLoggedIn is: " + sessionManager.isLogin()); //TODO: for delete
        return sessionManager.isLogin();

    }

    public void logoutAccount(){
        sessionManager.logoutUser();
    }

    public String getSessionName(){
       return sessionManager.getUserDetails().get("name");
    }
}