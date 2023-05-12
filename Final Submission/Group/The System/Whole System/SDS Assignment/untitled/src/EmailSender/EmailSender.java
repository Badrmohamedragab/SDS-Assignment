package EmailSender;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailSender {
    Session session ;
    Message message ;
    public void sendEmail(String To, String OTP) throws MessagingException {
        EmailSender mail = new EmailSender() ;
        mail.setServerProperties() ;
        mail.send(To, OTP) ;
    }
    private void send(String To, String OTP) throws MessagingException {
        String emailSubject = "Confirm your Email" ;
        String emailBody = "You must to confirm your email by this OTP " + OTP ;

        message = new MimeMessage(session);
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(To));
        // input the email which send mail from

        message.setFrom(new InternetAddress("mohamedamir5050@gmail.com"));
        message.setSubject(emailSubject);
        message.setText(emailBody);
        Transport.send(message);
    }
    private void setServerProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.host", "smtp.gmail.com");

        // input the email which send mail from
        // and it's App Password
        String username = "mohamedamir5050@gmail.com";
        String password = "rczcvhqlmrlsrgxd";

        session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }
}