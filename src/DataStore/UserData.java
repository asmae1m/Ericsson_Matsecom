package DataStore;

public class UserData {
	private String forename;
	private String surname;
	private String imsi;
	private double remainingData;
	private int voiceMinutes;
	private int voiceCharge;
	private TerminalType terminalType;
	private SubscriptionType subscriptionType;

	public UserData(String forename, String surname, String imsi, TerminalType terminalType,
			SubscriptionType subscriptionType) {
		this.forename = forename;
		this.surname = surname;
		this.imsi = imsi;
		this.terminalType = terminalType;
		this.subscriptionType = subscriptionType;
	}

	// Setter methods

	public void setRemainingData(double remainingData) {
		this.remainingData = remainingData;
	}

	public void setVoiceMinutes(int voiceMinutes) {
		this.voiceMinutes = voiceMinutes;
	}

	public void setVoiceCharge(int voiceCharge) {
		this.voiceCharge = voiceCharge;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public void setTerminalType(TerminalType terminalType) {
		this.terminalType = terminalType;
	}

	public void setSubscriptionType(SubscriptionType subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	// Getter methods
	public double getRemainingData() {
		return remainingData;
	}

	public int getVoiceMinutes() {
		return voiceMinutes;
	}

	public int getVoiceCharge() {
		return voiceCharge;
	}

	public String getForename() {
		return forename;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getName() {
		return this.forename + this.surname;
	}

	public String getImsi() {
		return imsi;
	}

	public TerminalType getTerminalType() {
		return terminalType;
	}

	public SubscriptionType getSubscriptionType() {
		return subscriptionType;
	}

}