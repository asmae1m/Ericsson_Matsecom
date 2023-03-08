package Configuration;

public interface Configuration {
	public SessionType getSessionType(String serviceType);
	public double getRequiredDataRate(String serviceType);
	public int getPricePerMinute(String subscriptionType);
	public int getBasePrice(String subscriptionType);
	public int getSubscriptionFreeMinutes(String subscriptionType);
	public int getSubscriptionDataVolume(String subscriptionType);
	public String getRan(String terminaltype);
	public double getMaxDataRate(String ran);
}
