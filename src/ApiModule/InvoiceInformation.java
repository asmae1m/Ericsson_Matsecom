package ApiModule;

public class InvoiceInformation {
    public String name;
    public double dataVolume;
    public int voiceMinutes;
    public int voiceCharge;
    public int basePrice;

    public InvoiceInformation(String name, double dataVolume, int voiceMinutes, int voiceCharge, int basePrice){
        this.name = name;
        this.dataVolume = dataVolume;
        this.voiceMinutes = voiceMinutes;
        this.voiceCharge = voiceCharge;
        this.basePrice = basePrice;
    }
}
