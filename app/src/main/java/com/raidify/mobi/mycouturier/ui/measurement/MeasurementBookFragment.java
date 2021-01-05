package com.raidify.mobi.mycouturier.ui.measurement;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
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

import com.raidify.mobi.mycouturier.R;
import com.raidify.mobi.mycouturier.ROOMDB.model.Measurement;
import com.raidify.mobi.mycouturier.adapter.MeasureEntryRecyclerAdapter;
import com.raidify.mobi.mycouturier.adapter.MeasurementRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MeasurementBookFragment extends Fragment implements LifecycleOwner {

    private MeasurementBookViewModel mViewModel;
    private RecyclerView measurementRecycleView;
    private MeasurementRecyclerAdapter measurementRecyclerAdapter;
    private Button clearBtn;


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
        mViewModel = new ViewModelProvider(requireActivity()).get(MeasurementBookViewModel.class); //TODO: modified this
        mViewModel.getMeasurementList().observe(getViewLifecycleOwner(), measurementListObserver);
        //find the recycler view
        measurementRecycleView = getView().findViewById(R.id.measurementRecycleView);
        measurementRecycleView.setHasFixedSize(true);

        //TODO: Refactor code . Was used for test purposes
        clearBtn = getView().findViewById(R.id.clearMeasurementBtn);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.clearAllMeasurements();
            }
        });

    }

    Observer<List<Measurement>> measurementListObserver = new Observer<List<Measurement>>() {
        @Override
        public void onChanged(List<Measurement> measurementList) {
            measurementRecyclerAdapter = new MeasurementRecyclerAdapter(getActivity(),measurementList);
            measurementRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
            measurementRecycleView.setAdapter(measurementRecyclerAdapter);
        }
    };

}