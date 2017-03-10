package sunView5x;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class POMTreasuryDBPage {
	
	WebDriver d;
	WebElement paymentsMenuOption, importProfileSubMenuOption,accountsWidget,TransfersMenuOption,SingleTransferSubMenuOption;
	WebElement treasuryDashboardMenuOption,exitLink;
	int wait = 1;
	
	public POMTreasuryDBPage(WebDriver driver){
		this.d = driver;
	}
	
	public void clickOnPayments_ImportProfileMenuoption(){
		
				
		//Implicit Wait - since the page takes long time to load after login
		d.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		/*
		 * Explicit wait:
		 * Because though page gets loaded, widgets take longer time to load.
		 * And when they load, Web page will refresh again.
		 * But the script would have clicked on Payments option by then(before widgets appear).
		 * The payments drop down will vanish(when widgets load) and script will not 
		 * be able to click on Import profiles
		 */
		WebDriverWait wait=new WebDriverWait(d,20);
		
		accountsWidget = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'widgetContainer Accounts']/div[@class = 'widget']")));
		//If explicit wait fails, script will fail. It will not execute rest of the lines below
		
		Reporter.log("Dashboard page loading complete...");
			
		//locate "Payment" options in the Menu
		paymentsMenuOption = d.findElement(By.xpath("//span[contains(text(),'Payments')]"));
		
		//performing mouse hover action on Payment option and then clicking on it
		 Actions action = new Actions(d);
		 action.moveToElement(paymentsMenuOption);
		 action.click().build().perform();
	
		//Locate the Import Profile Option now, which gets displayed in Payments list.
		 //use explicit wait again so that script waits till the payment list is fully visible
		importProfileSubMenuOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'sub_inner rounded']/ul/div[2]/li/a[contains(text(),'Import Profiles')]")));
		
		//Performing mouse hover action on Import Profile and then clicking
		action.moveToElement(importProfileSubMenuOption);
		action.click().build().perform();
		Reporter.log("Clicked on import profile option in SunView");
				
	}
	
	public void clickOnTransfers_SingleTransfer(){
		d.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		WebDriverWait wait=new WebDriverWait(d,20);
		accountsWidget = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'widgetContainer Accounts']/div[@class = 'widget']")));
		Reporter.log("Dashboard page loading complete...");
		TransfersMenuOption = d.findElement(By.xpath("//span[contains(text(),'Transfers')]"));
		
		Actions action = new Actions(d);
		action.moveToElement(TransfersMenuOption);
		action.click().build().perform();
		
		SingleTransferSubMenuOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id ='level3nav_a_3_1_0'][contains(text(),'Single')]")));
		
		action.moveToElement(SingleTransferSubMenuOption);
		action.click().build().perform();
		Reporter.log("Clicked on Single Transfer option in SunView");
		
	}
	
	public void clickOnTreasuryDashboard(){
		
		treasuryDashboardMenuOption = d.findElement(By.xpath("//span[contains(text(),'Treasury Dashboard')]"));
		treasuryDashboardMenuOption.click();
	}
	
	public void logoutOfSunView(){
		exitLink = d.findElement(By.id("exitCP"));
		exitLink.click();
	}
	
	
}
