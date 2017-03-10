package sunView5x;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import exceptionPackage.AccountTransferException;

public class POMPreviewAccountTransferPage extends POMCreateTransferPage {

	public POMPreviewAccountTransferPage(WebDriver driver) {
		super(driver);
	}
	
	WebElement transferFromStxt, transferToStxt,amountStxt,dateStxt,frequencyLine1Stxt,frequencyLine2Stxt,memoStxt,submitTransferBtn;
	String amountStaticText;
	int  isRecurrenceEndTypeCorrect =0;
	String actualFromAccountInApp,actualToAccountInApp,expectedMessage;
	
	public void PreviewTransfer () throws AccountTransferException{
		//split the from and To account text obtained from app, to get just the account numbers
		//From Account
		transferFromStxt = d.findElement(By.id("transferFrom"));
		String[] splitFromAccountStxt = transferFromStxt.getText().trim().split(" ");
		String actualFromAccountInApp = splitFromAccountStxt[0];
		//To Account
		transferToStxt = d.findElement(By.id("transferTo"));
		String[] splitToAccountStxt = transferToStxt.getText().trim().split(" ");
		String actualToAccountInApp = splitToAccountStxt[0];
		
		//Object Identification				
		amountStxt = d.findElement(By.id("transferAmount"));
		dateStxt = d.findElement(By.id("transferSchdDate"));
		memoStxt = d.findElement(By.id("memoStaticField"));
		submitTransferBtn = d.findElement(By.xpath("//span[contains(text(),'Submit Transfer')]"));
		
		//locating the frequency line#1 and 2 based on whether transfer is recurring or not
		if(isRecurring == 0){
			Reporter.log("Value of isRecurring Flag = "+ isRecurring);
			frequencyLine1Stxt = d.findElement(By.id("freqOneTimeXferPrev"));
		}else{
			frequencyLine1Stxt = d.findElement(By.id("freqMultiXferPrev"));
			frequencyLine2Stxt = d.findElement(By.id("freqMultiXferPrev_auxiliary_label"));
		}
			
		//add $ symbol and decimal to amount value got from app.		
		amountStaticText = "$"+transferAmount+".00";
		
		//*************Validation Starts***************
		
		//From Account Validation
		if (fromAccNumberInTestData.trim().equalsIgnoreCase(actualFromAccountInApp.trim())){
			Reporter.log("From Account in preview screen matches with value entered");
			System.out.println("From Account in preview screen matches with value entered");
		}else{
			throw new AccountTransferException("From Account in preview screen is not matching with value entered");
		}
		//To Account Validation
		if(toAccNumberInTestData.trim().equalsIgnoreCase(actualToAccountInApp.trim())){
			Reporter.log("To Account in preview screen matches with value entered");
		}else{
			throw new AccountTransferException("To Account in preview screen is not matching with value entered");
		}
		//Amount Validation
		if (amountStaticText.trim().equalsIgnoreCase(amountStxt.getText().trim())){
			Reporter.log("Amount in preview screen matches with value entered");
		}else{
			throw new AccountTransferException("Amount in preview screen is not matching with value entered");
		}
		//Date Validation
		if (transferDate.trim().equalsIgnoreCase(dateStxt.getText().trim())){
			Reporter.log("Date in preview screen matches with value entered");
		}else{
			throw new AccountTransferException("Transfer Date in preview screen is not matching with value entered");
		}
		//Recurring Frequency validation		
		switch (isRecurring){
				
		case 0:
			if(frequencyLine1Stxt.getText().trim().equals("One-Time only")){
				Reporter.log("Frequency in preview screen matches with value entered");
			}else{
				throw new AccountTransferException("Frequency in preview screen is not matching with value entered");
			}
			break;
			
		case 1:
			if(frequencyLine1Stxt.getText().trim().equals(transferRecSchd.trim())){
				Reporter.log("Recurring Schedule in preview screen matches with value entered");
			}else{
				throw new AccountTransferException("Recurring Schedule in preview screen is not matching with value entered");
			}
		}
		
		//Recurrence endtype validation
		switch (recEndType){
		
		case 1:
			if(frequencyLine2Stxt.getText().trim().equals("Continue recurring schedule until further notice")){
				Reporter.log("Recurrence End Type is correct in Preview Screen");
			}else{
				throw new AccountTransferException("Recurrence End Type in preview screen is not matching with value entered");
			}
							
		case 2:
			
			String totalTransfers = transferTotalRecPayments+" transfers";
			if(frequencyLine2Stxt.getText().trim().equalsIgnoreCase(totalTransfers)){
				Reporter.log("Recurrence End Type is correct in Preview Screen");
			}else{
				throw new AccountTransferException("Total # Transfer in preview screen is not matching with value entered");
			}
			break;
			
		case 3:
			
			if(frequencyLine2Stxt.getText().trim().equalsIgnoreCase(transferRecEndDate)){
				Reporter.log("Recurrence End Type is correct in Preview Screen");
			}else{
				throw new AccountTransferException("Recurrence End Type in preview screen is not matching with value entered");
			}
		
		}
		
		//Memo Text validation
		if(isMemoRequired>0){
			if(memoStxt.getText().trim().equalsIgnoreCase(transferMemo)){
				Reporter.log("Memo Text in preview screen is correct");
			}else{
				throw new AccountTransferException("Memo Text in preview screen is not matching with value entered");
			}
		}
		
		submitTransferBtn.click();
	}
	
}
