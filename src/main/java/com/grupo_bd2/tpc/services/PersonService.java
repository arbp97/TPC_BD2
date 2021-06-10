package com.grupo_bd2.tpc.services;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grupo_bd2.tpc.config.Config;
import com.grupo_bd2.tpc.entities.Person;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;

public class PersonService {

  private static PersonService personService;
  private MongoCollection<Person> personCollection = Config.getInstance().getMongoDatabase().getCollection("persons", Person.class);

  public static PersonService getInstance() {

    if (personService == null) {
      personService = new PersonService();
    }

    personService.createUniqueIndex();

    return personService;
  }

  public void createUniqueIndex() {

    /*
    se crea un index unico para no cargar documentos duplicados
    */

    BasicDBObject obj = new BasicDBObject();

    //se cargan los campos sobre los cuales el index va a chequear
    obj.put("dni", 1);

    personCollection.createIndex(obj, new IndexOptions().unique(true));
  }

  public void insert(Person client) {

    personCollection.insertOne(client);
  }

  public List<Person> findAll() {

    List<Person> clients = new ArrayList<Person>();

    personCollection.find().into(clients);

    return clients;
  }

  public Person findByDni(int dni) {
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
