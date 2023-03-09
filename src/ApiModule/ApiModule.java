package ApiModule;

import Configuration.Configuration;
import Configuration.ConfigurationImp;
import DataStore.DataStore;
import DataStore.UserData;
import DataStore.JsonDataStore;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Random;

public class ApiModule implements SessionManager {

    private Configuration config;
    private DataStore dataStore;
    private List<UserData> users;

    public ApiModule(Configuration config, DataStore dataStore){
        this.config = config;
        this.dataStore = dataStore;
        this.users = dataStore.loadUsers();
    }
    
    public ApiModule() {
    	this(new ConfigurationImp(), new JsonDataStore());
    }
    
    public ApiModule(Configuration config){
    	this(config, new JsonDataStore());
    }
    
    public ApiModule(DataStore dataStore){
    	this(new ConfigurationImp(), dataStore);
    }

    @Override
    public List<UserData>getUserList() {
        return this.users;
    }

    @Override
    public void addUser(UserData newUser) throws UserAlreadyExistsException {
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
    public void removeUser(int userIndex) throws UserIndexOutOfBoundsException {
    	if (!this.isValidUserIndex(userIndex)) {
    		throw new UserIndexOutOfBoundsException();
    	}

    	this.users.remove(userIndex);
    }
    
    @Override
    public void newSession(int userIndex, String serviceType, int time) throws UserIndexOutOfBoundsException, NotEnoughDataVolumeException {
    	
    	if (!this.isValidUserIndex(userIndex)) {
    		throw new UserIndexOutOfBoundsException();
    	}
    	
        switch (this.config.getSessionType(serviceType)) {
	        case DATA:
	            this.dataSession(userIndex, serviceType, time);
	            break;
            case VOICE:
            	UserData user = this.users.get(userIndex);
                int voiceMinutes = user.getVoiceMinutes();
                user.setVoiceMinutes(voiceMinutes+time);
                break;
        }
        //TODO: maybe return some infor for display?
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
            invoices.add(this.getInvoiceInfo(user));
            
            //reset user data
            user.setVoiceMinutes(0);
            user.setDataUsed(0);
        }
        return invoices;
    }

    private InvoiceInformation getInvoiceInfo(UserData user) {
    	String subscriptionType = user.getSubscriptionType();
    	
    	// get invoice data
    	String forename = user.getForename();
    	String surename = user.getSurname();
    	double dataUsed = user.getDataUsed();
    	int voiceMinutes = user.getVoiceMinutes();
    	int voiceCharge = this.getVoiceCharge(user);
        int basePrice = this.config.getBasePrice(subscriptionType);
        InvoiceInformation invoice = new InvoiceInformation(forename, surename, dataUsed, voiceMinutes, voiceCharge, basePrice);

        return invoice;
    }
    
    private int getVoiceCharge(UserData user) {
    	String subscriptionType = user.getSubscriptionType();
    	int freeVoiceMinutes = config.getSubscriptionFreeMinutes(subscriptionType);
    	int voiceMinutes = user.getVoiceMinutes() - freeVoiceMinutes;
    	
    	if (voiceMinutes<=0) {
    		return 0;
    	}
	
		int pricePerMinute = config.getPricePerMinute(subscriptionType);
		return pricePerMinute*voiceMinutes;	
    }

    @Override
    public void saveData() {
    	this.dataStore.saveUsers(this.users);
    }
}

