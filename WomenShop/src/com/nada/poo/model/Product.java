package com.nada.poo.model;

public abstract class Product implements Discount,Comparable<Product>{
  private static double capital = 10000.0;
  private static double cost = 0;
  private int id;
  private String name;
  private double price;
  private int nbItems;

  static int nb=0;
  static double income=0;

  public Product(String name, double price, int nbItems) {
    this.id=++nb;
    this.name = name;
    //this.price = price;
    setPrice(price);
    this.nbItems = nbItems;
  }

  public int getId() {
    return id;
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

  public static double getIncome() {
    return income;
  }

  public void sellItems(int quantity, double sellPrice) {
    if (quantity <= this.nbItems) {
      this.nbItems -= quantity;  // Decrease stock
      double totalIncome = sellPrice * quantity;
      income += totalIncome;     // Increment income
      capital += totalIncome;    // Increase capital by income
    } else {
      throw new IllegalArgumentException("Not enough stock to sell the requested amount.");
    }
  }

  public void purchaseItems(int quantity, double purchasePrice) {
    if (purchasePrice < this.price) {
      this.nbItems += quantity;    // Increase stock
      double totalCost = purchasePrice * quantity;
      cost += totalCost;           // Increment total cost
      capital -= totalCost;        // Decrease capital by cost
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
  @Override
  public int compareTo(Product o) {
    return Double.compare(this.getPrice(), o.getPrice());
  }

  public static double getCapital() {
    return capital;
  }

  public static void setCapital(double newCapital) {
    capital = newCapital;
  }

  public static double getCost() {
    return cost;
  }

  public static void setCost(double newCost) {
    cost = newCost;
  }
}
