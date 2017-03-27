package jhd;

import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import jhd.config.Constant;

/**
 * 
 * @author jia.haodong v1.2
 */
public class Main {

	private static WebDriver driver;
	private static String baseUrl;
	private static Mp3Player player = new Mp3Player();
	private static Mp3Player player2 = new Mp3Player("SuperMarioBros.mp3");
	static Formatter formatter = new Formatter(System.out);
	/*
	 * static { Constant.readProperties(); }
	 */

	public static void main(String[] args) {

		// 初始化密码等信息
		Constant.readProperties();

		prepare();
		
		//最大化浏览器
		driver.manage().window().maximize();

		while (true) {
			try {

				// http://www.th7.cn/system/win/201610/186464.shtml
				// new ProcessBuilder("cmd", "cls").inheritIO().start();
				loopRemedy();
			} /*
				 * catch (StaleElementReferenceException e) { //break;
				 * System.out.println("StaleElementReferenceException");
				 * 
				 * }
				 */
			catch (Exception e) {	
				e.printStackTrace();
				System.out.println(new Date().toString());
				ErrOutput.err("循环出错");
				if (e instanceof StaleElementReferenceException) {
					// 有時候,会出现这种异常，据说是因为浏览器刷新导致的，remedy系统会定时刷新，
					// 可能在获取内容时，浏览器正好刷新了，所以导致该异常抛出
					ErrOutput.err("StaleElementReference");
				}
				if (e instanceof NoSuchElementException) {
					// 有時候,会出现这种异常，据说是因为浏览器刷新导致的，remedy系统会定时刷新，
					// 可能在获取内容时，浏览器正好刷新了，所以导致该异常抛出
					ErrOutput.err("NoSuchElement");
				}
				player2.play();
			}

		}

	}

	/*
	 * public static void start() {
	 * 
	 * // System.setProperty("webdriver.firefox.bin", //
	 * "D:\\software\\firefox\\firefox.exe"); //
	 * System.setProperty("webdriver.firefox.marionette",
	 * "D:\\download\\360dl\\geckodriver-v0.11.1-win64\\geckodriver.exe");
	 * System.setProperty("webdriver.chrome.driver",
	 * "D:\\download\\360dl\\chromedriver_win32\\chromedriver.exe"); WebDriver
	 * driver = new ChromeDriver(); driver.get("http://www.baidu.com");
	 * driver.findElement(By.id("kw")).sendKeys("selenium java");
	 * driver.findElement(By.id("su")).click(); driver.quit();//
	 * quit之后会关闭chromedriver.exe }
	 */
	public static void prepare() {
		// driver = new FirefoxDriver();
		try{
			driver = new ChromeDriver();
		}
		catch (IllegalStateException e) {
			ErrOutput.err("请设置google chrome驱动环境变量，并且确保驱动版本和您的chrome版本一致");
			// TODO: handle exception
			System.exit(0);
		}
		
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
		Thread.sleep(60000l);
		//采取刷新整个页面的方式
		driver.navigate().refresh();
		/*
		 * /try { Thread.sleep(3000l); } catch (InterruptedException e1) { //
		 */
		//// driver.findElement(By.cssSelector("div.btntextdiv")).click();
		// 点击打开按钮
		//driver.findElement(By.xpath("//a[@id='WIN_1_304016900']/div/div")).click();

		// wait for 3s, ensure the data is exist
		//Thread.sleep(3000l);
		// a[@id='WIN_1_304017100']/div/div
		// 点击未确认按钮
		// driver.findElement(By.xpath("//a[@id='WIN_1_304017100']/div/div")).click();

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
					System.out.println("有未受理ticket" + new Date().toString());
					player.play();
				}
				// System.out.println(status);
			}
		}
		System.out.println(new Date().toString() + " UnACK:" + unACK);
	}

}
