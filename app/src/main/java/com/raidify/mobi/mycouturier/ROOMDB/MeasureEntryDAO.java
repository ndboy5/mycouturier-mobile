package com.raidify.mobi.mycouturier.ROOMDB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.raidify.mobi.mycouturier.ROOMDB.model.MeasureEntry;
import com.raidify.mobi.mycouturier.ROOMDB.model.Measurement;

import java.util.List;

@Dao
public interface MeasureEntryDAO {
    //Inserts a new entry
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertEntry (MeasureEntry entry);

    //Retrieves body length entries belonging to a single measurement
    @Query("SELECT * FROM MeasureEntry WHERE measurementId =:measureId")
    List<MeasureEntry> getMeasureEntries(int measureId);

    //Deletes all body length entries belonging to a single Measurement by ID
    @Query("DELETE FROM MeasureEntry WHERE measurementId =:id")
    void deleteMeasurement(int id);
}
