package Configuration;

public enum TerminalType {
    PhairPhone("PhairPhone"),
    Pearaphone4s("Pear aphone 4s"),
    SamsungS42plus("Samsung S42plus");

    private String stringValue;

    TerminalType(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }
}
