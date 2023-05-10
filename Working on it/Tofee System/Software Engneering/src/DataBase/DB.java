package DataBase;
import  Products.*;
import Registration.*;
import User.UserInformation;

import java.sql.*;

public class DB {

    public static void main(String[] args) {
        Type ByKilo = new ByKilo();
        Register register = new Register();
        UserInformation user = new UserInformation("name", "userName",1 ,"password", "email", "status");
        Login login = new Login();

        ProductInformation product = new ProductInformation("name", "description", 9.99, 10, "category", "brand", ByKilo, "status", 10, 1);
        //connect to database
        try {
            String currentDir = java.lang.System.getProperty("user.dir");
            String url = "jdbc:sqlite:" + currentDir + "\\src\\DataBase\\toffee.db";
            Connection conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");

//            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Products");
//            PreparedStatement stmt2 = conn.prepareStatement("insert into Products (price, discountAmount) values (?, ?)");
//            stmt2.setDouble(1, product.getPrice());
//            stmt2.setDouble(2, product.getDiscountAmount());
//            stmt2.executeUpdate();
            //add user to database
            PreparedStatement stmt = conn.prepareStatement("insert into User (id, name, userName, address, password, E_mail,status,phone) values (?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setInt(1, user.getId());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getUserName());
            stmt.setString(4, user.getAddress());
            stmt.setString(5, user.getPassword());
            stmt.setString(6, user.getEmail());
            stmt.setString(7, user.getStatus());
            stmt.setString(8, user.getPhoneNumber());
            stmt.executeUpdate();







        } catch (SQLException e) {
        System.out.println("Error: " + e.getMessage());
//            e.printStackTrace();
        }
    }


























    //            ResultSet rs = stmt.executeQuery();
//            while (rs.next()) {
//                System.out.println(rs.getString("Name"));
//                System.out.println(rs.getString("description"));
//            }





//
//
//
//    int rowsAffected = stmt.executeUpdate();
//
//      Product.setString(1, "Product Name");
//      stmt.setString(2, "Product Description");
//      stmt.setDouble(3, 9.99);
//      stmt.setInt(4, 10);
//
//
//
//
//    String sql = "UPDATE ShoppingCart SET NumberOfItems = ?, " +
//                 "TotalPrice = ? WHERE id = ?;";
//    PreparedStatement statement = Connection.prepareStatement(sql);
//    statement.executeUpdate();
//
//
//
//
//    String sql = "INSERT INTO ShoppingCart (Id) VALUES (?)";
//    PreparedStatement statement = connection.prepareStatement(sql);
//    statement.setInt(1,cart.getId());
//
//
//    try {
//        // Connect to the database
//        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "username", "password");
//
//        // Prepare a statement to insert a new product
//        PreparedStatement stmt = conn.prepareStatement("INSERT INTO products (name, description, price, quantity) VALUES (?, ?, ?, ?)");
//
//        // Set the parameter values for the new product
//        stmt.setString(1, "Product Name");
//        stmt.setString(2, "Product Description");
//        stmt.setDouble(3, 9.99);
//        stmt.setInt(4, 10);
//
//        // Execute the statement to insert the new product
//        int rowsAffected = stmt.executeUpdate();
//        if (rowsAffected == 1) {
//            System.out.println("Product added successfully");
//        } else {
//            System.out.println("Product not added");
//        }
//
//        // Close the statement and connection
//        stmt.close();
//        conn.close();
//    } catch (SQLException e) {
//        System.out.println("Error: " + e.getMessage());
//    }



}