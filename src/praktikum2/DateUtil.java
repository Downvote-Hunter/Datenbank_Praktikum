package praktikum2;

import java.sql.Date;
import java.time.LocalDate;

public class DateUtil {

	public static Date parse(String dateAsString) {

		String[] splittedDate = dateAsString.split("-");

		int year = Integer.parseInt(splittedDate[0]);
		int month = Integer.parseInt(splittedDate[1]);
		int day = Integer.parseInt(splittedDate[2]);

		
		LocalDate date = LocalDate.of(year, month, day);
		Date.valueOf(date);


		return Date.valueOf(date);

	}

}
