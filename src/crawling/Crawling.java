package crawling;

import java.util.List;

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
		try {
			Thread.sleep(500);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// login
		if (!"Facebook".equals(driver.getTitle())) {
			WebElement id = driver.findElement(By.id("userId"));
			WebElement pwd = driver.findElement(By.id("password"));
			id.sendKeys(Config.ID);
			pwd.sendKeys(Config.PWD);
			pwd.sendKeys(Keys.RETURN);
		}
		try {
			Thread.sleep(500);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// open 학사시스템
		driver.get("https://cais.kaist.ac.kr");
		try {
			Thread.sleep(500);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// open 수강신청
		driver.get("https://cais.kaist.ac.kr/courseRegistration");
		try {
			Thread.sleep(500);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 인문사회과학부 선택, 배덕현 교수 입력
		Select s = new Select(driver.findElement(By.id("sel_dept")));
		s.selectByValue("4424");
		driver.findElement(By.id("txt_prof")).sendKeys("배덕현");

		// 조회 버튼 누르기
		driver.findElement(By.id("btn_search")).click();
		try {
			Thread.sleep(500);
		} catch (Exception e) {
			e.printStackTrace();
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
