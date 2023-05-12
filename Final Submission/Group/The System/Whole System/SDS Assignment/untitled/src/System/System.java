package System ;
import Database.* ;
import Orders.Cash;
import Orders.PaymentMethod;
import Products.Catalog;
import User.* ;
import javax.mail.MessagingException;
import java.sql.SQLException ;
import java.text.ParseException;
import java.util.Scanner;

public class System {
    public static void main(String[] args) throws SQLException, MessagingException, ParseException {
        Database.loadDatabase() ;
        Catalog.LoadProducts() ;
        Customer user = new Customer();

        java.lang.System.out.println("<---------------------------------- Register ----------------------------------------->");
        {
            Address add = new Address("Aswan", "Cornish El-Nil", "El-Nil St.", 12,
                    3, 9, "Next to Old Cataract Hotel");

            CustomerInformation information = new CustomerInformation("Khaled", 1009, add, "7890",
                    "mohamedamir500800@gmail.com", CustomerStatus.unSuspended);

            user.Register(information) ;
            user.Logout() ;
        }
        java.lang.System.out.println("<-------------------------------- Login ------------------------------------------->");

        //--------------------------------------------
        {
            user.Login(1007, "7890");
            user.Logout() ;
        }

        java.lang.System.out.println("<----------------------------------- View Catalog ---------------------------------------->");

        //--------------------------------------------
        {
            user.Login(1006, "7890");
            user.viewCatalog();
        }

        java.lang.System.out.println("<----------------------------- Add To Cart ---------------------------------------------->");

        //-------------------------------------------
        {
            String pName = "Milk Toffee", pCategory = "Confectionery", pBrand = "Candyland";
            int quantity = 11;

            user.AddToCart(pName, pCategory, pBrand, quantity) ;
            user.print();
        }

        {
            String pName = "Milk Toffee", pCategory = "Confectionery", pBrand = "Candyland";
            int quantity = 4;

            user.AddToCart(pName, pCategory, pBrand, quantity);
        }
        {
            String pName = "Almond Toffee", pCategory = "Confectionery", pBrand = "See's Candies";
            int quantity = 1;

            user.AddToCart(pName, pCategory, pBrand, quantity);
        }

        user.printCart();

        java.lang.System.out.println("<-------------------------------- CheckOut ------------------------------------------->");
        {
            Address add = new Address("Aswan", "Cornish El-Nil", "El-Nil St.", 12,
                    3, 9, "Next to Old Cataract Hotel");
            user.checkOut(10, add);

            user.printOrders() ;
            java.lang.System.out.print("Enter your order id -> ");
            Scanner scanner = new Scanner(java.lang.System.in);
            int Id = scanner.nextInt() ;

            java.lang.System.out.print("Enter The Amount which you pay -> ");
            double Amount = scanner.nextDouble() ;
            double Price = user.getOrderPrice(Id) ;

            PaymentMethod payment = new Cash(Price, Amount);
            user.payOrder(payment,  Id);
        }
    }
}
