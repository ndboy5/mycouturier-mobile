package com.raidify.mobi.mycouturier.ui.login;

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

import com.raidify.mobi.mycouturier.R;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private LoginViewModel mViewModel;
    private EditText emailText;
    private EditText passwordText;
    private Button loginBtn;
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
        emailText = getView().findViewById(R.id.nameEditTxt);
        passwordText = getView().findViewById(R.id.pwdEditTxt);
        loginBtn =  getView().findViewById(R.id.loginButton);
        attemptMsgText = getView().findViewById(R.id.attemptText);



        loginBtn.setOnClickListener(this);


    }

    private boolean isValidCredential(String email, String pwd){
        //Perform validation here
        return true;
    }

    @Override
    public void onClick(View view) {
     String   email = emailText.getText().toString();
      String  password = passwordText.getText().toString();

        //first check for emptiness
        if(email.isEmpty() || password.isEmpty() ){
            Toast.makeText(getContext(), "Please enter all details correctly.", Toast.LENGTH_SHORT).show();
        }else {
            //validate the credentials
            if(isValidCredential(email, password)){
                //Navigate to home page;
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_nav_home);
            }
            else {
                counter--;
                attemptMsgText.setText(counter + " attempts remaining. You may login using Facebook or Twitter");
                //Disable button after 5 attempts
                if (counter==0) loginBtn.setEnabled(false);
            }
        }
    }
}