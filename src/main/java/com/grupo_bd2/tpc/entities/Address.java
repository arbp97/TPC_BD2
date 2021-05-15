package com.grupo_bd2.tpc.entities;

import org.bson.types.ObjectId;
import com.google.gson.Gson;

public class Address {

  private ObjectId id;
  private int number;
  private String street;
  private String city;
  private String province;

  public Address() {
  }

  public Address(int number, String street, String city, String province) {

    this.number = number;
    this.street = street;
    this.city = city;
    this.province = province;
  }

  public ObjectId getId() {
    return this.id;
  }

  public void setId(final ObjectId id) {
    this.id = id;
  }

  public int getNumber() {
    return this.number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public String getStreet() {
    return this.street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getCity() {
    return this.city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getProvince() {
    return this.province;
  }

  public void setProvince(String province) {
    this.province = province;
  }


  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }
}