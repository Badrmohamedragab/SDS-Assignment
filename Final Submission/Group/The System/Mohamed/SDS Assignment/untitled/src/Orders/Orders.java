package Orders;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import ShoppingCart.* ;

public class Orders {
    double totalPrice ;
    Date date ;
    List<ShoppingItem> items;
    String deliveryAddress;
    private OrderStatus status ;
    private final double taxPercentage ;
    int Id ;

    public Orders(double taxPercentage, String deliveryAddress, int ID) {
        this.taxPercentage = taxPercentage;
        this.deliveryAddress = deliveryAddress;
        this.Id = ID;
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

    public int getNumberOfItems(){
        return items.size() ;
    }
    public void setTotalPrice(double price) {
        this.totalPrice = price ;
    }

    public String getDeliveryAddress() {
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
        System.out.println("Order Id -> " + Id);

        for (ShoppingItem i: items) {
            i.print();
            System.out.println("-----------------------");
        }

        System.out.println("Total Price -> " + totalPrice);
        System.out.println("Status -> " + status);
    }

    public void setOrder(List<ShoppingItem> items, double totalPrice) {
        this.items.addAll(items);

        double tax = totalPrice * (taxPercentage/100);
        this.totalPrice = totalPrice + tax;
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

    public Date getDate() {
        return date ;
    }
}
