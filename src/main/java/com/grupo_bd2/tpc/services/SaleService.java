package com.grupo_bd2.tpc.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grupo_bd2.tpc.config.Config;
import com.grupo_bd2.tpc.entities.Sale;
import com.grupo_bd2.tpc.entities.Store;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Updates;

import org.bson.Document;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
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

  public String thirdReport(LocalDate date_1, LocalDate date_2) {

    List<Document> report = new ArrayList<Document>();
    double totalGeneral = 0;
    double totalSucursal = 0;
    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    for (Sale sale : findAll()) { //total de todas las sucursales

      if(sale.getDate().toLocalDate().isAfter(date_1) && sale.getDate().toLocalDate().isBefore(date_2)) {

        totalGeneral = totalGeneral + sale.getTotal();
      }
    }

    report.add(new Document("TOTAL DE SUCURSALES", totalGeneral));

    for(Store store : StoreService.getInstance().findAll()) { //total por sucursal

      for(Sale sale : findAll()) {

        if(sale.getSalesman().getStore().equals(store.getId())) {

          if(sale.getDate().toLocalDate().isAfter(date_1) && sale.getDate().toLocalDate().isBefore(date_2)) {

            totalSucursal = totalSucursal + sale.getTotal();
          }
        }
      }

      report.add(new Document(store.getAddress().getStreet()+" "+store.getAddress().getNumber(), totalSucursal));
      totalSucursal = 0;
    }

    return gson.toJson(report);
  }

  public String firstReport(LocalDate date_1, LocalDate date_2) {

    List<Document> report = new ArrayList<Document>();
    double totalGeneral = 0;
    double totalSucursal = 0;
    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    for (Sale sale : findAll()) { //total de todas las sucursales

      if(sale.getDate().toLocalDate().isAfter(date_1) && sale.getDate().toLocalDate().isBefore(date_2)) {

        totalGeneral = totalGeneral + 1;
      }
    }

    report.add(new Document("TOTAL DE SUCURSALES", totalGeneral));

    for(Store store : StoreService.getInstance().findAll()) { //total por sucursal

      for(Sale sale : findAll()) {

        if(sale.getSalesman().getStore().equals(store.getId())) {

          if(sale.getDate().toLocalDate().isAfter(date_1) && sale.getDate().toLocalDate().isBefore(date_2)) {

            totalSucursal = totalSucursal + 1;
          }
        }
      }

      report.add(new Document(store.getAddress().getStreet()+" "+store.getAddress().getNumber(), totalSucursal));
      totalSucursal = 0;
    }

    return gson.toJson(report);
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
