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
    public int basePrice;
    public int voiceMinutes;
    public int voiceCharge;
    public double dataVolume;
    public int dataVolumeUpgrades;
    public int dataCharge;
    public int totalCharge;
    

    /**
     * Main constructor
     * 
     * @param forename: First name of the subscriber
     * @param surname: Family name of the subscriber
     * @param basePrice: Base price of the users subscription in Euro-cent
     * @param voiceMinutes: Total amount of voice minutes since last invoice in minutes 
     * @param voiceCharge: Charge from voice calls since the last invoice, taking free minutes into account in Euro-cent
     * @param dataVolume: Total amount of data used since last invoice in MB
     * @param dataVolumeUpgrades: Total number of data volume upgrades the user has purchased since the last invoice
     * @param dataCharge: Charge from upgrading data volume since the last invoice in Euro-cent
     */
    public InvoiceInformation(String forename, String surname, int basePrice, int voiceMinutes, int voiceCharge, double dataVolume, int dataVolumeUpgrades, int dataCharge, int totalCharge){
        this.forename = forename;
        this.surname = surname;
        this.basePrice = basePrice;
        this.voiceMinutes = voiceMinutes;
        this.voiceCharge = voiceCharge;
        this.dataVolume = dataVolume;
        this.dataVolumeUpgrades = dataVolumeUpgrades;
        this.dataCharge = dataCharge;
        this.totalCharge = totalCharge;
    }
}
