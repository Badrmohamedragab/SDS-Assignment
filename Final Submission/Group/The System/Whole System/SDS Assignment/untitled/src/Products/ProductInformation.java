package Products;

public class ProductInformation {
    String name;
    int id;
    String description;
    double price;
    double discountAmount;
    String category;
    String brand;
    UnitType unitType;
    Status status;
    int quantity;

    public ProductInformation(String Name, String description, double price, double discountAmount,
                              String category, String brand, UnitType unitType, Status status, int quantity, int id) {
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

    public UnitType getUnitType() {
        return unitType;
    }

    public Status getStatus() {
        return status;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getId() {
        return id;
    }

    public void print() {
        System.out.print("\n--------------Product Information--------------");
        System.out.println(
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

    public void setQuantity(int quantity) {
        this.quantity = quantity ;
    }
}

