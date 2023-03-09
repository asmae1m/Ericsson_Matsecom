package ApiModule;

import static org.junit.Assert.*;

import org.junit.Test;

public class InvoiceInformationTest {

	@Test
	public void constructorWorksTest() {
		//given
		String forename = "testForename";
        String surname = "testSurname";
        int basePrice = 10000;
        int voiceMinutes = 5;
        int voiceCharge = 750;
        double dataVolume = 200.0;
        int dataVolumeUpgrades = 0;
        int dataCharge = 0 ;
        int totalCharge = 750;
        
        //when
        InvoiceInformation invoiceInformation = new InvoiceInformation(forename, surname, basePrice, voiceMinutes, voiceCharge, dataVolume, dataVolumeUpgrades, dataCharge, totalCharge);
        
        //then
        assertTrue("forename is correct", invoiceInformation.forename.equals(forename));
        assertTrue("surname is correct", invoiceInformation.surname.equals(surname));
        assertEquals("basePrice is correct", basePrice, invoiceInformation.basePrice);
        assertEquals("voiceMinutes is correct", voiceMinutes, invoiceInformation.voiceMinutes);
        assertEquals("voiceCharge is correct", voiceCharge, invoiceInformation.voiceCharge);
        assertEquals("dataVolume is correct", dataVolume, invoiceInformation.dataVolume, 0.0);
        assertEquals("dataVolumeUpgrades is correct", dataVolumeUpgrades, invoiceInformation.dataVolumeUpgrades);
        assertEquals("dataCharge is correct", dataCharge, invoiceInformation.dataCharge);
        assertEquals("totalCharge is correct", totalCharge, invoiceInformation.totalCharge);
	}

}
