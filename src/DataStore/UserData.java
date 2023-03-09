package DataStore;

public class UserData {
	private String forename;
	private String surname;
	private String imsi;
	private double dataUsed;
	private int voiceMinutes;
	private String terminalType;
	private String subscriptionType;

	public UserData() {
		super();
	}

	public UserData(String forename, String surname, String imsi, String terminalType, String subscriptionType) {
		this.forename = forename;
		this.surname = surname;
		this.imsi = imsi;
		this.terminalType = terminalType;
		this.subscriptionType = subscriptionType;
		this.dataUsed = 0.0;
		this.voiceMinutes = 0;
	}

	// Setter methods
	public void setDataUsed(double dataUsed) {
		this.dataUsed = dataUsed;
	}

	public void setVoiceMinutes(int voiceMinutes) {
		this.voiceMinutes = voiceMinutes;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}

	public void setSubscriptionType(String subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	// Getter methods
	public double getDataUsed() {
		return dataUsed;
	}

	public int getVoiceMinutes() {
		return voiceMinutes;
	}

	public String getForename() {
		return forename;
	}

	public String getSurname() {
		return surname;
	}

	public String getImsi() {
		return imsi;
	}

	public String getTerminalType() {
		return terminalType;
	}

	public String getSubscriptionType() {
		return subscriptionType;
	}

}