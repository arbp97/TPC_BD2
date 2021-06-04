package com.grupo_bd2.tpc.entities;

import com.google.gson.Gson;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Person {

  @BsonProperty("_id")
  @BsonId
  private ObjectId id;
  private Address address;
  private Insurance insurance;
  private int dni;
  private String name;
  private String surname;
  private String affiliateNumber;

  public Person() {
  }

  public Person(Address address, int dni, String name, String surname, Insurance insurance, String affiliateNumber) {

    this.address = address;
    this.dni = dni;
    this.name = name;
    this.surname = surname;
    this.insurance = insurance;
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

  public Insurance getInsurance() {
    return this.insurance;
  }

  public void setInsurance(Insurance insurance) {
    this.insurance = insurance;
  }

  public String getAffiliateNumber() {
    return this.affiliateNumber;
  }

  public void setAffiliateNumber(String affiliateNumber) {
    this.affiliateNumber = affiliateNumber;
  }

  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }
}
