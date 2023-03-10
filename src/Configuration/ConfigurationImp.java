package Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Properties;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * the configuration class help us to get list of terminals , ran
 * services ,sessionType and some price ...
 * 
 * @author ALARA
 * 
 * @since 1.0
 * */

public class ConfigurationImp implements Configuration {
	/**
	 * Attributes
	 * */
	private Properties sessionType;
	private Properties requiredDataRate;
	private Properties pricePerMinute;
	private Properties basePrice;
	private Properties freeMinutes;
	private Properties dataVolume;
	private Properties ran;
	private Properties maxDataRate;
	
	/**
	 * Main Constructor
	 * 
	 * used by ApiModule constructor so he can load all the properties 
	 * that help us to get all the configuration data or write it 
	 * when its empty
	 * @author ALARA
	 * 
	 * @since 1.0
	 * */
	public ConfigurationImp() {
		try {
			loadConfig();
		} catch(IOException e) {
			try {
				init();
				loadConfig();
			} catch(IOException f) {
				// oops!
			}
		}
	}

	/**
	 * return an enum value  if service is voice or data according to the value of serviceType
	 * 
	 * 
	 * @since 1.0
	 * */
	
	@Override
	public SessionType getSessionType(String serviceType) {
		String value = sessionType.getProperty(serviceType);
		if (value.equals("VOICE")) {
			return SessionType.VOICE;
		} else {
			return SessionType.DATA;
		}
	}
	 
	/**
	 * return data rate for service type
	 * @param serviceType :either "Voice call" , "Browsing and social networking","App download" or "Adaptive HD video"
	 * 
	 * @since 1.0
	 * */
	@Override
	public double getRequiredDataRate(String serviceType) {
		return Double.parseDouble(requiredDataRate.getProperty(serviceType));
	}
	
	
	/**
	 * return the price per minute of voice calls in cent for the type of subscription
	 * 
	 * @since 1.0
	 * */
	@Override
	public int getPricePerMinute(String subscriptionType) {
		return Integer.parseInt(pricePerMinute.getProperty(subscriptionType));
	}
	
	
	/**
	 * return the base price in cent for subscriptionType
	 * 
	 * @since 1.0
	 * */
	@Override
	public int getBasePrice(String subscriptionType) {
		return Integer.parseInt(basePrice.getProperty(subscriptionType));
	}

	@Override
	public int getSubscriptionFreeMinutes(String subscriptionType) {
		return Integer.parseInt(freeMinutes.getProperty(subscriptionType));
	}

	@Override
	public int getSubscriptionDataVolume(String subscriptionType) {
		return Integer.parseInt(dataVolume.getProperty(subscriptionType));
	}
	
	@Override
	public String getRan(String terminaltype) {
		return ran.getProperty(terminaltype);
	}

	@Override
	public double getMaxDataRate(String ran) {
		return Double.parseDouble(maxDataRate.getProperty(ran));
	}
	
	private void loadConfig() throws IOException {
		sessionType = loadProperties("configservice.properties");
		requiredDataRate = loadProperties("configdatarate.properties");
		pricePerMinute = loadProperties("configPricePerMinute.properties");
		basePrice = loadProperties("configbasePrice.properties");
		freeMinutes = loadProperties("configFreeMinute.properties");
		dataVolume = loadProperties("configsubDataVolume.properties");
		ran = loadProperties("configRan.properties");
		maxDataRate = loadProperties("configmaxDataRate.properties");
	}
	
	public Properties loadProperties(String file) throws IOException {
		File configFile = new File(file);
		Properties props = new Properties();
		FileReader reader = new FileReader(configFile);
	    props.load(reader);
	    reader.close();
		return props;
	}
	
	@Override
	public List<String> getPossibleSubscriptionTypes() {
		List<String> subscriptionTypes = new ArrayList<String>();
		for (Object obj : Collections.list(basePrice.keys())){
			subscriptionTypes.add(obj.toString());
		}
		return subscriptionTypes;
	}
	
	@Override
	public List<String> getPossibleTerminalTypes() {
		List<String> terminalTypes = new ArrayList<String>();
		for (Object obj : Collections.list(ran.keys())){
			terminalTypes.add(obj.toString());
		}
		return terminalTypes;
	}
	
