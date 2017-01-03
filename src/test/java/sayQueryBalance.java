import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class sayQueryBalance {

  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  ExpectedCondition<WebElement> preferenceOfNewMessage = new ExpectedCondition<WebElement>() {
	public WebElement apply(WebDriver driver) {
	return driver.findElement(By.xpath("//div[@id='msgs_overlay_div']/preceding::span[@class='message_body'][1]"));
	    }
	};

  @Before
  public void setUp() throws Exception {
	System.setProperty("webdriver.ie.driver", "C:/pleiades/workspace/test_selenium_gradle/driver/IEDriverServer.exe");
	driver = new InternetExplorerDriver();
    baseUrl = "https://slack.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testSayQueryBalance() throws Exception {

	//access slack
	driver.get(baseUrl);

	Set<String> beforeHandles = driver.getWindowHandles();
	String beforeHandle = driver.getWindowHandle();

	//click team link
	driver.findElement(By.xpath("//ts-icon")).click();
	driver.manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS);

	//After click my team icon, new windows is opened in windows11. close base window and switch new one.
	Thread.sleep(10 * 1000);
	Set<String> afterHandles = driver.getWindowHandles();
	String afterHandle = getNewHandle(beforeHandles,afterHandles);
	driver.close();
	driver.switchTo().window(afterHandle);

	//window size maxmize
    driver.manage().window().maximize();

	//click bot channel
	driver.findElement(By.cssSelector("li.channel_C3JCT4E9K.channel  > a.channel_name > span.display_flex > span.overflow_ellipsis")).click();

    driver.findElement(By.id("msg_form")).sendKeys("@adam_bot2 query balance");
    driver.findElement(By.id("msg_form")).sendKeys(Keys.ENTER);

    WebElement element =  driver.findElement(By.xpath("//div[@id='msgs_overlay_div']/preceding::span[@class='message_body'][1]"));
    String message = element.getText().replace("\n", "");;
    String collectMessage = "Whose balance do you want to know ?Please tell me person name.If you want to quit, say quit.";
    try{
    	assertEquals(collectMessage, message);
    }catch(Error e){
    	verificationErrors.append(e.toString());
    }

    driver.findElement(By.id("msg_form")).sendKeys("test");
    driver.findElement(By.id("msg_form")).sendKeys(Keys.ENTER);

    element =  driver.findElement(By.xpath("//div[@id='msgs_overlay_div']/preceding::span[@class='message_body'][1]"));
    message = element.getText().replace("\n", "");
    String collectMessage2 = "personName:testbalance:10000";
    try{
    	assertEquals(collectMessage2, message);
    }catch(Error e){
    	verificationErrors.append(e.toString());
    }

    driver.close();

  }

  public String getNewHandle(Collection<String> beforeHandle, Collection<String> newHandle) {

	  ArrayList <String> handles = new ArrayList<String>(newHandle);
	  handles.removeAll(beforeHandle);

	  return handles.get(0);

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