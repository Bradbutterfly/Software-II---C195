package Model;
/**
 * Variables for customer
 **/
public class Customer {
    private final int Customer_ID;
    private final String Customer_Name;
    private final String Address;
    private final String Postal_Code;
    private final String Phone;
    private final int Division_ID;
    private final int Country_ID;
    /**
     * Constructors for customer
     * @param Customer_ID customer id
     * @param Customer_Name customer name
     * @param Address address
     * @param Postal_Code postal
     * @param Phone phone
     * @param Division_ID division
     * @param Country_ID country id
     */
    public Customer(int Customer_ID, String Customer_Name, String Address, String Postal_Code, String Phone, int Division_ID, int Country_ID) {
        this.Customer_ID = Customer_ID;
        this.Customer_Name = Customer_Name;
        this.Address = Address;
        this.Postal_Code = Postal_Code;
        this.Phone = Phone;
        this.Division_ID = Division_ID;
        this.Country_ID = Country_ID;
    }
    /**
     * Getters for customer
     * @return customer variables
     */
    public int getCustomer_ID() {
        return Customer_ID;
    }
    public String getCustomer_Name() {
        return Customer_Name;
    }
    public String getAddress() {
        return Address;
    }
    public String getPostal_Code() {
        return Postal_Code;
    }
    public String getPhone() {
        return Phone;
    }
    public int getDivision_ID() {
        return Division_ID;
    }
    public int getCountry_ID() {
        return Country_ID;
    }
    /**
     * Converts data into readable content for combo boxes
     */
    @Override
    public String toString() {
        return String.valueOf(Customer_ID);
    }
}