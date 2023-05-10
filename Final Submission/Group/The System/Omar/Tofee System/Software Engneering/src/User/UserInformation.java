package User;

public class UserInformation {




    String Name;
    String UserName;
    int Id;
    String address;
    String Password;
    String Email;
    String Status;
    String PhoneNumber;



    public UserInformation(String name, String userName, int id, String password, String email, String status) {
        Name = name;
        UserName = userName;
        Id = id;
        Password = password;
        Email = email;
        Status = status;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getName() {
        return Name;
    }

    public String getUserName() {
        return UserName;
    }

    public int getId() {
        return Id;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return Password;
    }

    public String getEmail() {
        return Email;
    }

    public String getStatus() {
        return Status;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setStatus(String status) {
        Status = status;
    }







}
