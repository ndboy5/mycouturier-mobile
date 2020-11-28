package com.raidify.mobi.mycouturier.ui.measurement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.raidify.mobi.mycouturier.R;

public class measurementTypeFragment extends Fragment {

    private MeasurementTypeViewModel mViewModel;

    public static measurementTypeFragment newInstance() {
        return new measurementTypeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.measurement_type__fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(MeasurementTypeViewModel.class); //Deprecated
        //This is the new way of loading the View model.
        mViewModel = new ViewModelProvider(this).get(MeasurementTypeViewModel.class);

        Button nextBtn = getView().findViewById(R.id.goToMeasurementEntryBtn);
        final MaterialButtonToggleGroup genderToggleBtn = getView().findViewById(R.id.toggleButton);


        // NOTE: The gender and Body Type selected determines the fragment to bew called.
        //To check for gender when the user clicks genderToggleBtn
        nextBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                int genderIndex =  genderToggleBtn.indexOfChild(genderToggleBtn.findViewById(genderToggleBtn.getCheckedButtonId()));

                final int MALE = 0; final int FEMALE = 1; //declare constants for Male and Female toggleBtn
                //TODO: Optionally perform validation here. Call validation method
                if (genderIndex==MALE){
                    Navigation.findNavController(view).navigate(R.id.action_nav_measurement_to_nav_fullBodyMaleFragment);

                } else if (genderIndex==FEMALE){

                    Navigation.findNavController(view).navigate(R.id.action_nav_measurement_to_nav_fullBodyFemaleFragment);
                } else {
                    //TODO: Error handling here
                }
            }
        });



    }

}