package ph.com.mynt.parcelcostapi.service;

import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ph.com.mynt.parcelcostapi.controller.VoucherController;
import ph.com.mynt.parcelcostapi.utility.VoucherExpiryUtility;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@ExtendWith(SpringExtension.class)
class ComputeCostServiceTest {

    @InjectMocks
    private ComputeCostService computeCostService;
    @Mock
    private VoucherController voucherController;
    @Mock
    private VoucherExpiryUtility voucherExpiryUtility;
    @Mock
    private LoggingService loggingService;
    @Mock
    private VoucherLookUpService voucherLookUpService;

    @BeforeEach
    void setUp() throws JSONException {
        MockitoAnnotations.initMocks(this);
        computeCostService = new ComputeCostService(voucherController, voucherExpiryUtility);
        voucherController = new VoucherController(loggingService, voucherLookUpService,"MYNT", "GFI", "url/to/mynt", "url/to/gfi");
    }

    @Test
    void testGetTotalCostForWeightParcel() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        double cost = computeCostService.getTotalCostForParcel(5,5);
        Assertions.assertEquals(25, cost);
    }

    @Test
    void testGetTotalCostForWeightParcel2() {
        double cost = computeCostService.getTotalCostForParcel(2, 4, 2, 5);
        Assertions.assertEquals(80, cost);
    }

    @Test
    void getTotalVolume() {
        int volume = computeCostService.getTotalVolume(2,2,2);
        Assertions.assertEquals(8, volume);
    }
}