package ApiModule;

import DataStore.UserData;
import DataStore.DataStore;
import Configuration.Configuration;

import java.lang.Math;
import java.lang.RuntimeException;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class ApiModule implements SessionManager {

    private Configuration config;
    private DataStore dataStore;
    private List<UserData> users;

    public ApiModule(Configuration config, DataStore dataStore){
        this.config = config;
        this.dataStore = dataStore;
        this.users = dataStore.loadUserData();
    }

    @Override
    public void newSession(int userIndex, String serviceType, int time){
        switch (this.config.getSessionType(serviceType)) {
            case "voice":
                this.voiceSession(userIndex, time);
                break;
            case "data":
                this.dataSession(userIndex, serviceType, time);
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

        double dataRate = Math.min(requiredRate, availableRate);

        double dataVolume = dataRate * time;
        double remainingData = user.getRemainingData();

        if (dataVolume >= remainingData) {
        	throw new NotEnoughDataVolumeException();
        } else {
        	user.setRemainingData(remainingData-dataVolume);
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

    @Override
    public List<InvoiceInformation> invoice() {
        List<InvoiceInformation> invoices = new ArrayList<>();

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

    @Override
    public List<UserData>getUserList() {
        return this.users;
    }

    @Override
    public void addUser(UserData newUser){
        //check if user is duplicate
        String imsi = newUser.getImsi();
        for (UserData user :  this.users){
            if (imsi.equals(user.getImsi())){
                throw new UserAlreadyExistsException();
            }
        }

        this.users.add(newUser);
    }
    
    @Override
    public void removeUser(int userIndex){
        if (userIndex >= this.users.size()) {
            throw new UserIndexOutOfBoundsException();
        }
        this.users.remove(userIndex);
    }
}

