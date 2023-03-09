package Configuration;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConfigurationImp con = new ConfigurationImp();
		con.saveProperty("Voice call",SessionType.VOICE.toString(), "Service type");
		con.saveProperty("Browsing and social networking", SessionType.DATA.toString(),null);
	}

}
