package ApiModule;

import DataStore.UserData;

public interface SessionManager {
    void newSession(int id, int service, int time);
    InvoiceInformation[] invoice();
    UserData[] getUserList();
    double calculateBandwidth(int service, double signalStrength);
}