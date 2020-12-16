package com.raidify.mobi.mycouturier.model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.raidify.mobi.mycouturier.R;

public class Measurement extends ViewModel {
    private String description;
    private  String gender;
    private String unitOfMeasure;
    private float bust;
    private float highBust;
    private float   waist;
    private float centerBack;
    private float  crotchLength;
    private float  hip;
    private float  inseam;
    private float  outseam;
    private float  shoulder;
    private float  ankle;
    private float  neck;
    private float  wrist;
    private float  fullHeight;
    private boolean  isFavourite;
    private String  lastupdateBy;


    //Getters and setters for Measurement attributes
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public float getBust() {
        return bust;
    }

    public void setBust(float bust) {
        this.bust = bust;
    }

    public float getHighBust() {
        return highBust;
    }

    public void setHighBust(float highBust) {
        this.highBust = highBust;
    }

    public float getWaist() {
        return waist;
    }

    public void setWaist(float waist) {
        this.waist = waist;
    }

    public float getCenterBack() {
        return centerBack;
    }

    public void setCenterBack(float centerBack) {
        this.centerBack = centerBack;
    }

    public float getCrotchLength() {
        return crotchLength;
    }

    public void setCrotchLength(float crotchLength) {
        this.crotchLength = crotchLength;
    }

    public float getHip() {
        return hip;
    }

    public void setHip(float hip) {
        this.hip = hip;
    }

    public float getInseam() {
        return inseam;
    }

    public void setInseam(float inseam) {
        this.inseam = inseam;
    }

    public float getOutseam() {
        return outseam;
    }

    public void setOutseam(float outseam) {
        this.outseam = outseam;
    }

    public float getShoulder() {
        return shoulder;
    }

    public void setShoulder(float shoulder) {
        this.shoulder = shoulder;
    }

    public float getAnkle() {
        return ankle;
    }

    public void setAnkle(float ankle) {
        this.ankle = ankle;
    }

    public float getNeck() {
        return neck;
    }

    public void setNeck(float neck) {
        this.neck = neck;
    }

    public float getWrist() {
        return wrist;
    }

    public void setWrist(float wrist) {
        this.wrist = wrist;
    }

    public float getFullHeight() {
        return fullHeight;
    }

    public void setFullHeight(float fullHeight) {
        this.fullHeight = fullHeight;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public String getLastupdateBy() {
        return lastupdateBy;
    }

    public void setLastupdateBy(String lastupdateBy) {
        this.lastupdateBy = lastupdateBy;
    }

}