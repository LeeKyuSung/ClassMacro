package task;

import java.text.SimpleDateFormat;
import java.util.Date;

import crawling.Crawling;

public class Task {

	public static void main(String[] args) {
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		String date = dayTime.format(new Date());
		System.out.println("Task Start : " + date);

		Crawling crawling = Crawling.getInstance();

		// 수강신청 버튼 계속해서 클릭
		while (true) {
			try {
				crawling.clickSaveCourse();
				Thread.sleep(500);
			} catch (Exception e) {
				System.out.println("Something Wrong : " + e.getMessage());
				e.printStackTrace();
			}

		}
	}
}
