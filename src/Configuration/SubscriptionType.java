package Configuration;

public enum SubscriptionType {
		GreenMobilS("GreenMobil S"),
	    GreenMobilM("GreenMobil M"),
	    GreenMobilL("GreenMobil L");

	    private String stringValue;

	    private SubscriptionType(String stringValue) {
			// TODO Auto-generated constructor stub
	        this.stringValue = stringValue;
	    }

	    public String getStringValue() {
	        return stringValue;
	    }
}
