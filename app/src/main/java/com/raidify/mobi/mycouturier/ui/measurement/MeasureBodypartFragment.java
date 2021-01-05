package com.raidify.mobi.mycouturier.ui.measurement;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.raidify.mobi.mycouturier.R;
import com.raidify.mobi.mycouturier.ROOMDB.model.MeasureEntry;
import com.raidify.mobi.mycouturier.ROOMDB.model.Measurement;
import com.raidify.mobi.mycouturier.adapter.MeasureEntryRecyclerAdapter;
import com.raidify.mobi.mycouturier.adapter.MeasurementRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MeasureBodypartFragment extends Fragment implements View.OnClickListener, LifecycleOwner {

    private Button saveButton;
//    private MutableLiveData<List<MeasureEntry>> measureEntryList = new MutableLiveData<>();

    //Declare recycler view items
    private RecyclerView recyclerView;
    private MeasureEntryRecyclerAdapter measureEntryRecyclerAdapter;

    private NewMeasurementViewModel mViewModel;

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
        mViewModel.getMeasureEntries().observe(getViewLifecycleOwner(), measureEntryListObserver);

        //NOTE: The recycler view adapter must only be loaded after generating the measurementEntry from user's selection
        recyclerView = getActivity().findViewById(R.id.measureEntryRecyclerView); //TODO Test the context used
        recyclerView.setHasFixedSize(true);

        //instantiate the views
        saveButton = getView().findViewById(R.id.saveMLBodyBtn);

        // assign onClick listeners to image Buttons
        saveButton.setOnClickListener(this);
    }


    Observer<List<MeasureEntry>> measureEntryListObserver = new Observer<List<MeasureEntry>>() {
        @Override
        public void onChanged(List<MeasureEntry> measureEntryList) {
            measureEntryRecyclerAdapter = new MeasureEntryRecyclerAdapter(getActivity(),measureEntryList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(measureEntryRecyclerAdapter);
        }
    };

    @Override
    public void onClick(View view) {
        int buttonId = view.getId();

        switch (buttonId) {
            case R.id.saveMLBodyBtn:
                mViewModel.saveNewMeasurementToLocalDB();
                Measurement measurement = mViewModel.getActiveMeasurement();
                Log.i("NDBOY", "measurement saved to local DB");
                Toast.makeText (getContext(), measurement.getId() + ": "+ measurement.getDescription() + " measurement details saved successfully", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(R.id.action_measureBodyPartFragment_to_nav_home);
        //TODO: Save to local DB
                break;
            default:
                break;
        }

    }



}