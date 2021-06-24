package com.grupo_bd2.tpc.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grupo_bd2.tpc.config.Config;
import com.grupo_bd2.tpc.entities.Insurance;
import com.grupo_bd2.tpc.entities.Item;
import com.grupo_bd2.tpc.entities.Person;
import com.grupo_bd2.tpc.entities.Sale;
import com.grupo_bd2.tpc.entities.SaleDetail;
import com.grupo_bd2.tpc.entities.Store;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Updates;

import org.bson.Document;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SaleService {

  private static SaleService saleService;
  private MongoCollection<Sale> saleCollection = Config.getInstance().getMongoDatabase().getCollection("sales", Sale.class);

  public static SaleService getInstance() {

    if (saleService == null) {
      saleService = new SaleService();
    }

    saleService.createUniqueIndex();

    return saleService;
  }

  public void createUniqueIndex() {

    /*
    se crea un index unico para no cargar documentos duplicados
    */

    BasicDBObject obj = new BasicDBObject();

    //se cargan los campos sobre los cuales el index va a chequear
    obj.put("ticketNumber", 1);

    saleCollection.createIndex(obj, new IndexOptions().unique(true));
  }

  public Sale insert(Sale sale) {

    sale.setId(saleCollection.insertOne(sale).getInsertedId().asObjectId().getValue());

    return sale;
  }

  public void update(Sale sale) {

    //date and ticket number should not be updatable

    saleCollection.updateOne(Filters.eq("_id", sale.getId()),
    Updates.combine(
      Updates.set("details", sale.getDetails()),
      Updates.set("salesman", sale.getSalesman()),
      Updates.set("cashier", sale.getCashier()),
      Updates.set("client", sale.getClient()),
      Updates.set("total", sale.getTotal()),
      Updates.set("paymentMethod", sale.getPaymentMethod())
    ));
  }

  public List<Sale> findAll() {

    List<Sale> saleList = new ArrayList<Sale>();

    saleCollection.find().into(saleList);

    return saleList;
  }

  public String thirdReport(LocalDate date_1, LocalDate date_2) {

    List<Document> report = new ArrayList<Document>();
    double totalGeneral = 0;
    double totalSucursal = 0;
    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    for (Sale sale : findAll()) { //total de todas las sucursales

      if(sale.getDate().toLocalDate().isAfter(date_1) && sale.getDate().toLocalDate().isBefore(date_2)) {

        totalGeneral = totalGeneral + sale.getTotal();
      }
    }

    report.add(new Document("TOTAL DE SUCURSALES", totalGeneral));

    for(Store store : StoreService.getInstance().findAll()) { //total por sucursal

      for(Sale sale : findAll()) {

        if(sale.getSalesman().getStore().equals(store.getId())) {

          if(sale.getDate().toLocalDate().isAfter(date_1) && sale.getDate().toLocalDate().isBefore(date_2)) {

            totalSucursal = totalSucursal + sale.getTotal();
          }
        }
      }

      report.add(new Document(store.getAddress().getStreet()+" "+store.getAddress().getNumber(), totalSucursal));
      totalSucursal = 0;
    }

    return gson.toJson(report);
  }

  public String firstReport(LocalDate date_1, LocalDate date_2) {

    List<Document> report = new ArrayList<Document>();
    double totalGeneral = 0;
    double totalSucursal = 0;
    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    for (Sale sale : findAll()) { //total de todas las sucursales

      if(sale.getDate().toLocalDate().isAfter(date_1) && sale.getDate().toLocalDate().isBefore(date_2)) {

        totalGeneral = totalGeneral + 1;
      }
    }

    report.add(new Document("TOTAL DE SUCURSALES", totalGeneral));

    for(Store store : StoreService.getInstance().findAll()) { //total por sucursal

      for(Sale sale : findAll()) {

        if(sale.getSalesman().getStore().equals(store.getId())) {

          if(sale.getDate().toLocalDate().isAfter(date_1) && sale.getDate().toLocalDate().isBefore(date_2)) {

            totalSucursal = totalSucursal + 1;
          }
        }
      }

      report.add(new Document(store.getAddress().getStreet()+" "+store.getAddress().getNumber(), totalSucursal));
      totalSucursal = 0;
    }

    return gson.toJson(report);
  }

  public String secondReport (LocalDate date_1, LocalDate date_2) {

    List<Document> report = new ArrayList<Document>();
    double totalObraSocial = 0;
    double totalPrivado = 0;
    boolean isPrivadoDone = false;
    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    for(Insurance insurance : InsuranceService.getInstance().findAll()) {

      for(Sale sale : findAll()) {

        if(sale.getDate().toLocalDate().isAfter(date_1) && sale.getDate().toLocalDate().isBefore(date_2)) {

          if(sale.getClient().getInsurance() != null) {

            if(sale.getClient().getInsurance().getInsuranceCode().equals(insurance.getInsuranceCode())) {

              totalObraSocial = totalObraSocial + 1;
            }
          } else if(!isPrivadoDone) {

            totalPrivado = totalPrivado + 1;
          }
        }

      }

      report.add(new Document(insurance.getName()+": ", totalObraSocial));
      isPrivadoDone = true;
      totalObraSocial = 0;

    }

    report.add(new Document("PRIVADO: ", totalPrivado));

    return gson.toJson(report);
  }


  public String FourthReport(LocalDate date_1, LocalDate date_2) {
    List<Document> report = new ArrayList<Document>();
    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    List<Sale> listStore = findAll();
    int cantVentasMedicine = 0;
    int cantVentasPerfumeria = 0;

    for (Sale sale : listStore) {
      if ((sale.getDate().toLocalDate().equals(date_1)) || sale.getDate().toLocalDate().isAfter(date_1)
          && (sale.getDate().toLocalDate().isBefore(date_2) || sale.getDate().toLocalDate().equals(date_2))) {
        boolean isMedicine = false;
        for (SaleDetail saleDetail :
            sale.getDetails()) {
          if (saleDetail.getItem().getIsMedicine()) {
            isMedicine = true;
          }
        }
        if (isMedicine) {
          cantVentasMedicine = cantVentasMedicine + 1;
        } else {
          cantVentasPerfumeria = cantVentasPerfumeria + 1;
        }
      }

    }
    report.add(new Document("CANTIDAD DE VENTAS AGRUPADAS POR PERFUMERIA:", cantVentasPerfumeria));
    report.add(new Document("CANTIDAD DE VENTAS AGRUPADAS POR MEDICAMENTO", cantVentasMedicine));

    return gson.toJson(report);
  }

  public String firstRanking() {

    List<Document> report = new ArrayList<Document>();
    Document auxDoc = new Document();
    double montoVendido = 0;
    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    for(Item item : ItemService.getInstance().findAll()) {

      auxDoc.put(item.getDescription(), "MONTO VENDIDO");

      for(Store store : StoreService.getInstance().findAll()) {

        for(Sale sale : findAll()) {

          if(sale.getSalesman().getStore().equals(store.getId())) {

            for(SaleDetail detail : sale.getDetails()) {

              if(detail.getItem().getDescription().equals(item.getDescription())) {

                montoVendido = montoVendido + detail.getTotal();
              }
            }
          }

        }// for sale

        auxDoc.append(store.getAddress().getStreet()+" "+store.getAddress().getNumber(), montoVendido);

        montoVendido = 0;

      }// for store

      report.add(new Document(auxDoc));
      auxDoc.clear();

    }// for item

    return gson.toJson(report);
  }

  public String secondRanking() {

    List<Document> report = new ArrayList<Document>();
    Document auxDoc = new Document();
    double cantVendida = 0;
    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    for(Item item : ItemService.getInstance().findAll()) {

      auxDoc.put(item.getDescription(), "CANTIDAD VENDIDA");

      for(Store store : StoreService.getInstance().findAll()) {

        for(Sale sale : findAll()) {

          if(sale.getSalesman().getStore().equals(store.getId())) {

            for(SaleDetail detail : sale.getDetails()) {

              if(detail.getItem().getDescription().equals(item.getDescription())) {

                cantVendida = cantVendida + detail.getQuantity();
              }
            }
          }

        }// for sale

        auxDoc.append(store.getAddress().getStreet()+" "+store.getAddress().getNumber(), cantVendida);

        cantVendida = 0;

      }// for store

      report.add(new Document(auxDoc));
      auxDoc.clear();

    }// for item

    return gson.toJson(report);
  }

  public String fourthRanking() {

    List<Document> report = new ArrayList<Document>();
    Document auxDoc = new Document();
    double cantVenta = 0;
    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    for(Person client : PersonService.getInstance().findAll()) {

      auxDoc.put(client.getName()+" "+client.getSurname()+" "+client.getDni(), "COMPRAS X SUCURSAL");

      for(Store store : StoreService.getInstance().findAll()) {

        for(Sale sale : findAll()) {

          if((sale.getClient().getDni() == client.getDni()) && (sale.getSalesman().getStore().equals(store.getId()))) {

            cantVenta = cantVenta +1;
          }

        }

        auxDoc.append(store.getAddress().getStreet()+" "+store.getAddress().getNumber(), cantVenta);
        cantVenta = 0;
      }

      report.add(new Document(auxDoc));
      auxDoc.clear();
    }

    return gson.toJson(report);
  }

  public String thirdRanking() {

    List<Document> report = new ArrayList<Document>();
    double cantVenta = 0;
    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    for(Person client : PersonService.getInstance().findAll()) {

      for(Sale sale : findAll()) {

        if(sale.getClient().getDni() == client.getDni()) {

          cantVenta = cantVenta +1;
        }

      }

      report.add(new Document(client.getName()+" "+client.getDni(), cantVenta));
      cantVenta = 0;
    }

    return gson.toJson(report);
  }

  public String exportAll(Boolean write) throws IOException {

    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();
    String json = null;

    json = gson.toJson(findAll());

    if(write) {

      Writer writer = new FileWriter("sales.json");

      gson.toJson(findAll(), writer);
      writer.flush();
      writer.close();

    }

    return json;
  }

}
