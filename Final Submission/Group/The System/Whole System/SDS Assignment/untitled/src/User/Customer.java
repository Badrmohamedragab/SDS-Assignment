package User;
import Database.*;
import Orders.*;
import Products.*;
import ShoppingCart.*;
import javax.mail.MessagingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Customer implements User{
    private CustomerInformation info ;
    private Map<Integer, Order> orders = new HashMap<>();
    private boolean LoggedIn ;
    private ShoppingCart cart ;

    @Override
    public void Login(int userId, String Pass) {
        if (!LoggedIn) {
            Control control = new Control();
            Customer temp = control.Login(userId, Pass);

            if (temp != null) {
                this.setInfo(temp.getInfo());
                this.setOrders(temp.getOrders());
                this.setCart(temp.getCart());

                java.lang.System.out.println("You Logged in successfully welcome " + info.getName() + " :)");
                LoggedIn = true;
            }
        }
        else{
            System.out.println("You already Logged in");
        }
    }

    @Override
    public void Register(CustomerInformation info) throws SQLException, MessagingException {
        if(!LoggedIn) {
            Control control = new Control();
            Customer customer = control.Register(info) ;

            if(customer != null){
                LoggedIn = true ;
                System.out.println("You registered successfully welcome " + info.getName() + " :)");
                this.setCart(customer.getCart());
                this.setInfo(customer.getInfo());
            }
            else{
                System.out.println("You entered existing id");
            }
        }
        else{
            System.out.println("You already Logged in");
        }
    }
    public void Logout(){
        if (LoggedIn){
            String name = info.getName() ;
            info = null ;
            LoggedIn = false ;
            java.lang.System.out.println("You Logged out successfully good bye " + name);
        }
        else{
            System.out.println("You already Logged out");
        }
    }
    public void setInfo(CustomerInformation Info){
        this.info = Info ;
    }

    public CustomerInformation getInfo(){
        return info ;
    }

    public void print() {
        System.out.print("\n") ;
        if (this.getInfo() != null) {
            info.print() ;
        }
        else{
            System.out.println("Can't see your information and you logged out");
        }
    }

    public void printCart(){
        if (cart != null) {
            cart.print();
        }
        else{
            System.out.println("Can't see your cart and you logged out");
        }
    }
    public void viewCatalog(){
        Catalog catalog = new Catalog() ;
        catalog.print();
    }
    public void AddToCart(String pName, String pCategory, String pBrand, int quantity) throws SQLException {
        if (!LoggedIn){
            System.out.println("Can't add products to cart and you logged out");
            return ;
        }
        if (this.getInfo().getStatus() == CustomerStatus.Suspended){
            System.out.println("Can't add products to cart and you Suspended");
            return ;
        }
        Catalog catalog = new Catalog() ;
        Product product = catalog.getProduct(pName, pCategory, pBrand) ;

        this.cart.addProduct(product, quantity) ;
    }

    public ShoppingCart getCart() {
        return this.cart ;
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart ;
    }

    public void checkOut(double tax, Address deliveryAddress) throws SQLException {
        if (cart.isEmpty()){
            System.out.println("You can't make an order without cart");
            return;
        }

        Order order = new Order(tax, deliveryAddress) ;
        order.setOrder(this.cart.getItems(), cart.getTotalPrice()) ;
        orders.put(order.getId(), order) ;

        Control control = new Control() ;
        control.addOrder(order, info.getId()) ;
        control.RemoveCart(cart) ;
    }
    public void payOrder(PaymentMethod payment, int Id) throws SQLException {
        if (!orders.containsKey(Id)){
            System.out.println("Id is invalid");
            return;
        }
        if(orders.get(Id).payOrder(payment)){
            Control control = new Control() ;
            control.updateOrderStatus(Id) ;
        }
    }
    public void printOrders() {
        for (Order order :
                orders.values()) {
            order.printOrder();
            System.out.println("-----------------------------------");
        }
    }

    public boolean hasOrderId(int randomId) {
        return orders.containsKey(randomId) ;
    }

    public void setOrders(Map<Integer,Order> orders) {
        this.orders = orders ;
    }

    public void addOrder(Order order) {
        orders.put(order.getId(), order) ;
    }

    public double getOrderPrice(int id) {
        return orders.get(id).getTotalPrice() ;
    }

    public Map<Integer,Order> getOrders() {
        return orders ;
    }
}
