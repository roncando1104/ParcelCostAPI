package ph.com.mynt.parcelcostapi.utility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.AssertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class VoucherExpiryUtilityTest {

    @InjectMocks
    private VoucherExpiryUtility voucherExpiryUtility;
    @Mock
    private LocalDate localDate;
    @InjectMocks
    private DateUtil dateUtil = new DateUtil();

    @BeforeEach
    public void setUp(){
        voucherExpiryUtility = new VoucherExpiryUtility(dateUtil);
    }
    @Test
    void isPromoExpired() {
        dateUtil.getTodaysDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime dateToday = LocalDateTime.now();
        String formattedDate = formatter.format(dateToday);
       boolean isExpired = voucherExpiryUtility.isPromoExpired(formattedDate);
       Assertions.assertFalse(isExpired);
    }

    @Test
    void isPromoNotExpired() {
        dateUtil.getTodaysDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime dateToday = LocalDateTime.now().minusDays(5);
        String formattedDate = formatter.format(dateToday);
        boolean isExpired = voucherExpiryUtility.isPromoExpired(formattedDate);
        Assertions.assertTrue(isExpired);
    }
}