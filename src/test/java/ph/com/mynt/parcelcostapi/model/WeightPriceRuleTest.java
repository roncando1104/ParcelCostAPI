package ph.com.mynt.parcelcostapi.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class WeightPriceRuleTest {

    @Test
    void testWeightPriceRule(){
        WeightPriceRule weightPriceRule = new WeightPriceRule();
        weightPriceRule.setWeight(20);
        weightPriceRule.setPrice(10);
        weightPriceRule.setVoucher("GFI");

        assertNotNull(weightPriceRule);
    }
}