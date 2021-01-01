package com.raidify.mobi.mycouturier.ROOMDB;

import androidx.lifecycle.LiveData;

import com.raidify.mobi.mycouturier.ROOMDB.model.Measurement;

import java.util.List;

public interface RepositoryCallback {
    void onComplete(List<Measurement> result);
}
