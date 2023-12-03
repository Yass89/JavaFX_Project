package com.nada.poo.model;

public class Accessories extends Product {

  public Accessories(String name, double price, int nbItems,double purchasePrice) {

    super(name, price, nbItems, purchasePrice);
  }

  @Override
  public String toString() {
    return "Accessories{"+ super.toString()+"}";
  }

  @Override
  public void applyDiscount() {
    this.setPrice(this.getPrice()*(1-DISCOUNT_ACCESSORIES));

  }
}
