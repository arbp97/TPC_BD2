package com.grupo_bd2.tpc.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grupo_bd2.tpc.config.Config;
import com.grupo_bd2.tpc.entities.Client;
import com.grupo_bd2.tpc.entities.Employee;
import com.mongodb.client.MongoCollection;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {

  private static EmployeeService employeeService;
  private MongoCollection<Employee> employeeCollection = Config.getInstance().getMongoDatabase().getCollection("employee", Employee.class);

  public static EmployeeService getInstance() {

    if (employeeService == null) {
      employeeService = new EmployeeService();
    }

    return employeeService;
  }

  public void insertOne(Employee employee) {

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

  public String exportAll() throws IOException {

    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();
    String json = null;
    Writer writer = new FileWriter("employee.json");

    gson.toJson(findAll(), writer);
    writer.flush();
    writer.close();
    json = gson.toJson(findAll());

    return json;
  }

}
