package com.grupo_bd2.tpc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grupo_bd2.tpc.config.Config;
import com.grupo_bd2.tpc.entities.*;
import com.grupo_bd2.tpc.services.*;
import com.mongodb.MongoWriteException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


public class DataLoadTest {
  public static void main(String[] args) {
    //Java-MongoDB connection

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    System.out.println(Config.getInstance().getStatus());

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
    Insurance insurance2 = new Insurance("SWISS MEDICAL","424344");

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
    Person client = new Person(addressOne, 12345678, "Nicolas", "Gonzalez", null, null);
    Person client2 = new Person(addressOne, 36654321, "Javier", "Alvarez", insurance1, "123");
    Person client3 = new Person(addressSix, 23569254, "Paula", "Godoy", insurance2, "30");
    Person client4 = new Person(addressFour, 34566543, "Micaela", "Muñoz", null, null);


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

    //Set<Employee> listEmployee = new HashSet<Employee>();
    //no tiene sentido aca
    Set<Employee> listEmployeeForStoreOne = new HashSet<Employee>();
    Set<Employee> listEmployeeForStoreTwo = new HashSet<Employee>();
    Store storeOne = new Store(addressFour, 231521);
    Store storeTwo = new Store(addressSix, 999000);

    try {
      storeOne=StoreService.getInstance().insert(storeOne);
    } catch (MongoWriteException e) {
      System.out.println(e.getMessage());
    }

    try {
      storeTwo=StoreService.getInstance().insert(storeTwo);
    } catch (MongoWriteException e) {
      System.out.println(e.getMessage());
    }

    //Employee(Address, ObjectId store, Boolean isManager, String cuil, dni, name, surname, Insurance insurance, String affiliateNumber)
    Employee employeeOne = new Employee(addressTwo, storeOne.getId(), true, "23152323510", 152323510,
        "Juan", "Perez", insurance1, "23021015");
    Employee employeeTwo = new Employee(addressThree, storeOne.getId(), false, "23193265120", 19326512,
        "Pablo", "Rodriguez", insurance1, "3320120");
    Employee employeeThree = new Employee(addressOne, storeTwo.getId(), false, "23131415160", 13141516, "Gabriel", "Coronel", null, null);
    Employee employeeFour = new Employee(addressFive, storeTwo.getId(), false, "23422456320", 42245632, "Axel", "Dominguez", insurance2, "6300");


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
    storeOne.setEmployees(listEmployeeForStoreOne);
    StoreService.getInstance().update(storeOne);

    listEmployeeForStoreTwo.add(employeeThree);
    listEmployeeForStoreTwo.add(employeeFour);
    storeTwo.setEmployees(listEmployeeForStoreTwo);
    StoreService.getInstance().update(storeTwo);

    // public Item(String description, float price, String manufacturer, Boolean isMedicine) {
    Set<Item> listItem = new HashSet<Item>();
    Item itemOne = new Item("Bayaspirina", 200f, "Bayer", true);
    Item itemTwo = new Item("Adermicina", 650f, "Elea", false);
    Item itemThree = new Item("Actron", 365f, "Bayer", true);
    Item itemFour = new Item("Half-Life", 600f, "Valve", false);
    Item itemFive = new Item("Ibuprofeno 0.1g", 1f, "Elea", true);
    Item itemSix = new Item("Actimel", 101f, "Valve", false);
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

    //TODO - refactorear todos los bloques de sales y details como el primero
    Sale saleOne = new Sale();
    SaleDetail detalleOne = new SaleDetail();
    try {
      saleOne = SaleService.getInstance().insert(new Sale(client,employeeThree, employeeThree, LocalDateTime.now(),
          2, "Visa"));
      detalleOne = new SaleDetail(saleOne.getId(), itemFour, 1);
      saleOne.addDetail(detalleOne);
      SaleService.getInstance().update(saleOne);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}

    Sale saleTwo = new Sale();
    try {saleTwo = SaleService.getInstance().insert(new Sale(client,employeeOne, employeeTwo, LocalDateTime.now().minusDays(1),
        6, "Santander Rio"));
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    SaleDetail detalleTwoA = new SaleDetail(saleTwo.getId(), itemTwo, 3);
    SaleDetail detalleTwoB = new SaleDetail(saleTwo.getId(), itemSix, 6);
    saleTwo.addDetail(detalleTwoA);
    saleTwo.addDetail(detalleTwoB);
    SaleService.getInstance().update(saleTwo);

    Sale saleThree = new Sale();
    try {saleThree = SaleService.getInstance().insert(new Sale(client2,employeeThree, employeeFour, LocalDateTime.now().minusWeeks(2),
        5, "Mercado Pago"));
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    SaleDetail detalleThree = new SaleDetail(saleThree.getId(), itemOne, 12);
    saleThree.addDetail(detalleThree);
    SaleService.getInstance().update(saleThree);

    Sale saleFour = new Sale();
    try {saleFour = SaleService.getInstance().insert(new Sale(client2,employeeThree, employeeFour, LocalDateTime.now().minusWeeks(2),
        44, "Visa"));
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    SaleDetail detalleFour = new SaleDetail(saleFour.getId(), itemSeven, 2);
    saleFour.addDetail(detalleFour);
    SaleService.getInstance().update(saleFour);

    Sale saleFive = new Sale();
    try {saleFive = SaleService.getInstance().insert(new Sale(client3,employeeTwo, employeeOne, LocalDateTime.now().plusDays(1),
        45, "Mastercard"));
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    SaleDetail detalleFiveA = new SaleDetail(saleFive.getId(), itemSix, 1);
    SaleDetail detalleFiveB = new SaleDetail(saleFive.getId(), itemSix, 2);
    SaleDetail detalleFiveC = new SaleDetail(saleFive.getId(), itemSix, 4);
    saleFive.addDetail(detalleFiveA);
    saleFive.addDetail(detalleFiveB);
    saleFive.addDetail(detalleFiveC);
    SaleService.getInstance().update(saleFive);

    Sale saleSix = new Sale();
    try {saleSix = SaleService.getInstance().insert(new Sale(client3,employeeTwo, employeeTwo, LocalDateTime.now().minusMinutes(24),
        47, "Mastercard"));
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    SaleDetail detalleSixA = new SaleDetail(saleSix.getId(), itemOne, 3);
    SaleDetail detalleSixB = new SaleDetail(saleSix.getId(), itemThree, 5);
    SaleDetail detalleSixC = new SaleDetail(saleSix.getId(), itemFive, 7);
    SaleDetail detalleSixD = new SaleDetail(saleSix.getId(), itemSeven, 9);
    saleSix.addDetail(detalleSixA);
    saleSix.addDetail(detalleSixB);
    saleSix.addDetail(detalleSixC);
    saleSix.addDetail(detalleSixD);
    SaleService.getInstance().update(saleSix);

    Sale saleSeven = new Sale();
    try {saleSeven = SaleService.getInstance().insert(new Sale(client3,employeeFour, employeeFour, LocalDateTime.now(),
        49, "Mercado Pago"));
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    SaleDetail detalleSevenA = new SaleDetail(saleSeven.getId(), itemThree, 1);
    SaleDetail detalleSevenB = new SaleDetail(saleSeven.getId(), itemOne, 5000);
    saleSeven.addDetail(detalleSevenA);
    saleSeven.addDetail(detalleSevenB);
    SaleService.getInstance().update(saleSeven);

    Sale saleEight = new Sale();
    try {saleEight = SaleService.getInstance().insert(new Sale(client4,employeeOne, employeeOne, 
      LocalDateTime.now(), 51, "Visa"));
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    SaleDetail detalleEight = new SaleDetail(saleEight.getId(), itemSeven, 92);
    saleEight.addDetail(detalleEight);
    SaleService.getInstance().update(saleEight);

    Sale saleNine = new Sale();
     try {saleNine = SaleService.getInstance().insert(new Sale(client4,employeeOne, employeeTwo,
      LocalDateTime.now(), 55, "Mastercard"));
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    SaleDetail detalleNineA = new SaleDetail(saleNine.getId(), itemTwo, 32);
    SaleDetail detalleNineB = new SaleDetail(saleNine.getId(), itemThree, 12);
    saleNine.addDetail(detalleNineA);
    saleNine.addDetail(detalleNineB);
    SaleService.getInstance().update(saleNine);

    Sale saleTen = new Sale();
    try {saleTen = SaleService.getInstance().insert(new Sale(client4,employeeFour, employeeThree, LocalDateTime.now(), 60, "Visa"));
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    SaleDetail detalleTenA = new SaleDetail(saleTen.getId(), itemOne, 2);
    SaleDetail detalleTenB = new SaleDetail(saleTen.getId(), itemTwo, 4);
    SaleDetail detalleTenC = new SaleDetail(saleTen.getId(), itemThree, 6);
    SaleDetail detalleTenD = new SaleDetail(saleTen.getId(), itemFour, 8);
    SaleDetail detalleTenE = new SaleDetail(saleTen.getId(), itemFive, 10);
    SaleDetail detalleTenF = new SaleDetail(saleTen.getId(), itemSix, 12);
    SaleDetail detalleTenG = new SaleDetail(saleTen.getId(), itemSeven, 14);
    SaleDetail detalleTenH = new SaleDetail(saleTen.getId(), itemOne, 16);
    saleTen.addDetail(detalleTenA);
    saleTen.addDetail(detalleTenB);
    saleTen.addDetail(detalleTenC);
    saleTen.addDetail(detalleTenD);
    saleTen.addDetail(detalleTenE);
    saleTen.addDetail(detalleTenF);
    saleTen.addDetail(detalleTenG);
    saleTen.addDetail(detalleTenH);
    SaleService.getInstance().update(saleTen);

    //#region trycatch de sales (para el FOLD de vscode)

    try { SaleDetailService.getInstance().insert(detalleOne);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}

    try { SaleDetailService.getInstance().insert(detalleTwoA);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    try { SaleDetailService.getInstance().insert(detalleTwoB);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}

    try { SaleDetailService.getInstance().insert(detalleThree);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}

    try { SaleDetailService.getInstance().insert(detalleFour);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}

    try { SaleDetailService.getInstance().insert(detalleFiveA);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    try { SaleDetailService.getInstance().insert(detalleFiveB);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    try { SaleDetailService.getInstance().insert(detalleFiveC);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}

    try { SaleDetailService.getInstance().insert(detalleSixA);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    try { SaleDetailService.getInstance().insert(detalleSixB);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    try { SaleDetailService.getInstance().insert(detalleSixC);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    try { SaleDetailService.getInstance().insert(detalleSixD);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}

    try { SaleDetailService.getInstance().insert(detalleSevenA);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    try { SaleDetailService.getInstance().insert(detalleSevenB);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}

    try { SaleDetailService.getInstance().insert(detalleEight);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}

    try { SaleDetailService.getInstance().insert(detalleNineA);
    } catch (MongoWriteException e) {System.out.println(e.getMessage());}
    try { SaleDetailService.getInstance().insert(detalleNineB);
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

  }
}
