package ApiModule;

import DataStore.UserData;

public class InvoiceInformation {
    public String name;
    public double dataVolume;
    public int voiceMinutes;

    public int voiceCharge;
    public int basePrice;

    public InvoiceInformation(UserData userData, int basePrice){
        this.name = userData.getName(); //TODO: check what is to be displayed
        this.dataVolume = userData.getRemainingData();
        this.voiceMinutes = userData.getVoiceMinutes();
        this.voiceCharge = userData.getVoiceCharge();
        this.basePrice = basePrice;
    }
}
