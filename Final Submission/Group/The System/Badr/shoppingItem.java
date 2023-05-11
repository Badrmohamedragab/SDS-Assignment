public class shoppingItem {
    private final Product Item;
    private double quantity;


    public shoppingItem(Product item, int id) {
        this.quantity = item.getQuantity();
        this.Item = item;

    }
    public void print() {
        System.out.println(quantity + " of -> " +
                Item.getName() + "\nPrice of one unit\\Kilo -> " + Item.getPrice()) ;
    }

    public double getQuantity() {
        return quantity;
    }

//    public double getPrice() {
//        return price;
//    }
//    public double getTotalCost()
//    {
//        return (price*quantity);
//    }
//    public double getUnitPrice()
//    {
//        return (price/ quantity);
//    }
    public void addQuantity(int quantity) {
        this.quantity += quantity ;
    }

    public Product getItem() {
        return Item;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
