package User;

public class UserInformation {
    private String Name ;
    private int id ;
    private int loyaltyPoints ;
    private Address address ;
    private String Password;
    private String Email;
    private UserStatus status ;
    public UserInformation(String name, int id, Address address, String password, String email, UserStatus status) {
        Name = name;
        this.id = id;
        this.address = address;
        Password = password;
        Email = email;
        this.status = status;
        loyaltyPoints = 0;
    }

    public UserInformation() {}

    public String getName() {
        return Name;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public int getId() {
        return id;
    }

    public Address getAddress() {
        return address;
    }

    public String getPassword() {
        return Password;
    }

    public String getEmail() {
        return Email;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void print() {
        System.out.print("<--------------User Information-------------->\n");
        System.out.println("Name -> " + Name + "\nId -> " + id +
                "\nEmail -> " + Email + "\nStatus -> " + status);

        address.print() ;
    }


}
