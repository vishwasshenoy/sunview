package exceptionPackage;

import org.testng.Reporter;

public class AccountTransferException extends Exception {

	private static final long serialVersionUID = -9056269649239570547L;
	
	public AccountTransferException(String msg){
		Reporter.log(msg);
	}

}
