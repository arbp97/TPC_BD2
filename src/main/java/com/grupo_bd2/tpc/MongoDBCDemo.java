package com.grupo_bd2.tpc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grupo_bd2.tpc.config.Config;
import com.grupo_bd2.tpc.entities.*;
import com.grupo_bd2.tpc.services.*;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoWriteException;

import org.bson.types.ObjectId;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


public class MongoDBCDemo {
  public static void main(String[] args) {
    //Java-MongoDB connection

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    System.out.println(Config.getInstance().getStatus());


    //creando los indices unicos... no funciona en config, no se por que, de momento queda aca
    AddressService.getInstance().createUniqueIndex();
    PersonService.getInstance().createUniqueIndex();
    EmployeeService.getInstance().createUniqueIndex();
    ItemService.getInstance().createUniqueIndex();
    SaleDetailService.getInstance().createUniqueIndex();
    SaleService.getInstance().createUniqueIndex();
    StoreService.getInstance().createUniqueIndex();
    InsuranceService.getInstance().createUniqueIndex();


    Address addressOne = new Address(888, "Erezcano", "Almirante Brown", "Argentina");
    Address addressTwo = new Address(1201, "Irigoyen", "Lanus", "Argentina");
    Address addressThree = new Address(100, "Laprida", "Lomas de zamora", "Argentina");
    Address addressFour = new Address(2509, "Boedo", "Avellaneda", "Argentina");

    try {
      AddressService.getInstance().insert(addressOne);
    } catch (MongoWriteException e) {
      System.out.println(e.getMessage());
    }

    try {
      AddressService.getInstance().insert(addressTwo);
    } catch (MongoWriteException e) {
      System.out.println(e.getMessage());
    }

    try {
      AddressService.getInstance().insert(addressThree);
    } catch (MongoWriteException e) {
      System.out.println(e.getMessage());
    }

    try {
      AddressService.getInstance().insert(addressFour);
    } catch (MongoWriteException e) {
      System.out.println(e.getMessage());
    }

    Insurance insurance1 = new Insurance("OSDE","456781");

    try {
      InsuranceService.getInstance().insert(insurance1);
    } catch (MongoWriteException e) {
      System.out.println(e.getMessage());
    }

    Person client = new Person(addressOne, 12345678, "Hello", "Moto", null, null);
    Person client2 = new Person(addressOne, 87654321, "Bye", "Auto", insurance1, "123");


    try {
      PersonService.getInstance().insert(client2);
    } catch (MongoWriteException e) {
      System.out.println(e.getMessage());
    }

    try {
      PersonService.getInstance().insert(client);
    } catch (MongoWriteException e) {
      System.out.println(e.getMessage());
    }

    System.out.println("TEST" + PersonService.getInstance().findAll());

    Set<Employee> listEmployee = new HashSet<Employee>();
    Store storeOne = new Store(addressFour, 231521);
    Store lastStore = new Store();
    try {
      lastStore=StoreService.getInstance().insert(storeOne);
    } catch (MongoWriteException e) {
      System.out.println(e.getMessage());
    }


    Employee employeeOne = new Employee(addressTwo,lastStore.getId(), true, "23152323510", 152323510,
        "Juan", "Perez", insurance1, "23021015");
    Employee employeeTwo = new Employee(addressThree,lastStore.getId(), false, "23193265120", 19326512,
        "Pablo", "Rodriguez", insurance1, "3320120");


    try {
      EmployeeService.getInstance().insert(employeeOne);
    } catch (MongoWriteException e) {
      System.out.println(e.getMessage());
    }

    try {
      EmployeeService.getInstance().insert(employeeTwo);
    } catch (MongoWriteException e) {
      System.out.println(e.getMessage());
    }

    listEmployee.add(employeeOne);
    listEmployee.add(employeeTwo);
    lastStore.setEmployees(listEmployee);
    StoreService.getInstance().update(lastStore);


    Set<Item> listItem = new HashSet<Item>();
    Item itemOne = new Item("Bayaspirina", 200f, "Bayer", true);
    Item itemTwo = new Item("Adermicina", 650f, "Elea", false);
    Item itemThree = new Item("Actron", 365f, "Bsyer", true);
    listItem.add(itemOne);
    listItem.add(itemTwo);
    listItem.add(itemThree);


    try {
      ItemService.getInstance().insert(itemOne);
    } catch (MongoWriteException e) {
      System.out.println(e.getMessage());
    }

    try {
      ItemService.getInstance().insert(itemTwo);
    } catch (MongoWriteException e) {
      System.out.println(e.getMessage());
    }

    try {
      ItemService.getInstance().insert(itemThree);
    } catch (MongoWriteException e) {
      System.out.println(e.getMessage());
    }

    try { //JSON con todos los datos de la base de datos
      Config.getInstance().exportDatabase();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

    /*
    try {
      System.out.println(ClientService.getInstance().exportAll(true));
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

    try {
      System.out.println(AddressService.getInstance().exportAll(true));
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

    try {
      System.out.println(StoreService.getInstance().exportAll(true));
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

    try {
      System.out.println(EmployeeService.getInstance().exportAll(true));
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

    try {
      System.out.println(ItemService.getInstance().exportAll(true));
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

    try {
      System.out.println(SaleService.getInstance().exportAll(true));
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

    try {
      System.out.println(SaleDetailService.getInstance().exportAll(true));
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    */
  }
}
