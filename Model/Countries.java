package Model;
/**
 * Variables for countries
 */
public class Countries {
    private final Integer Country_ID;
    private final String Country;
    /**
     * Constructors for countries
     * @param country_ID country
     * @param country country
     */
    public Countries(Integer country_ID, String country) {
        Country_ID = country_ID;
        Country = country;
    }
    /**
     * Getters for countries
     * @return country variables
     */
    public Integer getCountry_ID() {
        return Country_ID;
    }
    public String getCountry() {
        return Country;
    }
    /**
     * Converts data into readable content for combo boxes
     * @return readable data
     */
    @Override
    public String toString() {
        return Country;
    }
}