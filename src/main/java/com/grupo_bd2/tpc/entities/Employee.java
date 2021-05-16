package com.grupo_bd2.tpc.entities;

import com.google.gson.Gson;

import org.bson.types.ObjectId;

public class Employee {

  private ObjectId id;
  private Address address;
  private ObjectId store;
  private Boolean isManager;
  private long cuil;
  private int dni;
  private String name;
  private String surname;
  private String medicalInsurer;
  private int affiliateNumber;

  public Employee() {
  }

  public Employee(Address address,ObjectId store, Boolean isManager, long cuil, int dni, String name, String surname, String medicalInsurer, int affiliateNumber) {

    this.address = address;
    this.store = store;
    this.isManager = isManager;
    this.cuil = cuil;
    this.dni = dni;
    this.name = name;
    this.surname = surname;
    this.medicalInsurer = medicalInsurer;
    this.affiliateNumber = affiliateNumber;
  }

  public ObjectId getId() {
    return this.id;
  }

  public void setId(ObjectId id) {
    this.id = id;
  }

  public Address getAddress() {
    return this.address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public ObjectId getStore() {
    return this.store;
  }

  public void setStore(ObjectId store) {
    this.store = store;
  }

  public long getCuil() {
    return this.cuil;
  }

  public void setCuil(long cuil) {
    this.cuil = cuil;
  }

  public int getDni() {
    return this.dni;
  }

  public void setDni(int dni) {
    this.dni = dni;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return this.surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getMedicalInsurer() {
    return this.medicalInsurer;
  }

  public void setMedicalInsurer(String medicalInsurer) {
    this.medicalInsurer = medicalInsurer;
  }

  public int getAffiliateNumber() {
    return this.affiliateNumber;
  }

  public void setAffiliateNumber(int affiliateNumber) {
    this.affiliateNumber = affiliateNumber;
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