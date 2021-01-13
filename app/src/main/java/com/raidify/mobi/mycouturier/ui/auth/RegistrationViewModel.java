package com.raidify.mobi.mycouturier.ui.auth;

import androidx.lifecycle.ViewModel;

import com.raidify.mobi.mycouturier.ROOMDB.model.Account;

public class RegistrationViewModel extends ViewModel {
    private Account account;

    public RegistrationViewModel() {
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
        return true; //TODO: develop logic
    }

}