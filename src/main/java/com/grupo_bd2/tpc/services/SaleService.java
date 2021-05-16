package com.grupo_bd2.tpc.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grupo_bd2.tpc.config.Config;
import com.grupo_bd2.tpc.entities.Sale;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class SaleService {

  private static SaleService saleService;
  private MongoCollection<Sale> saleCollection = Config.getInstance().getMongoDatabase().getCollection("sales", Sale.class);

  public static SaleService getInstance() {

    if (saleService == null) {
      saleService = new SaleService();
    }

    return saleService;
  }

  public void createUniqueIndex() {

    /*
    se crea un index unico para no cargar documentos duplicados
    */

    BasicDBObject obj = new BasicDBObject();

    //se cargan los campos sobre los cuales el index va a chequear
    obj.put("date", 1);
    obj.put("ticketNumber", 1);

    saleCollection.createIndex(obj, new IndexOptions().unique(true));
  }

  public void insert(Sale sale) {

    saleCollection.insertOne(sale);
  }

  public List<Sale> findAll() {

    List<Sale> saleList = new ArrayList<Sale>();

    saleCollection.find().into(saleList);

    return saleList;
  }

  public String exportAll() throws IOException {

    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();
    String json = null;
    Writer writer = new FileWriter("sales.json");

    gson.toJson(findAll(), writer);
    writer.flush();
    writer.close();
    json = gson.toJson(findAll());

    return json;
  }

}
