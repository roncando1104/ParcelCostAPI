package ph.com.mynt.parcelcostapi.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import ph.com.mynt.parcelcostapi.model.VolumePriceRule;
import ph.com.mynt.parcelcostapi.model.WeightPriceRule;
import ph.com.mynt.parcelcostapi.service.LoggingService;

import java.io.IOException;

class DroolConfigTest {

    @InjectMocks
    private DroolConfig droolConfig;
    @Mock
    private WeightPriceRule weightPriceRule;
    @Mock
    private VolumePriceRule volumePriceRule;
    @Mock
    private LoggingService loggingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        droolConfig = new DroolConfig(loggingService);
        weightPriceRule = new WeightPriceRule();
        weightPriceRule.setPrice(40);
        weightPriceRule.setWeight(12);
        weightPriceRule.setVoucher("MYNT");
        volumePriceRule = new VolumePriceRule();
        volumePriceRule.setPrice(20);
        volumePriceRule.setHeight(2);
        volumePriceRule.setWidth(12);
        volumePriceRule.setLength(20);
        volumePriceRule.setVoucher("GFI");

    }

    @Test
    void getKieContainer() throws IOException {
        Assertions.assertNotNull(droolConfig.getKieContainer());
    }

    @Test
    void getKieSession() throws IOException {
        Assertions.assertNotNull(droolConfig.getKieSession());
    }
}