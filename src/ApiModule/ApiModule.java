package ApiModule;

import DataStore.JSONDataStore;
import DataStore.UserInfo;
import Configuration.Configuration;

interface SessionManager {
    void newSession(int id, int service, int time);
    InvoiceInformation[] invoice();
    UserInfo[] getUserList();
    double calculateBandwidth(int service, double signalStrength);
}

class InvoiceInformation {
    public String name;
    public double dataVolume;
    public int voiceMinutes;
}

class ApiModule implements SessionManager {

    private Configuration config;
    private DataStore data;

    public ApiModule(){
        this.config = new Configuration();
        this.data = new JSONDataStore();

    }
}

