package ApiModule;

import DataStore.UserData;

public class InvoiceInformation {
    public String name;
    public double dataVolume;
    public int voiceMinutes;

    public int voiceCharge;
    public int basePrice;

    public InvoiceInformation(UserData userData, int voiceCharge, int basePrice){
        this.name = userData.getName(); //TODO: check what is to be displayed
        this.dataVolume = userData.getDataVolume();
        this.voiceMinutes = userData.getDataVolume();
        this.voiceCharge = voiceCharge;
        this.basePrice = basePrice;
    }
}
