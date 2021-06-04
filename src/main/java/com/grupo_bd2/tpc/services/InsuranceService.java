package com.grupo_bd2.tpc.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grupo_bd2.tpc.config.Config;
import com.grupo_bd2.tpc.entities.Insurance;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class InsuranceService {

  private static InsuranceService insuranceService;
  private MongoCollection<Insurance> insuranceCollection = Config.getInstance().getMongoDatabase().getCollection("insurances", Insurance.class);

  public static InsuranceService getInstance() {

    if (insuranceService == null) {
      insuranceService = new InsuranceService();
    }

    return insuranceService;
  }

  public void createUniqueIndex() {

    /*
    se crea un index unico para no cargar documentos duplicados
    */

    BasicDBObject obj = new BasicDBObject();

    //se cargan los campos sobre los cuales el index va a chequear
    obj.put("name", 1);
    obj.put("insuranceCode", 1);

    insuranceCollection.createIndex(obj, new IndexOptions().unique(true));
}

  public void insert(Insurance insurance) throws MongoWriteException{

    insuranceCollection.insertOne(insurance);
  }

  public List<Insurance> findAll() {

    List<Insurance> insurances = new ArrayList<Insurance>();

    insuranceCollection.find().into(insurances);

    return insurances;
  }

  public String exportAll(Boolean write) throws IOException {

    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();
    String json = null;

    json = gson.toJson(findAll());

    if(write) {

      Writer writer = new FileWriter("insurances.json");

      gson.toJson(findAll(), writer);
      writer.flush();
      writer.close();

    }

    return json;
  }

}
