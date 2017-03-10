package sunView5x;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class POMSunViewLoginPage {
	
	WebDriver d;
	WebElement companyIDTxt, userIdTxt, passwordTxt, signOnBtn;
	
	public POMSunViewLoginPage(WebDriver driver){
		this.d = driver;
	}
	
	public void loginToSunView(String coID,String uID,String pw){
		companyIDTxt = d.findElement(By.id("company1"));
		userIdTxt = d.findElement(By.id("uid1"));
		passwordTxt = d.findElement(By.id("password1"));
		signOnBtn = d.findElement(By.xpath("//input[@id = 'input2649'][@class = 'formBtnText']"));
		
		companyIDTxt.sendKeys(coID);
		userIdTxt.sendKeys(uID);
		passwordTxt.sendKeys(pw);
		
		//clicking on signON button is not working in Chrome driver
		passwordTxt.sendKeys(Keys.ENTER);
		
		
	}

}
