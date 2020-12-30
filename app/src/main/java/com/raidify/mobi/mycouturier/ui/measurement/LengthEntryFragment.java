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
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.raidify.mobi.mycouturier.R;
import com.raidify.mobi.mycouturier.ROOMDB.model.MeasureEntry;

import kotlin.text.UStringsKt;

public class LengthEntryFragment extends Fragment implements View.OnClickListener{

    private NewMeasurementViewModel mViewModel;
    private Button saveBtn;
    private TextInputLayout measureTextLayout;
    private int measureEntryListPosition;
    private MeasureEntry measureEntry;

    public static LengthEntryFragment newInstance() {
        return new LengthEntryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.length_entry_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       measureEntryListPosition = getArguments().getInt("adtrPosition");

        mViewModel = new ViewModelProvider(requireActivity()).get(NewMeasurementViewModel.class);
        measureEntry = mViewModel.getMeasureEntryByListIndex(measureEntryListPosition);

        measureTextLayout = getView().findViewById(R.id.measureLengthTextLayout);
        //set the Text Layouts's hint text
        measureTextLayout.setHint(measureEntry.getPart());

        saveBtn = getView().findViewById(R.id.saveBodyLengthBtn);
        saveBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.saveBodyLengthBtn:

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
            measureEntry.setLength(Float.valueOf(editTextString));
            mViewModel.updateMeasureEntry(measureEntryListPosition, measureEntry);
            Toast.makeText (getContext(),"Size " + editTextString + " saved", Toast.LENGTH_SHORT).show();
        }
                Navigation.findNavController(view).navigate(R.id.action_lengthEntryFragment_to_measureBodyPartFragment);

        break;
            default:
                break;
        }//End switch
    }
}