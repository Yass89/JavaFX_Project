package com.nada.poo.model;

public class Shoes extends Product {

  private int shoeSize;
    private final int category_id=2;

  public Shoes(String name, double price, int nbItems, int shoeSize) {
    super(name, price, nbItems);
    this.shoeSize = shoeSize;
  }

    public Shoes(int id, String name, double price, int nbItems, int shoeSize) {
        super(id, name, price, nbItems);
        this.shoeSize = shoeSize;
    }

    // get type of product
  public String getType(){
    return "Shoes";
  }

  public Object getSize() {
    return shoeSize;
  }

  public void setSize(Object shoeSize) {
    this.shoeSize = (int) shoeSize;
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
    this.isDiscounted=true;
  }

  public void removeDiscount() {
    this.setPrice(this.getPrice()/(1-DISCOUNT_SHOES));
    this.isDiscounted=false;
  }
}
