package ApiModule;

import static org.junit.Assert.*;
import TestingPackage.TestHelper;
import Configuration.*;
import DataStore.*;

import org.junit.Test;

public class ApiModuleTest {

	@Test
	public void test() {
		Configuration testConfig = TestHelper.getDefaultTestConfiguration();
		System.out.println(testConfig.getSessionType("Voice Call"));
		//DataStore testDataStore = TestHelper.getDefaultTestDataStore();
		
		fail("Not yet implemented");
	}

}
