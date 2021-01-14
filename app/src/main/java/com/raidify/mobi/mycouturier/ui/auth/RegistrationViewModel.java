package com.raidify.mobi.mycouturier.ui.auth;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.raidify.mobi.mycouturier.R;
import com.raidify.mobi.mycouturier.ROOMDB.RepositoryMeasurementCallback;
import com.raidify.mobi.mycouturier.ROOMDB.model.Account;
import com.raidify.mobi.mycouturier.api_server.APIServerSingleton;
import com.raidify.mobi.mycouturier.util.Constants;
import com.raidify.mobi.mycouturier.util.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationViewModel extends AndroidViewModel {
    private Account account;
    SessionManager sessionManager;

    public RegistrationViewModel(Application application) {
        super(application);
    sessionManager = new SessionManager(application);
        this.account = new Account();
    }

    public void setAccountDetails(String email, String password, String phoneNo, String role){
        account.setRole(role);
        account.setPwd(password);
        account.setEmail(email);
        account.setPhone(phoneNo);
    }

    public boolean createAccount(){

        //send to server
        String url = Constants.baseUrl + "/auth/register";  //TODO: For tests. Identify unprotected urls

        JSONObject postData = new JSONObject();
        try {
            postData.put("email", account.getEmail());
            postData.put("password", account.getPwd());
            postData.put("role", account.getRole());
            postData.put("phone",  account.getPhone());
            //TODO: Modify Registration layout to accept first and last names
            postData.put("firstname", "Mike");
            postData.put("surname", "tyson");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //Save response to repo here

                        //NOTE: Volley responds on the main thread
                        try {
                            //Create session only if account was created successfully
                            if (response.getBoolean("success")){
                                sessionManager.createLoginSession(response.getString("id"), response.getString("name"),
                                        account.getEmail(), response.getString("role"), response.getString("token"));
                                Log.i("NDBOY", "JSON Status: " + response.getBoolean("success"));
                            } else{
                                //TODO: Do something if registration fails

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error here

                    }
                });

        //set request policy to avoid double requests being sent
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Make asynchronous call to server to retrieve account token and status
        APIServerSingleton.getInstance(getApplication()).addToRequestQueue(jsonObjectRequest);
        return true; //TODO: develop logic
    }

}