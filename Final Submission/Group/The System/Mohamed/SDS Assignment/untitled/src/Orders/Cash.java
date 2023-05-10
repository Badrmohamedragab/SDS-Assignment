package Orders;

public class Cash extends PaymentMethod{
    private final double cash;
    private double change ;

    public Cash (double amount, double cash) {
        super (amount);
        this.cash = cash;
    }
    public boolean deductAmount() {
        change = cash - getAmount();
        return (change >= 0);
    }
    public void displayMessage () {
        if (change >=0){
            System.out.println("-------------------Successful Cash Payment-------------------");
            System.out.println("You paid -> " + cash + " and change -> " + change);
        }
        else {
            System.out.println("-------------------Unsuccessful Cash Payment-------------------");
            System.out.println("You paid -> " + cash + ", but required amount is -> " + getAmount());
        }
    }
}
