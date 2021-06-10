package com.grupo_bd2.tpc.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grupo_bd2.tpc.config.Config;
import com.grupo_bd2.tpc.entities.Item;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ItemService {

  private static ItemService itemService;
  private MongoCollection<Item> itemCollection = Config.getInstance().getMongoDatabase().getCollection("items", Item.class);

  public static ItemService getInstance() {

    if (itemService == null) {
      itemService = new ItemService();
    }

    itemService.createUniqueIndex();

    return itemService;
  }

  public void createUniqueIndex() {

    /*
    se crea un index unico para no cargar documentos duplicados
    */

    BasicDBObject obj = new BasicDBObject();

    //se cargan los campos sobre los cuales el index va a chequear
    obj.put("description", 1);
    obj.put("manufacturer", 1);

    itemCollection.createIndex(obj, new IndexOptions().unique(true));
  }

  public void insert(Item item) {

    itemCollection.insertOne(item);
  }

  public List<Item> findAll() {

    List<Item> items = new ArrayList<Item>();

    itemCollection.find().into(items);

    return items;
  }

  public String exportAll(Boolean write) throws IOException {

    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();
    String json = null;

    json = gson.toJson(findAll());

    if(write) {

      Writer writer = new FileWriter("items.json");

      gson.toJson(findAll(), writer);
      writer.flush();
      writer.close();

    }

    return json;
  }

}
