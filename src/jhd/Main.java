package jhd;

import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.bcel.generic.NEW;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import jhd.config.Constant;

public class Main {

	private static WebDriver driver;
	private static String baseUrl;
	private static Mp3Player player = new Mp3Player();
	static Formatter formatter = new Formatter(System.out);
	/*
	 * static { Constant.readProperties(); }
	 */

	public static void main(String[] args) {

		// 初始化密码等信息
		Constant.readProperties();

		prepare();

		Runtime runtime = Runtime.getRuntime();
		while (true) {
			try {
				runtime.exec("cmd cls");
				loopRemedy();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(new Date().toString());
				ErrOutput.err("循环出错");
				player.play();
			}
		}

	}

	/*public static void start() {

		// System.setProperty("webdriver.firefox.bin",
		// "D:\\software\\firefox\\firefox.exe");
		// System.setProperty("webdriver.firefox.marionette","D:\\download\\360dl\\geckodriver-v0.11.1-win64\\geckodriver.exe");
		System.setProperty("webdriver.chrome.driver", "D:\\download\\360dl\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://www.baidu.com");
		driver.findElement(By.id("kw")).sendKeys("selenium java");
		driver.findElement(By.id("su")).click();
		driver.quit();// quit之后会关闭chromedriver.exe
	}
*/
	public static void prepare() {
		// driver = new FirefoxDriver();
		driver = new ChromeDriver();
		baseUrl = "https://chinabluemix.itsm.unisysedge.cn";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.get(baseUrl + "/arsys/shared/loggedout.jsp");

		// login
		driver.findElement(By.id("username-id")).clear();
		driver.findElement(By.id("username-id")).sendKeys(Constant.USERNAME);
		driver.findElement(By.id("pwd-id")).clear();
		driver.findElement(By.id("pwd-id")).sendKeys(Constant.PASSWORD);
		driver.findElement(By.id("loginText")).click();
	}

	/**
	 * 循环判断是否有未受理ticket出现 时间间隔可自定义 有任何异常 直接抛出处理，且证明监听遇到问题
	 * 
	 * @throws Exception
	 */
	public static void loopRemedy() throws Exception {

		Thread.sleep(40000l);

		
		/*
		 * /try { Thread.sleep(3000l); } catch (InterruptedException e1) { //
		 */
		//// driver.findElement(By.cssSelector("div.btntextdiv")).click();
		// 点击打开按钮
		driver.findElement(By.xpath("//a[@id='WIN_1_304016900']/div/div")).click();
		// a[@id='WIN_1_304017100']/div/div
		// 点击未确认按钮
		//driver.findElement(By.xpath("//a[@id='WIN_1_304017100']/div/div")).click();

		WebElement table = driver.findElement(By.id("T302087200"));
		// System.out.println(findElement.getText());
		System.out.println("------------------------------------");

		WebElement tbody = table.findElement(By.tagName("tbody"));
		List<WebElement> trs = tbody.findElements(By.tagName("tr"));
		int unACK = 0;
		for (int i = 1; i < trs.size(); i++) {// 跳过第一个，第一个是表头
			List<WebElement> tds = trs.get(i).findElements(By.tagName("td"));
			if (tds != null && tds.size() >= 4) {
				String ID = tds.get(0).getText();
				String priority = tds.get(2).getText();
				// 第四列是状态״̬
				String status = tds.get(3).getText();
				formatter.format("%16s %-5s %-5s\n", ID, priority, status);
				if (status.equals("已指派")) {
					unACK++;
					System.out.println("有未受理ticket"+new Date().toString());
					player.play();
				}
				// System.out.println(status);
			}
		}
		System.out.println(new Date().toString()+" UnACK:" + unACK);
	}

}
