package User;

import javax.mail.MessagingException;
import java.sql.SQLException;

public interface User {
    void Login(int userId, String Pass) ;
    void Register(CustomerInformation info) throws SQLException, MessagingException ;
}
