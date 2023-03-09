package DataStore;

public class UserData {
	private String forename;
	private String surname;
	private String imsi;
	private double dataUsed;
	private int voiceMinutes;
	private String terminalType;
	private String subscriptionType;
	private int dataVolumeUpgrades;

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
		this.dataVolumeUpgrades = 0;
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

	public void setDataVolumeUpgrades(int dataVolumeUpgrades) {
		this.dataVolumeUpgrades = dataVolumeUpgrades;
	}

	// Getter methods
	public double getDataUsed() {
		return this.dataUsed;
	}

	public int getVoiceMinutes() {
		return this.voiceMinutes;
	}

	public String getForename() {
		return this.forename;
	}

	public String getSurname() {
		return this.surname;
	}

	public String getImsi() {
		return this.imsi;
	}

	public String getTerminalType() {
		return this.terminalType;
	}

	public String getSubscriptionType() {
		return this.subscriptionType;
	}
	
	public int getDataVolumeUpgrades() {
		return this.dataVolumeUpgrades;
	}

}