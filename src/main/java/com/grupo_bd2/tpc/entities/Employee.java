package com.grupo_bd2.tpc.entities;

import com.google.gson.Gson;

import org.bson.types.ObjectId;

public class Employee extends Person {

  private ObjectId store;
  private Boolean isManager;
  private String cuil;

  public Employee() {
  }

  public Employee(Address address, ObjectId store, Boolean isManager, String cuil, int dni, String name, String surname,
      Insurance insurance, String affiliateNumber) {

    super(address, dni, name, surname, insurance, affiliateNumber);
    this.store = store;
    this.isManager = isManager;
    this.cuil = cuil;

  }

  public ObjectId getStore() {
    return this.store;
  }

  public void setStore(ObjectId store) {
    this.store = store;
  }

  public String getCuil() {
    return this.cuil;
  }

  public void setCuil(String cuil) {
    this.cuil = cuil;
  }

  public Boolean getIsManager() {
    return this.isManager;
  }

  public void setIsManager(Boolean isManager) {
    this.isManager = isManager;
  }

  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }

}