package ph.com.mynt.parcelcostapi.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class VoucherExpiryUtility {

    private DateUtil dateUtil;

    @Autowired
    public VoucherExpiryUtility(DateUtil dateUtil) {
        this.dateUtil = dateUtil;
    }

    public boolean isPromoExpired(String expiry) {
        boolean isExpired;
        LocalDate dateToday = dateUtil.getTodaysDate();
        LocalDate voucherPromoDate = dateUtil.getDate(expiry);

            if (dateToday.isAfter(voucherPromoDate)) {
                isExpired = true;
            } else {
                isExpired = false;
            }
        return isExpired;
    }
}
