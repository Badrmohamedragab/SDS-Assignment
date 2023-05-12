/**
    This class represents a shopping cart for a specific user, containing a list of shopping items.
 */

package ShoppingCart;
import Products.*;
import java.sql.SQLException;
import java.util.*;
import Database.Control ;

public class ShoppingCart {
    private ArrayList<ShoppingItem> items ;
    private final int id ;
    private final int userId ;
    private double totalPrice ;
    private int numberOfItems ;

    /**
     * Constructs a new ShoppingCart object with the given user ID and cart ID.
     *
     * @param userId The ID of the user who owns the shopping cart.
     * @param id The ID of the shopping cart.
     */
    public ShoppingCart(int userId, int id){
        items = new ArrayList<>() ;
        this.id = id ;
        this.userId = userId ;
        this.totalPrice = 0 ;
        this.numberOfItems = 0 ;
    }

    /**
     * Returns the ID of the shopping cart.
     *
     * @return The ID of the shopping cart.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the ID of the user who owns the shopping cart.
     *
     * @return The ID of the user who owns the shopping cart.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Returns the total price of all the items in the shopping cart.
     *
     * @return The total price of all the items in the shopping cart.
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Returns the number of items in the shopping cart.
     *
     * @return The number of items in the shopping cart.
     */
    public int getNumberOfItems() {
        return numberOfItems;
    }

    /**
     * Adds a new product to the shopping cart or updates the quantity of an existing product in the cart.
     * Updates the total price and the number of items in the cart accordingly.
     *
     * @param product The product to add or update in the cart.
     * @param quantity The quantity of the product to add or update.
     */
    public void addProductToList(Product product, int quantity){
        ShoppingItem newItem = new ShoppingItem(product, quantity) ;
        ShoppingItem Item = null ;

        for (ShoppingItem item : items) {
            if (product.getInfo().getId() == item.getId()){
                Item = item ;
            }
        }
        if (Item == null){
            items.add(newItem) ;
        }
        else{
            Item.addQuantity(quantity) ;
        }

        double pPrice = product.getInfo().getPrice() ;
        totalPrice += (quantity * (pPrice - (pPrice * product.getInfo().getDiscountAmount()))) ;
        numberOfItems += quantity ;
    }

    /**
     * Adds a new product to the shopping cart and updates the product's quantity in the database.
     *
     * @param product The product to add to the shopping cart.
     * @param quantity The quantity of the product to add to the shopping cart.
     * @throws SQLException If there is an error while updating the product's quantity in the database.
     */
    public void addItem(Product product, int quantity) throws SQLException {
        addProductToList(product, quantity);

        Control control = new Control() ;
        control.UpdateProduct(product, this, quantity) ;
        System.out.println("You added " + quantity + " of " + product.getInfo().getName() + " successfully");
    }

    /**
     * Prints information about the shopping cart, including the cart ID, number of items,
     * and the items in the cart with their respective prices and quantities.
     */
    public void print() {
        System.out.println("<-------------- Cart -------------->");
        System.out.println("Cart id -> " + id + "\nNumber of items in it -> " + numberOfItems);

        if (items.size() == 0){
            System.out.print("No items added yet");
        }
        else {
            int i =  1;
            for (ShoppingItem SI : items) {
                System.out.println("---------- Product# " + i++ + " ----------");
                SI.print();
            }
        }
        System.out.println("\nTotal Price -> $" + totalPrice);
    }

    /**
     * Adds a product to the shopping cart with the specified quantity.
     * Updates the quantity and price of the item if it already exists in the cart.
     * Checks if there is enough quantity of the product in the inventory before adding it to the cart.
     *
     * @param product the product to add to the cart
     * @param quantity the quantity of the product to add
     * @throws SQLException if there is an error updating the product quantity and price in the database
     */
    public void addProduct(Product product, int quantity) throws SQLException {
        if (this.items.size() == 0){
            items = new ArrayList<>();
        }

        Control control = new Control() ;
        if(!control.checkQuantity(product, quantity)){
            return;
        }

        addProductToList(product, quantity) ;

        control.UpdateProduct(product, this, quantity) ;
        System.out.println("You added " + quantity + " of " + product.getInfo().getName() + " successfully");
    }

    /**
     * Returns a copy of the ArrayList of ShoppingItems in the shopping cart.
     *
     * @return a LinkedList of ShoppingItems representing the items in the shopping cart
     */
    public List<ShoppingItem> getItems() {
        return new LinkedList<>(items);
    }

    /**
     * Resets the shopping cart by clearing all items and setting the total price and number of items to 0.
     */
    public void reset() {
        items.clear();
        totalPrice = 0;
        numberOfItems = 0;
    }

    /**
     * Returns a boolean indicating whether or not the shopping cart is empty.
     *
     * @return true if the shopping cart is empty, false otherwise
     */
    public boolean isEmpty() {
        return items.isEmpty() ;
    }
}