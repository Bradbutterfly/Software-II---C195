package Model;
/**
 * Variables for division
 */
public class Division {
    private int Division_ID;
    private String Division;
    private int Country_ID;
    /**
     * Constructors for division
     * @param Division_ID division id
     * @param Division division
     * @param Country_ID country id
     */
    public Division (int Division_ID, String Division, int Country_ID) {
        this.Division_ID = Division_ID;
        this.Division = Division;
        this.Country_ID = Country_ID;
    }
    /**
     * Setters and Getters for division
     */
        public int getDivision_ID() {
            return Division_ID;
        }
        public String getDivision() {
            return Division;
        }
        public int getCountry_ID() {
            return Country_ID;
        }

        public void setDivision_ID(int Division_ID) {
        this.Division_ID = Division_ID;
    }
        public void setDivision (String Division) {
        this.Division = Division;
    }
        public void setCountry_ID(int Country_ID) {
            this.Country_ID = Country_ID;
        }
    /**
     * Converts data into readable content for combo boxes
     */
        @Override
        public String toString(){
            return((Division));
        }
}
