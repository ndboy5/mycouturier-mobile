package com.raidify.mobi.mycouturier.ROOMDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.raidify.mobi.mycouturier.ROOMDB.model.MeasureEntry;
import com.raidify.mobi.mycouturier.ROOMDB.model.Measurement;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Measurement.class, MeasureEntry.class}, version = 2)
public abstract class MCDatabase extends RoomDatabase {

    public abstract MeasurementDAO measurementDAO();
    public abstract MeasureEntryDAO measureEntryDAO();

    private static MCDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =  Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    static MCDatabase getDatabase(final Context context) {
    if (INSTANCE == null) {
        synchronized (MCDatabase.class) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MCDatabase.class,"mycouturier_database").build();
            }
        }
    }
    return INSTANCE;
}

}
