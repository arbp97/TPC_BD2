package com.grupo_bd2.tpc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grupo_bd2.tpc.config.*;
import com.grupo_bd2.tpc.entities.Address;
import com.grupo_bd2.tpc.entities.Client;
import com.grupo_bd2.tpc.services.ClientService;
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

import java.util.List;
import java.util.Arrays;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;


public class MongoDBCDemo
{
    public static void main( String[] args )
    {
        //Java-MongoDB connection

        Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();

        MongoCollection<Address> addresses = Config.getInstance().getMongoDatabase().getCollection("addresses",Address.class);

        //cars.find(eq("modelo","corolla")).forEach((Consumer<Car>) System.out::println); //ejemplosxd

        //cars.find(and(gt("año", 2000))).forEach((Consumer<Car>) System.out::println); // de filtros, gt() es greater than

        Address address = new Address(888,"Calle Falsa","Springfield","Argenchina");

        addresses.insertOne(address);

        //addresses.find().forEach((Consumer<Address>) System.out::println);

        Client client = new Client(address, 12345678, "Hello", "Moto", null, null);
        Client client2 = new Client(address, 87654321, "Bye", "Auto", "OSDE", "1");

        ClientService.getInstance().insertOne(client);
        ClientService.getInstance().insertOne(client2);

        try {
            System.out.println(ClientService.getInstance().exportAll());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
