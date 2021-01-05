package com.raidify.mobi.mycouturier.ROOMDB;

import com.raidify.mobi.mycouturier.ROOMDB.model.MeasureEntry;
import com.raidify.mobi.mycouturier.ROOMDB.model.Measurement;

import java.util.List;

public interface RepositoryMeasureEntryCallback {
    void onComplete(List<MeasureEntry> result);
}
