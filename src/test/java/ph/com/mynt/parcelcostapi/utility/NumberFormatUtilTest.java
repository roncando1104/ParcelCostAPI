package ph.com.mynt.parcelcostapi.utility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;

class NumberFormatUtilTest {

    @InjectMocks
    private NumberFormatUtil numberFormatUtil = new NumberFormatUtil();
    @Test
    void doubleNumberFormatter() {
        String value = numberFormatUtil.doubleNumberFormatter(20);
        Assertions.assertEquals(numberFormatUtil.doubleNumberFormatter(20), value);
    }
}