/**
 * This view model is used by the Measurements fragments for the creation of new Measurement Records
 */

package com.raidify.mobi.mycouturier.ui.measurement;

import android.app.Application;
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

    public LiveData<Float> getCurrEntry(){
        return currEntry;
    }

    public void setCurrEntry(Float m){
        this.currEntry.setValue(m);
    }

    public void setUpperBDPartEntry(String val, int buttonID) {



            setCurrEntry(Float.valueOf(val));

            //TODO: Update the model with the current Values
        }


    public void setActiveBtnBackgroundColor(View view){
        ImageButton imageButton = (ImageButton) view;
        imageButton.setBackgroundResource(R.color.colorPrimaryDark); //TODO: Find a better color jor
     }


     //TODO: Resolve the error in this method
    public void resetActiveBtnBackgroundColor(View view){
        ImageButton imageButton = (ImageButton) view;
        imageButton.setBackgroundResource(R.color.WHITE); //TODO: Find a better color
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

//TODO:
    public void saveMeasurementToLocalDB(){
    repository.insertMeasurement(this.measurement);
    Log.i("NDBOY", "measurement inserted successfully.");
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
    }

    /**
     * This method converts the MeasureEntry to a list.
     * The use of LIST as against a SET or MAP for the Recycler View object is advisable
     */
    public List<MeasureEntry> generateMeasureEntryList(){
        List<MeasureEntry> newMeasureEntries= new ArrayList<>();
        for (String part: selectedBodyPartList){
            MeasureEntry measureEntry = new MeasureEntry();
            measureEntry.setPart(part);
            measureEntry.setLength(0F); //TODO: Modify from DB to default to zero (0)
            newMeasureEntries.add(measureEntry);
        }
        this.measureEntries = newMeasureEntries;
        Log.i("NDBOY", "Measure Entry list count: " + this.measureEntries.size());
        return this.measureEntries;
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
