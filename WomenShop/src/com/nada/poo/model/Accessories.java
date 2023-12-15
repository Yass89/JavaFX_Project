package com.nada.poo.model;

public class Accessories extends Product {

  private final int category_id=3;
  public Accessories(String name, double price, int nbItems) {

    super(name, price, nbItems);
  }

    public Accessories(int id, String name, double price, int nbItems) {
        super(id, name, price, nbItems);
    }

  public Object getSize() {
    return null;
  }

  public void setSize(Object size) {
  }

  // get type of product
    public String getType(){
        return "Accessories";
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
    this.isDiscounted=true;
  }

    public void removeDiscount() {
        this.setPrice(this.getPrice()/(1-DISCOUNT_ACCESSORIES));
        this.isDiscounted=false;
    }
}
