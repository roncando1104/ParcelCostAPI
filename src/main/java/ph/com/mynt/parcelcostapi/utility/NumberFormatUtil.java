package ph.com.mynt.parcelcostapi.utility;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

@Component
public class NumberFormatUtil {

    public String doubleNumberFormatter(double value) {
        DecimalFormat df = new DecimalFormat("###,###,###.00");
        return df.format(value);
    }
}
