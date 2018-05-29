package loginTest;

import login.loginAPI;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;
import static org.testng.Assert.assertTrue;

/**
 * Test Class for login API
 */
@Listeners(core.customListener.class)
public class loginAPITest{
    loginAPI login;
    Map<String, String> params = new HashMap<String,String>();

    @BeforeClass
    public void Setup(){
        login = new loginAPI();
    }

    @Test(timeOut = 2000)
    public void validUserNamePasswordTest() {
        params.put("userName", "automation_assignment");
        params.put("password", "Automation1234$");

        login.parseSuccess(login.loginAPIPostRequest(params));
        login.verifyLoginSuccessful();
    }

    @Test(timeOut = 2000)
    public void validateLoginAPISchemaTest() {
        params.put("userName", "automation_assignment");
        params.put("password", "Automation1234$");

        login.validateAPIPostRequestScemaValidation(params);
    }

    @Test(timeOut = 2000)
    public void noUserNameNoPasswordTest() {
        login.parseError(login.loginAPIPostRequest(new HashMap<String,String>()));
        login.verifyLoginUnsuccessful();
    }

    @Test(timeOut = 2000)
    public void noUserNameValidPasswordTest() {
        params.put("password", "Automation1234$");
        login.parseError(login.loginAPIPostRequest(params));

        login.verifyLoginUnsuccessful();
    }

    @Test(timeOut = 2000)
    public void validUserNameNoPasswordTest() {
        params.put("userName", "automation_assignment");
        login.parseError(login.loginAPIPostRequest(params));

        login.verifyLoginUnsuccessful();
    }

    @Test(timeOut = 2000)
    public void longUserNameValidPasswordTest() {
        params.put("userName", "automation_assignmentautomation_assignmentautomation_assignmentautomation_assignment" +
                "automation_assignmentautomation_assignmentautomation_assignmentautomation_assignmentautomation_assignment" +
                "automation_assignmentautomation_assignmentautomation_assignmentautomation_assignmentautomation_assignment");
        params.put("password", "Automation1234$");
        login.parseError(login.loginAPIPostRequest(params));

        login.verifyLoginUnsuccessful();
    }

    @Test(timeOut = 2000)
    public void specialCharUserNameValidPasswordTest() {
        params.put("userName", "automation_assignment@gmail.com");
        params.put("password", "Automation1234$");

        login.parseError(login.loginAPIPostRequest(params));
        login.verifyLoginUnsuccessful();
    }

    @Test(timeOut = 60000)
    public void responseTimeTest() {
        long totalResponseTime = 0;
        long tempResponseTime = 0;
        params.put("userName", "automation_assignment");
        params.put("password", "Automation1234$");

        for(int i=0;i<5;i++) {
            login.parseSuccess(login.loginAPIPostRequest(params));
            tempResponseTime = login.getResponseTime();
            totalResponseTime += tempResponseTime;
        }
        if(totalResponseTime/5 < 1000)
            assertTrue(true,"Average response times for 5 calls is under acceptable value of 1000ms");
        else
            assertTrue(false,"Average response times for 5 calls is under acceptable value of 1000ms");
    }
}
