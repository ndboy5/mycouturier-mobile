package com.raidify.mobi.mycouturier.ROOMDB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.raidify.mobi.mycouturier.ROOMDB.model.Measurement;

import java.util.List;

@Dao
public interface MeasurementDAO {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    Long insertMeasurement (Measurement measurement); //TODO: Test the return value after insert to get the ID

    @Query("SELECT * FROM Measurements")
    List<Measurement> getAllMeasurements();

    @Query("DELETE FROM Measurements WHERE id= :id")
    void deleteMeasurement(int id);

    @Query("SELECT * FROM Measurements WHERE id= :id")
    Measurement getMeasurement(int id);
}
