package ph.com.mynt.parcelcostapi.utility;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateUtil {
    public LocalDate getTodaysDate() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime dateToday = LocalDateTime.now();
        String formattedDate = formatter.format(dateToday);

        return LocalDate.parse(formattedDate, formatter);
    }

    public LocalDate getDate(String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate dateStr = LocalDate.parse(date, formatter);
        return LocalDate.parse(dateStr.toString(), formatter);
    }
}
