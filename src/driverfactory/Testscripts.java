package driverfactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import applicationlayer.AdminPage;
import applicationlayer.Employeepage;
import applicationlayer.Logoutpage;
import applicationlayer.Userpage;
public class Testscripts {
		WebDriver driver;
	ExtentReports report;
	ExtentTest test;
	@BeforeTest
	public void generatereport()
	{
	report=new ExtentReports("./Reports/Pom.html");
	}
	@BeforeMethod
	public void setUp()throws Throwable
	{
	System.setProperty("webdriver.chrome.driver", "D:\\TESTING\\11oClockSelenium\\Selenium_Frameworks\\Drivers\\chromedriver.exe");
	driver=new ChromeDriver();
	driver.get("http://orangehrm.qedgetech.com/");
	driver.manage().window().maximize();
	//call page class
	AdminPage login=PageFactory.initElements(driver, AdminPage.class);
	login.verifyLogin("Admin", "Qedge123!@#");
	}
	@Test(priority=0)
	public void userCreation()throws Throwable
	{
		test=report.startTest("User Creation");
		Userpage user=PageFactory.initElements(driver, Userpage.class);
		user.verifyAddUser("Aarya Santhosh", "Ritameta", "Akhilesh@800", "Akhilesh@800");
		if(driver.getCurrentUrl().contains("viewSystemUsers"))
		{
			Reporter.log("User Created Success",true);
			test.log(LogStatus.PASS, "User Created Success");
		}
		else
		{
			Reporter.log("User Created Fail",true);	
			test.log(LogStatus.FAIL, "User Created Fail");
		}
	}
	@Test(priority=1)
	public void empCreation()throws Throwable
	{
		test=report.startTest("Employee Creation");
	Employeepage emp=PageFactory.initElements(driver, Employeepage.class);
	emp.verifyAddEmp("Selenium", "Qtp", "8765432");
	if(driver.getCurrentUrl().contains("empNumber"))
	{
		Reporter.log("Emp Created Success",true);
		test.log(LogStatus.PASS, "Emp Created Success");
	}
	else
	{
		Reporter.log("Emp Created Fail",true);
		test.log(LogStatus.FAIL, "Emp Created Fail");	
	}
	}
	@AfterMethod
	public void tearDown()throws Throwable
	{
		report.endTest(test);
		report.flush();
		Logoutpage logout=PageFactory.initElements(driver, Logoutpage.class);
		logout.verifyLogout();
		driver.close();
	}
	}


