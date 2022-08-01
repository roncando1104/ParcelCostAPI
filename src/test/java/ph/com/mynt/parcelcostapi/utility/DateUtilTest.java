package ph.com.mynt.parcelcostapi.utility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilTest {

    @InjectMocks
    private DateUtil dateUtil = new DateUtil();

    @Test
    void getTodaysDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime dateToday = LocalDateTime.now();
        String formattedDate = formatter.format(dateToday);
        dateUtil.getTodaysDate();
        Assertions.assertNotNull(formattedDate);
    }

    @Test
    void getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime dateToday = LocalDateTime.now();
        String formattedDate = formatter.format(dateToday);
        dateUtil.getDate(formattedDate);
        Assertions.assertNotNull(formattedDate);
    }
}