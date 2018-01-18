package jhd.page;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Test01 {
	 private WebDriver driver;
	  private String baseUrl;
	  private boolean acceptNextAlert = true;
	  private StringBuffer verificationErrors = new StringBuffer();

	  @Before
	  public void setUp() throws Exception {
	    driver = new FirefoxDriver();
	    baseUrl = "https://www.katalon.com/";
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  }

	  @Test
	  public void testUntitledTestCase() throws Exception {
	    driver.get("https://chinabluemix.itsm.unisysedge.cn/arsys/shared/login.jsp");
	    driver.findElement(By.id("username-id")).click();
	    driver.findElement(By.id("username-id")).clear();
	    driver.findElement(By.id("username-id")).sendKeys("jia.haodong-bl@21vianet.com");
	    driver.findElement(By.id("pwd-id")).click();
	    driver.findElement(By.id("pwd-id")).clear();
	    driver.findElement(By.id("pwd-id")).sendKeys("MME6Q7C8");
	    driver.findElement(By.id("loginText")).click();
	    // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | win_ser_1 | ]]
	    driver.close();
	    // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | win_ser_local | ]]
	    driver.findElement(By.xpath("//table[@id='T302087200']/tbody/tr[2]/td[2]/nobr/span")).click();
	    driver.findElement(By.xpath("//table[@id='T302087200']/tbody/tr[2]/td[2]/nobr/span")).click();
	    // ERROR: Caught exception [ERROR: Unsupported command [doubleClick | //table[@id='T302087200']/tbody/tr[2]/td[2]/nobr/span | ]]
	    driver.findElement(By.cssSelector("a.btn.btn3d.expand > img.btnimg")).click();
	    driver.findElement(By.id("editor")).click();
	    driver.findElement(By.id("ardivpcancel")).click();
	    driver.findElement(By.cssSelector("#WIN_2_536870940 > a.btn.btn3d.expand > img.btnimg")).click();
	    driver.findElement(By.id("editor")).click();
	    driver.findElement(By.id("editor")).click();
	    driver.findElement(By.id("ardivpcancel")).click();
	    driver.findElement(By.id("arid_WIN_2_303530000")).click();
	    driver.findElement(By.id("arid_WIN_2_303530000")).click();
	  }

	  @After
	  public void tearDown() throws Exception {
	    driver.quit();
	    String verificationErrorString = verificationErrors.toString();
	    if (!"".equals(verificationErrorString)) {
	      fail(verificationErrorString);
	    }
	  }

	  private boolean isElementPresent(By by) {
	    try {
	      driver.findElement(by);
	      return true;
	    } catch (NoSuchElementException e) {
	      return false;
	    }
	  }

	  private boolean isAlertPresent() {
	    try {
	      driver.switchTo().alert();
	      return true;
	    } catch (NoAlertPresentException e) {
	      return false;
	    }
	  }

	  private String closeAlertAndGetItsText() {
	    try {
	      Alert alert = driver.switchTo().alert();
	      String alertText = alert.getText();
	      if (acceptNextAlert) {
	        alert.accept();
	      } else {
	        alert.dismiss();
	      }
	      return alertText;
	    } finally {
	      acceptNextAlert = true;
	    }
	  }
}
