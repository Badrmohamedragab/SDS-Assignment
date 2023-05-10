package User;

public class Address {
    String governorate ;
    String district ;
    String street ;
    int buildingNumber ;
    int buildingFloor ;
    int buildingFlat ;
    String Landmark ;

    public Address(String governorate, String district, String street, int buildingNumber, int buildingFloor, int buildingFlat, String landmark) {
        this.governorate = governorate;
        this.district = district;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.buildingFloor = buildingFloor;
        this.buildingFlat = buildingFlat;
        Landmark = landmark;
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

    public void print() {
        System.out.println("<--------------Address-------------->");
        System.out.println("governorate -> " + governorate + "\ndistrict -> " + district + "\nstreet -> " + street +
                "\nbuildingNumber -> " + buildingNumber + "\nbuildingFloor -> " + buildingFloor + "\nbuildingFlat -> " +
                buildingFlat + "\nLandmark -> " + Landmark);
    }
}
