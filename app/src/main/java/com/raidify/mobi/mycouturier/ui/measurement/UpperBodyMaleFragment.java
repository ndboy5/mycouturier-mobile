package com.raidify.mobi.mycouturier.ui.measurement;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raidify.mobi.mycouturier.R;

public class UpperBodyMaleFragment extends Fragment {

    private UpperBodyMaleViewModel mViewModel;

    public static UpperBodyMaleFragment newInstance() {
        return new UpperBodyMaleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.upper_body_male_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(UpperBodyMaleViewModel.class);
        // TODO: Use the ViewModel
    }

}