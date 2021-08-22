package crawling;

import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import conf.Config;

public class Crawling {
	private static Crawling instance = new Crawling();

	private final String startUrl = Config.URL;
	private WebDriver driver; // 1. chrome (at window local test)
	// private FirefoxDriver driver; // 2. firefox (at linux test server)

	private Crawling() {
		// 1. chrome (at window local test)
		System.setProperty("webdriver.chrome.driver", Config.CHROME);
		driver = new ChromeDriver();

		// 2. firefox (at linux test server)
		// System.setProperty("webdriver.gecko.driver", Config.FIREFOX);
		// driver = new FirefoxDriver();

		driver.get(startUrl);
		// 로그인 하고 과목 검색할때까지 대기
		Scanner kb = new Scanner(System.in);
		while (true) {
			System.out.print("로그인, 과목 검색 후 계속하세요. 완료하심? (완료 : Y) : ");
			String in = kb.nextLine();
			if ("Y".equals(in.trim())) {
				break;
			}
		}
	}

	public static Crawling getInstance() {
		return instance;
	}

	public void clickSaveCourse() {

		WebElement table = driver.findElement(By.id("listTable"));
		// 수강 신청 버튼 클릭
		WebElement saveButton = table.findElement(By.name("saveCourse"));
		saveButton.sendKeys(Keys.ENTER);
	}

	public void reset() {
		instance = new Crawling();
	}

	public void close() {
		driver.close();
	}
}
