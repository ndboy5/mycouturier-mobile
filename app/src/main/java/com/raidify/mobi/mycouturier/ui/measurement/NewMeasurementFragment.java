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
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.raidify.mobi.mycouturier.R;

public class NewMeasurementFragment extends Fragment {

    private NewMeasurementViewModel mViewModel;

    public static NewMeasurementFragment newInstance() {
        return new NewMeasurementFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.new_measurement__fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(NewMeasurementViewModel.class); //Deprecated
        //This is the new way of loading the View model.
        mViewModel = new ViewModelProvider(requireActivity()).get(NewMeasurementViewModel.class);

        Button nextBtn = getView().findViewById(R.id.goToMeasurementEntryBtn);
        final MaterialButtonToggleGroup genderToggleBtn = getView().findViewById(R.id.toggleButton);
        final MaterialButtonToggleGroup unitsToggleBtn = getView().findViewById(R.id.unitsOfMeasurement);
        final TextInputLayout descText = getView().findViewById(R.id.description);



        // NOTE: The gender and Body Type selected determines the fragment to bew called.
        //To check for gender when the user clicks genderToggleBtn
        nextBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //set the measurement unit
                mViewModel.setMeasurementUnit(unitsToggleBtn.getCheckedButtonId());

                int genderIndex =  genderToggleBtn.indexOfChild(genderToggleBtn.findViewById(genderToggleBtn.getCheckedButtonId()));
                mViewModel.setDescription(descText.getEditText().getText().toString());
                final int MALE = 0; final int FEMALE = 1; //declare constants for Male and Female toggleBtn
                //TODO: Optionally perform validation here. Call validation method
                if (genderIndex==MALE){
                    mViewModel.setGender("M");
                    Navigation.findNavController(view).navigate(R.id.action_nav_new_measurement_to_maleUpperBodyFragment);

                } else if (genderIndex==FEMALE){
                    mViewModel.setGender("F");
                    Navigation.findNavController(view).navigate(R.id.action_nav_new_measurement_to_femaleUpperBodyFragment);
                } else {
                    //TODO: Error handling here
                }
            }
        });



    }

}