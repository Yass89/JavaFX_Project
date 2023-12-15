package com.nada.poo.model;

public class Clothes extends Product {

  private int size;
  private final int category_id=1;


  public Clothes(String name, double price, int nbItems, int size) {
    super(name, price, nbItems);
    setSize(size);
  }

    public Clothes(int id, String name, double price, int nbItems, int size) {
        super(id, name, price, nbItems);
        setSize(size);
    }

  public Object getSize() {
    return size;
  }

  public void setSize(Object sizeInt) throws IllegalArgumentException {
    int size = (int) sizeInt;
    if(size>=36 && size <=50){
      this.size = size;
    }else throw new IllegalArgumentException("Size is not valid, it has to be between 36 and 50+");
  }

  @Override
  public String toString() {
    return "Clothes{" +super.toString()+
            " size=" + size +
            '}';
  }


  @Override
  public int getCategoryId() {
    return this.category_id;
  }

  // get type of product
    public String getType(){
        return "Clothes";
    }

  @Override
  public void applyDiscount() {
    this.setPrice(this.getPrice()*(1-DISCOUNT_CLOTHES));
    this.isDiscounted=true;
  }

    public void removeDiscount() {
        this.setPrice(this.getPrice()/(1-DISCOUNT_CLOTHES));
        this.isDiscounted=false;
    }

}
