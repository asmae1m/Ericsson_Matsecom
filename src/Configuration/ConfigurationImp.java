package Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationImp implements Configuration {

	@Override
	public SessionType getSessionType(String serviceType) {
		// TODO Auto-generated method stub
		if(serviceType.equals("Voice call")) {
			return SessionType.VOICE;
		}else if (serviceType.equals("Browsing and social networking") ||serviceType.equals("App download") || serviceType.equals("Adaptive HD video") ) 
			{return SessionType.DATA;}
		return null;
	}

	@Override
	public double getRequiredDataRate(String serviceType) {
		// TODO Auto-generated method stub
		double requiredDataRate=0.0;
		if(serviceType.equals("Voice call")) {
			requiredDataRate =2;
		}else if (serviceType.equals("App download")) {
			requiredDataRate = 10;
		}else if ( serviceType.equals("Adaptive HD video")) {
			requiredDataRate=100;
		}
		return requiredDataRate;
	}

	@Override
	public int getPricePerMinute(String subscriptionType) {
		// TODO Auto-generated method stub
		int pricePerMinute =0;
		if(subscriptionType.equals("GreenMobil S")) {
			pricePerMinute=8;
		}else if(subscriptionType.equals("GreenMobil M")) {
			pricePerMinute=6;
		}else if(subscriptionType.equals("GreenMobil L")) {
			pricePerMinute=4;
		}
		return pricePerMinute;
	}

	@Override
	public int getBasePrice(String subscriptionType) {
		// TODO Auto-generated method stub
		int baseMinute =0;
		if(subscriptionType.equals("GreenMobil S")) {
			baseMinute=800;
		}else if(subscriptionType.equals("GreenMobil M")) {
			baseMinute=2200;
		}else if(subscriptionType.equals("GreenMobil L")) {
			baseMinute=4200;
		}
		return baseMinute;
	}

	@Override
	public int getSubscriptionFreeMinutes(String subscriptionType) {
		// TODO Auto-generated method stub
		int freeMinute =-1;
		if(subscriptionType.equals("GreenMobil S")) {
			freeMinute=0;
		}else if(subscriptionType.equals("GreenMobil M")) {
			freeMinute=100;
		}else if(subscriptionType.equals("GreenMobil L")) {
			freeMinute=150;
		}
		return freeMinute;
	}

	@Override
	public int getSubscriptionDataVolume(String subscriptionType) {
		// TODO Auto-generated method stub
		int dataMinute =-1;
		if(subscriptionType.equals("GreenMobil S")) {
			dataMinute=500;
		}else if(subscriptionType.equals("GreenMobil M")) {
			dataMinute=2048;
		}else if(subscriptionType.equals("GreenMobil L")) {
			dataMinute=5120;
		}
		return dataMinute;
	}

	@Override
	public String getRan(String terminaltype) {
		// TODO Auto-generated method stub
		if(terminaltype.equals(TerminalType.PhairPhone.getStringValue())|| terminaltype.equals(TerminalType.Pearaphone4s.getStringValue())) {
			return Ran.threeG.getStringValue();
		}else if(terminaltype.equals(TerminalType.SamsungS42plus.getStringValue()))
			return Ran.fourG.getStringValue();
		return null;
	}

	@Override
	public double getMaxDataRate(String ran) {
		// TODO Auto-generated method stub
		if(ran.equals(Ran.threeG.getStringValue())) {
			return 20;
		}else if (ran.equals(Ran.fourG.getStringValue())) {
			return 300;
		}else 
			return 0;
		
	}

	@Override
	public String getProperty(String key) {
		// TODO Auto-generated method stub
		File configFile = new File("config.properties");
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
	public void saveProperty(String key, String value, String cmt) {
		// TODO Auto-generated method stub
		File configFile = new File("config.properties");
		 
		try {
		    Properties props = new Properties();
		    props.setProperty(key, value);
		    FileWriter writer = new FileWriter(configFile);
		    props.store(writer, cmt);
		    writer.close();
		} catch (FileNotFoundException ex) {
		    // file does not exist
		} catch (IOException ex) {
		    // I/O error
		}
	}

}
