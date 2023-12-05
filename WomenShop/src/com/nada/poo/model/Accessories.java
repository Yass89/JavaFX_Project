package com.nada.poo.model;

public class Accessories extends Product {

  private final int category_id=3;
  public Accessories(String name, double price, int nbItems) {

    super(name, price, nbItems);
  }

  @Override
  public String toString() {
    return "Accessories{"+ super.toString()+"}";
  }

  @Override
  public int getCategoryId() {
    return this.category_id;
  }

  @Override
  public void applyDiscount() {
    this.setPrice(this.getPrice()*(1-DISCOUNT_ACCESSORIES));

  }
}
