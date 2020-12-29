package com.raidify.mobi.mycouturier.ui.measurement;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.raidify.mobi.mycouturier.R;
import com.raidify.mobi.mycouturier.ROOMDB.model.MeasureEntry;
import com.raidify.mobi.mycouturier.adapter.MeasureEntryRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MeasureBodypartFragment extends Fragment implements View.OnClickListener {

    private TextInputLayout measureTextLayout;
    private Button saveButton;
    private List<MeasureEntry> measureEntryList = new ArrayList<>();

    //Declare recycler view items
    private RecyclerView recyclerView;
    private MeasureEntryRecyclerAdapter measureEntryRecyclerAdapter;

    private NewMeasurementViewModel mViewModel;
    private int activeBodyPart; //The button Active Button ID;

    public static MeasureBodypartFragment newInstance() {
        return new MeasureBodypartFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       // super.onViewCreated(view, savedInstanceState); //TODO: Find out why this line isn't used

        return inflater.inflate(R.layout.measure_bodypart_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(NewMeasurementViewModel.class);

        //build Measurement Entry List for Recycler View
        measureEntryList = mViewModel.generateMeasureEntryList(); //TODO: Turn this into an observable

        //NOTE: The recycler view adapter must only be loaded after generating the measurementEntry from user's selection
        recyclerView = getActivity().findViewById(R.id.measureEntryRecyclerView); //TODO Test the context used
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())); //TODO: Test the context used here



        //setup the adapter
        measureEntryRecyclerAdapter = new MeasureEntryRecyclerAdapter(getActivity(), measureEntryList); //TODO: test the context used here (e.g fragment or getActivity)
        recyclerView.setAdapter(measureEntryRecyclerAdapter);

        //instantiate the views
        measureTextLayout = getView().findViewById(R.id.measureLengthTextLayout);
        saveButton = getView().findViewById(R.id.saveMLBodyBtn);

        // assign an observer to watch the current value in the measurement Text view
        mViewModel.getCurrEntry().observe(getViewLifecycleOwner(), text -> {
            measureTextLayout.getEditText().setText(text.toString());
        }); //TODO: how to work with the LiveData Object

        // assign onClick listeners to image Buttons
        saveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int buttonId = view.getId();

//        switch (buttonId) {
//            case R.id.saveMLBodyBtn:
//        String editTextString = measureTextLayout.getEditText().getText().toString();
//        //TODO: Perform further validation here
//        boolean isNotEmptyTextField = true;
//
//        if (editTextString.isEmpty()) {
//            measureTextLayout.setError(getResources().getString(R.string.error_empty_string));
//            isNotEmptyTextField = false;
//        } else {
//            measureTextLayout.setError(null);
//        }
//        if (isNotEmptyTextField && activeBodyPart!= 0) {
//            mViewModel.setUpperBDPartEntry(editTextString, activeBodyPart);
//            mViewModel.saveMeasurementToLocalDB();
//            Toast.makeText (getContext(),"Size " + editTextString + " saved", Toast.LENGTH_SHORT).show();
//        }
//                break;
//            case R.id.imageButton1:
//                setTextLayoutHint(Constants.BD_NECK);
//                activeBodyPart = buttonId;
//                mViewModel.setActiveBtnBackgroundColor(view);
//                break;
//            case R.id.imageButton2:
//                setTextLayoutHint(Constants.BD_HEAD);
//                activeBodyPart = buttonId;
//                mViewModel.setActiveBtnBackgroundColor(view);
//                break;
//            case R.id.imageButton3:
//                setTextLayoutHint(Constants.BD_CENTERBACK);
//                activeBodyPart = buttonId;
//                mViewModel.setActiveBtnBackgroundColor(view);
//                break;
//            case R.id.imageButton4:
//                setTextLayoutHint(Constants.BD_HIGHBUST);
//                activeBodyPart = buttonId;
//                mViewModel.setActiveBtnBackgroundColor(view);
//                break;
//            case R.id.imageButton5:
//                setTextLayoutHint(Constants.BD_SHOULDER);
//                activeBodyPart = buttonId;
//                mViewModel.setActiveBtnBackgroundColor(view);
//                break;
//            case R.id.imageButton6:
//                setTextLayoutHint(Constants.BD_WRIST);
//                activeBodyPart = buttonId;
//                mViewModel.setActiveBtnBackgroundColor(view);
//                break;
//            case R.id.imageButton7:
//                setTextLayoutHint(Constants.BD_FULLHEIGHT);
//                activeBodyPart = buttonId;
//                mViewModel.setActiveBtnBackgroundColor(view);
//                break;
//            case R.id.lowerBodyNavBtn:
//                Navigation.findNavController(view).navigate(R.id.action_maleUpperBodyFragment_to_maleLowerBodyFragment);
//                break;
//        }


    }


    private void setTextLayoutHint(String hint){
        measureTextLayout.setHint(hint);
    }


}