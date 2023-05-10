package Orders;

public abstract class PaymentMethod {
    private final double amount;

    public PaymentMethod (double amount) {
        this.amount = amount;
    }

    public double getAmount () {
        return amount;
    }

    public boolean settlePayment (){
        boolean successfulPayment = deductAmount();
        displayMessage ();
        return successfulPayment ;
    }

    public abstract boolean deductAmount ();

    public abstract void displayMessage ();
}
