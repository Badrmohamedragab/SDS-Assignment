import java.util.ArrayList;
import java.util.Objects;

public class ShoppingCart {
    private final ArrayList<shoppingItem> items = new ArrayList<>();
    int id ;
    int userId ;
    double totalPrice ;
    int numberOfItems ;
    public ShoppingCart(int userId, int Id){
        this.id = Id ;
        this.userId = userId ;
        this.totalPrice = 0 ;
        this.numberOfItems = 0 ;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void addItem(shoppingItem item)
    {
       items.add(item);
    }
    public void print()
    {
        System.out.println("<-------------- Cart -------------->");
        System.out.println("Cart id -> " + id + "\nNumber of items in it -> " + numberOfItems);

        if (items.size() == 0){
            System.out.print("No items added yet");
        }
        else {
            int i =  1;
            for (shoppingItem SI : items) {
                System.out.println("---------- Product# " + i++ + " ----------");
                SI.print();
            }
        }
        System.out.println("\nTotal Price -> $" + totalPrice);
    }
    public void updateQuantity(int itemId, double newQuantity){
        for (shoppingItem item : items) {
            if (Objects.equals(item.getItem().getId(), itemId)) {
                item.setQuantity(newQuantity);
            }
        }
    }
//    public void removeItem(int  itemId){
//        items.removeIf(item -> Objects.equals(item.getId(), itemId));
//    }
//    public shoppingItem getItem(int productId)
//    {
//        for (shoppingItem item : items) {
//            if (Objects.equals(item.getId(), productId)) {
//                return item;
//            }
//        }
//        return null;
//    }
//    public int countItems()
//    {
//        return items.size();
//    }
//    public void checkOut(PaymentMethod p) {
//        System.out.println("You select cash please wait for your order");
////         items.addOrder();
//            items.clear();
//    }


}
