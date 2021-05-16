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
    ClientService.getInstance().createUniqueIndex();
    EmployeeService.getInstance().createUniqueIndex();
    ItemService.getInstance().createUniqueIndex();
    SaleDetailService.getInstance().createUniqueIndex();
    SaleService.getInstance().createUniqueIndex();
    StoreService.getInstance().createUniqueIndex();


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

    Client client = new Client(addressOne, 12345678, "Hello", "Moto", null, null);
    Client client2 = new Client(addressOne, 87654321, "Bye", "Auto", "OSDE", "1");


    try {
      ClientService.getInstance().insert(client);
    } catch (MongoWriteException e) {
      System.out.println(e.getMessage());
    }

    try {
      ClientService.getInstance().insert(client2);
    } catch (MongoWriteException e) {
      System.out.println(e.getMessage());
    }

    Set<Employee> listEmployee = new HashSet<Employee>();
    Store storeOne = new Store(addressFour, 231521);
    Store lastStore=StoreService.getInstance().insert(storeOne);

    Employee employeeOne = new Employee(addressTwo,lastStore.getId(), true, 23152323510L, 152323510,
        "Juan", "Perez", "OSDE", 23021015);
    Employee employeeTwo = new Employee(addressThree,lastStore.getId(), false, 23193265120L, 19326512,
        "Pablo", "Rodriguez", "OSDE", 3320120);


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


    try {
      System.out.println(ClientService.getInstance().exportAll());
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

    try {
      System.out.println(AddressService.getInstance().exportAll());
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

    try {
      System.out.println(StoreService.getInstance().exportAll());
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

    try {
      System.out.println(EmployeeService.getInstance().exportAll());
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

    try {
      System.out.println(ItemService.getInstance().exportAll());
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

    try {
      System.out.println(SaleService.getInstance().exportAll());
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

    try {
      System.out.println(SaleDetailService.getInstance().exportAll());
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

  }
}
