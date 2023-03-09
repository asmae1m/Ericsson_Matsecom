package Configuration;

import java.io.FileNotFoundException;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConfigurationImp con = new ConfigurationImp();
		try {
			con.saveProperty("App download", SessionType.DATA.toString(), null);
			con.saveProperty("Adaptive HD video", SessionType.DATA.toString(), null);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
