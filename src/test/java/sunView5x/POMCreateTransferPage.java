package sunView5x;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import exceptionPackage.AccountTransferException;

public class POMCreateTransferPage {

	
	WebDriver d;
	WebElement transferFromList, transferToList,amountTxt,dateTxt,one_Time_Only_frequencyRB,recurring_frequencyRB,optionsCB,memoTxt,continueBtn;
	WebElement recurringScheduleList, continueFurtherNotice_NumberOfPaymentsRB,sendTotalTransfers_NumberOfPaymentsRB,sendTotalTransfersTxt,endOnThisDateRB,endOnThisDateTxt;
	List<WebElement> fromAccountsList, toAccountsList;
	int isRecurring = 0,recEndType = 0,isMemoRequired=0;
	int FromIndex=1,ToIndex=1,fromFlag =0,toFlag=0;
	String transferFrom, transferTo, transferAmount, transferDate, transferFrequency, transferRecSchd, transferRecNOP, transferTotalRecPayments, transferRecEndDate, transferOptions,transferMemo;
	String fromAccNumberInTestData, toAccNumberInTestData;
	String temp;
	
	public POMCreateTransferPage(WebDriver driver){
		this.d = driver;
	}
	
	public void createSingleTransfer(String FromAccount, String ToAccount,String amount,String Date,String frequency, String recSchd, String NOP, String totalPayments, String endDate,String Options,String Memo) throws AccountTransferException{
		
		this.transferFrom = FromAccount;
		this.transferTo = ToAccount;
		this.transferAmount = amount;
		this.transferDate = Date;
		this.transferFrequency = frequency;
		this.transferRecSchd = recSchd;
		this.transferRecNOP = NOP;
		this.transferTotalRecPayments = totalPayments;
		this.transferRecEndDate = endDate;
		this.transferOptions = Options;
		this.transferMemo = Memo;
		
		//get the list of all accounts from From and To Drop down
		fromAccountsList = d.findElements(By.xpath("//select[@id = 'fromParty']/optgroup/option"));
		
		
		//object identification
		amountTxt = d.findElement(By.id("amount_value"));
		dateTxt = d.findElement(By.id("executionDate"));
		one_Time_Only_frequencyRB = d.findElement(By.id("recurring_recurring"));
		recurring_frequencyRB = d.findElement(By.id("recurring_nonrecurring"));
		optionsCB = d.findElement(By.id("memoInfo"));
		continueBtn = d.findElement(By.xpath("//span[contains(text(),'Continue')]"));
		Select fAccount = new Select(d.findElement(By.id("fromParty")));
		Select tAccount = new Select(d.findElement(By.id("toParty")));
		
		//split the from and To account text obtained from Test Data sheet, to get just the account numbers
		//From Account 
		String[] splitFromAccount = transferFrom.split("-");
		fromAccNumberInTestData = splitFromAccount[0].trim();
		
		//To Account
		String[] splitToAccount = transferTo.split("-");
		toAccNumberInTestData = splitToAccount[0].trim();
		
		//Select the From Account based on id
		for(WebElement eachAccount: fromAccountsList){
			temp=eachAccount.getText();
			if(temp.trim().contains(fromAccNumberInTestData.trim())){
				fAccount.selectByIndex(FromIndex);
				fromFlag = 1;
				break;
			}
			FromIndex++;
		}
		
		//Select the To Account based on ID
		toAccountsList = d.findElements(By.xpath("//select[@id = 'toParty']/optgroup/option"));//Locate the object here
		for (WebElement eachAccount: toAccountsList){
			temp=eachAccount.getText();
			if(temp.trim().contains(toAccNumberInTestData.trim())){
				tAccount.selectByIndex(ToIndex);
				toFlag = 1;
				break;
			}
			ToIndex++;
		}
		
		//if accounts not found, throw exception.
		if(toFlag==0){
			throw new AccountTransferException("To Account Not found in the Create Transfer screen");
		}else if(fromFlag==0){
			throw new AccountTransferException("From Account Not found in the Create Transfer screen");
		}
				
		//if recurring, click on the RB, so that other objects are visible
		if (transferFrequency.trim().equalsIgnoreCase("recurring")){
			isRecurring = 1;
			Select recurringScheduleList = new Select(d.findElement(By.name("frequency")));
			
			recurring_frequencyRB.click();
			recurringScheduleList.selectByVisibleText(recSchd);
			
			if(NOP.trim().equalsIgnoreCase("Continue recurring schedule until further notice")){
				recEndType = 1;
				
				continueFurtherNotice_NumberOfPaymentsRB = d.findElement(By.id("recurrenceDuration_indefinite"));
				
				continueFurtherNotice_NumberOfPaymentsRB.click();
				
			}else if (NOP.trim().equalsIgnoreCase("Send Total Transfers")){
				recEndType =2;
				
				sendTotalTransfers_NumberOfPaymentsRB = d.findElement(By.id("recurrenceDuration_finite"));
				sendTotalTransfersTxt = d.findElement(By.id("recurrenceNumber"));
				
				sendTotalTransfers_NumberOfPaymentsRB.click();
				sendTotalTransfersTxt.sendKeys(transferTotalRecPayments);
								
			}else if(NOP.trim().equalsIgnoreCase(" End on this date")){
				recEndType = 3;
				
				endOnThisDateRB = d.findElement(By.id("recurrenceDuration_endDate"));
				endOnThisDateTxt = d.findElement(By.id("endDate"));
				
				endOnThisDateRB.click();
				endOnThisDateTxt.sendKeys(transferRecEndDate);
				
			}
		}
		
		//Transfer Option
		if(transferOptions.trim().equalsIgnoreCase("Yes")){
			isMemoRequired = 1;
			optionsCB = d.findElement(By.id("memoInfo"));
			memoTxt = d.findElement(By.xpath("//textarea[@id = 'memo']"));
			
			optionsCB.click();
			memoTxt.sendKeys(Memo);		
		}
		//Transfer Date
		dateTxt.clear();
		dateTxt.sendKeys(transferDate);
		
		//Transfer Amount
		amountTxt.sendKeys(transferAmount);
		
		//Click Continue
		continueBtn.click();
	}
}
