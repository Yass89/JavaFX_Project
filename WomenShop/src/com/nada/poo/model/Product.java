package com.nada.poo.model;

public abstract class Product implements Discount,Comparable<Product>{


  private int id;
  private String name;
  private double price;
  private int nbItems;

  protected boolean isDiscounted=false;

  static int nb=0;

  public Product(String name, double price, int nbItems) {
    this.id=++nb;
    this.name = name;
    //this.price = price;
    setPrice(price);
    this.nbItems = nbItems;
  }

  public Product(int id, String name, double price, int nbItems) {
    this.id=id;
    this.name = name;
    setPrice(price);
    this.nbItems = nbItems;
  }

  public int getId() {
    return id;
  }

  public int setId(int id) {
    return this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) throws IllegalArgumentException {
      if (price >= 0) {
        this.price = price;
      } else throw new IllegalArgumentException("Price is negative");
    }



  public int getNbItems() {
    return nbItems;
  }

  public void setNbItems(int nbItems) {
    this.nbItems = nbItems;
  }

  public static int getNb() {
    return nb;
  }

  public void sellItems(double sellPrice) {
    if (1 <= this.nbItems) {
      this.nbItems -= 1;  // Decrease stock
      Financial.income += sellPrice;     // Increment income
      Financial.capital += sellPrice;  // Increase capital by income
    } else {
      throw new IllegalArgumentException("Not enough stock to sell the requested amount.");
    }
  }

  public void purchaseItems(double purchasePrice) {
    if (purchasePrice < this.price) {
      this.nbItems += 1;    // Increase stock;
      Financial.cost += purchasePrice;           // Increment total cost
      Financial.capital -= purchasePrice;        // Decrease capital by cost
    } else {
      throw new IllegalArgumentException("Purchase price must be less than the sale price.");
    }
  }
  @Override
  public String toString() {
    return "Product{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", price=" + price +
            ", nbItems=" + nbItems +
            '}';
  }

  public abstract int getCategoryId();

  public abstract String getType();

  public abstract Object getSize();

  public abstract void setSize(Object size);

  @Override
  public int compareTo(Product o) {
    return Double.compare(this.getPrice(), o.getPrice());
  }

  // get discount
  public boolean isDiscounted(){
    return this.isDiscounted;
  }

}
