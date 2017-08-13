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
 * @author jia.haodong v1.5
 */
public class Main {
	// 刷新间隔75秒
	private static int TIME_INT = 75;
	// private static long TIME = 75000l;

	private static WebDriver driver;
	private static String baseUrl;
	private static Mp3Player player = new Mp3Player();
	private static Mp3Player player2 = new Mp3Player("SuperMarioBros.mp3");
	static Formatter formatter = new Formatter(System.out);

	/**
	 * 每次程序执行完先关闭chromedriver 保证程序运行几次始终都会有一个chromedriver在运行 节省内存消耗
	 */
	public static void closeChromeDriverByCmd() {
		Runtime runtime = Runtime.getRuntime();
		try {
			runtime.exec("cmd /c taskkill /im chromedriver.exe /F");
		} catch (Exception e) {
			System.out.println("Error when close chromedriver!");
		}
	}

	public static void main(String[] args) {

		// 初始化密码等信息
		Constant.readProperties();
		// 每次先关掉已打开的chromedriver.exe
		closeChromeDriverByCmd();

		prepare();

		// 最大化浏览器
		driver.manage().window().maximize();

		while (true) {
			try {
				countdown();
				Pending temp = new Pending();
				temp.start();
				// 将pending对象传进去，由里面去控制何时停止刷新提示
				loopRemedy(temp);
			} catch (Exception e) {
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

	public static void prepare() {
		// driver = new FirefoxDriver();
		try {
			driver = new ChromeDriver();
		} catch (IllegalStateException e) {
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
	 * @param Pending
	 *            temp 控制停止刷新
	 * @throws Exception
	 */
	public static void loopRemedy(Pending temp) throws Exception {

		// 采取刷新整个页面的方式
		driver.navigate().refresh();

		WebElement table = driver.findElement(By.id("T302087200"));
		// System.out.println(findElement.getText());

		WebElement tbody = table.findElement(By.tagName("tbody"));
		List<WebElement> trs = tbody.findElements(By.tagName("tr"));
		int unACK = 0;

		// 停止正在刷新的提示
		temp.stop();

		System.out.println("\n------------------------------------");
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
		System.out.println(new Date().toString() + " UnACK:" + unACK + "\n");
	}

	// 倒计时
	static public void countdown() throws InterruptedException {
		// 25*3 75s
		// =========================
		// ---
		// 进度总数
		int SUM = TIME_INT / 3;
		boolean isFirst = true;
		int N = 3;

		// 倒计时次数
		int count = TIME_INT;

		while (count > 0) {
			count--;
			if (!isFirst) {
				System.out.print('\r');
			}
			isFirst = false;
			// 将 倒计时除3得到 进度
			int realCount = count / N;
			TimeUnit.SECONDS.sleep(1);
			System.out.print("|");
			for (int i = 0; i < (SUM - realCount); i++) {
				System.out.print("=");
			}
			for (int i = 0; i < (realCount); i++) {
				System.out.print("-");
			}
			if (count < 10) {
				System.out.print("| 0" + count);
			} else {
				System.out.print("| " + count);
			}
			System.out.print("s");
		}
		System.out.print("\n");
	}

}

class Pending {

	private boolean RUN = true;

	private Thread d = new Thread() {
		public void run() {
			try {
				pending();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	};

	private void pending() throws InterruptedException {

		// 01234321 01234321
		String[] anim = { "^        ", " ^      ", "  ^    ", "   ^  ", "    ^" };
		int[] rule = { 0, 1, 2, 3, 4, 3, 2, 1 };
		int i = 0;
		while (RUN) {
			System.out.print('\r');
			System.out.print("Refreshing:  " + anim[rule[i % 8]]);
			i++;
			TimeUnit.MILLISECONDS.sleep(100l);
		}
	}

	public void start() {
		if (d != null) {
			d.start();
		}
	}

	public void stop() {
		RUN = false;
	}
}
