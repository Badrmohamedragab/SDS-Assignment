import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import Registration.*;
import Products.*;

public class Main {

    //forget password and send OTP to email

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the Toffee Online Shopping System");
        System.out.println("Please select an option:");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Forget Password");
        System.out.println("4. Exit");
        int option = input.nextInt();
        switch (option) {
            case 1 -> {
                Login login = new Login();
                login.login();
            }
            case 2 -> {
                Register register = new Register();
//                register.register();
            }
            case 3 -> {
//                ForgetPassword forgetPassword = new ForgetPassword();
//                forgetPassword.forgetPassword();
                break;
            }
            case 4 -> System.exit(0);
            default -> System.out.println("Invalid option");
        }
    }
};



