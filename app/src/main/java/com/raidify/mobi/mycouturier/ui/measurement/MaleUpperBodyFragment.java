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

public class MaleUpperBodyFragment extends Fragment implements View.OnClickListener {

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
    private Button navLowerFragmentBtn;

    private NewMeasurementViewModel mViewModel;
    private int activeBodyPart; //The button Active Button ID;

    public static MaleUpperBodyFragment newInstance() {
        return new MaleUpperBodyFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       // super.onViewCreated(view, savedInstanceState); //TODO: Find out why this line isn't used

        return inflater.inflate(R.layout.male_upper_body_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(NewMeasurementViewModel.class);

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
        saveButton = getView().findViewById(R.id.saveMLBodyBtn);
        navLowerFragmentBtn = getView().findViewById(R.id.lowerBodyNavBtn);

        // assign an observer to watch the current value in the measurement Text view
        mViewModel.getCurrEntry().observe(getViewLifecycleOwner(), text -> {
            measureTextLayout.getEditText().setText(text.toString());
        }); //TODO: how to work with the LiveData Object

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
        navLowerFragmentBtn.setOnClickListener(this);
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
            case R.id.imageButton1:
                setTextLayoutHint(Constants.BD_NECK);
                activeBodyPart = buttonId;
                mViewModel.setActiveBtnBackgroundColor(view);
                break;
            case R.id.imageButton2:
                setTextLayoutHint(Constants.BD_HEAD);
                activeBodyPart = buttonId;
                mViewModel.setActiveBtnBackgroundColor(view);
                break;
            case R.id.imageButton3:
                setTextLayoutHint(Constants.BD_CENTERBACK);
                activeBodyPart = buttonId;
                mViewModel.setActiveBtnBackgroundColor(view);
                break;
            case R.id.imageButton4:
                setTextLayoutHint(Constants.BD_HIGHBUST);
                activeBodyPart = buttonId;
                mViewModel.setActiveBtnBackgroundColor(view);
                break;
            case R.id.imageButton5:
                setTextLayoutHint(Constants.BD_SHOULDER);
                activeBodyPart = buttonId;
                mViewModel.setActiveBtnBackgroundColor(view);
                break;
            case R.id.imageButton6:
                setTextLayoutHint(Constants.BD_WRIST);
                activeBodyPart = buttonId;
                mViewModel.setActiveBtnBackgroundColor(view);
                break;
            case R.id.imageButton7:
                setTextLayoutHint(Constants.BD_FULLHEIGHT);
                activeBodyPart = buttonId;
                mViewModel.setActiveBtnBackgroundColor(view);
                break;
            case R.id.lowerBodyNavBtn:
                Navigation.findNavController(view).navigate(R.id.action_maleUpperBodyFragment_to_maleLowerBodyFragment);
                break;
        }


    }


    private void setTextLayoutHint(String hint){
        measureTextLayout.setHint(hint);
    }


}