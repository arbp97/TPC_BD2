package com.grupo_bd2.tpc.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grupo_bd2.tpc.config.Config;
import com.grupo_bd2.tpc.entities.Sale;
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

public class SaleService {

  private static SaleService saleService;
  private MongoCollection<Sale> saleCollection = Config.getInstance().getMongoDatabase().getCollection("sales", Sale.class);

  public static SaleService getInstance() {

    if (saleService == null) {
      saleService = new SaleService();
    }

    saleService.createUniqueIndex();

    return saleService;
  }

  public void createUniqueIndex() {

    /*
    se crea un index unico para no cargar documentos duplicados
    */

    BasicDBObject obj = new BasicDBObject();

    //se cargan los campos sobre los cuales el index va a chequear
    obj.put("ticketNumber", 1);

    saleCollection.createIndex(obj, new IndexOptions().unique(true));
  }

  public Sale insert(Sale sale) {

    sale.setId(saleCollection.insertOne(sale).getInsertedId().asObjectId().getValue());

    return sale;
  }

  public void update(Sale sale) {

    //date and ticket number should not be updatable

    saleCollection.updateOne(Filters.eq("_id", sale.getId()),
    Updates.combine(
      Updates.set("details", sale.getDetails()),
      Updates.set("salesman", sale.getSalesman()),
      Updates.set("cashier", sale.getCashier()),
      Updates.set("client", sale.getClient()),
      Updates.set("total", sale.getTotal()),
      Updates.set("paymentMethod", sale.getPaymentMethod())
    ));
  }

  public List<Sale> findAll() {

    List<Sale> saleList = new ArrayList<Sale>();

    saleCollection.find().into(saleList);

    return saleList;
  }

  public String exportAll(Boolean write) throws IOException {

    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();
    String json = null;

    json = gson.toJson(findAll());

    if(write) {

      Writer writer = new FileWriter("sales.json");

      gson.toJson(findAll(), writer);
      writer.flush();
      writer.close();

    }

    return json;
  }

}
