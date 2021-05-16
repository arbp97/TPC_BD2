package com.grupo_bd2.tpc.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;

import org.bson.types.ObjectId;

public class Sale {

  private ObjectId id;
  private Employee salesman;
  private Employee cashier;
  private LocalDateTime date;
  private long ticketNumber;
  private float total;
  private String paymentMethod;
  private Set<SaleDetail> details;

  public Sale() {
  }

  public Sale(Employee salesman, Employee cashier, LocalDateTime date, long ticketNumber, String paymentMethod) {

    this.salesman = salesman;
    this.cashier = cashier;
    this.date = date;
    this.ticketNumber = ticketNumber;
    this.total = 0;
    this.paymentMethod = paymentMethod;
    this.details = new HashSet<SaleDetail>();
  }

  public ObjectId getId() {
    return this.id;
  }

  public void setId(ObjectId id) {
    this.id = id;
  }

  public Employee getSalesman() {
    return this.salesman;
  }

  public void setSalesman(Employee salesman) {
    this.salesman = salesman;
  }

  public Employee getCashier() {
    return this.cashier;
  }

  public void setCashier(Employee cashier) {
    this.cashier = cashier;
  }

  public LocalDateTime getDate() {
    return this.date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  public long getTicketNumber() {
    return this.ticketNumber;
  }

  public void setTicketNumber(long ticketNumber) {
    this.ticketNumber = ticketNumber;
  }

  public float getTotal() {
    return this.total;
  }

  public void setTotal(float total) {
    this.total = total;
  }

  public String getPaymentMethod() {
    return this.paymentMethod;
  }

  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public Set<SaleDetail> getDetails() {
    return this.details;
  }

  public void setDetails(Set<SaleDetail> details) {
    this.details = details;
  }

  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }

}
