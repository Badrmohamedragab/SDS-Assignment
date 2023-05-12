package Orders;
import javax.swing.JOptionPane ;
import java.util.Date;
import java.util.Random;

public class EWallets extends PaymentMethod{
    private final String cardNumber;
    private final String cardType;
    private final Date expiryDate;
    private CardStatus cardStatus;
    private final int randomNumber;

    public EWallets (String cardNumber, String cardType, Date expiryDate, float amount) {
        super (amount);
        this.cardNumber = cardNumber;
        this.cardType   = cardType;
        this.expiryDate = expiryDate;
        Random rand = new Random();
        randomNumber = rand.nextInt(100);
    }
    public boolean deductAmount() {
        if (randomNumber <= 10) {
            cardStatus = CardStatus.INVALID;
        }
        else if (randomNumber <= 20) {
            cardStatus = CardStatus.INSUFFICIENT_FUND;
        }
        else if (randomNumber <= 30) {
            cardStatus = CardStatus.EXPIRED;
        }
        else {
            System.out.println ("Connecting with the bank .....");
            System.out.println (getAmount() + " where successfully deducted from your card.");
            cardStatus = CardStatus.VALID_SUFFICIENT;
        }

        return (cardStatus == CardStatus.VALID_SUFFICIENT);
    }
    public void displayMessage () {
        if (randomNumber <= 30)
            JOptionPane.showMessageDialog(null,
                    "I could not process your card payment - " + cardStatus,
                    "Unsuccessful Card Payment",
                    JOptionPane.ERROR_MESSAGE);
        else
            JOptionPane.showMessageDialog(null,
                    "You paid -> " + getAmount() + " by Credit Card",
                    "Successful Card Payment",
                    JOptionPane.INFORMATION_MESSAGE);
    }
    public void print(){
        System.out.println("<--------------- Your Card information --------------->");
        System.out.println(
                "CardNumber -> " + cardNumber +
                "\nCardType -> " + cardType +
                "\nCardStatus -> " + cardStatus +
                "\nExpiryDate -> " + expiryDate);
    }
}
