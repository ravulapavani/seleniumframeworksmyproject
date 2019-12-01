package driverfactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import applicationlayer.AdminPage;
import applicationlayer.Employeepage;
import applicationlayer.Logoutpage;
import utilities.ExcelFileUtil;
public class Pomdatadriven {
	WebDriver driver;
	ExtentReports report;
	ExtentTest test;
	String inputpath="D:\\TESTING\\11oClockSelenium\\Selenium_Frameworks\\Test Input\\EmpCreation.xlsx";
	String outputpath="D:\\TESTING\\11oClockSelenium\\Selenium_Frameworks\\Test output\\empresults.xlsx";
	@BeforeTest
	public void setUp()throws Throwable
	{
		report=new ExtentReports("./Reports/Pomdatadriven.html");
		System.setProperty("webdriver.chrome.driver", "D:\\TESTING\\11oClockSelenium\\Selenium_Frameworks\\Drivers\\chromedriver.exe");
		driver=new ChromeDriver();
		driver.get("http://orangehrm.qedgetech.com/");
		driver.manage().window().maximize();
		AdminPage login=PageFactory.initElements(driver, AdminPage.class);
		login.verifyLogin("Admin", "Qedge123!@#");
	}
	@Test
	public void Verifyempcreation()throws Throwable
	{
		Employeepage emp=PageFactory.initElements(driver, Employeepage.class);
		ExcelFileUtil xl=new ExcelFileUtil(inputpath);
		int rc=xl.rowCount("Emp");
		Reporter.log("no of rows are::"+rc,true);
		for(int i=1;i<=rc;i++)
		{
			test=report.startTest("Emp Creation");
			String fname=xl.getCellData("Emp", i, 0);
			String lname=xl.getCellData("Emp", i, 1);
			String eid=xl.getCellData("Emp", i, 2);
			emp.verifyAddEmp(fname, lname, eid);
			if(driver.getCurrentUrl().contains("empNumber"))
			{
				xl.setCellData("Emp", i, 3, "Pass", outputpath);		
				Reporter.log("Emp Created Success",true);
				test.log(LogStatus.PASS, "Emp Created Success");
			}
			else
			{
				xl.setCellData("Emp", i, 3, "Fail", outputpath);
				Reporter.log("Emp Created Fail",true);
				test.log(LogStatus.FAIL, "Emp Created Fail");	
			}	
			report.endTest(test);
			report.flush();
		}
	}
	@AfterTest
	public void tearDown()throws Throwable
	{
		Logoutpage logout=PageFactory.initElements(driver, Logoutpage.class);
		logout.verifyLogout();
		driver.close();
	}
}


