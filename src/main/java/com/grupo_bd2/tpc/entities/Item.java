package com.grupo_bd2.tpc.entities;

import com.google.gson.Gson;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Item {

  @BsonProperty("_id")
  @BsonId
  private ObjectId id;
  private String description;
  private float price;
  private String manufacturer;
  private Boolean isMedicine;

  public Item() {
  }

  public Item(String description, float price, String manufacturer, Boolean isMedicine) {

    this.description = description;
    this.price = price;
    this.manufacturer = manufacturer;
    this.isMedicine = isMedicine;
  }

  public ObjectId getId() {
    return this.id;
  }

  public void setId(ObjectId id) {
    this.id = id;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public float getPrice() {
    return this.price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public String getManufacturer() {
    return this.manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public Boolean getIsMedicine() {
    return this.isMedicine;
  }

  public void setIsMedicine(Boolean isMedicine) {
    this.isMedicine = isMedicine;
  }

  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }
}
