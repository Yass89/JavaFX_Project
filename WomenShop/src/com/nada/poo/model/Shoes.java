package com.nada.poo.model;

public class Shoes extends Product {

  private int shoeSize;
    private final int category_id=2;

  public Shoes(String name, double price, int nbItems, int shoeSize) {
    super(name, price, nbItems);
    this.shoeSize = shoeSize;
  }

  public int getShoeSize() {
    return shoeSize;
  }

  public void setShoeSize(int shoeSize) {
    this.shoeSize = shoeSize;
  }

  @Override
  public String toString() {
    return "Shoes{" + super.toString()+
            " shoeSize=" + shoeSize +
            '}';
  }

  @Override
  public int getCategoryId() {
    return this.category_id;
  }

  @Override
  public void applyDiscount() {
    this.setPrice(this.getPrice()*(1-DISCOUNT_SHOES));

  }
}
