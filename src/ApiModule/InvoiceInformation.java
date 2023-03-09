package ApiModule;

public class InvoiceInformation {
    public String forename;
    public String surname;
    public double dataVolume;
    public int voiceMinutes;
    public int voiceCharge;
    public int basePrice;

    public InvoiceInformation(String forename, String surname, double dataVolume, int voiceMinutes, int voiceCharge, int basePrice){
        this.forename = forename;
        this.surname = surname;
        this.dataVolume = dataVolume;
        this.voiceMinutes = voiceMinutes;
        this.voiceCharge = voiceCharge;
        this.basePrice = basePrice;
    }
}
