package com.grupo_bd2.tpc.entities;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;

import org.bson.types.ObjectId;

public class Store {

  private ObjectId id;
  private Address address;
  private int pointOfSaleCode;
  private Set<Employee> employees;

  public Store() {
  }

  public Store(Address address, int pointOfSaleCode) {

    this.address = address;
    this.employees = new HashSet<Employee>();
    this.pointOfSaleCode = pointOfSaleCode;
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

  public int getPointOfSaleCode() {
    return this.pointOfSaleCode;
  }

  public void setPointOfSaleCode(int pointOfSaleCode) {
    this.pointOfSaleCode = pointOfSaleCode;
  }

  public Set<Employee> getEmployees() {
    return this.employees;
  }

  public void setEmployees(Set<Employee> employees) {
    this.employees = employees;
  }

  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }

}
