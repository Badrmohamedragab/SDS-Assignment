package System ;
import Database.* ;
import Orders.Cash;
import Orders.PaymentMethod;
import Products.Catalog;
import User.* ;
import java.sql.SQLException ;
import java.util.Scanner;

public class System {
    public static void main(String[] args) throws SQLException {
        Database.loadDatabase() ;
        Catalog.LoadProducts() ;

        /*// For Load data Form database
        Database.loadDatabase() ;
        Catalog.LoadProducts() ;

        java.lang.System.out.println("<---------------------------------- Register ----------------------------------------->");
        {
            Address add = new Address("Aswan", "Cornish El-Nil", "El-Nil St.", 12, 3, 9, "Next to Old Cataract Hotel");
            UserInformation information = new UserInformation("Khaled", 1005, add, "7890", "khaled@example.com", UserStatus.unSuspended);

            User user = new User() ;
            user.Register(information) ;

            java.lang.System.out.println("-------------------------");
        }
        java.lang.System.out.println("<-------------------------------- Login ------------------------------------------->");

        //--------------------------------------------
        {
            User user = new User();

            user.Login(1005, "7890");
        }

        java.lang.System.out.println("<--------------------------------- Login and Logout ------------------------------------------>");

        //--------------------------------------------
        {
            User user = new User() ;

            user.Login(1005, "7890");
            user.Logout();
        }

        java.lang.System.out.println("<----------------------------------- View Catalog ---------------------------------------->");

        //--------------------------------------------
        {
            User user = new User() ;
            user.viewCatalog();
        }


        java.lang.System.out.println("<----------------------------- By Name ---------------------------------------------->");

        //--------------------------------------------
        {
            User user = new User() ;
            Search search = new SearchByName() ;

            String Name = "Chocolate Toffee" ;
            user.searchForItem(search, Name);
        }

        java.lang.System.out.println("<----------------------------- By Brand ---------------------------------------------->");

        //---------------------------------------
        {
            User user = new User() ;
            Search search = new SearchByBrand() ;

            String Brand = "Candyland" ;
            user.searchForItem(search, Brand);
        }

        java.lang.System.out.println("<----------------------------- By Category ---------------------------------------------->");

        //-------------------------------------------
        {
            User user = new User() ;
            Search search = new SearchByCategory() ;

            String Category = "Confectionery" ;
            user.searchForItem(search, Category);
        }
        java.lang.System.out.println("<----------------------------- Add To Cart ---------------------------------------------->");
*/
        //-------------------------------------------
        {
            /*User user = new User() ;
            user.Login(1005, "7890");
            String pName = "Milk Toffee", pCategory = "Confectionery", pBrand = "Candyland";
            int quantity = 11;

            user.AddToCart(pName, pCategory, pBrand, quantity) ;
            user.print();*/
        }

        /*User user = new User();
        user.Login(6005, "7890");

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

        user.print();*/

        Database.loadDatabase() ;
        Catalog.LoadProducts() ;
        User user = new User() ;

        /*java.lang.System.out.println("<---------------------------------- Register ----------------------------------------->");
        {
            Address add = new Address("Aswan", "Cornish El-Nil", "El-Nil St.", 12, 3, 9, "Next to Old Cataract Hotel");
            UserInformation information = new UserInformation("Khaled", 1005, add, "7890", "khaled@example.com", UserStatus.unSuspended);

            user.Register(information) ;
            user.Logout() ;

            java.lang.System.out.println("-------------------------");
        }
        java.lang.System.out.println("<-------------------------------- Login ------------------------------------------->");

        //--------------------------------------------
        {
            user.Login(1005, "7890");
        }

        {
            String pName = "Chocolate Toffee", pCategory = "Confectionery", pBrand = "Enstrom's";
            int quantity = 4;

            user.AddToCart(pName, pCategory, pBrand, quantity);

            String pName2 = "Almond Toffee", pCategory2 = "Confectionery", pBrand2 = "See's Candies";
            int quantity2 = 1;

            user.AddToCart(pName2, pCategory2, pBrand2, quantity2);

        }*/

        java.lang.System.out.println("<-------------------------------- CheckOut ------------------------------------------->");

        {

            user.Login(1005, "7890");

//            String pName = "English Toffee", pCategory = "Confectionery", pBrand = "Heath";
//            int quantity = 2;
//
//            user.AddToCart(pName, pCategory, pBrand, quantity);
//
//            String pName2 = "Almond Toffee", pCategory2 = "Confectionery", pBrand2 = "See's Candies";
//            int quantity2 = 1;
//
//            user.AddToCart(pName2, pCategory2, pBrand2, quantity2);
//            user.print();

            user.checkOut(10, "Giza - Fysal - El-Malkha st. - 10 - 5 - 19");

           /* user.printOrders() ;
            java.lang.System.out.print("Enter your order id -> ");
            Scanner scanner = new Scanner(java.lang.System.in);
            int Id = scanner.nextInt() ;

            java.lang.System.out.print("Enter The Amount which you pay -> ");
            double Amount = scanner.nextDouble() ;
            double Price = user.getOrderPrice(Id) ;

            PaymentMethod payment = new Cash(Price, Amount);
            user.payOrder(payment,  Id);*/
        }
    }
}

/*
    Write a test case for the System
 */