package Orders;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import Database.Control;
import ShoppingCart.* ;
import User.Address;

public class Order {
    double totalPrice ;
    Date date ;
    List<ShoppingItem> items;
    Address deliveryAddress;
    private OrderStatus status ;
    private double taxPercentage ;
    int Id ;

    public Order(double taxPercentage, Address deliveryAddress) {
        this.taxPercentage = taxPercentage;
        this.deliveryAddress = deliveryAddress;

        Control control = new Control() ;
        this.Id = control.generateOrderId();
        status = OrderStatus.OPEN ;
        date = new Date() ;
        items = new LinkedList<>() ;
    }
    public double getTotalPrice () {
        return totalPrice;
    }
    public OrderStatus getStatus () {
        return status;
    }
    public void setId(int id) {
        Id = id;
    }
    public int getNumberOfItems(){
        return items.size() ;
    }
    public void setTotalPrice(double price) {
        this.totalPrice = price ;
    }

    public Address getAddress() {
        return deliveryAddress;
    }
    public double getTaxPercentage() {
        return (taxPercentage / 100);
    }
    public boolean payOrder (PaymentMethod payment) {
        if (status == OrderStatus.CLOSED) {
            System.out.println("-------------------Order Payment-------------------");
            System.out.println("Order is already paid");
            return false ;
        }
        else {
            boolean paidOrder = payment.settlePayment();

            if (paidOrder) {
                System.out.println("-------------------Order Payment-------------------");
                System.out.println("Order is paid successfully, and has closed");

                status = OrderStatus.CLOSED;
                return true ;
            }
        }

        return false ;
    }
    public void printOrder () {
        System.out.println("-----------------------------------");
        System.out.println("Order Id -> " + Id);

        for (ShoppingItem i: items) {
            i.print();
            System.out.println("-----------------------");
        }
        System.out.println("Taxes -> $" + taxPercentage);
        System.out.println("Total Price After Adding Taxes -> $" + totalPrice);
    }
    public void setOrder(List<ShoppingItem> items, double totalPrice) {
        this.items.addAll(items);

        taxPercentage = totalPrice * (taxPercentage/100);
        this.totalPrice = totalPrice + taxPercentage;
    }
    public void setDate(Date date) {
        this.date = date ;
    }
    public void setStatus(String status) {
        if (Objects.equals(status, "CLOSED")) {
            this.status = OrderStatus.CLOSED;
        }
        else{
            this.status = OrderStatus.OPEN;
        }
    }
    public int getId() {
        return Id ;
    }

    public String getDate() {
        return date.toString() ;
    }
}
