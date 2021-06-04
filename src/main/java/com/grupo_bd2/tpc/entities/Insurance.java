package com.grupo_bd2.tpc.entities;

import com.google.gson.Gson;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Insurance {

    @BsonProperty("_id")
    @BsonId
    private ObjectId id;
    private String name;
    private String insuranceCode;

    public Insurance() {}

    public Insurance(String name,String insuranceCode) {
        this.name = name;
        this.insuranceCode = insuranceCode;

    }

    public ObjectId getId() {
        return this.id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInsuranceCode() {
        return this.insuranceCode;
    }

    public void setInsuranceCode(String insuranceCode) {
        this.insuranceCode = insuranceCode;
    }

    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
      }

}
