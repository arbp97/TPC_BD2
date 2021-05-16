package com.grupo_bd2.tpc.config;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.grupo_bd2.tpc.services.AddressService;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoWriteException;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Config {

  private String dbUri;
  private String dbName;
  private MongoClient mongoClient;
  private MongoDatabase mongoDatabase;
  private static Config config = null;
  private String status;

  public static Config getInstance() {

    if (config == null) {
      config = new Config();
    }

    return config;
  }

  private Config() {

    Logger.getLogger("org.mongodb.driver").setLevel(Level.OFF);

    this.dbUri = "mongodb://localhost:27017";
    this.dbName = "tpc_db";

    CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
        fromProviders(PojoCodecProvider.builder().automatic(true).build()));

    MongoClientSettings settings = MongoClientSettings.builder()
        .codecRegistry(pojoCodecRegistry)
        .build();

    this.mongoClient = MongoClients.create(settings);
    this.mongoDatabase = mongoClient.getDatabase(this.dbName);

    //////////////////////////////////////////////////////////////////////////////////

    this.status = "STATUS: OK";
  }

  public String getDbUri() {
    return dbUri;
  }

  public void setDbUri(String dbName) {
    this.dbName = dbName;
  }

  public String getDbName() {
    return dbName;
  }

  public void setDbName(String dbName) {
    this.dbName = dbName;
  }

  public MongoClient getMongoClient() {
    return this.mongoClient;
  }

  public MongoDatabase getMongoDatabase() {
    return this.mongoDatabase;
  }

  public String getStatus() {
    return this.status;
  }

}
