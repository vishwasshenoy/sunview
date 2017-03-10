package sunView5x;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class POMTransferCenterPage extends POMPreviewAccountTransferPage {

	public POMTransferCenterPage(WebDriver driver) {
		super(driver);
		
	}

	WebElement successMsgStxt, transferSummaryStxt;
	WebElement allTab,transactionNumberTxt,searchBtn,toDateTxt;
	List <WebElement> transferTableRows,searchBtns;
	String transferNumber;
	String[] splitSuccessMessage ;
	int numberOfSearchButtons;
	
	public void validateTransfer(){
		successMsgStxt = d.findElement(By.xpath("//section[@id ='messagepanel']/h2"));
		transferSummaryStxt = d.findElement(By.xpath("//section[@id ='messagepanel']/ol/li"));
		allTab = d.findElement(By.xpath("//a[@tabid = 'allTab']"));
		
		//verify success message
		if (successMsgStxt.getText().trim().equalsIgnoreCase("Successful Submit")){
			Reporter.log("Transfer Success message displayed as "+successMsgStxt.getText());
		}else{
			Reporter.log("Transfer Success Message is incorrect or missing");
		}
		
		//verify TransferMessage summary text
		splitSuccessMessage = transferSummaryStxt.getText().split(" "); //split the summary text so that we can extract the transfer number from it
		
		switch (isRecurring){
		case 1: 
			//recurring
			//The Transfer 59968028 from 1000077120466 to 292000176125 scheduled for 12/09/2016 and occurring Every Week of $3.00 has been created successfully.	
			transferNumber = splitSuccessMessage[2];
			expectedMessage = "The Transfer "+transferNumber+" from "+fromAccNumberInTestData+" to "+toAccNumberInTestData+" scheduled for "+transferDate+" and occurring "+transferRecSchd+" of $"+transferAmount+".00 has been created successfully.";
			transferMessageValidation();
			break;
			
		case 0: 
			//One-time only
			//Transfer 59968027 from 292000175412 to 1000077120466 scheduled for 12/09/2016 of $10.00 has been created successfully.
			transferNumber = splitSuccessMessage[1];
			expectedMessage = "Transfer "+transferNumber+" from "+fromAccNumberInTestData+" to "+toAccNumberInTestData+" scheduled for "+transferDate+" of $"+transferAmount+".00 has been created successfully.";
			transferMessageValidation();	
		}
	}
	
	public void searchTransferToVerifySuccessfullSubmit(){
		
		allTab.click();
		
		WebDriverWait wait= new WebDriverWait(d,20);
		transactionNumberTxt = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@originalid = 'sequenceNumberall']")));
		transactionNumberTxt.sendKeys(transferNumber);
		
		toDateTxt = d.findElement(By.xpath("//input[@id = 'toDateall']"));
		toDateTxt.sendKeys(transferDate);
		
		/*
		 * Search button from all 3 tabs - pending, processed,All - had same properties
		 * So xpath used to match with 3 nodes in HTML doc. Hence i stored them all in List and 
		 * clicked on 3rd object in this list - which points to the search button in 3rd tab - "All" tab.
		 */
		searchBtns = d.findElements(By.xpath("//a [@id = 'transferCenterSearchButton_Link']"));
		numberOfSearchButtons = searchBtns.size();
		
		if (searchBtns.size()>1){
			searchBtns.get(numberOfSearchButtons-1).click();
		}else{
			searchBtns.get(0).click();
		}
		
		//when you search by transfer number, it returns the search record in a row.
		transferTableRows = d.findElements(By.xpath("//table [@id = 'allTransferTable_table']/tbody/tr"));
		
		if(transferTableRows.size()>0){
			Reporter.log("Transfer number "+transferNumber+" Search is success in Transfer Center");
		}else{
			Reporter.log("Transfer number "+transferNumber+" Cannot be located in Transfer Center");
		}
	}
	
	public void transferMessageValidation(){
		
		if(transferSummaryStxt.getText().trim().equals(expectedMessage)){
			Reporter.log("Transfer summary message is matching with expected. Following message is displayed on screen - "+transferSummaryStxt.getText());
			System.out.println("Transfer summary message is matching with expected. Following message is displayed on screen - "+transferSummaryStxt.getText());
		}else{
			Reporter.log("Transfer Summary Message is not matching. Message displayed on screen - "+transferSummaryStxt.getText());
			Reporter.log("Expected String - "+expectedMessage);
		}
		
	}
}
