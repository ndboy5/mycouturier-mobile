package com.raidify.mobi.mycouturier.ROOMDB.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MeasureEntry")
public class MeasureEntry {
 @PrimaryKey
 @ColumnInfo(name = "id")
 private int id;
 @ColumnInfo(name = "measurementId")
 private int measurementId;
 @ColumnInfo(name = "part")
 private String part;
 @ColumnInfo(name = "length")
 private Float length;

 public int getId() {
  return id;
 }

 public void setId(int id) {
  this.id = id;
 }

 public int getMeasurementId() {
  return measurementId;
 }

 public void setMeasurementId(int measurementId) {
  this.measurementId = measurementId;
 }

 public String getPart() {
  return part;
 }

 public void setPart(String part) {
  this.part = part;
 }

 public Float getLength() {
  return length;
 }

 public void setLength(Float length) {
  this.length = length;
 }
}
