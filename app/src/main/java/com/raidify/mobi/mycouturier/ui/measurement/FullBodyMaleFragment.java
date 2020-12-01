package com.raidify.mobi.mycouturier.ui.measurement;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.raidify.mobi.mycouturier.R;
import com.raidify.mobi.mycouturier.model.FullBodyMaleViewModel;

public class FullBodyMaleFragment extends Fragment implements View.OnClickListener {

    private FullBodyMaleViewModel mViewModel;
    private TextInputLayout measureTextLayout;
    private ImageButton imageButton1;
    private ImageButton imageButton2;
    private ImageButton imageButton3;
    private ImageButton imageButton4;
    private ImageButton imageButton5;
    private ImageButton imageButton6;
    private ImageButton imageButton7;
    private ImageButton imageButton8;
    private ImageButton imageButton9;
    private Button saveButton;
    private String activeBodyPart = "shoulder"; //used to determine which measurement attribute is updated/active

    public static FullBodyMaleFragment newInstance() {
        return new FullBodyMaleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.full_body_male_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FullBodyMaleViewModel.class);
        //instantiate the Text View
        measureTextLayout = getView().findViewById(R.id.measureLengthTextLayout);

        //Get the image buttons
        imageButton1 = getView().findViewById(R.id.imageButton1);
        imageButton2 = getView().findViewById(R.id.imageButton2);
        imageButton3 = getView().findViewById(R.id.imageButton3);
        imageButton4 = getView().findViewById(R.id.imageButton4);
        imageButton5 = getView().findViewById(R.id.imageButton5);
        imageButton6 = getView().findViewById(R.id.imageButton6);
        imageButton7 = getView().findViewById(R.id.imageButton7);
        imageButton8 = getView().findViewById(R.id.imageButton8);
        imageButton9 = getView().findViewById(R.id.imageButton9);
        saveButton = getView().findViewById(R.id.saveBtn);

        //Configure LiveData observer
        final Observer<Float> measureTextObserver = new Observer<Float>() {
            @Override
            public void onChanged(@Nullable Float measuredText) {
                measureTextLayout.getEditText().setText(measuredText.toString()); //TODO: Test this code line
            }
        };
        // assign an observer to watch the current value in the measurement Text view
        mViewModel.getCurrValue().observe(getViewLifecycleOwner(), measureTextObserver);

        // assign onClick listeners to image Buttons
        imageButton1.setOnClickListener(this);
        imageButton2.setOnClickListener(this);
        imageButton3.setOnClickListener(this);
        imageButton4.setOnClickListener(this);
        imageButton5.setOnClickListener(this);
        imageButton6.setOnClickListener(this);
        imageButton7.setOnClickListener(this);
        imageButton8.setOnClickListener(this);
        imageButton9.setOnClickListener(this);
        saveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int buttonId = view.getId();


        //TODO: Refactor this code
//        mViewModel.updateCurrValue(editTextString, buttonId);

        switch (buttonId) {
            case R.id.saveBtn:
                Log.i("NDBOY", "you clicked the save button");
                updateModel(activeBodyPart);

                break;
            case R.id.imageButton1:
                changeBackgroundColor(view);
                activeBodyPart = "shoulder";
                break;
            case R.id.imageButton2:
                changeBackgroundColor(view);
                activeBodyPart = "waist";
                break;
            case R.id.imageButton3:
                changeBackgroundColor(view);
                activeBodyPart = "hip";
                break;
                //TODO: complete logic for all Nine (9) buttons
        }
        measureTextLayout.setHint(activeBodyPart);

        }



    private void changeBackgroundColor(View view){
        ImageButton imageButton = (ImageButton) view;
        imageButton.setBackgroundResource(R.color.colorPrimaryDark); //TODO: Find a better color jor
    }

    private void updateModel(String activeBodyPart) {

        String editTextString = measureTextLayout.getEditText().getText().toString();
        //TODO: Perform further validation here
        boolean isNotEmptyTextField = true;

        if (editTextString.isEmpty()) {
            measureTextLayout.setError(getResources().getString(R.string.error_empty_string));
            isNotEmptyTextField = false;
        } else {
            measureTextLayout.setError(null);
        }
        if (isNotEmptyTextField) {
            switch (activeBodyPart) {
                case "shoulder":

                    Log.i("NDBOY", "shoulder");
                    break;
                case "waist":
                    Log.i("NDBOY", "waist");
                    break;
                case "inseam":
                    Log.i("NDBOY", "inseam");
            }
            Toast.makeText (getContext(),"Size " + editTextString + " saved", Toast.LENGTH_SHORT).show();
        }
    }
}