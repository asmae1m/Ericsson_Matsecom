package Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationImp implements Configuration {
	
	public ConfigurationImp() throws FileNotFoundException {
		init();
	}
	
	public ConfigurationImp(String string) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public SessionType getSessionType(String serviceType) {
		// TODO Auto-generated method stub
		//return null;
		return SessionType.valueOf(getProperty(serviceType,"configservice.properties"));
	}

	@Override
	public double getRequiredDataRate(String serviceType) {
		// TODO Auto-generated method stub
		return Double.parseDouble(getProperty(serviceType, "configdatarate.properties"));

	}

	@Override
	public int getPricePerMinute(String subscriptionType) {
		// TODO Auto-generated method stub
		return Integer.parseInt(getProperty(subscriptionType, "configPricePerMinute.properties"));
	}

	@Override
	public int getBasePrice(String subscriptionType) {
		// TODO Auto-generated method stub
		return Integer.parseInt(getProperty(subscriptionType, "configbasePrice.properties"));
	}

	@Override
	public int getSubscriptionFreeMinutes(String subscriptionType) {
		// TODO Auto-generated method stub
		return Integer.parseInt(getProperty(subscriptionType, "configsubFreeMinute.properties"));
	}

	@Override
	public int getSubscriptionDataVolume(String subscriptionType) {
		// TODO Auto-generated method stub
		return Integer.parseInt(getProperty(subscriptionType, "configsubDataVolume.properties"));

	}
	@Override
	public String getRan(String terminaltype) {
		// TODO Auto-generated method stub
		return getProperty(terminaltype, "configRan.properties");

	}

	@Override
	public double getMaxDataRate(String ran) {
		return Double.parseDouble(getProperty(ran, "configRan.properties"));

	}

	@Override
	public String getProperty(String key,String file) {
		// TODO Auto-generated method stub
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

	@Override
	public void saveProperty(String key, String value, String cmt,String file) throws FileNotFoundException {
		// TODO Auto-generated method stub
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

	@Override
	public void init() {
		// TODO Auto-generated method stub
		ConfigurationImp con = new ConfigurationImp("test");
		try {
			con.saveProperty("App download", SessionType.DATA.toString(), "Service type","configservice.properties");
			con.saveProperty("Adaptive HD video", SessionType.DATA.toString(),null, "configservice.properties");
			con.saveProperty("Voice call", SessionType.VOICE.toString(), null,"configservice.properties");
			/************/
			con.saveProperty("Voice call",String.valueOf(0.0),"RequiredData Rate","configdatarate.properties");
			con.saveProperty("Browsing and social networking",String.valueOf(2.0),null,"configdatarate.properties");
			con.saveProperty("App download",String.valueOf(10.0),null,"configdatarate.properties");
			con.saveProperty("Adaptive HD video",String.valueOf(100.0),null,"configdatarate.properties");
			/*************/
			con.saveProperty("GreenMobil S  ",String.valueOf(8),"Price Per Minute (cent)","configPricePerMinute.properties");
			con.saveProperty("GreenMobil M",String.valueOf(6),null,"configPricePerMinute.properties");
			con.saveProperty("GreenMobil L",String.valueOf(4),null,"configPricePerMinute.properties");
			/*************/
			con.saveProperty("GreenMobil S",String.valueOf(800),"Base Price","configbasePrice.properties");
			con.saveProperty("GreenMobil M",String.valueOf(2200),null,"configbasePrice.properties");
			con.saveProperty("GreenMobil L",String.valueOf(4200),null,"configbasePrice.properties");
			/*************/
			con.saveProperty("GreenMobil S",String.valueOf(0),"SubscriptionFreeMinutes","configsubFreeMinute.properties");
			con.saveProperty("GreenMobil M",String.valueOf(100),null,"configFreeMinute.properties");
			con.saveProperty("GreenMobil L",String.valueOf(150),null,"configFreeMinute.properties");
			/*************/
			con.saveProperty("GreenMobil S",String.valueOf(500),"Subscribtion data volume","configsubDataVolume.properties");
			con.saveProperty("GreenMobil M",String.valueOf(2048),null,"configsubDataVolume.properties");
			con.saveProperty("GreenMobil L",String.valueOf(5120),null,"configsubDataVolume.properties");
			/*************/
			con.saveProperty("PhairPhone","3G","getRan","configRan.properties");
			con.saveProperty("Pear aphone 4s","3G",null,"configRan.properties");
			con.saveProperty("Samsung S42plus","4G",null,"configRan.properties");
			/************/
			con.saveProperty("3G",String.valueOf(20),null,"configmaxDataRate.properties");
			con.saveProperty("4G",String.valueOf(300),null,"configmaxDataRate.properties");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
