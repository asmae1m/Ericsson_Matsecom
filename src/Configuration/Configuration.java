package Configuration;

import java.io.FileNotFoundException;

public interface Configuration {
	public SessionType getSessionType(String serviceType);
	public double getRequiredDataRate(String serviceType);
	public int getPricePerMinute(String subscriptionType);
	public int getBasePrice(String subscriptionType);
	public int getSubscriptionFreeMinutes(String subscriptionType);
	public int getSubscriptionDataVolume(String subscriptionType);
	public String getRan(String terminaltype);
	public double getMaxDataRate(String ran);
	
	/****/
	//public String getProperty(String key);
	public void saveProperty(String key , String value, String cmt,String file) throws FileNotFoundException;
	public void init();
	public String getProperty(String key, String file);
}
