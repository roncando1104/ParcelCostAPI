package ph.com.mynt.parcelcostapi.model;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class VolumePriceRuleTest {


    @Test
    void getHeight() {
        VolumePriceRule volumePriceRule = new VolumePriceRule();
        volumePriceRule.setVolume(20);
        volumePriceRule.setHeight(1);
        volumePriceRule.setWidth(2);
        volumePriceRule.setLength(4);
        volumePriceRule.setPrice(20);
        volumePriceRule.setVoucher("MYNT");

        assertNotNull(volumePriceRule);
//        assertNotNull(volumePriceRule.getHeight());
//        assertNotNull(volumePriceRule.getWidth());
//        assertNotNull(volumePriceRule.getLength());
//        assertNotNull(volumePriceRule.getPrice());
//        assertNotNull(volumePriceRule.getVoucher());

    }


}