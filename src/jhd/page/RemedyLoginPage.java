package jhd.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import jhd.config.Constant;
import jhd.page.base.BasePage;

public class RemedyLoginPage extends BasePage {

	private String URL = "https://chinabluemix.itsm.unisysedge.cn/arsys/shared/login.jsp";

	@FindBy(id = "username-id")
	private WebElement usernameInput;
	@FindBy(id = "pwd-id")
	private WebElement pwdInput;
	@FindBy(id = "loginText")
	private WebElement loginButton;

	public RemedyLoginPage(WebDriver webDriver) {
		super(webDriver);
		//PageFactory.initElements(webDriver, this);
	}

	public void login(String username, String password) {

		this.webDriver.get(this.URL);
		usernameInput.clear();
		usernameInput.sendKeys(username);
		pwdInput.clear();
		pwdInput.sendKeys(password);
		loginButton.click();

	}

}
