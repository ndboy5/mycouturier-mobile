package com.raidify.mobi.mycouturier.ui.auth;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.raidify.mobi.mycouturier.R;
import com.raidify.mobi.mycouturier.ROOMDB.model.Account;
import com.raidify.mobi.mycouturier.api_server.APIServerSingleton;
import com.raidify.mobi.mycouturier.util.Constants;
import com.raidify.mobi.mycouturier.util.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginFragment extends Fragment implements View.OnClickListener {
    SessionManager sessionManager;
    private LoginViewModel mViewModel;
    private EditText emailText;
    private EditText passwordText;
    private Button loginBtn;
    private Button signUpBtn;
    private TextView attemptMsgText;
    private int counter = 5;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        sessionManager = new SessionManager(requireActivity());// Initialize the session

        emailText = getView().findViewById(R.id.nameEditTxt);
        passwordText = getView().findViewById(R.id.pwdEditTxt);
        loginBtn =  getView().findViewById(R.id.loginButton);
        signUpBtn =  getView().findViewById(R.id.signUpbutton);
        attemptMsgText = getView().findViewById(R.id.attemptText);

        loginBtn.setOnClickListener(this);
        signUpBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginButton:
                String   email = emailText.getText().toString();
                String  password = passwordText.getText().toString();

                //first check for emptiness
                if(email.isEmpty() || password.isEmpty() ){
                    Toast.makeText(getContext(), "Please enter all details correctly.", Toast.LENGTH_SHORT).show();
                }else {
                    //validate the credentials from server
                        String url = Constants.baseUrl + "/auth/login";  //TODO: For tests. Identify unprotected urls

                        JSONObject postData = new JSONObject();
                        try {
                            postData.put("email", email);
                            postData.put("password", "jerryforlife");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                                (Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        //NOTE: Volley responds on the main thread
                                        try {
                                            //Create session only if account exists on server
                                            if (response.getBoolean("success")){
                                            sessionManager.createLoginSession(response.getString("id"), response.getString("name"),
                                                    email, response.getString("role"), response.getString("token"));
                                            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_nav_home);
                                            Log.i("NDBOY", "JSON Status: " + response.getBoolean("success"));
                                            } else{
                                                // Decrement counter if credentials are not valid on server
                                                counter--;
                                                attemptMsgText.setText(counter + " attempts remaining. You may login using Facebook or Twitter");
                                                //Disable button after 5 attempts
                                                if (counter==0) loginBtn.setEnabled(false);
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
                    //Make asynchronous call to server to retrieve account token and status
                        APIServerSingleton.getInstance(requireActivity()).addToRequestQueue(jsonObjectRequest);
}
                break;
            case R.id.signupBtn:
                //Go to registration fragment
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registrationFragment);
                break;
        }

    }

}