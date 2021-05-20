package com.grupo_bd2.tpc.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grupo_bd2.tpc.config.Config;
import com.grupo_bd2.tpc.entities.Store;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Updates;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class StoreService {

  private static StoreService storeService;
  private MongoCollection<Store> StoreCollection = Config.getInstance().getMongoDatabase().getCollection("stores", Store.class);

  public static StoreService getInstance() {

    if (storeService == null) {
      storeService = new StoreService();
    }

    return storeService;
  }

  public void createUniqueIndex() {

    /*
    se crea un index unico para no cargar documentos duplicados
    */

    BasicDBObject obj = new BasicDBObject();

    //se cargan los campos sobre los cuales el index va a chequear
    obj.put("date", 1);
    obj.put("ticketNumber", 1);

    StoreCollection.createIndex(obj, new IndexOptions().unique(true));
  }

  public Store insert(Store store) {

    //se inserta la store y se consigue el ObjectId generado por Mongo, para devolver la misma
    //store con su id correspondiente.
    store.setId(StoreCollection.insertOne(store).getInsertedId().asObjectId().getValue());

    return store;
  }

  public void update(Store store) {

    StoreCollection.updateOne(Filters.eq("_id", store.getId()), Updates.set("employees", store.getEmployees()));
  }

  public List<Store> findAll() {

    List<Store> storeList = new ArrayList<Store>();

    StoreCollection.find().into(storeList);

    return storeList;
  }

  public String exportAll(Boolean write) throws IOException {

    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();
    String json = null;

    json = gson.toJson(findAll());

    if(write) {

      Writer writer = new FileWriter("stores.json");

      gson.toJson(findAll(), writer);
      writer.flush();
      writer.close();

    }

    return json;
  }

}
