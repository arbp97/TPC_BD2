package com.grupo_bd2.tpc.entities;

import com.google.gson.Gson;

import org.bson.types.ObjectId;

public class SaleDetail {

  private ObjectId id;
  private Sale sale;
  private Item item;
  private float total;
  private int quantity;

  public SaleDetail() {
  }

  public SaleDetail(Sale sale, Item item, int quantity) {

    this.sale = sale;
    this.item = item;
    this.quantity = quantity;
    this.total = item.getPrice() * quantity;
  }

  public ObjectId getId() {
    return this.id;
  }

  public void setId(ObjectId id) {
    this.id = id;
  }

  public Sale getSale() {
    return this.sale;
  }

  public void setSale(Sale sale) {
    this.sale = sale;
  }

  public Item getItem() {
    return this.item;
  }

  public void setItem(Item item) {
    this.item = item;
  }

  public float getTotal() {
    return this.total;
  }

  public void setTotal(float total) {
    this.total = total;
  }

  public int getQuantity() {
    return this.quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }

}
