package Orders;

/** This is an abstrat class of payment.
 */
public abstract class PaymentMethod {
    private final double amount;

    /** consturctor sets the amount to be paid
     * @param amount - is the amount to be paid
     */
    public PaymentMethod (double amount) {
        this.amount = amount;
    }

    /** get the amount to be paid
     * @return the mount that needs to be paid.
     */
    public double getAmount () {
        return amount;
    }

    /** A template method for making a payment
     * It first deducts the amount to be paid
     * And then displays a message if payment
     * was successful or not.
     * @return - true if payment was succssful and false
     * and false otherwise.
     */
    public boolean settlePayment (){
        boolean successfulPayment = deductAmount();
        displayMessage ();
        return successfulPayment;
    }

    /** Deduct the due amount from payment
     * @return true if amount was deducted
     */
    public abstract boolean deductAmount ();

    /** Display a message showing payment success
     * or failure
     */
    public abstract void displayMessage ();
}
