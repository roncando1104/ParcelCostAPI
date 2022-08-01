package ph.com.mynt.parcelcostapi.controller;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ph.com.mynt.parcelcostapi.ParcelCostApplication;
import ph.com.mynt.parcelcostapi.service.GenerateUUIDService;
import ph.com.mynt.parcelcostapi.service.LoggingService;
import ph.com.mynt.parcelcostapi.service.VoucherLookUpService;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(properties = "spring.profiles.active=dev")
@ContextConfiguration(classes = {ParcelCostApplication.class})
class VoucherControllerTest {

    @InjectMocks
    private VoucherController voucherController;
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private LoggingService loggingService;
    @Mock
    private VoucherLookUpService voucherLookUpService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        voucherController = new VoucherController(loggingService, voucherLookUpService,"MYNT", "GFI", "api/url/mynt", "api/url/gfi");
    }

    @Test
    void getVoucherMyntCode() throws Exception {
        String endpoint = "/template/mynt/voucher";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(endpoint))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
    }

    @Test
    void getVoucherGfiCode() throws Exception {
        String endpoint = "/template/gfi/voucher";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(endpoint))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
    }
}