package ApiModule;

import static org.junit.Assert.*;

import java.io.IOException;

import TestingPackage.TestHelper;
import Configuration.*;
import DataStore.*;

import org.junit.Before;
import org.junit.Test;

public class ApiModuleTest {
	
	private Configuration config;
	private DataStore data;
	
	
	@Before
	public void doBeforeEach() throws IOException {
		config = new ConfigurationImp();
		data = new JsonDataStore("testUserData.json");
	}


}
