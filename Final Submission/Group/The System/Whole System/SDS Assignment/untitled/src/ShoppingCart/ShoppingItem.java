package ShoppingCart;
import Products.Product;

public class ShoppingItem {
    private final Product Item;
    private int quantity;

    public ShoppingItem(Product item, int quantity) {
        this.quantity = quantity;
        this.Item = item;
    }

    public void print() {
        System.out.println(quantity + " of -> " +
                Item.getInfo().getName() + "\nPrice of one unit\\Kilo -> " + Item.getInfo().getPrice()) ;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getId() {
        return Item.getInfo().getId() ;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity ;
    }
//
//    public double totalPriceForItem(){
//        double productPrice = this.item.getInfo().getPrice(), discount = this.item.getInfo().getDiscountAmount();
//        return (this.quantity) * (productPrice - (productPrice * discount));
//    }
}
