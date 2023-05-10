package Products;

import java.sql.*;

public class ProductInformation {
    String name;
    int id;
    String description;
    double price;
    double discountAmount;
    String category;
    String brand;
    Type unitType;
    String status;
    int quantity;


    public ProductInformation(String Name, String description, double price, double discountAmount,
                              String category, String brand, Type unitType, String status, int quantity, int id) {
        this.id = id;
        this.name = Name;
        this.description = description;
        this.price = price;
        this.discountAmount = discountAmount;
        this.category = category;
        this.brand = brand;
        this.unitType = unitType;
        this.status = status;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public String getCategory() {
        return category;
    }

    public String getBrand() {
        return brand;
    }

    public Type getUnitType() {
        return unitType;
    }

    public String getStatus() {
        return status;
    }

    public int getQuantity() {
        return quantity;
    }


    public void print() {
        System.out.print("\n--------------Product Information--------------");
        System.out.print(
                "\nName -> " + name +
                        "\nDescription -> " + description +
                        "\nPrice -> " + price +
                        "\nDiscount Amount -> " + discountAmount +
                        "\nCategory -> " + category +
                        "\nBrand -> " + brand +
                        "\nUnit Type -> " + unitType +
                        "\nStatus -> " + status +
                        "\nQuantity -> " + quantity
        );

    }

    public static void main(String[] args) {
        ProductInformation p = new ProductInformation("Mile Toffee", "Candy with milk", 50, 0.02, "Milk", "Milka", null, "Available", 2, 550);

        p.print();


    }
}