	@Override
	public List<String> getPossibleServices() {
		List<String> services = new ArrayList<String>();
		for (Object obj : Collections.list(requiredDataRate.keys())){
			services.add(obj.toString());
		}
		return services;
	}
	
	/*
	 * create missing config files & helper funcitons for testing
	 * 
	 * */
	
	public static String getProperty(String key,String file) {
		File configFile = new File(file);
		String host = null;
		try {
		    FileReader reader = new FileReader(configFile);
		    Properties props = new Properties();
		    props.load(reader);
		 
		    host = props.getProperty(key);
		    
		    reader.close();
		} catch (FileNotFoundException ex) {
		    // file does not exist
		} catch (IOException ex) {
		    // I/O error
		}
		return host;
	}

	public static void saveProperty(String key, String value, String cmt,String file) throws FileNotFoundException {
		FileOutputStream configFile = new FileOutputStream(file,true);
		 
		try {
		    Properties props = new Properties();
		    props.setProperty(key, value);
		    props.store(configFile, cmt);
		    configFile.close();
		} catch (FileNotFoundException ex) {
		    // file does not exist
		} catch (IOException ex) {
		    // I/O error
		}
	}
	
	public static void emptyConfig(String filename) {

        File configFile = new File(filename);

        try {
            RandomAccessFile raf = new RandomAccessFile(configFile, "rw");
            raf.setLength(0);
            raf.close();
        } catch (IOException e) {
        }
    }
	
	
	@SuppressWarnings("static-access")
	public static void init() throws IOException {
		String[] fileNames = {"configservice.properties","configdatarate.properties","configPricePerMinute.properties","configbasePrice.properties","configFreeMinute.properties","configsubDataVolume.properties","configRan.properties","configmaxDataRate.properties"};
		for (String string : fileNames) {
			emptyConfig(string);
		}
		/**************/
		saveProperty("App download", SessionType.DATA.toString(), "Service type","configservice.properties");
		saveProperty("Adaptive HD video", SessionType.DATA.toString(),null, "configservice.properties");
		saveProperty("Voice call", SessionType.VOICE.toString(), null,"configservice.properties");
		saveProperty("Browsing and social networking", SessionType.DATA.toString(), null, "configservice.properties");
		/************/
		saveProperty("Voice call",String.valueOf(0.0),"RequiredData Rate","configdatarate.properties");
		saveProperty("Browsing and social networking",String.valueOf(2.0),null,"configdatarate.properties");
		saveProperty("App download",String.valueOf(10.0),null,"configdatarate.properties");
		saveProperty("Adaptive HD video",String.valueOf(100.0),null,"configdatarate.properties");
		/*************/
		saveProperty("GreenMobil S",String.valueOf(8),"Price Per Minute (cent)","configPricePerMinute.properties");
		saveProperty("GreenMobil M",String.valueOf(6),null,"configPricePerMinute.properties");
		saveProperty("GreenMobil L",String.valueOf(4),null,"configPricePerMinute.properties");
		/*************/
		saveProperty("GreenMobil S",String.valueOf(800),"Base Price","configbasePrice.properties");
		saveProperty("GreenMobil M",String.valueOf(2200),null,"configbasePrice.properties");
		saveProperty("GreenMobil L",String.valueOf(4200),null,"configbasePrice.properties");
		/*************/
		saveProperty("GreenMobil S",String.valueOf(0),"SubscriptionFreeMinutes","configFreeMinute.properties");
		saveProperty("GreenMobil M",String.valueOf(100),null,"configFreeMinute.properties");
		saveProperty("GreenMobil L",String.valueOf(150),null,"configFreeMinute.properties");
		/*************/
		saveProperty("GreenMobil S",String.valueOf(500),"Subscribtion data volume","configsubDataVolume.properties");
		saveProperty("GreenMobil M",String.valueOf(2048),null,"configsubDataVolume.properties");
		saveProperty("GreenMobil L",String.valueOf(5120),null,"configsubDataVolume.properties");
		/*************/
		saveProperty("PhairPhone","3G","getRan","configRan.properties");
		saveProperty("Pear aphone 4s","3G",null,"configRan.properties");
		saveProperty("Samsung S42plus","4G",null,"configRan.properties");
		/************/
		saveProperty("3G",String.valueOf(20),null,"configmaxDataRate.properties");
		saveProperty("4G",String.valueOf(300),null,"configmaxDataRate.properties");
	}
	
	

}
