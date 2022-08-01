package ph.com.mynt.parcelcostapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kie.api.runtime.KieSession;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ph.com.mynt.parcelcostapi.ParcelCostApplication;
import ph.com.mynt.parcelcostapi.model.VolumePriceRule;
import ph.com.mynt.parcelcostapi.model.WeightPriceRule;
import ph.com.mynt.parcelcostapi.service.ComputeCostService;
import ph.com.mynt.parcelcostapi.service.LoggingService;
import ph.com.mynt.parcelcostapi.utility.NumberFormatUtil;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(properties = "spring.profiles.active=dev")
@ContextConfiguration(classes = {ParcelCostApplication.class})
class PriceRuleControllerTest {

    @InjectMocks
    private PriceRuleController priceRuleController;
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private KieSession kieSession;
    @Mock
    private ComputeCostService computeCostService;
    @Mock
    private NumberFormatUtil numberFormatUtil;
    @Mock
    private WeightPriceRule weightPriceRule;
    @Mock
    private VolumePriceRule volumePriceRule;
    @Mock
    private LoggingService loggingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        priceRuleController = new PriceRuleController(kieSession, computeCostService, numberFormatUtil, loggingService);
        weightPriceRule = new WeightPriceRule();
        weightPriceRule.setPrice(20);
        weightPriceRule.setWeight(12);
        weightPriceRule.setVoucher("MYNT");

        volumePriceRule = new VolumePriceRule();
        volumePriceRule.setPrice(20);
        volumePriceRule.setVolume(1500);
        volumePriceRule.setLength(12);
        volumePriceRule.setWidth(12);
        volumePriceRule.setLength(22);
        volumePriceRule.setVoucher("GFI");

    }

    @Test
    void inputWeight() throws Exception {
        String endpoint = "/weight";
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String request = ow.writeValueAsString(weightPriceRule);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
    }

    @Test
    void inputWeightWithErrorCode() throws Exception {
        weightPriceRule = new WeightPriceRule();
        weightPriceRule.setPrice(20);
        weightPriceRule.setWeight(15);
        weightPriceRule.setVoucher("error");
        String endpoint = "/weight";
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String request = ow.writeValueAsString(weightPriceRule);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(400, status);
    }

    @Test
    void testInputWeightExceededFiftyKg() throws Exception {
        weightPriceRule = new WeightPriceRule();
        weightPriceRule.setPrice(20);
        weightPriceRule.setWeight(150);
        weightPriceRule.setVoucher("GFI");
        weightPriceRule.setStatus("reject");
        String endpoint = "/weight";
        String res = priceRuleController.constructInvalidTransaction("requestStatus", "Rejected. Weight exceeded 50kg.");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String request = ow.writeValueAsString(res);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(400, status);
    }

    @Test
    void testInputWeightLowerThanTenKg() throws Exception {
        weightPriceRule = new WeightPriceRule();
        weightPriceRule.setPrice(20);
        weightPriceRule.setWeight(1);
        weightPriceRule.setVoucher("MYNT");
        weightPriceRule.setStatus("below_minimum");
        String endpoint = "/weight";
        String res = priceRuleController.constructInvalidTransaction("requestStatus", "Rejected. Weight is lower that 10kg.");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String request = ow.writeValueAsString(res);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(400, status);
    }

    @Test
    void inputVolume() throws Exception {
        String endpoint = "/volume";
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String request = ow.writeValueAsString(volumePriceRule);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
    }

    @Test
    void inputVolumeWithErrorCode() throws Exception {
        weightPriceRule = new WeightPriceRule();
        weightPriceRule.setPrice(20);
        weightPriceRule.setWeight(15);
        weightPriceRule.setVoucher("error");
        String endpoint = "/volume";
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String request = ow.writeValueAsString(weightPriceRule);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(400, status);
    }

    @Test
    void testConstructPayloadSuccess() throws JsonProcessingException {
        String res = priceRuleController.constructPayload("weightInKg", "20", "MNYT", "costPerUnit(kg)", numberFormatUtil.doubleNumberFormatter(20), numberFormatUtil.doubleNumberFormatter(15));
        Assertions.assertNotNull(res);
    }

    @Test
    void constructInvalidPayload() throws JsonProcessingException {
       String res = priceRuleController.constructInvalidTransaction("error", "invalid");
       Assertions.assertNotNull(res);
    }
}