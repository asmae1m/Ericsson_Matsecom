package Configuration;

import java.io.FileNotFoundException;
import java.io.IOException;

public class test {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		ConfigurationImp con = new ConfigurationImp();
		
		//con.saveProperty("Adaptive HD video", SessionType.DATA.toString(), null);
		//System.out.println(con.getRequiredDataRate("Adaptive HD video"));
		try {
			con.init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(con.getPossibleServices().get(0));
	}

}
