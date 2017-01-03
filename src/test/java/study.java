import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.ie.InternetExplorerDriver;

//test selnium
public class study {

	public static void main(String[] args){

		//WebDriver driver = new FirefoxDriver();
		File file = new File("C:/pleiades/workspace/test_selenium_gradle/driver/IEDriverServer.exe");

		System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
		InternetExplorerDriver driver = new InternetExplorerDriver();


		driver.get("http://example.selenium.jp/reserveApp/");
        driver.findElement(By.id("guestname")).sendKeys("サンプルユーザ");
        driver.findElement(By.id("goto_next")).click();
	}

}


