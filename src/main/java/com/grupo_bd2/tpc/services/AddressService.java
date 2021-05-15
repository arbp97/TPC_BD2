package com.grupo_bd2.tpc.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grupo_bd2.tpc.config.Config;
import com.grupo_bd2.tpc.entities.Address;
import com.mongodb.client.MongoCollection;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class AddressService {

  private static AddressService addressService;
  private MongoCollection<Address> clientCollection = Config.getInstance().getMongoDatabase().getCollection("address", Address.class);

  public static AddressService getInstance() {

    if (addressService == null) {
      addressService = new AddressService();
    }

    return addressService;
  }

  public void insertOne(Address address) {

    clientCollection.insertOne(address);
  }

  public List<Address> findAll() {

    List<Address> addresses = new ArrayList<Address>();

    clientCollection.find().into(addresses);

    return addresses;
  }

  public String exportAll() throws IOException {

    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();
    String json = null;
    Writer writer = new FileWriter("address.json");

    gson.toJson(findAll(), writer);
    writer.flush();
    writer.close();
    json = gson.toJson(findAll());

    return json;
  }

}
