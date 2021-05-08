package com.grupo_bd2.tpc.entities;
import com.google.gson.Gson;

import org.bson.types.ObjectId;

public class Client {

    private ObjectId id;
    private Address address;
    private int dni;
    private String name;
    private String surname;
    private String medicalInsurer;
    private int affiliateNumber;

    public Client() {}

    public Client(Address address, int dni, String name, String surname, String medicalInsurer, int affiliateNumber) {

        this.address = address;
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

    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
