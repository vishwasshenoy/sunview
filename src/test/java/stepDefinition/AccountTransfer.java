package stepDefinition;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import sunView5x.POMSunViewLoginPage;
import sunView5x.POMTransferCenterPage;
import sunView5x.POMTreasuryDBPage;
import utilityPackage.DataHelper;

public class AccountTransfer {
	
	WebDriver sunViewDriver;
	POMSunViewLoginPage l; 
	POMTreasuryDBPage t;
	POMTransferCenterPage ct;
	
	public List <HashMap<String,String>> dataSet;
	
	public AccountTransfer(){
		
		File file = new File("C:/Selenium/JarFiles/chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		sunViewDriver = new ChromeDriver();
		sunViewDriver.get("https://wholesaleportal-itca.suntrust.com/SunView/user/login?contextType=external&username=string&initial_command=NONE&password=secure_string&challenge_url=https%3A%2F%2Fwholesaleportal-itca.suntrust.com%2FSunView%2Fuser%2Flogin&request_id=6651191237148972048");
		
		dataSet = DataHelper.readExcelDatafromFile("C://Selenium//TestData//AccountTransfer.xlsx", "Sheet1");
	}
	
	@Given("^Login to SunView as \"(.*?)\" customer$")
	public void login_to_SunView_as_customer(String excelDataRow) throws Throwable {
		
		//access the first hashmap in the List
		int dataRow = Integer.parseInt(excelDataRow)-1;
		l = new POMSunViewLoginPage(sunViewDriver);
		//Make sure that they key name matches with column name you provided on header row in Excel
		l.loginToSunView(dataSet.get(dataRow).get("customerID"), dataSet.get(dataRow).get("userID5x"), dataSet.get(dataRow).get("password5x_SunView"));
		sunViewDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@When("^user provides provides account transfer data as in \"(.*?)\"$")
	public void user_provides_provides_account_transfer_data_as_in(String excelDataRow) throws Throwable{
		
		//access the first hashmap in the List
		int dataRow = Integer.parseInt(excelDataRow)-1;
		t = new POMTreasuryDBPage(sunViewDriver);
		ct = new POMTransferCenterPage(sunViewDriver);
		
		t.clickOnTransfers_SingleTransfer();
		ct.createSingleTransfer(dataSet.get(dataRow).get("FromAccount"), dataSet.get(dataRow).get("ToAccount"),dataSet.get(dataRow).get("Amount"), dataSet.get(dataRow).get("Date"), dataSet.get(dataRow).get("Frequency"), dataSet.get(dataRow).get("RecurringSchedule"), dataSet.get(dataRow).get("NumberOfPaymentsOption"), dataSet.get(dataRow).get("SendTotalTransfers"), dataSet.get(dataRow).get("EndOnThisDate"), dataSet.get(dataRow).get("Options"), dataSet.get(dataRow).get("Memo"));
			    
	}

	@When("^Submits the transfer$")
	public void submits_the_transfer() throws Throwable  {
		ct.PreviewTransfer();
	    
	}

	@Then("^That Transfer should be successful$")
	public void that_Transfer_should_be_successful() throws Throwable  {
	    
		ct.validateTransfer();
		ct.searchTransferToVerifySuccessfullSubmit();
		t.clickOnTreasuryDashboard();
		t.logoutOfSunView();
	}
}
