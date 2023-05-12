package Database;
import EmailSender.EmailSender;
import Orders.Order;
import Products.*;
import ShoppingCart.*;
import User.*;
import javax.mail.MessagingException;
import java.sql.SQLException;
import java.util.*;

public class Control {
    public boolean existId(int id) {
        Database database = new Database();
        return database.exist(id);
    }

    public boolean checkQuantity(Product product, int quantity){
        UnitType unitType = product.getInfo().getUnitType() ;

        if (quantity > unitType.getMaxAmount()){
            System.out.print("Can't add product upon " + unitType.getMaxAmount());
            if (unitType instanceof ByUnits){
                System.out.println(" Units");
            }
            else{
                System.out.println(" Kilos");
            }
            return false;
        }
        if (product.getInfo().getStatus() == Status.OutOfStock){
            System.out.println("This Product is out of stock");
            return false;
        }
        if (quantity > product.getInfo().getQuantity()){
            System.out.println("You exceeded quantity limit\nQuantity of this product -> " + product.getInfo().getQuantity());
            return false;
        }

        return true ;
    }
    public boolean existShoppingCartId(int id) {
        Database database = new Database();
        return database.existShoppingCartId(id);
    }

    public void addUser(Customer customer) throws SQLException {
        Database database = new Database();
        database.addUser(customer);
    }

    public int getRandom(){
        Random rand = new Random();
        return rand.nextInt(50);
    }
    public Customer Register(CustomerInformation info) throws MessagingException, SQLException {
        if (!this.existId(info.getId())) {
            int randomId;
            do {
                randomId = getRandom() ;
            } while (this.existShoppingCartId(randomId));

            ShoppingCart cart = new ShoppingCart(info.getId(), randomId);

            if(confirmEmail(info.getEmail())) {
                Customer customer = new Customer() ;
                customer.setInfo(info);
                customer.setCart(cart);
                addUser(customer);

                return customer;
            }
        }
        return null;
    }
    private String generateOTP() {
        StringBuilder OTP = new StringBuilder();
        for (int i =0; i < 8; ++i){
            Random rand = new Random();
            OTP.append(rand.nextInt(10));
        }
        return OTP.toString();
    }
    public String sendOTP(String Email) throws MessagingException {
        String OTP = generateOTP() ;

        EmailSender emailSender = new EmailSender() ;
        emailSender.sendEmail(Email, OTP);

        return OTP ;
    }
    public boolean confirmEmail(String Email) throws MessagingException {
        String requiredOTP, OTP ;

        for (int i = 0; i <= 5; ++i) {
            OTP = sendOTP(Email);

            System.out.println("An OTP sent to your email. enter it -> ");
            Scanner scanner = new Scanner(System.in) ;
            requiredOTP = scanner.nextLine() ;

            if (requiredOTP.equals(OTP)){
                return true ;
            }
            else{
                System.out.print("\nInvalid OTP The number of remaining attempts -> "+ (5 - i - 1) +"\n");
            }
        }
        return false ;
    }
    public Customer Login(int id, String pass) {
        Database database = new Database();

        if (this.existId(id)) {
            String temp = database.getPass(id);

            if (Objects.equals(temp, pass)) {
                return database.getCustomer(id);
            }
            else {
                System.out.println("Password is incorrect");
            }
        }
        else {
            System.out.println("Id is not exist");
        }

        return null;
    }

    public static List<Product> LoadProducts(){
        Database database = new Database() ;
        return database.getProducts() ;
    }
    public ShoppingCart getCart(int userId) {
        Database database = new Database();
        return database.getCart(userId);
    }

    public void UpdateProduct(Product product, ShoppingCart cart, int quantity) throws SQLException {
        Database database = new Database();

        database.UpdateProducts(product, quantity);
        database.UpdateShoppingCart(cart);
        database.UpdateShoppingItems(product, quantity, cart);
    }
    public int generateOrderId() {
        Database database = new Database() ;

        int randomId;
        do {
            randomId = getRandom() ;
        } while (database.existOrderId(randomId));

        return randomId ;
    }
    public void addOrder(Order order, int id) throws SQLException {
        Database database = new Database() ;
        database.addOrder(order, id);
    }
    public void RemoveCart(ShoppingCart cart) throws SQLException {
        Database database = new Database() ;
        database.resetCart(cart) ;
    }
    public Map<Integer, Order> getOrders(int userId) {
        Database database = new Database() ;
        return database.getOrders(userId) ;
    }
    public void updateOrderStatus(int id) throws SQLException {
        Database database = new Database() ;
        database.updateOrderStatus(id);
    }
}
