package Configuration;

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
		return 0;
	}

	@Override
	public int getBasePrice(String subscriptionType) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSubscriptionFreeMinutes(String subscriptionType) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSubscriptionDataVolume(String subscriptionType) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getRan(String terminaltype) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getMaxDataRate(String ran) {
		// TODO Auto-generated method stub
		return 0;
	}

}
