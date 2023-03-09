package Configuration;

public enum Ran {
    twoG("2G (GSM)"),
    threeG("3G (HSPA)"),
    fourG("4G (LTE)");

    private String stringValue;

    Ran(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }
}
