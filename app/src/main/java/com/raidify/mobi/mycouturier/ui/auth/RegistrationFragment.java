package com.raidify.mobi.mycouturier.ui.auth;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.raidify.mobi.mycouturier.R;

public class RegistrationFragment extends Fragment implements View.OnClickListener {

    private RegistrationViewModel mViewModel;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText phoneEditText;
    private CheckBox designerCheckBox;
    private Button signUpBtn;



    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.registration_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);

        emailEditText = getView().findViewById(R.id.emailEditTextr);
        passwordEditText = getView().findViewById(R.id.passwordEditTextr);
        phoneEditText = getView().findViewById(R.id.phoneEditTextr);
        designerCheckBox = getView().findViewById(R.id.designerCheckBox);
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
            mViewModel.setAccountDetails(email, password, phone, role);
           if (mViewModel.createAccount())
               Navigation.findNavController(view).navigate(R.id.action_registrationFragment_to_nav_home);
        }

    }
}