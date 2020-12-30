/**
 * This view model is used by the Measurements fragments for the creation of new Measurement Records
 */

package com.raidify.mobi.mycouturier.ui.measurement;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.raidify.mobi.mycouturier.R;
import com.raidify.mobi.mycouturier.ROOMDB.Repository;
import com.raidify.mobi.mycouturier.ROOMDB.model.MeasureEntry;
import com.raidify.mobi.mycouturier.ROOMDB.model.Measurement;
import com.raidify.mobi.mycouturier.util.Constants;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import static com.raidify.mobi.mycouturier.util.Constants.BUST;
import static com.raidify.mobi.mycouturier.util.Constants.CENTERBACK;
import static com.raidify.mobi.mycouturier.util.Constants.FULLBODY;
import static com.raidify.mobi.mycouturier.util.Constants.HIGHBUST;
import static com.raidify.mobi.mycouturier.util.Constants.INSEAM;
import static com.raidify.mobi.mycouturier.util.Constants.OUTSEAM;
import static com.raidify.mobi.mycouturier.util.Constants.SHIRT;
import static com.raidify.mobi.mycouturier.util.Constants.TROUSER;
import static com.raidify.mobi.mycouturier.util.Constants.WAIST;

public class NewMeasurementViewModel extends AndroidViewModel {
    private final MutableLiveData<Float> currEntry = new MutableLiveData<>();
    private final Measurement measurement = new Measurement();

//    private List<Measurement> allMeasurements;
    private Repository repository;
    private Set<String> selectedBodyPartList = new HashSet<String>();
    private List<MeasureEntry> measureEntries = new ArrayList<>();


    //TODO: Initialize it with a nice initial capacity and loading factor for production
    private  static final Map<String, String[]> clothingStyleTree = new HashMap<String, String[]>();

    //Constructor
    public NewMeasurementViewModel (Application application) {
        super(application);
        repository = new Repository(application);
 //        allMeasurements = repository.getAllMeasurements();

        //Initialize the clothing Style Tree.
        initializeClothingStylesTree();
    }

    public MeasureEntry getMeasureEntryByListIndex(int index){
        return measureEntries.get(index);
    }

   //This method updates the List of measure entries being used by the MeasureBodyPart Fragment
   //This does not save to the Local DB but only saves the changes on the view model Array List
    public void updateMeasureEntry(int rowIndex, MeasureEntry mEntry){
        this.measureEntries.set(rowIndex, mEntry);
    }

    //Returns measurement Entries TODO: Make the entries live data for the UI
    public List<MeasureEntry> getMeasureBodyPart(){
        return measureEntries;
    }

    public void setGender(int genderIndex){
    String gender;
        if (genderIndex==0){
        gender = "M";
        } else gender = "F";

        measurement.setGender(gender);
    }

    public void setDescription(String text){
        measurement.setDescription(text);
    }

    public void setMeasurementUnit(int selectedUnit){
        switch (selectedUnit) {
            case R.id.cmUnit:
                measurement.setUnitOfMeasure("cm");
                break;
            case R.id.mmUnit:
                measurement.setUnitOfMeasure("mm");
                break;
            case R.id.inchUnit:
                measurement.setUnitOfMeasure("inch");
                break;

            default:
                measurement.setUnitOfMeasure("cm");
                break;
        }

    }

    // This method saves the measurement details and obtains the measurement ID before saving the entries
    //TODO: Consider moving the method call to the NewMeasurements fragment (separating concerns)
    public void saveMeasurementToLocalDB(){
    Long measurementRowId  = repository.insertMeasurement(this.measurement);
    Log.i("NDBOY", "Measurement" + measurement.getDescription() + "inserted successfully with ID: " + measurementRowId);
    //TODO
    saveMeasurementEntriesToLocalDB(measurementRowId);
    }

    //This method is called by the saveMeasurementToLocalDB method.
    //It saves the measurementEntries list when the user is done with measurement entries.
    private void saveMeasurementEntriesToLocalDB(Long measurementId){
        for (MeasureEntry me: this.measureEntries){
            me.setMeasurementId(measurementId);
            repository.insertMeasureEntry(me);
        }

        Log.i("NDBOY", this.measureEntries.size() +  " Measurement entries inserted successfully"); //TODO: For delete
    }

    //This method takes the clothing style/categories selected by the user as input.
    //It then finds the corresponding body parts from the Tree and updates the measure Part List afterwards
    public void generateBodyPartList(List<Integer> selectedClothingStyles){
        String styleKey = "";
        for (int styleId: selectedClothingStyles ){
            //update Body part HashMap
            switch (styleId){
                case R.id.fullBodyChip:
                    styleKey = FULLBODY;
                    Log.i("NDBOY", "full body");
                    break;
                case R.id.shirtChip:
                    styleKey = SHIRT;
                    Log.i("NDBOY", "shirt");
                    break;
                case R.id.trouserChip:
                    styleKey = TROUSER;
                    Log.i("NDBOY", "trouser");
                    break;
                default:
                    //TODO:
                    break;
            }
            // add parts to the HashMap
            for (int i = 0; i <clothingStyleTree.get(styleKey).length ; i++) {
                selectedBodyPartList.add(clothingStyleTree.get(styleKey)[i]);
            }

            Log.i("NDBOY", "Selected Part list count :" + selectedBodyPartList.size()); //TODO: Remove log test code

    }

        //Build the List of measure Entries before loading to UI/Recycle Adapter
        generateMeasureEntryList();
    }

    /**
     * This method converts the MeasureEntry to a list.
     * The use of LIST as against a SET or MAP for the Recycler View object is advisable
     */
    private void generateMeasureEntryList(){
        List<MeasureEntry> newMeasureEntries= new ArrayList<>();
        for (String part: selectedBodyPartList){
            MeasureEntry measureEntry = new MeasureEntry();
            measureEntry.setPart(part);
            measureEntry.setLength(0F); //TODO: Modify from DB to default to zero (0)
            newMeasureEntries.add(measureEntry);
        }
        this.measureEntries = newMeasureEntries;
        Log.i("NDBOY", "Measure Entry list count: " + this.measureEntries.size());

    }


    private void initializeClothingStylesTree(){
        //TODO: Work with professional designers to build this  Tree to salutary standard
        final String[]  shirtBodyParts = new String[]{BUST, HIGHBUST, CENTERBACK};
        final String[]  fullBodyParts = new String[]{BUST, HIGHBUST, CENTERBACK, WAIST, OUTSEAM, INSEAM};
        final String[]  trouserBodyParts = new String[]{WAIST, OUTSEAM, INSEAM};
//        Add clothing style and corresponding body parts to tree
        clothingStyleTree.put(SHIRT , shirtBodyParts );
        clothingStyleTree.put(TROUSER, trouserBodyParts);
        clothingStyleTree.put(FULLBODY, fullBodyParts);
    }

}
