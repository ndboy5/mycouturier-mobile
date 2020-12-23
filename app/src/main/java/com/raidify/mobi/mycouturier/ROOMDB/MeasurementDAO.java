package com.raidify.mobi.mycouturier.ROOMDB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.raidify.mobi.mycouturier.ROOMDB.model.Measurement;

import java.util.List;

@Dao
public interface MeasurementDAO {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertMeasurement (Measurement measurement);

    @Query("SELECT * FROM Measurements")
    List<Measurement> getAllMeasurements();

    @Query("DELETE FROM Measurements WHERE id= :id")
    void deleteMeasurement(int id);
}
