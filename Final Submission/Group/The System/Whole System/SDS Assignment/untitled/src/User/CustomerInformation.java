package User;

public class CustomerInformation {
    private String Name ;
    private int id ;
    private Address address ;
    private String Password;
    private String Email;
    private CustomerStatus status ;
    public CustomerInformation(String name, int id, Address address, String password, String email, CustomerStatus status) {
        Name = name;
        this.id = id;
        this.address = address;
        Password = password;
        Email = email;
        this.status = status;
    }
    public CustomerInformation() {}

    public String getName() {
        return Name;
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
    public CustomerStatus getStatus() {
        return status;
    }

    public void print() {
        System.out.print("<--------------User Information-------------->\n");
        System.out.println("Name -> " + Name + "\nId -> " + id +
                "\nEmail -> " + Email + "\nStatus -> " + status);

        address.print() ;
    }

}
