package ApiModule;

import DataStore.UserData;
import DataStore.DataStore;
import Configuration.Configuration;
import Configuration.SessionType;

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
        this.users = dataStore.loadUsers();
    }

    @Override
    public void newSession(int userIndex, String serviceType, int time){
        switch (this.config.getSessionType(serviceType)) {
            case VOICE:
                this.voiceSession(userIndex, time);
                break;
            case DATA:
                this.dataSession(userIndex, serviceType, time);
                break;
        }
    }

    private void voiceSession(int userIndex, int time) {
        UserData user = this.users.get(userIndex);
        int voiceMinutes = user.getVoiceMinutes();
        user.setVoiceMinutes(voiceMinutes+time);

        // store data?
    }   

    private void dataSession(int userIndex, String serviceId, int time) {
        UserData user = this.users.get(userIndex);
        String subscriptionType = user.getSubscriptionType();
        int dataVolume = this.config.getSubscriptionDataVolume(subscriptionType);
        
        double availableRate = this.getAvailableDataRate(user);
        double requiredRate = this.config.getRequiredDataRate(serviceId);
        double dataRate = Math.min(requiredRate, availableRate);
        double newData = 7.5 * dataRate * time + user.getDataUsed();
        
        if (newData >= dataVolume) {
        	throw new NotEnoughDataVolumeException();
        } else {
        	user.setDataUsed(newData);
        }
    }
    
    private double getAvailableDataRate(UserData user) {
        String terminalType = user.getTerminalType();
        String ran = this.config.getRan(terminalType);
        double subscriptionRate = this.config.getMaxDataRate(ran);
        double signalStrength = this.getSignalStrength();
        return subscriptionRate*signalStrength;
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
            default: // good
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
    	String subscriptionType = user.getSubscriptionType();
    	
    	// get invoice data
    	String name = user.getName(); // TODO: ask which name is displayed!
    	double dataUsed = user.getDataUsed();
    	int voiceMinutes = user.getVoiceMinutes();
    	int voiceCharge = this.getVoiceCharge(user);
        int basePrice = this.config.getBasePrice(subscriptionType);
        InvoiceInformation invoice = new InvoiceInformation(name, dataUsed, voiceMinutes, voiceCharge, basePrice);

        // reset user data
        user.setVoiceMinutes(0);
        user.setDataUsed(0);

        return invoice;
    }
    
    private int getVoiceCharge(UserData user) {
    	String subscriptionType = user.getSubscriptionType();
    	int freeVoiceMinutes = config.getFreeVoiceMinutes(subscriptionType);
    	int voiceMinutes = user.getVoiceMinutes() - freeVoiceMinutes;
    	
    	if (voiceMinutes<=0) {
    		return 0;
    	}
	
		int pricePerMinute = config.getPricePerMinute(subscriptionType);
		return pricePerMinute*voiceMinutes;	
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
    
    @Override
    public void saveData() {
    	this.dataStore.saveUsers(this.users);
    }
}

