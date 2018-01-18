package jhd.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import jhd.page.base.BasePage;

public class RemedyTicektDetailPage extends BasePage {

	@FindBy(id = "arid_WIN_2_303530000")
	private WebElement customerNameTextarea;

	// #WIN_2_1000000000 > a
	@FindBy(id = "arid_WIN_2_536870940")
	private WebElement intialResponseTextarea;

	public RemedyTicektDetailPage(WebDriver webDriver) {
		super(webDriver);
	}

	public void intialResponse(String title) {

		System.out.println("name: " + customerNameTextarea.getAttribute("class"));
		System.out.println("name: " + customerNameTextarea.getAttribute("wrap"));
		System.out.println("name: " + customerNameTextarea.getAttribute("id"));
		System.out.println("name: " + customerNameTextarea.getAttribute("style"));
		System.out.println("name: " + customerNameTextarea.getAttribute("title"));
		System.out.println(customerNameTextarea.getTagName());

		System.out.println("title: " + title);
		// *[@id="editor"]
	}

}
/*
 * Dear Biswas Suvoraj,
 * 
 * Thank you for contacting the Internet-of-Things support team. We have
 * received your problem about 'KONE: Around 112 devices were disconnected due
 * to some issues at Watsn IoT side'. We will inform you as soon as there is any
 * progress. If you have any other detailed logs or information, please be free
 * to send to us. Thanks.
 * 
 * Best regards!
 */

/*
 * <textarea class="text sr " wrap="off" id="arid_WIN_2_303530000" cols="20"
 * maxlen="255"
 * style="top: 0px; left: 116px; width: 159px; height: 19px; background-color: rgb(204, 204, 204);"
 * armenu="$NULL$" mstyle="2" arautoc="1" arautocmb="1" arautocak="1"
 * arautoctt="400" rows="1" readonly="" title="贾, 豪东 "></textarea>
 */
