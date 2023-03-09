/**
 * Provides an Implementation of the SessionManager Interface.
 * 
 * Supports custom Configuration and/or DataStore classes.
 * 
 * @author ALARA
 * 
 * @since 1.0
 */

package ApiModule;

import Configuration.Configuration;
import Configuration.ConfigurationImp;
import DataStore.DataStore;
import DataStore.UserData;
import DataStore.JsonDataStore;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.lang.Math;
import java.util.Random;


/**
 * the ApiModule class
 * This is the default implementation of the SessionManager Interface.
 * After loading, all user data lives here.
 * 
 * @author ALARA
 * 
 * @since 1.0
 *
 */
public class ApiModule implements SessionManager {
	
	/*
	 * Attributes
	 * 
	 */
	
    private Configuration config;
    private DataStore dataStore;
    private List<UserData> users;
    
    
    /*
     * Constructors
     * 
     * */

    /**
     * Main Constructor
     * 
     * used by other constructors
     * 
     * @param config: Configuration to be used
     * @param dataStore: DataStore-Object to be used
     * 
     * @since 1.0
     */
    public ApiModule(Configuration config, DataStore dataStore) throws IOException {
        this.config = config;
        this.dataStore = dataStore;
        this.users = dataStore.loadUsers();
    }
    
    /**
     * Default Constructor
     * 
     * uses ConfigurationImp as default Configuration
     * uses JsonDataStore as default DataStore
     * 
     * @since 1.0
     */
    public ApiModule() throws IOException {
    	this(new ConfigurationImp(), new JsonDataStore());
    }
    
    
    /**
     * 
     * Optional Constructor
     * 
     * uses JsonDataStore as default DataStore
     * 
     * @param config: Configuration to be used 
     * 
     * @since 1.0
     */
    public ApiModule(Configuration config) throws IOException {
    	this(config, new JsonDataStore());
    }
    
    /**
     * 
     * Optional Constructor
     * 
     * uses ConfigurationImp as default Configuration
     * 
     * @param dataStore: DataStore to be used 
     * 
     * @since 1.0
     */
    public ApiModule(DataStore dataStore) throws IOException {
    	this(new ConfigurationImp(), dataStore);
    }
    
    
    /*
     * Interface methods
     * 
     * */
    
    /**
	 * Get list of all users.
	 * 
	 * @return List of UserData of all subscribers.
	 * 
	 * @since 1.0
	 */
    @Override
    public List<UserData>getUserList() {
        return this.users;
    }

    /**
	 * add a new user
	 * 
	 * @param data of user to be added.
	 * 
	 * @throws UserAlreadyExistsException if a User with the same IMSI already exists.
	 * 
	 * @since 1.0
	 */
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
    
    /**
	 * remove a user
	 * 
	 * @param userIndex: Index of subscriber to be removed.
	 * 
	 * @throws UserIndexOutOfBoundsException if an invalid userIndex is entered.
	 * 
	 * @since 1.0
	 */
    @Override
    public void removeUser(int userIndex) throws UserIndexOutOfBoundsException {
    	if (!this.isValidUserIndex(userIndex)) {
    		throw new UserIndexOutOfBoundsException();
    	}

    	this.users.remove(userIndex);
    }
    
