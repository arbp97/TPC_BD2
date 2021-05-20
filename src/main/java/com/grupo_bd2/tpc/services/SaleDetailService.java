package com.grupo_bd2.tpc.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grupo_bd2.tpc.config.Config;
import com.grupo_bd2.tpc.entities.SaleDetail;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class SaleDetailService {

  private static SaleDetailService saleDetailService;
  private MongoCollection<SaleDetail> saleDetailCollection = Config.getInstance().getMongoDatabase().getCollection("saleDetails", SaleDetail.class);

  public static SaleDetailService getInstance() {

    if (saleDetailService == null) {
      saleDetailService = new SaleDetailService();
    }

    return saleDetailService;
  }

  public void createUniqueIndex() {

    /*
    se crea un index unico para no cargar documentos duplicados
    */

    BasicDBObject obj = new BasicDBObject();

    //se cargan los campos sobre los cuales el index va a chequear
    obj.put("sale", 1);
    obj.put("item", 1);
    obj.put("quantity", 1);

    saleDetailCollection.createIndex(obj, new IndexOptions().unique(true));
  }

  public SaleDetail insert(SaleDetail saleDetail) {

    saleDetail.setId(saleDetailCollection.insertOne(saleDetail).getInsertedId().asObjectId().getValue());

    return saleDetail;
  }

  public List<SaleDetail> findAll() {

    List<SaleDetail> saleDetailList = new ArrayList<SaleDetail>();

    saleDetailCollection.find().into(saleDetailList);

    return saleDetailList;
  }

  public String exportAll(Boolean write) throws IOException {

    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();
    String json = null;

    json = gson.toJson(findAll());

    if(write) {

      Writer writer = new FileWriter("saleDetails.json");

      gson.toJson(findAll(), writer);
      writer.flush();
      writer.close();

    }

    return json;
  }

}
