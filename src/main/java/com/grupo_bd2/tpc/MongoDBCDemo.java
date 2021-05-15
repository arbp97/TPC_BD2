package com.grupo_bd2.tpc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grupo_bd2.tpc.config.*;
import com.grupo_bd2.tpc.entities.*;
import com.grupo_bd2.tpc.services.*;
import com.mongodb.*;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.*;
import com.mongodb.internal.async.SingleResultCallback;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.*;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;


public class MongoDBCDemo {
  public static void main(String[] args) {
    //Java-MongoDB connection

    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();

//        MongoCollection<Address> addresses = Config.getInstance().getMongoDatabase().getCollection("addresses",Address.class);

    //cars.find(eq("modelo","corolla")).forEach((Consumer<Car>) System.out::println); //ejemplosxd

    //cars.find(and(gt("año", 2000))).forEach((Consumer<Car>) System.out::println); // de filtros, gt() es greater than


    Address addressOne = new Address(888, "Calle Falsa", "Springfield", "Argenchina");
    AddressService.getInstance().insertOne(addressOne);
    Address addressTwo = new Address(1201, "Irigoyen", "Lanus", "Argentina");
    AddressService.getInstance().insertOne(addressTwo);
    Address addressThree = new Address(100, "Laprida", "Lomas de zamora", "Argentina");
    AddressService.getInstance().insertOne(addressThree);
    Address addressFour = new Address(2509, "Boedo", "Avellaneda", "Argentina");
    AddressService.getInstance().insertOne(addressFour);

    //addresses.find().forEach((Consumer<Address>) System.out::println);

    Client client = new Client(addressOne, 12345678, "Hello", "Moto", null, null);
    Client client2 = new Client(addressOne, 87654321, "Bye", "Auto", "OSDE", "1");

    ClientService.getInstance().insertOne(client);
    ClientService.getInstance().insertOne(client2);

    Set<Employee> listEmployee = new HashSet<Employee>();
    Store storeOne = new Store(addressFour, 231521);


    Employee employeeOne = new Employee(addressTwo, "storeOne", true, 23152323510L, 152323510,
        "Juan", "Perez", "OSDE", 23021015);
    Employee employeeTwo = new Employee(addressThree, "storeOne", false, 23193265120L, 19326512,
        "Pablo", "Rodriguez", "OSDE", 3320120);

    EmployeeService.getInstance().insertOne(employeeOne);
    EmployeeService.getInstance().insertOne(employeeTwo);
    listEmployee.add(employeeOne);
    listEmployee.add(employeeTwo);
    storeOne.setEmployees(listEmployee);
    StoreService.getInstance().insertOne(storeOne);

    Set<Item> listItem = new HashSet<Item>();
    Item itemOne = new Item("Bayaspirina", 200f, "Bayer", true);
    Item itemTwo = new Item("Adermicina", 650f, "Elea", false);
    Item itemThree = new Item("Actron", 365f, "Bsyer", true);
    listItem.add(itemOne);
    listItem.add(itemTwo);
    listItem.add(itemThree);
    ItemService.getInstance().insertOne(itemOne);
    ItemService.getInstance().insertOne(itemTwo);
    ItemService.getInstance().insertOne(itemThree);

    Sale saleOne = new Sale(employeeOne, employeeTwo, LocalDateTime.now(), 20101L, "DEBITO");
    saleOne.setItems(listItem);
    SaleService.getInstance().insertOne(saleOne);


    SaleDetail saleDetailOne = new SaleDetail(saleOne, itemOne, 3);
    SaleDetail saleDetailTwo = new SaleDetail(saleOne, itemTwo, 5);
    SaleDetail saleDetailThree = new SaleDetail(saleOne, itemThree, 7);
    SaleDetailService.getInstance().insertOne(saleDetailOne);
    SaleDetailService.getInstance().insertOne(saleDetailTwo);
    SaleDetailService.getInstance().insertOne(saleDetailThree);

    try {
      System.out.println(ClientService.getInstance().exportAll());
      System.out.println(AddressService.getInstance().exportAll());
      System.out.println(StoreService.getInstance().exportAll());
      System.out.println(EmployeeService.getInstance().exportAll());
      System.out.println(ItemService.getInstance().exportAll());
      System.out.println(SaleService.getInstance().exportAll());
      System.out.println(SaleDetailService.getInstance().exportAll());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }


  }
}
