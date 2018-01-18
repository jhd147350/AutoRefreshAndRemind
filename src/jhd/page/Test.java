package jhd.page;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		RemedyLoginPage loginPage = new RemedyLoginPage(driver);
		loginPage.login("jia.haodong-bl@21vianet.com", "MME6Q7C8");

		RemedyConsolePage consolePage = new RemedyConsolePage(driver);
		PageResult pageResullt = consolePage.hasTicket();
		if(pageResullt.isHasNewTicket()) {
			RemedyTicektDetailPage detailPage=new RemedyTicektDetailPage(driver);
			detailPage.intialResponse(pageResullt.getTitle());
		}

	}

}
