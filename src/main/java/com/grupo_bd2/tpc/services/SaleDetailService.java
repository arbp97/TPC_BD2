package com.grupo_bd2.tpc.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grupo_bd2.tpc.config.Config;
import com.grupo_bd2.tpc.entities.Address;
import com.grupo_bd2.tpc.entities.SaleDetail;
import com.mongodb.client.MongoCollection;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class SaleDetailService {

  private static SaleDetailService saleDetailService;
  private MongoCollection<SaleDetail> saleDetailCollection = Config.getInstance().getMongoDatabase().getCollection("saleDetail", SaleDetail.class);

  public static SaleDetailService getInstance() {

    if (saleDetailService == null) {
      saleDetailService = new SaleDetailService();
    }

    return saleDetailService;
  }

  public void insertOne(SaleDetail saleDetail) {

    saleDetailCollection.insertOne(saleDetail);
  }

  public List<SaleDetail> findAll() {

    List<SaleDetail> saleDetailList = new ArrayList<SaleDetail>();

    saleDetailCollection.find().into(saleDetailList);

    return saleDetailList;
  }

  public String exportAll() throws IOException {

    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();
    String json = null;
    Writer writer = new FileWriter("saleDetail.json");

    gson.toJson(findAll(), writer);
    writer.flush();
    writer.close();
    json = gson.toJson(findAll());

    return json;
  }

}
