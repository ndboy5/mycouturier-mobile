package com.raidify.mobi.mycouturier.ui.measurement;

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
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.raidify.mobi.mycouturier.R;
import com.raidify.mobi.mycouturier.util.Constants;

public class MaleLowerBodyFragment extends Fragment implements View.OnClickListener{
    private TextInputLayout measureTextLayout;
    private ImageButton imageButton10;
    private ImageButton imageButton11;
    private ImageButton imageButton12;
    private ImageButton imageButton13;
    private ImageButton imageButton14;
    private ImageButton imageButton15;
    private ImageButton imageButton16;
    private ImageButton imageButton17;
    private ImageButton imageButton18;
    private Button saveButton;
    private Button navToUpperFragmentBtn;

    private NewMeasurementViewModel mViewModel;
    private int activeBodyPart; //The button Active Button ID;

    public static MaleLowerBodyFragment newInstance() {
        return new MaleLowerBodyFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.male_lower_body_fragment, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(NewMeasurementViewModel.class);

        //instantiate the Text View
        measureTextLayout = getView().findViewById(R.id.measureLengthTextLayout);

        //Get the image buttons
        imageButton10 = getView().findViewById(R.id.imageButton10);
        imageButton11 = getView().findViewById(R.id.imageButton11);
        imageButton12 = getView().findViewById(R.id.imageButton12);
        imageButton13 = getView().findViewById(R.id.imageButton13);
        imageButton14 = getView().findViewById(R.id.imageButton14);
        imageButton15 = getView().findViewById(R.id.imageButton15);
        imageButton16 = getView().findViewById(R.id.imageButton16);
        imageButton17 = getView().findViewById(R.id.imageButton17);
        imageButton18 = getView().findViewById(R.id.imageButton18);
        saveButton = getView().findViewById(R.id.saveMLBodyBtn);
        navToUpperFragmentBtn = getView().findViewById(R.id.upperBodyNavBtn);

        // assign an observer to watch the current value in the measurement Text view
        mViewModel.getCurrEntry().observe(getViewLifecycleOwner(), text -> {
            measureTextLayout.getEditText().setText(text.toString());
        }); //TODO: how to work with the LiveData Object

        // assign onClick listeners to Buttons
        imageButton10.setOnClickListener(this);
        imageButton11.setOnClickListener(this);
        imageButton12.setOnClickListener(this);
        imageButton13.setOnClickListener(this);
        imageButton14.setOnClickListener(this);
        imageButton15.setOnClickListener(this);
        imageButton16.setOnClickListener(this);
        imageButton17.setOnClickListener(this);
        imageButton18.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        navToUpperFragmentBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int buttonId = view.getId();

        switch (buttonId) {
            case R.id.saveMLBodyBtn:
                Log.i("NDBOY", "you clicked the save button");
                String editTextString = measureTextLayout.getEditText().getText().toString();

                //TODO: Perform further validation here
                boolean isNotEmptyTextField = true;

                if (editTextString.isEmpty()) {
                    measureTextLayout.setError(getResources().getString(R.string.error_empty_string));
                    isNotEmptyTextField = false;
                } else {
                    measureTextLayout.setError(null);
                }
                if (isNotEmptyTextField && activeBodyPart!= 0) {
                    mViewModel.setUpperBDPartEntry(editTextString, activeBodyPart);
                    Toast.makeText (getContext(),"Size " + editTextString + " saved", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.imageButton10:
                setTextLayoutHint(Constants.BD_WAIST);
                activeBodyPart = buttonId;
                mViewModel.setActiveBtnBackgroundColor(view);
                break;
            case R.id.imageButton11:
                setTextLayoutHint(Constants.BD_CROTCHLENGHT);
                activeBodyPart = buttonId;
                mViewModel.setActiveBtnBackgroundColor(view);
                break;
            case R.id.imageButton12:
                setTextLayoutHint(Constants.BD_HIP);
                activeBodyPart = buttonId;
                mViewModel.setActiveBtnBackgroundColor(view);
                break;
            case R.id.imageButton13:
                setTextLayoutHint(Constants.BD_INSEAM);
                activeBodyPart = buttonId;
                mViewModel.setActiveBtnBackgroundColor(view);
                break;
            case R.id.imageButton14:
                setTextLayoutHint(Constants.BD_OUTSEAM);
                activeBodyPart = buttonId;
                mViewModel.setActiveBtnBackgroundColor(view);
                break;
            case R.id.imageButton15:
                setTextLayoutHint(Constants.BD_ANKLE);
                activeBodyPart = buttonId;
                mViewModel.setActiveBtnBackgroundColor(view);
                break;
            case R.id.imageButton16:
                setTextLayoutHint(Constants.BD_FULLHEIGHT);
                activeBodyPart = buttonId;
                mViewModel.setActiveBtnBackgroundColor(view);
                mViewModel.saveMeasurementToLocalDB(); //TODO: For test only
                break;
            //TODO: complete logic for all Nine (9) buttons
            case R.id.upperBodyNavBtn:
                Navigation.findNavController(view).navigate(R.id.action_maleLowerBodyFragment_to_maleUpperBodyFragment);
        }


    }


    private void setTextLayoutHint(String hint){
        measureTextLayout.setHint(hint);
    }


}