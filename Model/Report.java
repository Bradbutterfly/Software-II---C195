package Model;
/**
 * Variables for report
 */
public class Report {
    private String Report_Month;
    private String Report_Type;
    private String Report_total;
    private int Report_ContactID;
    private int Report_CustomerID;
    /**
     * Constructors for report
     * @param Report_Month month
     * @param Report_Type type
     * @param Report_Total total
     * @param Report_ContactID contact id
     * @param Report_CustomerID customer id
     */
    public Report(String Report_Month, String Report_Type, String Report_Total, int Report_ContactID, int Report_CustomerID){
        this.Report_Month = Report_Month;
        this.Report_Type = Report_Type;
        this.Report_total = Report_Total;
        this.Report_ContactID = Report_ContactID;
        this.Report_CustomerID = Report_CustomerID;
    }
    /**
     * Setters and Getters for report
     */
    public String getReport_Type() {
        return Report_Type;
    }
    public String getReport_total() {
        return Report_total;
    }
    public String getReport_Month() {
        return Report_Month;
    }
    public int getReport_ContactID() {
        return Report_ContactID;
    }
    public int getReport_CustomerID() {
        return Report_CustomerID;
    }

    public void setReport_total(String Report_total) {
        this.Report_total = Report_total;
    }
    public void setReport_CustomerID(int Report_CustomerID) {
        this.Report_CustomerID = Report_CustomerID;
    }
    public void setReport_ContactID(int Report_ContactID) {
        this.Report_ContactID = Report_ContactID;
    }
    public void setReport_Month(String Report_Month) {
        this.Report_Month = Report_Month;
    }
    public void setReport_Type(String Report_Type) {
        this.Report_Type = Report_Type;
    }
}



