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
	}

	public static Crawling getInstance() {
		return instance;
	}

	public boolean checkAndSaveCourse() {
		// true : 수강 가능 & 수강 성공
		// false : 꽉참 & 수강 실패

		// 조회 버튼 누르기
		driver.findElement(By.id("btn_search")).click();
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		WebElement table = driver.findElement(By.id("listTable"));
		List<WebElement> tmp = table.findElements(By.cssSelector("*"));
		String max = tmp.get(34).getText();
		String now = tmp.get(35).getText();
		System.out.println("[" + max + "]/[" + now + "]");

		if (Integer.parseInt(max.trim()) > Integer.parseInt(now.trim())) {
			// 수강 가능
			WebElement saveButton = table.findElement(By.name("saveCourse"));
			// 5번 수강신청 클릭
			for (int i = 0; i < 5; i++) {
				saveButton.sendKeys(Keys.ENTER);

				// 수강신청 성공여부 체크
				String courseListStr = driver.findElement(By.id("courseList")).getText();
				if (courseListStr.contains("법학개론"))
					return true;

				try {
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return false;
		} else {
			// 꽉참
			return false;
		}
	}

	public void close() {
		driver.close();
	}
}
