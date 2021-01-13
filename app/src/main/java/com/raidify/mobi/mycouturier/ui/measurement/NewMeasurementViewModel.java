/**
 * This view model is used by the Measurements fragments for the creation of new Measurement Records
 */

package com.raidify.mobi.mycouturier.ui.measurement;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.raidify.mobi.mycouturier.R;
import com.raidify.mobi.mycouturier.ROOMDB.Repository;
import com.raidify.mobi.mycouturier.ROOMDB.RepositoryMeasureEntryCallback;
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

import static com.raidify.mobi.mycouturier.util.Constants.ANKLE;
import static com.raidify.mobi.mycouturier.util.Constants.BUST;
import static com.raidify.mobi.mycouturier.util.Constants.CENTERBACK;
import static com.raidify.mobi.mycouturier.util.Constants.FULLBODY;
import static com.raidify.mobi.mycouturier.util.Constants.HAT;
import static com.raidify.mobi.mycouturier.util.Constants.HIGHBUST;
import static com.raidify.mobi.mycouturier.util.Constants.INSEAM;
import static com.raidify.mobi.mycouturier.util.Constants.LONGSVSHIRT;
import static com.raidify.mobi.mycouturier.util.Constants.OUTSEAM;
import static com.raidify.mobi.mycouturier.util.Constants.SHORTSVSHIRT;
import static com.raidify.mobi.mycouturier.util.Constants.SKIRT;
import static com.raidify.mobi.mycouturier.util.Constants.TROUSER;
import static com.raidify.mobi.mycouturier.util.Constants.WAIST;

public class NewMeasurementViewModel extends AndroidViewModel {
    private final Measurement measurement = new Measurement();

    private Repository repository;
    private Set<String> selectedBodyPartList;
    private MutableLiveData<List<MeasureEntry>> measureEntries;


    //TODO: Initialize it with a nice initial capacity and loading factor for production
    private  static final Map<String, String[]> clothingStyleTree = new HashMap<String, String[]>();

    //Constructor
    public NewMeasurementViewModel (Application application) {
        super(application);
        repository = new Repository(application);
        selectedBodyPartList = new HashSet<>();
        measureEntries = new MutableLiveData<>();

        //Initialize the clothing Style Tree.
        initializeClothingStylesTree();
    }

    public Measurement getActiveMeasurement(){
        return this.measurement;
    }
    //Returns measurement Entries TODO: Make the entries live data for the UI
    public MutableLiveData<List<MeasureEntry>> getMeasureEntries(){
        return this.measureEntries;
    }

    public MeasureEntry getMeasureEntryByListIndex(int index){
        return measureEntries.getValue().get(index);
    }

   //This method updates the List of measure entries being used by the MeasureBodyPart Fragment
   //This does not save to the Local DB but only saves the changes on the view model Array List
    public void updateMeasureEntry(int rowIndex, MeasureEntry mEntry){
        this.measureEntries.getValue().set(rowIndex, mEntry);
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
                measurement.setUnit("cm");
                break;
            case R.id.mmUnit:
                measurement.setUnit("mm");
                break;
            case R.id.inchUnit:
                measurement.setUnit("inch");
                break;

            default:
                measurement.setUnit("cm");
                break;
        }

    }

    // This method saves the measurement details and obtains the measurement ID before saving the entries
    //TODO: Consider moving the method call to the NewMeasurements fragment (separating concerns)
    public void saveNewMeasurementToLocalDB(){
        try {

            Long measurementRowId  = repository.insertMeasurement(this.measurement, this.measureEntries.getValue());
        } catch (Exception e){
            Log.i("NDBOY", "error while saving measurement: " + e);
        }
    }

    //This method is called by the saveMeasurementToLocalDB method.
    //It saves the measurementEntries list when the user is done with measurement entries.
    private void saveMeasurementEntriesToLocalDB(Long measurementId){


    }

    //This method takes the clothing style/categories selected by the user as input.
    //It then finds the corresponding body parts from the Tree and updates the measure Part List afterwards
    public void generateBodyPartList(List<Integer> selectedClothingStyles){
        if (this.selectedBodyPartList!=null) this.selectedBodyPartList.clear(); //Clear the list;

        String styleKey = "";
        for (int styleId: selectedClothingStyles ){
            //update Body part HashMap
            switch (styleId){
                case R.id.fullBodyChip:
                    styleKey = FULLBODY;
                    Log.i("NDBOY", "full body");
                    break;
                case R.id.LnshirtChip:
                    styleKey = LONGSVSHIRT;
                    Log.i("NDBOY", "Long Sleeve shirt");
                    break;
                case R.id.trouserChip:
                    styleKey = TROUSER;
                    Log.i("NDBOY", "trouser");
                    break;
                case R.id.STSVShirtChip:
                    styleKey = SHORTSVSHIRT;
                    Log.i("NDBOY", "short sleeve shirt");
                    break;
                case R.id.hatChip:
                    styleKey = HAT;
                    Log.i("NDBOY", "Hat");
                    break;
                case R.id.skirtChip:
                    styleKey = SKIRT;
                    Log.i("NDBOY", "skirt");
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
      if (this.measureEntries.getValue()!=null) this.measureEntries.getValue().clear(); //clear list;

        List<MeasureEntry> newMeasureEntries= new ArrayList<>();
        for (String part: selectedBodyPartList){
            MeasureEntry measureEntry = new MeasureEntry();
            measureEntry.setPart(part);
            measureEntry.setLength(0F); //TODO: Modify from DB to default to zero (0)
            newMeasureEntries.add(measureEntry);
        }
        this.measureEntries.setValue(newMeasureEntries);
        Log.i("NDBOY", "Measure Entry list count: " + this.measureEntries.getValue().size());

    }

    private void initializeClothingStylesTree(){
        //TODO: Work with professional designers to build this  Tree to salutary standard
        final String[]  shortSleeve = new String[]{BUST, HIGHBUST, CENTERBACK, ANKLE};
        final String[]  fullBodyParts = new String[]{BUST, HIGHBUST, CENTERBACK, WAIST, OUTSEAM, INSEAM, Constants.CROTCHLENGHT};
        final String[]  trouser = new String[]{WAIST, OUTSEAM, INSEAM};
        final String[]  hatBodyParts = new String[]{Constants.HEAD};
        final String[]  skirt = new String[]{WAIST, INSEAM, ANKLE};
        final String[]  longSleeveShirt = new String[]{WAIST, OUTSEAM, INSEAM, };

//        Add clothing style and corresponding body parts to tree
        clothingStyleTree.put(SHORTSVSHIRT, shortSleeve );
        clothingStyleTree.put(LONGSVSHIRT, longSleeveShirt );
        clothingStyleTree.put(TROUSER, trouser);
        clothingStyleTree.put(FULLBODY, fullBodyParts);
        clothingStyleTree.put(SKIRT, skirt);
        clothingStyleTree.put(HAT, hatBodyParts);
    }

}