    /**
	 * updates subscriber data based on session data:
	 * - voice minutes used, taking free minutes into account
	 * - data used
	 * 
	 * @param userIndex: index of subscriber who has the session
	 * @param serviceType: name of the service to be used
	 * @param time: time spent using the service in minutes
	 * 
	 * @throws UserIndexOutOfBoundsException if an invalid userIndex is entered.
	 * @throws NotEnoughDataVolumeException if there is not enough data volume left for the subscriber
	 * IMPORTANT: This depends on latency and therefore may happen inconsistently!
	 * 
	 * @since 1.0
	 */
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
        //TODO: maybe return some info for display?
    }

    /**
     * Send invoices to all subscribers:
     * - collect invoice information for all subscribers
     * - reset free minutes for all subscribers
     * - reset data used for all subscribes
     * - return invoice information
     * 
     * @return list of invoice information for all subscribers
     * Each entry contains all relevant invoice information for one subscriber
     * 
     * @since 1.0
     */
    @Override
    public List<InvoiceInformation> invoice() {
        List<InvoiceInformation> invoices = new ArrayList<>();
        for (UserData user : this.users) {
            invoices.add(this.getInvoiceInfo(user));
            
            //reset user data
            user.setVoiceMinutes(0);
            user.setDataUsed(0.0);
            user.setDataVolumeUpgrades(0);
        }
        return invoices;
    }

    /**
     * save the current data
     * - First creates a backup of the existing data.
     * - Then updates the subscriber data.
     * 
     * This should always be called before exiting.
     * 
     * @since 1.0
     */
    @Override
    public void saveData() {
    	this.dataStore.saveUsers(this.users);
    }
    
    /**
     * adds a data volume upgrade to the subscribers account
     * 
     * @param userIndex: index of subscriber who gets the data volume upgrade
     * 
     * @throws UserIndexOutOfBoundsException when called with an invalid user index
     * 
     * @since 1.1
     */
    @Override
    public void upgradeDataVolume(int userIndex) throws UserIndexOutOfBoundsException {
    	
    	if (!this.isValidUserIndex(userIndex)) {
    		throw new UserIndexOutOfBoundsException();
    	}
    	
    	UserData user = users.get(userIndex);
    	int dataVolumeUpgrades = user.getDataVolumeUpgrades();
    	user.setDataVolumeUpgrades(dataVolumeUpgrades + 1);
    }
    
    /*
     * Private methods
     * 
     * */
    
    /**
     * update user data for data service session
     * 
     * @param userIndex: 
     * @param serviceId
     * @param time
     * 
     * @throws NotEnoughDataVolumeException if there is not enough data volume left for the subscriber
	 * IMPORTANT: This depends on latency and therefore may happen inconsistently!
	 *
	 * @since 1.0
     */
    private void dataSession(int userIndex, String serviceId, int time) throws NotEnoughDataVolumeException {
        UserData user = this.users.get(userIndex);
        String subscriptionType = user.getSubscriptionType();
        
        int baseDataVolume = this.config.getSubscriptionDataVolume(subscriptionType);
        int upgradeDataVolume = 1000 * user.getDataUpgrades();
        int dataVolume = baseDataVolume + upgradeDataVolume;
        
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
    
    /**
     * calculate available data rate for a user
     * 
     * 1. find out which terminal the user is using
     * 2. find out which type of RAN the terminal supports
     * 3. get maximum data rate for the RAN
     * 4. get signal strength factor
     * 5. available data rate equals maximum data rate multiplied by signal strength factor
     * 
     * @param user: user for which to calculate available data rate
     * 
     * @return available data rate for the user
     * 
     * @since 1.0
     */
    private double getAvailableDataRate(UserData user) {
        String terminalType = user.getTerminalType();
        String ran = this.config.getRan(terminalType);
        double subscriptionRate = this.config.getMaxDataRate(ran);
        double signalStrength = this.getSignalStrength();
        return subscriptionRate*signalStrength;
    }

    /**
     * get the signal strength factor for a session
     * 
     * there are 4 types of signal strength:
     * - N/A (0%)
     * - bad (10%)
     * - medium (25%)
     * - good (50%)
     * 
     * One is selected at random (equal distribution) and returned.
     * 
     * @return signal strength factor
     * 
     * @since 1.0
     */
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
    
    /**
     * creates the invoice information for a subscriber
     * 
     * @param user: subscriber information on which the invoice information is based
     * 
     * @return object containing all relevant invoice information
     * 
     * @since 1.0
     */
    private InvoiceInformation getInvoiceInfo(UserData user) {
    	String subscriptionType = user.getSubscriptionType();
    	
    	// get invoice data
    	String forename = user.getForename();
    	String surename = user.getSurname();
    	int basePrice = this.config.getBasePrice(subscriptionType);
    	int voiceMinutes = user.getVoiceMinutes();
    	int voiceCharge = this.getVoiceCharge(user);
    	double dataUsed = user.getDataUsed();
        int dataVolumeUpgrades = user.getDataVolumeUpgrades();
        int dataCharge = 10*100*dataVolumeUpgrades;
        int totalCharge = basePrice + voiceCharge + dataCharge;
        InvoiceInformation invoice = new InvoiceInformation(forename, surename, basePrice, voiceMinutes, voiceCharge, dataUsed, dataVolumeUpgrades, dataCharge, totalCharge);

        return invoice;
    }
    
    /**
     * calculate voice charge for a user
     * 
     * 1. get free minutes included in users subscription
     * 2. get voice minutes used since last invoice
     * 3. if voice minutes are less than or equal to free minutes, return 0, if not, continue
     * 4. charged minutes are equal to voice minutes - free minutes
     * 5. return charged minutes multiplied by the price per minute for users subscription
     * 
     * @param user: user for whom to calculate voice charge
     * 
     * @return voice charge in Euro-cents
     */
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
    
    /**
     * checks, if a given userIndex is valid
     * 
     * userIndex must be valid for the list of users
     * 
     * @param userIndex: index to be checked
     * @return if index is valid
     */
    private boolean isValidUserIndex(int userIndex) {
    	return (userIndex >= 0) && (userIndex < this.users.size());
    }
}

