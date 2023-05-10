package Database;
import Orders.Orders;
import Products.Product;
import ShoppingCart.*;
import User.*;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class Control {
    public boolean existId(int id) {
        Database database = new Database();
        return database.exist(id);
    }

    public boolean existShoppingCartId(int id) {
        Database database = new Database();
        return database.existShoppingCartId(id);
    }

    public void addUser(User user) throws SQLException {
        Database database = new Database();
        database.addUser(user);
    }

    public User Register(UserInformation info) throws SQLException {
        if (!this.existId(info.getId())) {
            int randomId;
            do {
                Random rand = new Random();
                int minId = 1;
                int maxId = 50;

                randomId = rand.nextInt((maxId - minId) + 1) + minId;
            } while (this.existShoppingCartId(randomId));

            ShoppingCart cart = new ShoppingCart(info.getId(), randomId);

            User user = new User();
            user.setInfo(info);
            user.setCart(cart);
            this.addUser(user);

            return user;
        }

        return null;
    }

    public UserInformation Login(int id, String pass) {
        Database database = new Database();

        if (this.existId(id)) {
            String temp = database.getPass(id);

            if (Objects.equals(temp, pass)) {
                return database.getUserInformation(id);
            } else {
                System.out.println("Password is incorrect :(");
            }
        } else {
            System.out.println("Id is not exist :(");
        }
        return null;
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
            Random rand = new Random();
            int minId = 1;
            int maxId = 50;

            randomId = rand.nextInt((maxId - minId) + 1) + minId;
        } while (database.existOrderId(randomId));

        return randomId ;
    }

    public void addOrder(Orders order, int id) throws SQLException {
        Database database = new Database() ;
        database.addOrder(order, id);
    }

    public void RemoveCart(ShoppingCart cart) throws SQLException {
        Database database = new Database() ;
        database.removeCart(cart) ;
    }

    public Map<Integer,Orders> getOrders(int userId) {
        Database database = new Database() ;
        return database.getOrders(userId) ;
    }

    public void updateOrderStatus(int id) throws SQLException {
        Database database = new Database() ;
        database.updateOrderStatus(id);
    }
}
