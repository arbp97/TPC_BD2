package com.grupo_bd2.tpc.services;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grupo_bd2.tpc.config.Config;
import com.grupo_bd2.tpc.entities.Client;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;

public class ClientService {

  private static ClientService clientService;
  private MongoCollection<Client> clientCollection = Config.getInstance().getMongoDatabase().getCollection("clients", Client.class);

  public static ClientService getInstance() {

    if (clientService == null) {
      clientService = new ClientService();
    }

    return clientService;
  }

  public void createUniqueIndex() {

    /*
    se crea un index unico para no cargar documentos duplicados
    */

    BasicDBObject obj = new BasicDBObject();

    //se cargan los campos sobre los cuales el index va a chequear
    obj.put("dni", 1);

    clientCollection.createIndex(obj, new IndexOptions().unique(true));
  }

  public void insert(Client client) {

    clientCollection.insertOne(client);
  }

  public List<Client> findAll() {

    List<Client> clients = new ArrayList<Client>();

    clientCollection.find().into(clients);

    return clients;
  }

  public Client findByDni(int dni) {
    return null;
  }

  public String exportAll(Boolean write) throws IOException {

    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();
    String json = null;

    json = gson.toJson(findAll());

    if(write) {

      Writer writer = new FileWriter("clients.json");

      gson.toJson(findAll(), writer);
      writer.flush();
      writer.close();

    }

    return json;
  }

}
