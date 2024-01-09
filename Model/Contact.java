package Model;
/**
 * Variables for contact
 */
public class Contact {
    private int Contact_ID;
    private String Contact_Name;
    private String Email;
    /**
     * Constructors for contact
     * @param Contact_ID contact id
     * @param Contact_Name contact name
     * @param Email Email
     */
    public Contact(int Contact_ID, String Contact_Name, String Email) {
        this.Contact_ID = Contact_ID;
        this.Contact_Name = Contact_Name;
        this.Email = Email;
    }
    public Contact() {
    }
    /**
     * Setters and Getters for contact
     */
    public int getContact_ID() {
        return Contact_ID;
    }
    public String getContact_Name() {
        return Contact_Name;
    }
    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }
    public void setContact_ID(int Contact_ID) {
        this.Contact_ID = Contact_ID;
    }
    public void setContact_Name(String Contact_Name) {
        this.Contact_Name = Contact_Name;
    }
    /**
     * Converts data into readable content for combo boxes
     */
    @Override
    public String toString(){
        return Integer.toString(getContact_ID()) + " " + getContact_Name();
    }
}
