package com.raidify.mobi.mycouturier.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

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
import com.raidify.mobi.mycouturier.util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class RegistrationFragment extends Fragment implements View.OnClickListener {

    private RegistrationViewModel mViewModel;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText phoneEditText;
    private CheckBox designerCheckBox;
    private Button signUpBtn;

    // Facebook login
    private LoginButton fbLoginBtn;
    //Facebook callback manager
    CallbackManager callbackManager;
    private static final String EMAIL = "email";

    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.registration_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fbLoginBtn = (LoginButton) getView().findViewById(R.id.login_button2);
        fbLoginBtn.setPermissions(Arrays.asList(EMAIL));
        // If you are using in a fragment, call loginButton.setFragment(this);
        fbLoginBtn.setFragment(this);


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

                            mViewModel.facebookLoginToServer("/auth/registration/facebook");
                            //TODO: check for login status and navigate to home page from here
                            Navigation.findNavController(getView()).navigate(R.id.action_registrationFragment_to_nav_home);
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);

        emailEditText = getView().findViewById(R.id.emailEditTextr);
        passwordEditText = getView().findViewById(R.id.passwordEditTextr);
        phoneEditText = getView().findViewById(R.id.phoneEditTextr);
        designerCheckBox = getView().findViewById(R.id.designerCheckBox);
        //TODO: find edit texts of name and surname from layout
        signUpBtn = getView().findViewById(R.id.signupBtn);

        signUpBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if(email==null || password==null) {
            //Show error message
            Toast.makeText(getContext(), "Please enter valid email address and password", Toast.LENGTH_SHORT).show();
        } else   {
            String role = "customer";
            //Collect remaining details and send to server
            String phone = phoneEditText.getText().toString();
            if (designerCheckBox.isChecked()) {
                role = "designer"; //TODO: prevent use of string literals
            }
            //set Account details
            mViewModel.setAccountDetails(email, password, phone, role, "jerry", "jerry", Constants.DEF_USER); //TODO: modify to use real names from Layout file
           mViewModel.createDefaultAccount("/auth/register");
               Navigation.findNavController(view).navigate(R.id.action_registrationFragment_to_nav_home);
        }

    }
}