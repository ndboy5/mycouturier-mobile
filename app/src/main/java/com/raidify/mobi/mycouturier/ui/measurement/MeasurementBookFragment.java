package com.raidify.mobi.mycouturier.ui.measurement;

import androidx.lifecycle.LiveData;
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

import com.raidify.mobi.mycouturier.R;
import com.raidify.mobi.mycouturier.ROOMDB.model.Measurement;
import com.raidify.mobi.mycouturier.adapter.MeasureEntryRecyclerAdapter;
import com.raidify.mobi.mycouturier.adapter.MeasurementRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MeasurementBookFragment extends Fragment {

    private MeasurementBookViewModel mViewModel;
    private RecyclerView measurementRecycleView;
    private MeasurementRecyclerAdapter measurementRecyclerAdapter;
    private LiveData<List<Measurement>> measurements = new LiveData<List<Measurement>>() {
    };


    public static MeasurementBookFragment newInstance() {
        return new MeasurementBookFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.measurement_book_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MeasurementBookViewModel.class); //TODO: modified this
        //Load the measurement list
        mViewModel.updateListFromLocalDB();//TODO: test code
//        this.measurements = mViewModel.getMeasurementList();
//        Log.i("NDBOY", "Size: " + this.measurements.getValue().size());

//        measurementRecycleView = getView().findViewById(R.id.measurementRecycleView);
//        measurementRecycleView.setHasFixedSize(true);
//        measurementRecycleView.setLayoutManager(new LinearLayoutManager(getContext())); //TODO: Test the context used here (getActivity)
//
//
//        //setup the adapter
//        //TODO: An error is returned when the DB is empty. Find a way to handle Nulls in the Adapter too
//        measurementRecyclerAdapter = new MeasurementRecyclerAdapter(getActivity(), this.measurements); //TODO: test the context used here (e.g fragment or getActivity)
//        measurementRecycleView.setAdapter(measurementRecyclerAdapter);
    }

}