package ph.com.mynt.parcelcostapi.constants;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class ParcelCostConstantsTest {

    @InjectMocks
    private ParcelCostConstants parcelCostConstants = new ParcelCostConstants();
    @Test
    void testConstants(){
        Assertions.assertNotNull(parcelCostConstants);
    }

}