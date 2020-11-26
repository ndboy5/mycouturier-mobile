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

public class HomeFragment extends Fragment {

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
     MaterialButton findBtn = getView().findViewById(R.id.findAccountBtn);

     // Create Listeners for each button
     measureBtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Navigation.findNavController(view).navigate(R.id.action_nav_home_to_nav_measurement);
         }
     });
 }

}