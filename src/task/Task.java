package task;

import java.text.SimpleDateFormat;
import java.util.Date;

import crawling.Crawling;

public class Task {

	public static void main(String[] args) {
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		String date = dayTime.format(new Date());
		System.out.println("Task Start : " + date);

		try {
			while (true) {
				boolean flag;
				try {
					flag = Crawling.getInstance().checkAndSaveCourse();
				} catch (Exception e){
					System.out.println("Something wrong : " + e.getMessage());
					e.printStackTrace();
					Crawling.getInstance().close();
					Crawling.getInstance().reset();
					flag = Crawling.getInstance().checkAndSaveCourse();
				}
				if (flag) { // 수강 성공
					// 수강 신청
					System.out.println("SUCCESS!!!!!!!!!!!!!!!!!!!!!!!!");
					System.out.println(dayTime.format(new Date()));
					Crawling.getInstance().close();
					return;
				} else { // 인원 꽉참 or 수강 실패
					// 1초 쉬고 다음 루프
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Task error : " + e.getMessage());
			System.out.println(dayTime.format(new Date()));
			e.printStackTrace();
		}

	}
}
