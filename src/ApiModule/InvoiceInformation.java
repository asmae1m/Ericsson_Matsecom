package ApiModule;

/**
 * Data-object containing all relevant information for displaying an invoice for one subscriber
 * 
 * @author ALARA
 * 
 * @since 1.0
 */
public class InvoiceInformation {
    public String forename;
    public String surname;
    public double dataVolume;
    public int voiceMinutes;
    public int voiceCharge;
    public int basePrice;

    /**
     * Main constructor
     * 
     * @param forename: First name of the subscriber
     * @param surname: Family name of the subscriber
     * @param dataVolume: Total amount of data used since last invoice in MB
     * @param voiceMinutes: Total amount of voice minutes since last invoice in minutes 
     * @param voiceCharge: Total charges based on voice minutes, taking free minutes into account in Euro-cent
     * @param basePrice: Base price of the users subscription in Euro-cent
     */
    public InvoiceInformation(String forename, String surname, double dataVolume, int voiceMinutes, int voiceCharge, int basePrice){
        this.forename = forename;
        this.surname = surname;
        this.dataVolume = dataVolume;
        this.voiceMinutes = voiceMinutes;
        this.voiceCharge = voiceCharge;
        this.basePrice = basePrice;
    }
}
