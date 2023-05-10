package User;
import Database.*;
import MethodOfSearch.Search;
import Orders.*;
import Products.*;
import ShoppingCart.ShoppingCart;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class User {
    private UserInformation info ;
    private Map<Integer, Orders> orders = new HashMap<>();
    private boolean LoggedIn ;
    private ShoppingCart cart ;
    public void Login(int userId, String Pass) {
        if (!LoggedIn) {
            Control control = new Control();
            UserInformation temp = control.Login(userId, Pass);

            if (temp != null) {
                System.out.println("You Logged in successfully");
                this.setInfo(temp);
                this.setOrders(control.getOrders(userId));
                cart = control.getCart(userId) ;
                LoggedIn = true;
            }
        }
        else{
            System.out.println("You already Logged in");
        }
    }
    public void Register(UserInformation info) throws SQLException {
        if(!LoggedIn) {
            Control control = new Control();
            User user = control.Register(info) ;
            this.setInfo(user.getInfo()) ;
            this.setCart(user.getCart()) ;

            if (this.info == null) {
                System.out.println("You entered existing id");
                return;
            }
            System.out.println("You registered successfully");
            LoggedIn = true;
        }
        else{
            System.out.println("You already registered");
        }
    }
    public void Logout(){
        if (LoggedIn){
            info = null ;
            LoggedIn = false ;
            java.lang.System.out.println("You Logged out successfully");
        }
        else{
            System.out.println("You already Logged out");
        }
    }
    public void setInfo(UserInformation Info){
        this.info = Info ;
    }
    public UserInformation getInfo(){
        return info ;
    }

    public void print() {
        System.out.print("\n") ;
        if (this.getInfo() != null) {
            info.print() ;
            if (cart != null) {
                cart.print();
            }
        }
        else{
            System.out.println("Can't see your information and you logged out");
        }
    }
    public void viewCatalog(){
        Catalog catalog = new Catalog() ;
        catalog.print();
    }
    public void searchForItem(Search method, String Something){
        Catalog catalog = new Catalog();
        catalog.search(method, Something) ;
    }
    public void AddToCart(String pName, String pCategory, String pBrand, int quantity) throws SQLException {
        if (!LoggedIn){
            System.out.println("Can't add products to cart and you logged out");
            return ;
        }
        if (this.getInfo().getStatus() == UserStatus.Suspended){
            System.out.println("Can't add products to cart and you Suspended");
            return ;
        }

        Catalog catalog = new Catalog() ;
        Product product = catalog.getProduct(pName, pCategory, pBrand) ;
        UnitType unitType = product.getInfo().getUnitType();

        if (quantity > unitType.getMaxAmount()){
            System.out.print("Can't add product upon " + unitType.getMaxAmount());
            if (unitType instanceof ByUnits){
                System.out.println(" Units");
            }
            else{
                System.out.println(" Kilos");
            }
            return ;
        }
        if (product.getInfo().getStatus() == Status.OutOfStock){
            System.out.println("This Product is out of stock");
            return ;
        }
        if (quantity > product.getInfo().getQuantity()){
            System.out.println("You exceeded quantity limit\nQuantity of this product -> " + product.getInfo().getQuantity());
            return ;
        }
        this.cart.addProduct(product, quantity) ;

        Control control = new Control() ;
        control.UpdateProduct(product, cart, quantity) ;
        System.out.println("You added " + quantity + " of " + pName + " successfully");
    }

    public ShoppingCart getCart() {
        return this.cart ;
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart ;
    }

    public void checkOut(double tax, String deliveryAddress) throws SQLException {
        if (cart.isEmpty() || orders.size() == 0){
            System.out.println("You can't make an order without cart");
            return;
        }
        Control control = new Control() ;
        int Id = control.generateOrderId() ;

        Orders order = new Orders(tax, deliveryAddress, Id) ;
        order.setOrder(this.cart.getItems(), cart.getTotalPrice()) ;
        orders.put(Id, order) ;

        control.addOrder(order, info.getId()) ;
        control.RemoveCart(cart) ;
    }
    public void payOrder(PaymentMethod payment, int Id) throws SQLException {
        if (!orders.containsKey(Id)){
            System.out.println("Id is invalid");
            return;
        }
        if(orders.get(Id).payOrder(payment)){
            orders.get(Id).setStatus("CLOSED");

            Control control = new Control() ;
            control.updateOrderStatus(Id) ;
        }
    }
    public void printOrders() {
        for (Orders order :
                orders.values()) {
            order.printOrder();
            System.out.println("-----------------------------------");
        }
    }

    public boolean hasOrderId(int randomId) {
        return orders.containsKey(randomId) ;
    }

    public void setOrders(Map<Integer,Orders> orders) {
        this.orders = orders ;
    }

    public void addOrder(Orders order) {
        orders.put(order.getId(), order) ;
    }

    public double getOrderPrice(int id) {
        return orders.get(id).getTotalPrice() ;
    }

    public Map<Integer,Orders> getOrders() {
        return orders ;
    }
}
