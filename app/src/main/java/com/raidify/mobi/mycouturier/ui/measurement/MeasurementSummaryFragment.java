package com.raidify.mobi.mycouturier.ui.measurement;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.raidify.mobi.mycouturier.R;
import com.raidify.mobi.mycouturier.ROOMDB.model.MeasureEntry;
import com.raidify.mobi.mycouturier.ROOMDB.model.Measurement;
import com.raidify.mobi.mycouturier.adapter.MeasureEntryRecyclerAdapter;
import com.raidify.mobi.mycouturier.adapter.MeasurementRecyclerAdapter;

import java.util.List;

public class MeasurementSummaryFragment extends Fragment implements LifecycleOwner, View.OnClickListener {

    //Declare recycler view items
    private RecyclerView recyclerView;
    private MeasureEntryRecyclerAdapter measureEntryRecyclerAdapter;
    private Button shareBtn;
    private TextView decriptionText;
    private TextView genderText;
    private TextView idText;
    private TextView unitText;
    private TextView dateText;
    private Measurement measurement;

    //declare View model
    private MeasurementBookViewModel mViewModel;

    private int measureRowId;

    public static MeasurementSummaryFragment newInstance() {
        return new MeasurementSummaryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //instantiate new Measurement;
        measurement = new Measurement();
        return inflater.inflate(R.layout.measurement_summary_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(MeasurementBookViewModel.class);
        //Load entries from if measurement ID is selected or use entries generated  from user clothing style selection
        measureRowId = getArguments().getInt("argMeId");
        //get the measurement from model
        measurement = mViewModel.getMeasureRecordByRowId(measureRowId);
        //Load body part entries from DB
        mViewModel.loadMeasureEntriesFromDB(measurement.getId());
        //build Measurement Entry List for Recycler View
        mViewModel.getMeasureEntries().observe(getViewLifecycleOwner(), measureEntryListObserver);
        shareBtn = getView().findViewById(R.id.shareBtn);
        //NOTE: The recycler view adapter must only be loaded after generating the measurementEntry from user's selection
        recyclerView = getActivity().findViewById(R.id.entriesRecyclerView); //TODO Test the context used
        recyclerView.setHasFixedSize(true);
       //Find the views from the Layout Resources
        decriptionText = getView().findViewById(R.id.descriptionText1);
        genderText = getView().findViewById(R.id.genderText);
        idText = getView().findViewById(R.id.measurementIdText);
        unitText = getView().findViewById(R.id.unitText);
        dateText = getView().findViewById(R.id.dateText);

        //Set the layout view values
        decriptionText.setText(measurement.getDescription());
        genderText.setText(measurement.getGender());
        idText.setText(String.valueOf(measurement.getId()));
        unitText.setText(measurement.getUnit());
        dateText.setText(measurement.getLastupdateBy());


        shareBtn.setOnClickListener(this);
    }


    Observer<List<MeasureEntry>> measureEntryListObserver = new Observer<List<MeasureEntry>>() {
        @Override
        public void onChanged(List<MeasureEntry> measureEntryList) {
            measureEntryRecyclerAdapter = new MeasureEntryRecyclerAdapter(getActivity(),measureEntryList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(measureEntryRecyclerAdapter);
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.shareBtn:
                //Navigate
                Log.i("NDBOY", "measurement shared to friends");
                break;
        }
    }
}