package com.grupo_bd2.tpc.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grupo_bd2.tpc.config.Config;
import com.grupo_bd2.tpc.entities.Employee;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {

  private static EmployeeService employeeService;
  private MongoCollection<Employee> employeeCollection = Config.getInstance().getMongoDatabase().getCollection("employees", Employee.class);

  public static EmployeeService getInstance() {

    if (employeeService == null) {
      employeeService = new EmployeeService();
    }

    return employeeService;
  }

  public void createUniqueIndex() {

    /*
    se crea un index unico para no cargar documentos duplicados
    */

    BasicDBObject obj = new BasicDBObject();

    //se cargan los campos sobre los cuales el index va a chequear
    obj.put("dni", 1);

    employeeCollection.createIndex(obj, new IndexOptions().unique(true));
  }

  public void insert(Employee employee) {

    employeeCollection.insertOne(employee);
  }

  public List<Employee> findAll() {

    List<Employee> employee = new ArrayList<Employee>();

    employeeCollection.find().into(employee);

    return employee;
  }

  public Employee findByDni(int dni) {
    return null;
  }

  public String exportAll(Boolean write) throws IOException {

    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();
    String json = null;

    json = gson.toJson(findAll());

    if(write) {

      Writer writer = new FileWriter("employees.json");

      gson.toJson(findAll(), writer);
      writer.flush();
      writer.close();

    }

    return json;
  }

}
