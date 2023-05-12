package Database;
import Orders.*;
import Products.*;
import ShoppingCart.*;
import User.* ;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class Database {
    static Map<Integer, Customer> customers;
    static Map<Integer, Product> products;
    static Connection connection;

    public static void loadDatabase() throws SQLException, ParseException {
        // Establish a connection to the database

        String currentDir = java.lang.System.getProperty("user.dir");
        String url = "jdbc:sqlite:" + currentDir + "/src/sqilite/Database.db";
        connection = DriverManager.getConnection(url);

        ProductsLoad();
        CustomersLoad();
    }

    public static void ProductsLoad() throws SQLException {
        products = new HashMap<>();

        // Create a statement object
        Statement statement = connection.createStatement();

        // Execute a SELECT statement
        String sql = "SELECT * FROM Products";
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String brand = resultSet.getString("brand");
            String category = resultSet.getString("category");
            String description = resultSet.getString("description");
            String unitType = resultSet.getString("unitType");
            String status = resultSet.getString("status");
            int quantity = resultSet.getInt("quantity");
            double Price = resultSet.getDouble("price");
            double discountAmount = resultSet.getDouble("discountAmount");

            Status pStatus;
            if (Objects.equals(status, "Available")) {
                pStatus = Status.Available;
            } else {
                pStatus = Status.OutOfStock;
            }

            UnitType unitType1;
            if (unitType.equals("ByKilos")) {
                unitType1 = new ByKilo();
            } else {
                unitType1 = new ByUnits();
            }

            ProductInformation info = new ProductInformation(name, description, Price, discountAmount, category, brand,
                    unitType1, pStatus, quantity, id);

            Product product = new Product();
            product.setInfo(info);

            products.put(id, product);
        }
    }

    private static void CustomersLoad() throws SQLException, ParseException {
        customers = new HashMap<>();

        Statement statement = connection.createStatement();

        // Execute a SELECT statement
        String sql = "SELECT * FROM Users";
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int id = resultSet.getInt("ID");
            String name = resultSet.getString("Name");
            String email = resultSet.getString("Email");
            String Password = resultSet.getString("Password");

            // For Address
            String district = resultSet.getString("addressDistrict");
            String street = resultSet.getString("addressStreet");
            String Governorate = resultSet.getString("addressGovernorate");
            int buildingNumber = resultSet.getInt("addressBuildingNumber");
            int addressBuildFloor = resultSet.getInt("addressBuildingFloor");
            int addressBuildFlat = resultSet.getInt("addressBuildingFlat");
            String landmark = resultSet.getString("Landmark");

            Address add = new Address(Governorate, district, street, buildingNumber, addressBuildFloor, addressBuildFlat, landmark);

            String Status = resultSet.getString("Status");
            CustomerStatus status;

            if (Objects.equals(Status, "Suspended")) {
                status = CustomerStatus.Suspended;
            } else {
                status = CustomerStatus.unSuspended;
            }
            int ShoppingCartId = resultSet.getInt("ShoppingCartId");
            CustomerInformation info = new CustomerInformation(name, id, add, Password, email, status);

            ShoppingCart cart = new ShoppingCart(id, ShoppingCartId);
            ShoppingItemsLoad(cart);

            Map<Integer, Order> orders = UserOrdersLoad(id) ;

            Customer user = new Customer();
            user.setInfo(info);
            user.setCart(cart);
            user.setOrders(orders) ;

            customers.put(id, user);
        }
    }

    private static Map<Integer, Order> UserOrdersLoad(int id) throws SQLException, ParseException {
        Map<Integer, Order> orders = new HashMap<>() ;

        String sql = "SELECT * FROM Orders where CustomerId = " + id + " and Status = 'OPEN'";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int Id = resultSet.getInt("Id") ;
            double totalPrice = resultSet.getDouble("TotalPrice") ;

            String dateString = resultSet.getString("OrderDate") ;
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            Date date = dateFormat.parse(dateString);

            double taxPercentage = resultSet.getDouble("taxPercentage") ;

            String district = resultSet.getString("addressDistrict");
            String street = resultSet.getString("addressStreet");
            String Governorate = resultSet.getString("addressGovernorate");
            int buildingNumber = resultSet.getInt("addressBuildingNumber");
            int addressBuildFloor = resultSet.getInt("addressBuildingFloor");
            int addressBuildFlat = resultSet.getInt("addressBuildingFlat");
            String landmark = resultSet.getString("Landmark");

            Address add = new Address(Governorate, district, street, buildingNumber, addressBuildFloor, addressBuildFlat, landmark);
            String Status = resultSet.getString("Status") ;

            Order order = new Order(taxPercentage, add) ;
            order.setId(Id) ;
            order.setTotalPrice(totalPrice);
            order.setDate(date) ;
            order.setStatus(Status) ;

            orders.put(Id, order) ;
        }
        return orders ;
    }

    public void addOrder(Order order, int userId) throws SQLException {
        String s ;
        if (order.getStatus() == OrderStatus.OPEN){
            s = "OPEN" ;
        }
        else{
            s = "CLOSED" ;
        }

        String sql = "INSERT INTO Orders VALUES (" + order.getId() + ", " + order.getTotalPrice()
                + ", '" + order.getDate() + "', " + order.getTaxPercentage() + ", '" + order.getAddress().getGovernorate() + "', '" +
                order.getAddress().getDistrict() + "', '" + order.getAddress().getStreet() + "', " + order.getAddress().getBuildingNumber() +
                ", " + order.getAddress().getBuildingFloor() + ", " + order.getAddress().getBuildingFlat() + ", '" +
                order.getAddress().getLandmark() + "', '" + s + "', " + userId + ");" ;

        Statement statement = connection.createStatement();
        statement.executeUpdate(sql) ;
        customers.get(userId).addOrder(order) ;
    }
    private static void ShoppingItemsLoad(ShoppingCart cart) throws SQLException {
        // Execute a SELECT statement
        String sql = "SELECT * FROM ShoppingItem where ShoppingCartId = " + cart.getId() +";";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int ProductId = resultSet.getInt("ProductId");
            int quantity = resultSet.getInt("quantity");

            Product product = products.get(ProductId);
            cart.addProductToList(product, quantity);
        }
    }

    public void addUser(Customer customer) throws SQLException {
        String s ;
        if (customer.getInfo().getStatus() == CustomerStatus.Suspended) {
            s = "Suspended";
        }
        else {
            s = "unSuspended" ;
        }

        String sql = "INSERT INTO Users VALUES (" + customer.getInfo().getId() + ", '" + customer.getInfo().getName() +
                "', '" + customer.getInfo().getEmail() + "', '" + customer.getInfo().getPassword() + "', '" + s + "', '" +
                customer.getInfo().getAddress().getGovernorate() + "', '" + customer.getInfo().getAddress().getDistrict() +
                "', '" + customer.getInfo().getAddress().getStreet() + "', '" + customer.getInfo().getAddress().getBuildingNumber() +
                "', '" + customer.getInfo().getAddress().getBuildingFloor() + "', '" + customer.getInfo().getAddress().getBuildingFlat() +
                "', '" + customer.getInfo().getAddress().getLandmark() + "', " + customer.getCart().getId() + ")";

        Statement statement = connection.createStatement();

        // Execute the INSERT statement
        statement.executeUpdate(sql);

        // Add the new customer to the in-memory list
        customers.put(customer.getInfo().getId(), customer);
        addShoppingCart(customer.getCart());
    }

    private void addShoppingCart(ShoppingCart cart) throws SQLException {
        String sql = "INSERT INTO ShoppingCart (Id, TotalPrice, NumberOfItems) VALUES ( "+ cart.getId() +", " + 0 + ", " + 0 + ")" ;
        Statement statement = connection.createStatement();

        // Execute the INSERT statement
        statement.executeUpdate(sql);
    }

    public Customer getCustomer(int id) {
        return customers.getOrDefault(id, null);
    }

    public boolean exist(int id) {
        return customers.containsKey(id);
    }

    public boolean existShoppingCartId(int CartId) {
        for (Map.Entry<Integer, Customer> entry : customers.entrySet()) {
            if (entry.getValue().getCart().getId() == CartId) {
                return true;
            }
        }
        return false;
    }

    public String getPass(int id) {
        Customer customer = customers.get(id);
        return customer.getInfo().getPassword();
    }

    public List<Product> getProducts() {
        return new LinkedList<>(products.values());
    }

    public ShoppingCart getCart(int userId) {
        return customers.get(userId).getCart();
    }

    public void UpdateProducts(Product product, int quantity) throws SQLException {
        String sql ;
        Statement statement;
        int pQuantity = products.get(product.getInfo().getId()).getInfo().getQuantity();

        if (pQuantity == quantity) {
            sql = "UPDATE products SET quantity = 0, " +
                    "status = 'OutOfStock' WHERE id = "+ product.getInfo().getId() +";";
        }
        else {
            sql = "UPDATE products SET quantity = quantity - " + quantity + " WHERE id = "+ product.getInfo().getId() +";";
        }
        statement = connection.createStatement();

        // Execute the INSERT statement
        statement.executeUpdate(sql);
        Product p = products.get(product.getInfo().getId()) ;
        p.getInfo().setQuantity(p.getInfo().getQuantity() - quantity) ;
    }

    public void UpdateShoppingCart(ShoppingCart cart) throws SQLException {
        String sql = "select count(Id) from ShoppingCart WHERE id = " + cart.getId() + ";";
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(sql);
        int Number = resultSet.getInt(1);

        if(Number == 0){
            addShoppingCart(cart);
        }
        else{
            String sql2 = "UPDATE ShoppingCart SET NumberOfItems = " + cart.getNumberOfItems() + ", " +
                    "TotalPrice = " + cart.getTotalPrice() + " WHERE id = " + cart.getId() + ";";

            Statement statement2 = connection.createStatement();
            // Execute the INSERT statement
            statement2.executeUpdate(sql2);
        }
        customers.get(cart.getUserId()).setCart(cart);
    }

    public void UpdateShoppingItems(Product product, int quantity, ShoppingCart cart) throws SQLException {
        String sql = "SELECT COUNT(*) FROM ShoppingItem WHERE ProductId = " + product.getInfo().getId() + " and ShoppingCartId = "+ cart.getId() +";" ;
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(sql);
        int Number = resultSet.getInt(1);
        Statement statement2;
        String sql2 ;

        if (Number == 0) {
            sql2 = "INSERT INTO ShoppingItem values (" + product.getInfo().getId() + ", " + quantity + ", " + cart.getId() + ");";
            statement2 = connection.createStatement();
        }
        else {
            sql2 = "UPDATE ShoppingItem set quantity = quantity + " + quantity + " where ShoppingCartId = " + cart.getId() + " " +
                    "and ProductId = " + product.getInfo().getId() + ";";
            statement2 = connection.createStatement();
        }

        // Execute the INSERT statement
        statement2.executeUpdate(sql2);
    }

    public void resetCart(ShoppingCart cart) throws SQLException {
        String sql = "UPDATE ShoppingCart SET TotalPrice = 0 , numberOfItems = 0  where id = "+ cart.getId() +";";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql) ;

        removeItems(cart.getId());
        customers.get(cart.getUserId()).getCart().reset();
    }
    public boolean existOrderId(int randomId) {
        for (Customer customer : customers.values()) {
            if (customer.hasOrderId(randomId)) {
                return true;
            }
        }
        return false;
    }
    public void updateOrderStatus(int Id) throws SQLException {
        String sql = "UPDATE Orders set Status = 'CLOSED' WHERE Id = "+ Id +";";

        Statement statement = connection.createStatement();
        statement.executeUpdate(sql) ;
    }
    private void removeItems(int Id) throws SQLException {
        String sql = "Delete from ShoppingItem where ShoppingCartId = " + Id + ";";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql) ;
    }
    public Map<Integer, Order> getOrders(int userId) {
        return customers.get(userId).getOrders() ;
    }
}