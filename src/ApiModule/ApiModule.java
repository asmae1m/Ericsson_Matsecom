package ApiModule;

import DataStore.JSONDataStore;
import DataStore.UserData;
import Configuration.Configuration;
import Configuration.ServiceInformation;

import java.lang.Math.min;
import java.lang.RuntimeException;
import java.util.Random;

class ApiModule implements SessionManager {

    private Configuration config;
    private DataStore dataStore;
    private List<UserData> users;

    public ApiModule(Configuration config, DataStore dataStore){
        this.config = config;
        this.dataStore = dataStore;
        this.users = dataStore.loadUserData();
    }

    public void newSession(int userIndex, String serviceId, int time){
        // TODO: check input!!!
        
        switch (this.config.getSessionType(service)) {
            case config.SessionType.VOICE_SESSION:
                this.voiceSession(userIndex, time);
                break;
            case config.SessionType.DATA_SESSION:
                this.dataSession(userIndex, serviceId, time);
                break;
        }
    }

    private void voiceSession(int userIndex, int time) {
        UserData user = this.users.get(userIndex);

        int freeMinutes = user.getFreeMinutes();
        int charge = 0;
        if (time>freeMinutes){
            int chargedMinutes = time-freeMinutes;
            String subscriptionType = user.getSubscriptionType();
            charge = chargedMinutes * this.config.getPricePerMinute(subscriptionType);
            freeMinutes = 0;
        } else { //enough free minutes
            freeMinutes -= time;
        }
        user.setFreeMinutes(freeMinutes);
        user.setVoiceCharge(user.getVoiceCharge() + charge);

        // store data?
    }   

    private void dataSession(String userIndex, String serviceId, int time) {
        UserData user = this.users.get(userIndex);
        
        String subscriptionType = user.getSubscriptionType();
        double subscriptionRate = this.config.getSubscriptionDataRate(subscriptionType);
        double signalStrength = this.getSignalStrength();
        double availableRate = subscriptionRate*signalStrength;

        double requiredRate = this.config.getRequiredDataRate(serviceId);

        double dataRate = min(requiredRate, availableRate);

        double dataCost = dataRate * time;
        double dataVolume = user.getDataVolume();

        if (dataCost < dataVolume) {
            user.setRemainingData(dataVolume-dataCost);
        } else {
            throw new NotEnoughDataVolumeException();
        }
    }

    private double getSignalStrength(){
        int random = new Random().nextInt(3);
        switch(random){
            case 0: // N/A
                return 0.0;
            case 1: // bad
                return 0.1;
            case 2: // middle
                return 0.25;
            case 3: // good
                return 0.5;
        }
    }

    public List<InvoiceInformation> invoice() {
        List<InvoiceInformation> invoices = new List<InvoiceInformation>();

        for (UserData user : this.users) {
            invoices.add(this.invoiceUser(user));
        }
        return invoices;
    }

    private InvoiceInformation invoiceUser(UserData user) {

        // get invoice data
        String subscriptionType = user.getSubscriptionType();
        int basePrice = this.config.getBasePrice(user.getSubscriptionType());
        InvoiceInformation invoice = new InvoiceInformation(user, basePrice);

        // reset user data
        int freeMinutes = this.config.getSubscriptionFreeMinutes(subscriptionType);
        int dataVolume = this.config.getSubscriptionDataVolume(subscriptionType);
        user.setFreeMinutes(freeMinutes);
        user.setRemainingData(dataVolume);
        user.setVoiceCharge(0);

        return invoice;
    }

    public List<UserData>getUserList() {
        return this.users;
    }

    public void addUser(UserData newUser){
        //check if user is duplicate
        String imsi = newUser.getImsi();
        for (UserData user :  this.users){
            if (imsi.equals(user.getImsi())){
                throw new UserAlreadyExistsException("A User with that IMSI already exists!");
            }
        }

        this.users.add(user);
    }
}

class NotEnoughDataVolumeException extends RuntimeException {
    super("Not enough Data Volume!");
}

class UserAlreadyExistsException extends RuntimeException {
    super("Not enough Data Volume!");
}


