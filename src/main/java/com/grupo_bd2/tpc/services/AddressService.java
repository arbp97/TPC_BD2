package com.grupo_bd2.tpc.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grupo_bd2.tpc.config.Config;
import com.grupo_bd2.tpc.entities.Address;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class AddressService {

  private static AddressService addressService;
  private MongoCollection<Address> AddressCollection = Config.getInstance().getMongoDatabase().getCollection("addresses", Address.class);
  
  public static AddressService getInstance() {

    if (addressService == null) {
      addressService = new AddressService();
    }

    return addressService;
  }

  public void createUniqueIndex() {

    /*
    se crea un index unico para no cargar documentos duplicados
    */

    BasicDBObject obj = new BasicDBObject();

    //se cargan los campos sobre los cuales el index va a chequear
    obj.put("street", 1);
    obj.put("number", 1);
    obj.put("city", 1);
    obj.put("province", 1);

    AddressCollection.createIndex(obj, new IndexOptions().unique(true));
}

  public void insert(Address address) throws MongoWriteException{

    AddressCollection.insertOne(address);
  }

  public List<Address> findAll() {

    List<Address> addresses = new ArrayList<Address>();

    AddressCollection.find().into(addresses);

    return addresses;
  }

  public String exportAll() throws IOException {

    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();
    String json = null;
    Writer writer = new FileWriter("addresses.json");

    gson.toJson(findAll(), writer);
    writer.flush();
    writer.close();
    json = gson.toJson(findAll());

    return json;
  }

}
