package com.grupo_bd2.tpc.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grupo_bd2.tpc.config.Config;
import com.grupo_bd2.tpc.entities.Item;
import com.mongodb.client.MongoCollection;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ItemService {

  private static ItemService itemService;
  private MongoCollection<Item> itemCollection = Config.getInstance().getMongoDatabase().getCollection("item", Item.class);

  public static ItemService getInstance() {

    if (itemService == null) {
      itemService = new ItemService();
    }

    return itemService;
  }

  public void insertOne(Item item) {

    itemCollection.insertOne(item);
  }

  public List<Item> findAll() {

    List<Item> items = new ArrayList<Item>();

    itemCollection.find().into(items);

    return items;
  }

  public String exportAll() throws IOException {

    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();
    String json = null;
    Writer writer = new FileWriter("item.json");

    gson.toJson(findAll(), writer);
    writer.flush();
    writer.close();
    json = gson.toJson(findAll());

    return json;
  }

}
