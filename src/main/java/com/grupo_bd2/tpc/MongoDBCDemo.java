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
    Address addressFive = new Address(399, "Bolivia", "Lanus", "Argentina");
    Address addressSix = new Address(6886, "Crispi", "Claypole", "Argentina");

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

    try {
      AddressService.getInstance().insert(addressFive);
    } catch (MongoWriteException e) {
      System.out.println(e.getMessage());
    }

    try {
      AddressService.getInstance().insert(addressSix);
    } catch (MongoWriteException e) {
      System.out.println(e.getMessage());
    }

    Insurance insurance1 = new Insurance("OSDE","456781");
    Insurance insurance2 = new Insurance("JAJA","424344");

    try {
      InsuranceService.getInstance().insert(insurance1);
    } catch (MongoWriteException e) {
      System.out.println(e.getMessage());
    }
    try {
      InsuranceService.getInstance().insert(insurance2);
    } catch (MongoWriteException e) {
      System.out.println(e.getMessage());
    }

    // Person(Address address, int dni, String name, String surname, Insurance insurance, String affiliateNumber)
    Person client = new Person(addressOne, 12345678, "Hello", "Moto", null, null);
    Person client2 = new Person(addressOne, 87654321, "Bye", "Auto", insurance1, "123");
    Person client3 = new Person(addressSix, 00000001, "John", "Firstman", insurance2, "30");
    Person client4 = new Person(addressFour, 34566543, "Minecraft", "Terraria", null, null);


    try {
      PersonService.getInstance().insert(client);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}

    try {
      PersonService.getInstance().insert(client2);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}

    try {
      PersonService.getInstance().insert(client3);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}

    try {
      PersonService.getInstance().insert(client4);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}

    System.out.println("TEST" + PersonService.getInstance().findAll());

    //Set<Employee> listEmployee = new HashSet<Employee>();
    //no tiene sentido aca
    Set<Employee> listEmployeeForStoreOne = new HashSet<Employee>();
    Set<Employee> listEmployeeForStoreTwo = new HashSet<Employee>();
    Store storeOne = new Store(addressFour, 231521);
    Store storeTwo = new Store(addressSix, 999000);
    Store lastStore = new Store();
    try {
      lastStore=StoreService.getInstance().insert(storeOne);
      StoreService.getInstance().insert(storeTwo);
    } catch (MongoWriteException e) {
      System.out.println(e.getMessage());
    }


    //Employee(Address, ObjectId store, Boolean isManager, String cuil, dni, name, surname, Insurance insurance, String affiliateNumber)
    Employee employeeOne = new Employee(addressTwo, lastStore.getId(), true, "23152323510", 152323510,
        "Juan", "Perez", insurance1, "23021015");
    Employee employeeTwo = new Employee(addressThree, lastStore.getId(), false, "23193265120", 19326512,
        "Pablo", "Rodriguez", insurance1, "3320120");
    Employee employeeThree = new Employee(addressFour, storeTwo.getId(), false, "23131415160", 13141516, "Gabe", "Newell", null, null);
    Employee employeeFour = new Employee(addressFive, storeTwo.getId(), false, "23422456320", 42245632, "Axel", "Madagascar", insurance2, "6300");


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

    try {
      EmployeeService.getInstance().insert(employeeThree);
    } catch (MongoWriteException e) {
      System.out.println(e.getMessage());
    }

    try {
      EmployeeService.getInstance().insert(employeeFour);
    } catch (MongoWriteException e) {
      System.out.println(e.getMessage());
    }

    listEmployeeForStoreOne.add(employeeOne);
    listEmployeeForStoreOne.add(employeeTwo);
    lastStore.setEmployees(listEmployeeForStoreOne);
    StoreService.getInstance().update(storeOne);

    listEmployeeForStoreTwo.add(employeeThree);
    listEmployeeForStoreTwo.add(employeeFour);
    lastStore.setEmployees(listEmployeeForStoreTwo);
    StoreService.getInstance().update(storeTwo);

    // public Item(String description, float price, String manufacturer, Boolean isMedicine) {
    Set<Item> listItem = new HashSet<Item>();
    Item itemOne = new Item("Bayaspirina", 200f, "Bayer", true);
    Item itemTwo = new Item("Adermicina", 650f, "Elea", false);
    Item itemThree = new Item("Actron", 365f, "Bayer", true);
    Item itemFour = new Item("Half-Life", 600f, "Valve", false);
    Item itemFive = new Item("Ibuprofeno 0.1g", 1f, "Pfizer creo", true);
    Item itemSix = new Item("Actimel", 101f, "Lase Renisima", false);
    Item itemSeven = new Item("VapoRub", 240f, "Vicks", true);
    listItem.add(itemOne);
    listItem.add(itemTwo);
    listItem.add(itemThree);
    listItem.add(itemFour);
    listItem.add(itemFive);
    listItem.add(itemSix);
    listItem.add(itemSeven);


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
      ItemService.getInstance().insert(itemFour);
    } catch (MongoWriteException e) {
      System.out.println(e.getMessage());
    }
    try {
      ItemService.getInstance().insert(itemFive);
    } catch (MongoWriteException e) {
      System.out.println(e.getMessage());
    }
    try {
      ItemService.getInstance().insert(itemSix);
    } catch (MongoWriteException e) {
      System.out.println(e.getMessage());
    }
    try {
      ItemService.getInstance().insert(itemSeven);
    } catch (MongoWriteException e) {
      System.out.println(e.getMessage());
    }


    

    // vamos de  ompras
    //          c

    // SaleDetail(ObjectId sale, Item item, int quantity)
    // Sale(Employee salesman, Employee cashier, LocalDateTime date, long ticketNumber, String paymentMethod)

    Sale saleOne = new Sale(employeeThree, employeeThree, LocalDateTime.now(), 2, "Bisa");
    SaleDetail detalleOne = new SaleDetail(saleOne.getId(), itemFour, 1);
    Sale saleTwo = new Sale(employeeOne, employeeTwo, LocalDateTime.now().minusDays(1), 6, "Santander Rie");
    SaleDetail detalleTwoA = new SaleDetail(saleTwo.getId(), itemTwo, 3);
    SaleDetail detalleTwoB = new SaleDetail(saleTwo.getId(), itemSix, 6);
    Sale saleThree = new Sale(employeeFour, employeeThree, LocalDateTime.now(), 43, "Mastercarb");
    SaleDetail detalleThree = new SaleDetail(saleThree.getId(), itemThree, 55);
    Sale saleFour = new Sale(employeeThree, employeeFour, LocalDateTime.now().minusWeeks(2), 44, "Huesitos de pollo");
    SaleDetail detalleFour = new SaleDetail(saleFour.getId(), itemSeven, 2);
    Sale saleFive = new Sale(employeeTwo, employeeOne, LocalDateTime.now().plusDays(1), 45, "Futurepay");
    SaleDetail detalleFiveA = new SaleDetail(saleFive.getId(), itemSix, 1);
    SaleDetail detalleFiveB = new SaleDetail(saleFive.getId(), itemSix, 2);
    SaleDetail detalleFiveC = new SaleDetail(saleFive.getId(), itemSix, 4);
    Sale saleSix = new Sale(employeeTwo, employeeTwo, LocalDateTime.now().minusMinutes(24), 47, "Drogiscard");
    SaleDetail detalleSixA = new SaleDetail(saleSix.getId(), itemOne, 3);
    SaleDetail detalleSixB = new SaleDetail(saleSix.getId(), itemThree, 5);
    SaleDetail detalleSixC = new SaleDetail(saleSix.getId(), itemFive, 7);
    SaleDetail detalleSixD = new SaleDetail(saleSix.getId(), itemSeven, 9);
    Sale saleSeven = new Sale(employeeFour, employeeFour, LocalDateTime.now(), 49, "Tarjeta generica #4");
    SaleDetail detalleSevenA = new SaleDetail(saleSeven.getId(), itemThree, 1);
    SaleDetail detalleSevenB = new SaleDetail(saleSeven.getId(), itemOne, 5000);
    Sale saleEight = new Sale(employeeOne, employeeOne, LocalDateTime.now(), 51, "Lamborghini Aventador LP 750-4 Superveloce Roadster");
    SaleDetail detalleEight = new SaleDetail(saleEight.getId(), itemSeven, 92);
    Sale saleNine = new Sale(employeeOne, employeeTwo, LocalDateTime.now(), 55, "Apreta ALT+F4 para deshabilitar chequeo de errores");
    SaleDetail detalleNineA = new SaleDetail(saleNine.getId(), itemTwo, 32);
    SaleDetail detalleNineB = new SaleDetail(saleNine.getId(), itemThree, 12);
    Sale saleTen = new Sale(employeeFour, employeeThree, LocalDateTime.now(), 60, "Noma Meswey");
    SaleDetail detalleTenA = new SaleDetail(saleTen.getId(), itemOne, 2);
    SaleDetail detalleTenB = new SaleDetail(saleTen.getId(), itemTwo, 4);
    SaleDetail detalleTenC = new SaleDetail(saleTen.getId(), itemThree, 6);
    SaleDetail detalleTenD = new SaleDetail(saleTen.getId(), itemFour, 8);
    SaleDetail detalleTenE = new SaleDetail(saleTen.getId(), itemFive, 10);
    SaleDetail detalleTenF = new SaleDetail(saleTen.getId(), itemSix, 12);
    SaleDetail detalleTenG = new SaleDetail(saleTen.getId(), itemSeven, 14);
    SaleDetail detalleTenH = new SaleDetail(saleTen.getId(), itemOne, 16);

    //#region trycatch de sales (para el FOLD de vscode)
    try { SaleService.getInstance().insert(saleOne);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    try { SaleDetailService.getInstance().insert(detalleOne);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}

    try { SaleService.getInstance().insert(saleTwo);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    try { SaleDetailService.getInstance().insert(detalleTwoA);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    try { SaleDetailService.getInstance().insert(detalleTwoB);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}

    try { SaleService.getInstance().insert(saleThree);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    try { SaleDetailService.getInstance().insert(detalleThree);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}

    try { SaleService.getInstance().insert(saleFour);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    try { SaleDetailService.getInstance().insert(detalleFour);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}

    try { SaleService.getInstance().insert(saleFive);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    try { SaleDetailService.getInstance().insert(detalleFiveA);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    try { SaleDetailService.getInstance().insert(detalleFiveB);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    try { SaleDetailService.getInstance().insert(detalleFiveC);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}

    try { SaleService.getInstance().insert(saleSix);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    try { SaleDetailService.getInstance().insert(detalleSixA);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    try { SaleDetailService.getInstance().insert(detalleSixB);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    try { SaleDetailService.getInstance().insert(detalleSixC);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    try { SaleDetailService.getInstance().insert(detalleSixD);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}

    try { SaleService.getInstance().insert(saleSeven);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    try { SaleDetailService.getInstance().insert(detalleSevenA);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    try { SaleDetailService.getInstance().insert(detalleSevenB);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}

    try { SaleService.getInstance().insert(saleEight);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    try { SaleDetailService.getInstance().insert(detalleEight);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}

    try { SaleService.getInstance().insert(saleNine);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    try { SaleDetailService.getInstance().insert(detalleNineA);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    try { SaleDetailService.getInstance().insert(detalleNineB);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}

    try { SaleService.getInstance().insert(saleTen);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    try { SaleDetailService.getInstance().insert(detalleTenA);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    try { SaleDetailService.getInstance().insert(detalleTenB);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    try { SaleDetailService.getInstance().insert(detalleTenC);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    try { SaleDetailService.getInstance().insert(detalleTenD);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    try { SaleDetailService.getInstance().insert(detalleTenE);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    try { SaleDetailService.getInstance().insert(detalleTenF);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    try { SaleDetailService.getInstance().insert(detalleTenG);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    try { SaleDetailService.getInstance().insert(detalleTenH);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    //#endregion

    try { //JSON con todos los datos de la base de datos
      Config.getInstance().exportDatabase();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

    
    /* No implementado
    try {
      System.out.println(ClientService.getInstance().exportAll(true));
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    */

    /*
    //#region export
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
    //#endregion
    */
    
  }
}
