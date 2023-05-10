package Database;
import Orders.*;
import Products.*;
import ShoppingCart.*;
import User.* ;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class Database {
    static Map<Integer, User> users;
    static Map<Integer, Product> products;
    static Connection connection;

    public static void loadDatabase() throws SQLException {
        // Establish a connection to the database

        String currentDir = java.lang.System.getProperty("user.dir");
        String url = "jdbc:sqlite:" + currentDir + "/src/sqilite/Database.db";
        connection = DriverManager.getConnection(url);

        ProductsLoad();
        UsersLoad();
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
                unitType1 = new ByKilos();
            } else {
                unitType1 = new ByUnits();
            }

            ProductInformation info = new ProductInformation(id, name, description, brand, category, Price,
                    discountAmount, pStatus, quantity, unitType1);

            Product product = new Product();
            product.setInfo(info);

            products.put(id, product);
        }
    }

    private static void UsersLoad() throws SQLException {
        users = new HashMap<>();

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
            UserStatus status;

            if (Objects.equals(Status, "Suspended")) {
                status = UserStatus.Suspended;
            } else {
                status = UserStatus.unSuspended;
            }
            int ShoppingCartId = resultSet.getInt("ShoppingCartId");
            UserInformation info = new UserInformation(name, id, add, Password, email, status);

            ShoppingCart cart = new ShoppingCart(id, ShoppingCartId);
            ShoppingItemsLoad(cart);

            Map<Integer, Orders> orders = UserOrdersLoad(id) ;

            User user = new User();
            user.setInfo(info);
            user.setCart(cart);
            user.setOrders(orders) ;

            users.put(id, user);
        }
    }

    private static Map<Integer, Orders> UserOrdersLoad(int id) throws SQLException {
        Map<Integer, Orders> orders = new HashMap<>() ;

        String sql = "SELECT * FROM Orders where CustomerId = ? and Status = 'OPEN'";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int Id = resultSet.getInt("Id") ;
            double totalPrice = resultSet.getDouble("TotalPrice") ;
            Date date = resultSet.getDate("OrderDate") ;
            double taxPercentage = resultSet.getDouble("taxPercentage") ;

            String address = resultSet.getString("Address") ;
            String Status = resultSet.getString("Status") ;

            Orders order = new Orders(taxPercentage, address, Id) ;
            order.setTotalPrice(totalPrice);
            order.setDate(date) ;
            order.setStatus(Status) ;

            orders.put(Id, order) ;
        }
        return orders ;
    }

    public void addOrder(Orders order, int userId) throws SQLException {
        String sql = "INSERT INTO Orders (Id, TotalPrice, OrderDate, taxPercentage, Address, " +
                "Status, CustomerId) VALUES (?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, order.getId());
        statement.setDouble(2, order.getTotalPrice());
        statement.setDate(3, new java.sql.Date(order.getDate().getTime()));
        statement.setDouble(4, order.getTaxPercentage());
        statement.setString(5, order.getDeliveryAddress());

        if (order.getStatus() == OrderStatus.OPEN){
            statement.setString(6, "OPEN");
        }
        else{
            statement.setString(6, "CLOSED");
        }
        statement.setInt(7, userId);

        users.get(userId).addOrder(order) ;
        statement.executeUpdate() ;
    }
    private static void ShoppingItemsLoad(ShoppingCart cart) throws SQLException {
        // Execute a SELECT statement
        String sql = "SELECT * FROM ShoppingItem where ShoppingCartId = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, cart.getId());

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int ProductId = resultSet.getInt("ProductId");
            int quantity = resultSet.getInt("quantity");

            Product product = products.get(ProductId);
            cart.addProduct(product, quantity);
        }
    }

    public void addUser(User user) throws SQLException {
        if (users.containsKey(user.getInfo().getId())) {
            System.out.print("error in ");
            System.out.println("Database.addUser");
            return;
        }

        String sql = "INSERT INTO Users (ID, Name, Email, Password, addressDistrict, addressStreet, " +
                "addressGovernorate, addressBuildingNumber, addressBuildingFloor, addressBuildingFlat, " +
                "Landmark, Status, ShoppingCartId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql); // Insert, delete, Update

        // Set parameter values for the new user
        statement.setInt(1, user.getInfo().getId());
        statement.setString(2, user.getInfo().getName());
        statement.setString(3, user.getInfo().getEmail());
        statement.setString(4, user.getInfo().getPassword());
        statement.setString(5, user.getInfo().getAddress().getDistrict());
        statement.setString(6, user.getInfo().getAddress().getStreet());
        statement.setString(7, user.getInfo().getAddress().getGovernorate());
        statement.setInt(8, user.getInfo().getAddress().getBuildingNumber());
        statement.setInt(9, user.getInfo().getAddress().getBuildingFloor());
        statement.setInt(10, user.getInfo().getAddress().getBuildingFlat());
        statement.setString(11, user.getInfo().getAddress().getLandmark());

        if (user.getInfo().getStatus() == UserStatus.Suspended) {
            statement.setString(12, "Suspended");
        } else {
            statement.setString(12, "unSuspended");
        }
        statement.setInt(13, user.getCart().getId());

        // Execute the INSERT statement
        statement.executeUpdate();

        // Add the new user to the in-memory list
        users.put(user.getInfo().getId(), user);
        addShoppingCart(user.getCart());
    }

    private void addShoppingCart(ShoppingCart cart) throws SQLException {
        String sql = "INSERT INTO ShoppingCart (Id) VALUES (?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, cart.getId());

        // Execute the INSERT statement
        statement.executeUpdate();
    }

    public UserInformation getUserInformation(int id) {
        if (users.containsKey(id)) {
            return users.get(id).getInfo();
        } else {
            return null;
        }
    }

    public boolean exist(int id) {
        return users.containsKey(id);
    }

    public boolean existShoppingCartId(int CartId) {
        for (Map.Entry<Integer, User> entry : users.entrySet()) {
            if (entry.getValue().getCart().getId() == CartId) {
                return true;
            }
        }
        return false;
    }

    public String getPass(int id) {
        User user = users.get(id);
        return user.getInfo().getPassword();
    }

    public List<Product> getProducts() {
        return new LinkedList<>(products.values());
    }

    public ShoppingCart getCart(int userId) {
        return users.get(userId).getCart();
    }

    public void UpdateProducts(Product product, int quantity) throws SQLException {
        String sql ;
        PreparedStatement statement;
        int pQuantity = products.get(product.getInfo().getId()).getInfo().getQuantity();

        if (pQuantity == quantity) {
            sql = "UPDATE products SET quantity = 0, " +
                    "status = 'OutOfStock' WHERE id = ?;";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, product.getInfo().getId());
        }
        else {
            sql = "UPDATE products SET quantity = quantity - ? WHERE id = ?;";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, quantity);
            statement.setInt(2, product.getInfo().getId());
        }

        // Execute the INSERT statement
        statement.executeUpdate();
        Product p = products.get(product.getInfo().getId()) ;
        p.getInfo().setQuantity(p.getInfo().getQuantity() - quantity) ;
    }

    public void UpdateShoppingCart(ShoppingCart cart) throws SQLException {
        String sql = "select count(Id) from ShoppingCart where Id = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, cart.getId());

        ResultSet resultSet = statement.executeQuery();
        int Number = resultSet.getInt(1);

        if(Number == 0){
            addShoppingCart(cart);
        }
        else{
            String sql2 = "UPDATE ShoppingCart SET NumberOfItems = ?, " +
                    "TotalPrice = ? WHERE id = ?;";

            PreparedStatement statement2 = connection.prepareStatement(sql2);
            statement2.setInt(1, cart.getNumberOfItems());
            statement2.setDouble(2, cart.getTotalPrice());
            statement2.setInt(3, cart.getId());

            // Execute the INSERT statement
            statement2.executeUpdate();
        }

        users.get(cart.getUserId()).setCart(cart);
    }

    public void UpdateShoppingItems(Product product, int quantity, ShoppingCart cart) throws SQLException {
        String sql = "SELECT COUNT(*) FROM ShoppingItem WHERE ProductId = ? and ShoppingCartId = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, product.getInfo().getId());
        statement.setInt(2, cart.getId());

        ResultSet resultSet = statement.executeQuery();
        int Number = resultSet.getInt(1);
        PreparedStatement statement2;

        if (Number == 0) {
            String sql2 = "INSERT INTO ShoppingItem values (?, ?, ?);";

            statement2 = connection.prepareStatement(sql2);
            statement2.setInt(1, product.getInfo().getId());
            statement2.setDouble(2, quantity);
            statement2.setInt(3, cart.getId());
        }
        else {
            String sql2 = "UPDATE ShoppingItem set quantity = quantity + ? where ShoppingCartId = ? and ProductId = ?;";

            statement2 = connection.prepareStatement(sql2);
            statement2.setInt(1, quantity);
            statement2.setDouble(2, cart.getId());
            statement2.setInt(3, product.getInfo().getId());
        }

        // Execute the INSERT statement
        statement2.executeUpdate();
    }

    public boolean existOrderId(int randomId) {
        for (User user : users.values()) {
            if (user.hasOrderId(randomId)) {
                return true;
            }
        }
        return false;
    }

    public void removeCart(ShoppingCart cart) throws SQLException {
        String sql = "Delete from ShoppingCart where Id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, cart.getId());
        statement.executeUpdate() ;

        removeItems(cart.getId()) ;
        users.get(cart.getUserId()).getCart().reset();
    }

    public void updateOrderStatus(int Id) throws SQLException {
        String sql = "UPDATE Orders set Status = 'CLOSED' WHERE Id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, Id);

        statement.executeUpdate() ;
    }
    private void removeItems(int Id) throws SQLException {
        String sql = "Delete from ShoppingItem where ShoppingCartId = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, Id);

        statement.executeUpdate() ;
    }
    public Map<Integer, Orders> getOrders(int userId) {
        return users.get(userId).getOrders() ;
    }
}