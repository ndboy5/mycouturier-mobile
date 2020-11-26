package com.raidify.mobi.mycouturier.ui.measurement;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

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
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
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
        final ChipGroup typeChipGrp = getView().findViewById(R.id.chipGroup);




        /** TODO: Review the listeners below to find the exact component that was clicked/checked
         The getCheckedId methods for the ToggleGroup and the ChipGroup doesn't return the ID given in the XML file
         */
        // NOTE: The gender and Body Type selected determines the fragment to bew called.
        //To check for gender and body type selected when the user clicks nextBtn
        nextBtn.setOnClickListener(new MaterialButton.OnClickListener(){

            @Override
            public void onClick(View view) {
                int genderIndex =  genderToggleBtn.indexOfChild(genderToggleBtn.findViewById(genderToggleBtn.getCheckedButtonId()));
                int bodyTypeIndex = typeChipGrp.indexOfChild(typeChipGrp.findViewById(typeChipGrp.getCheckedChipId()));

                final int MALE = 0; final int FEMALE = 1; //declare constants for Male and Female toggleBtn
                final int FULLBODY = 0; final int UPPERBODY = 1; final int LOWERBODY  = 2; //constants for index position of clothing measurement Area type in XML layout file
                //TODO: Optionally perform validation here. Call validation method
                if (genderIndex==MALE){
                    switch (bodyTypeIndex){
                        case FULLBODY:

                            Navigation.findNavController(view).navigate(R.id.action_nav_measurement_to_nav_fullBodyMaleFragment);
                            break;
                        case UPPERBODY:

                            Navigation.findNavController(view).navigate(R.id.action_nav_measurement_to_upperBodyMaleFragment);
                            break;
                        case LOWERBODY:

                            Navigation.findNavController(view).navigate(R.id.action_nav_measurement_to_lowerBodyMaleFragment);
                            break;
                        default:
                            //TODO: Error handling here
                    }
                } else if (genderIndex==FEMALE){
                    switch (bodyTypeIndex){
                        case FULLBODY:

                            Navigation.findNavController(view).navigate(R.id.action_nav_measurement_to_nav_fullBodyFemaleFragment);
                            break;
                        case UPPERBODY:

                            Navigation.findNavController(view).navigate(R.id.action_nav_measurement_to_upperBodyFemaleFragment);
                            break;

                        case LOWERBODY:

                            Navigation.findNavController(view).navigate(R.id.action_nav_measurement_to_lowerBodyFemaleFragment);
                            break;
                        default:
                            //TODO: Error handling here
                            break;
                    }
                } else {
                    //TODO: Error handling here
                }
            }
        });

    }

}