public class UserData {
    private String forename;
    private String surname;
    private String imsi;
    private TerminalType terminalType;
    private SubscriptionType subscriptionType;
    
    public UserData(String forename, String surname, String imsi, TerminalType terminalType, SubscriptionType subscriptionType) {
        this.forename = forename;
        this.surname = surname;
        this.imsi = imsi;
        this.terminalType = terminalType;
        this.subscriptionType = subscriptionType;
    }
    
    // getters and setters
    
    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return this.forename + this. surname;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public TerminalType getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(TerminalType terminalType) {
        this.terminalType = terminalType;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }
}