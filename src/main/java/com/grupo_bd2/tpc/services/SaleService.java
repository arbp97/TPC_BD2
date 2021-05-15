package com.grupo_bd2.tpc.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grupo_bd2.tpc.config.Config;
import com.grupo_bd2.tpc.entities.Address;
import com.grupo_bd2.tpc.entities.Sale;
import com.mongodb.client.MongoCollection;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class SaleService {

  private static SaleService saleService;
  private MongoCollection<Sale> saleCollection = Config.getInstance().getMongoDatabase().getCollection("sale", Sale.class);

  public static SaleService getInstance() {

    if (saleService == null) {
      saleService = new SaleService();
    }

    return saleService;
  }

  public void insertOne(Sale sale) {

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
    Writer writer = new FileWriter("sale.json");

    gson.toJson(findAll(), writer);
    writer.flush();
    writer.close();
    json = gson.toJson(findAll());

    return json;
  }

}
