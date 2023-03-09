package DataStore;

/**
 * The UserData class contains data for a single user, including their name,
 * International Mobile Subscriber Identity (IMSI), data usage, voice minutes,
 * terminal type, subscription type, and number of data volume upgrades.
 * 
 * @author ALARA
 * 
 * @since 1.0
 *
 */

public class UserData {

	/*
	 * Attributes
	 * 
	 */

	private String forename;
	private String surname;
	private String imsi;
	private double dataUsed;
	private int voiceMinutes;
	private String terminalType;
	private String subscriptionType;
	private int dataVolumeUpgrades;

	/*
	 * Constructors
	 * 
	 */

	/**
	 * Creates an instance of UserData with default values. Used by the
	 * Objectmapper.
	 */

	public UserData() {
		super();
	}

	/**
	 * Creates an instance of UserData with the specified values.
	 * 
	 * @param forename         the user's first name
	 * @param surname          the user's last name
	 * @param imsi             the user's International Mobile Subscriber Identity
	 *                         (IMSI)
	 * @param terminalType     the type of terminal used by the user
	 * @param subscriptionType the user's subscription type
	 */

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