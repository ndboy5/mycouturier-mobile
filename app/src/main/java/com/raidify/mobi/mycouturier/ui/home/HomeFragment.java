package com.raidify.mobi.mycouturier.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;
import com.raidify.mobi.mycouturier.R;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

 @Override
    public  void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        //Get the buttons
     MaterialButton measureBtn = getView().findViewById(R.id.measurementsBtn);
     MaterialButton ordersBtn = getView().findViewById(R.id.ordersBtn);
     MaterialButton recordBookBtn = getView().findViewById(R.id.recordBookBtn);

     // set Listeners for each button
     measureBtn.setOnClickListener(this);
     ordersBtn.setOnClickListener(this);
     recordBookBtn.setOnClickListener(this);

 }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.measurementsBtn:
                Navigation.findNavController(view).navigate(R.id.action_nav_home_to_nav_measurement);
                break;
            case R.id.ordersBtn:
                break;
            case R.id.recordBookBtn:
                Navigation.findNavController(view).navigate(R.id.action_nav_home_to_measurementBookFragment);
                break;
            default:
                break;
        }
    }
}