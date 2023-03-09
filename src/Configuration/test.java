package Configuration;

import java.io.FileNotFoundException;

public class test {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		ConfigurationImp con = new ConfigurationImp();
		
		//con.saveProperty("Adaptive HD video", SessionType.DATA.toString(), null);
		System.out.println(con.getRequiredDataRate("Adaptive HD video"));
		//con.init();
	}

}
