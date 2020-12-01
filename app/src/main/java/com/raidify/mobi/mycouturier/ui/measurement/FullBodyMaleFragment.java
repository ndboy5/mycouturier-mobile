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
import android.widget.ImageButton;

import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.raidify.mobi.mycouturier.R;

public class FullBodyMaleFragment extends Fragment implements View.OnClickListener {

    private FullBodyMaleViewModel mViewModel;
    private TextInputLayout measureTextLayout;
    private ImageButton imageButton1;

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
    }

    @Override
    public void onClick(View view) {
        String editTextString = measureTextLayout.getEditText().getText().toString();
        //TODO: Perform further validation here
        boolean isNotEmptyTextField = true;

        if (editTextString.isEmpty()) {
            measureTextLayout.setError(getResources().getString(R.string.error_empty_string));
            isNotEmptyTextField = false;
        } else {
            measureTextLayout.setError(null);
        }
    if(isNotEmptyTextField){

        Log.i("NDBOY", "you just clicked the button 2");
    }
    }
}