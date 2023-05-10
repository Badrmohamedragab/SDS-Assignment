package User;

public class Address {
    String governorate;
    String district;
    String street;
    int buildingNumber;
    int buildingFloor;
    int buildingFlat;
    String Landmark;

    public Address(String governorate, String district, String street, int buildingNumber, int buildingFloor, int buildingFlat, String Landmark) {
        this.governorate = governorate;
        this.district = district;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.buildingFloor = buildingFloor;
        this.buildingFlat = buildingFlat;
        this.Landmark = Landmark;
    }

    public String getGovernorate() {
        return governorate;
    }

    public String getDistrict() {
        return district;
    }

    public String getStreet() {
        return street;
    }

    public int getBuildingNumber() {
        return buildingNumber;
    }

    public int getBuildingFloor() {
        return buildingFloor;
    }

    public int getBuildingFlat() {
        return buildingFlat;
    }

    public String getLandmark() {
        return Landmark;
    }

    String getAddress() {
        return governorate + " " + district + " " + street + " " + buildingNumber + " " + buildingFloor + " " + buildingFlat + " " + Landmark;
    }


    public String getAddress_() {
        return "Address{" + "governorate=" + governorate + ", district=" + district + ", street=" + street + ", buildingNumber=" + buildingNumber + ", buildingFloor=" + buildingFloor + ", buildingFlat=" + buildingFlat + ", Landmark=" + Landmark + '}';
    }

    void printAddress() {
        System.out.println("Governorate: " + governorate);
        System.out.println("District: " + district);
        System.out.println("Street: " + street);
        System.out.println("Building Number: " + buildingNumber);
        System.out.println("Building Floor: " + buildingFloor);
        System.out.println("Building Flat: " + buildingFlat);
        System.out.println("Landmark: " + Landmark);
    }


}
