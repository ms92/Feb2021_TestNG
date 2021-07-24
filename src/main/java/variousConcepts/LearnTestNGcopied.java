package variousConcepts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LearnTestNGcopied {
	static WebDriver driver;
	static String browser;
	static String url;

	@BeforeClass
	public void readConfig() {

		Properties prop = new Properties();

		try {
			// InputStream //BufferedReader //FileReader //Scanner
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			prop.load(input);
			browser = prop.getProperty("browser");
			System.out.println("Browser used:" + browser);
			url = prop.getProperty("url");
			System.out.println("URL used:" + url);

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	@BeforeMethod
	public static void init() {

		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}

		driver.get(url);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();

	}

	@Test(priority=2)
	public void loginTest() {

		// Element Library
		By USERNAME_ELEMENT = By.xpath("// input[@id = 'username']");
		By PASSWORD_ELEMENT = By.xpath("// input[@id ='password']");
		By SIGNIN_ELEMENT = By.xpath("/html/body/div/div/div/form/div[3]/button");
		By DASHBOARD_FIELD_LOCATOR = By.xpath("//h2[text()=' Dashboard ']");
		// Login Data
		String userName = "demo@techfios.com";
		String passWord = "abc123";

		driver.findElement(USERNAME_ELEMENT).sendKeys(userName);
		driver.findElement(PASSWORD_ELEMENT).sendKeys(passWord);
		driver.findElement(SIGNIN_ELEMENT).click();

		Assert.assertEquals(driver.findElement(DASHBOARD_FIELD_LOCATOR).getText(), "Dashboard",
				"Dashboard page not found!");

	}

	@Test(priority=1)
	public void negativeloginTest() {

		// Element Library
		By USERNAME_ELEMENT = By.xpath("// input[@id = 'username']");
		By PASSWORD_ELEMENT = By.xpath("// input[@id ='password1']");
		By SIGNIN_ELEMENT = By.xpath("/html/body/div/div/div/form/div[3]/button");
		By DASHBOARD_FIELD_LOCATOR = By.xpath("//h2[text()=' Dashboard ']");

		// Login Data
		String userName = "demo@techfios.com";
		String passWord = "abc123";

		driver.findElement(USERNAME_ELEMENT).sendKeys(userName);
		driver.findElement(PASSWORD_ELEMENT).sendKeys(passWord);
		driver.findElement(SIGNIN_ELEMENT).click();

	}

	@AfterMethod
	public void tearDown() {
		// closes the window
		driver.close();
		// kills the process we started
		driver.quit();
	}

	@AfterClass
	public void afterClass() {
		System.out.println("After Class");
	}
}
