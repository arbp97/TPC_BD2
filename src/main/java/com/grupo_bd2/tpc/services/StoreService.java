package com.grupo_bd2.tpc.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grupo_bd2.tpc.config.Config;
import com.grupo_bd2.tpc.entities.Address;
import com.grupo_bd2.tpc.entities.Store;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

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

  public Store insertOne(Store store) {

    StoreCollection.insertOne(store);

    return StoreCollection.find().sort(new BasicDBObject("_id", -1)).first(); //retorna el ultimo documento creado.
  }

  public void update(Store store) {

    StoreCollection.updateOne(Filters.eq("_id", store.getId()), Updates.set("employees", store.getEmployees()));
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
