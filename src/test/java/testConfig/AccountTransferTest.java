package testConfig;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith (Cucumber.class)
@CucumberOptions (
		features = "src/test/java/Features",
		glue = "stepDefinition",
		plugin = {
				"pretty",
				"html:target/cucmber",
		}	
		)
public class AccountTransferTest {

}
