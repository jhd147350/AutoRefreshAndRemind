package jhd.page;

import java.util.Date;
import java.util.Formatter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import jhd.page.base.BasePage;

public class RemedyConsolePage extends BasePage {

	// @FindBy(id = "T302087200")
	// private WebElement dataTable;

	// @FindBys({ @FindBy(id = "T302087200"), @FindBy(tagName = "tbody") })
	// private WebElement tbody;

	@FindBys({ @FindBy(id = "T302087200"), @FindBy(tagName = "tbody"), @FindBy(tagName = "tr") })
	private List<WebElement> trs;

	static Formatter formatter = new Formatter(System.out);

	public RemedyConsolePage(WebDriver webDriver) {
		super(webDriver);
		// PageFactory.initElements(webDriver, this);
	}

	public PageResult hasTicket() {
		// WebElement tbody = dataTable.findElement(By.tagName("tbody"));
		// List<WebElement> trs = tbody.findElements(By.tagName("tr"));

		PageResult pageResult = new PageResult();
		for (int i = 1; i < trs.size(); i++) {// 跳过第一个，第一个是表头
			List<WebElement> tds = trs.get(i).findElements(By.tagName("td"));
			if (tds != null && tds.size() >= 4) {
				String ID = tds.get(0).getText();
				String priority = tds.get(2).getText();
				String title = tds.get(1).getText();
				// 第四列是状态״̬
				String status = tds.get(3).getText();
				formatter.format("%16s %-5s %-5s\n", ID, priority, status);
				if (status.equals("已指派")) {
					// trs.get(i)
				} else {
					trs.get(i).click();
					
					Actions actions = new Actions(webDriver);
					actions.doubleClick(trs.get(i)).perform();
					System.out.println("有受理ticket" + new Date().toString());
					pageResult.setHasNewTicket(true);
					pageResult.setTitle(title);
					return pageResult;
				}
				// System.out.println(status);
			}
		}
		pageResult.setHasNewTicket(false);
		return pageResult;

	}
}
/////*[@id="arid_WIN_1_301395300"]
