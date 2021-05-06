package com.raidify.mobi.mycouturier.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.raidify.mobi.mycouturier.R;
import com.raidify.mobi.mycouturier.api_server.APIServerSingleton;
import com.raidify.mobi.mycouturier.util.Constants;
import com.raidify.mobi.mycouturier.util.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginFragment extends Fragment implements View.OnClickListener {
    SessionManager sessionManager;
    private RegistrationViewModel mViewModel;
    private EditText emailText;
    private EditText passwordText;
    private Button loginBtn;
    private Button signUpBtn;
    private TextView attemptMsgText;
    private int counter = 5;
    
    // Facebook login
    private LoginButton fbLoginBtn;
    //Facebook callback manager
    CallbackManager callbackManager;
    private static final String EMAIL = "email";

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);
        sessionManager = new SessionManager(requireActivity());// Initialize the session

        emailText = getView().findViewById(R.id.nameEditTxt);
        passwordText = getView().findViewById(R.id.pwdEditTxt);
        loginBtn =  getView().findViewById(R.id.loginButton);
        signUpBtn =  getView().findViewById(R.id.signUpbutton);
        attemptMsgText = getView().findViewById(R.id.attemptText);

        loginBtn.setOnClickListener(this);
        signUpBtn.setOnClickListener(this);
        //Register a callback for FB login
        callbackManager = CallbackManager.Factory.create();

        fbLoginBtn = (LoginButton) getView().findViewById(R.id.FBlogin_button);
        fbLoginBtn.setPermissions(Arrays.asList(EMAIL));
        // If you are using in a fragment, call loginButton.setFragment(this);
        fbLoginBtn.setFragment(this);
        fbLoginBtn.setOnClickListener(this);
        // Callback registration
        fbLoginBtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.i("NDBOY", "Facebook login callback was successful: " + loginResult.getAccessToken().getUserId());
            }

            @Override
            public void onCancel() {
                // App code
                Log.i("NDBOY", "Facebook login callback was cancelled");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.i("NDBOY", "Facebook login callback error");
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        //Using the access token from FB to get user details
        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.i("NDBOY", "User detail" + object.toString());
                        try {
                            //SettAccountDetails for Facebook login
                            //TODO: Some people use phone numbers to register on FB. This should be handled client side so it doesn't throw errors on server side
                            //Note: Password and phone numbers are null
                            mViewModel.setAccountDetails(object.getString("email"), null,
                                    null, Constants.CUSTOMER_ROLE, object.getString("first_name"),
                                    object.getString("last_name") , Constants.FB_USER);

                            mViewModel.facebookLoginToServer("/auth/login/facebook");
                            
                              //create local Login session on mobile device
                            createLoginSession(mViewModel.getAccount().getId(), mViewModel.getAccount().getFirstName(),
                                    mViewModel.getAccount().getEmail(), mViewModel.getAccount().getRole(), mViewModel.getAccount().getToken(), Constants.FB_USER);
                                    
                            //TODO: check for login status and navigate to home page from here
                            Log.i("NDBOY", "Login Status on Phone is: " + sessionManager.isLogin());
                            //navigate to home fragment
                            navigateToHome();
                            
                        } catch (JSONException e) {

                            e.printStackTrace(); //TODO: handle Network Errors here
                        }
                        //TODO: Send FB Login data to server and create session

                    }
                });

        Bundle bundle = new Bundle();
        bundle.putString("fields", "email, first_name, last_name");
        //TODO: Collecting any info other than email would require facebook app review (See: https://developers.facebook.com/docs/permissions/reference#e )
        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();
    }

    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken==null){
                LoginManager.getInstance().logOut();
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Stop facebook access token tracking
        accessTokenTracker.stopTracking();
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
                                         createLoginSession(response.getString("id"), response.getString("name"),
                                            email, response.getString("role"), response.getString("token"), Constants.DEF_USER);

                                            //Navigate from login page to home
                                             navigateToHome();
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
            case R.id.signUpbutton:
                //Go to registration fragment
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registrationFragment);
                break;
            //To logout the user on device when he clicks on the FB login button to logout
            case R.id.FBlogin_button:
                    if(sessionManager.isLogin()){
                        // LoginManager.getInstance().logOut();
                        sessionManager.logoutUser();
                    }
                break;
        }

    }

 //This method is called is either the Facebook login (OAuth) or the In-built login is successful
    private void createLoginSession(String id, String name, String email, String role, String token, String userType){

        sessionManager.createLoginSession(id, name, email, role, token, userType);
    }

    //This method is used to navigate to the home fragment after login
    private void navigateToHome(){
        Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_nav_home);
    }


}