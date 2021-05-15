package com.grupo_bd2.tpc.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grupo_bd2.tpc.config.Config;
import com.grupo_bd2.tpc.entities.Address;
import com.grupo_bd2.tpc.entities.Store;
import com.mongodb.client.MongoCollection;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class StoreService {

  private static StoreService storeService;
  private MongoCollection<Store> StoreCollection = Config.getInstance().getMongoDatabase().getCollection("store", Store.class);

  public static StoreService getInstance() {

    if (storeService == null) {
      storeService = new StoreService();
    }

    return storeService;
  }

  public void insertOne(Store store) {

    StoreCollection.insertOne(store);
  }

  public List<Store> findAll() {

    List<Store> storeList = new ArrayList<Store>();

    StoreCollection.find().into(storeList);

    return storeList;
  }

  public String exportAll() throws IOException {

    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();
    String json = null;
    Writer writer = new FileWriter("store.json");

    gson.toJson(findAll(), writer);
    writer.flush();
    writer.close();
    json = gson.toJson(findAll());

    return json;
  }

}
