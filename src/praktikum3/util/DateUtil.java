package praktikum3.util;

import java.sql.Date;
import java.time.LocalDate;

public class DateUtil {

    public static Date parse(String dateAsString) {

        String[] splittedDate;
        LocalDate date = null;

        int year = 0;
        int day = 0;
        int month = 0;

        splittedDate = dateAsString.split("-");

        try {
            year = Integer.parseInt(splittedDate[0]);
            month = Integer.parseInt(splittedDate[1]);
            day = Integer.parseInt(splittedDate[2]);

            date = LocalDate.of(year, month, day);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (isValidDate(year, month, day)) {
            return Date.valueOf(date);
        } else {
            throw new RuntimeException("Invalid date");
        }

    }

    private static boolean isValidDate(int year, int month, int day) {
        if (year >= 1000 && year <= 9999) {
            if (month >= 1 && month <= 12) {
                return day >= 1 && day <= 31;
            }
        }
        return false;
    }
}
