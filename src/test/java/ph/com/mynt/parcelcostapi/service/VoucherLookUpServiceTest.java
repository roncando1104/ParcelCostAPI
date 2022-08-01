package ph.com.mynt.parcelcostapi.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class VoucherLookUpServiceTest {

    @InjectMocks
    private VoucherLookUpService voucherLookUpService;
    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getVoucher() {
        URI uri = UriComponentsBuilder.fromUriString("https://mynt-exam.mocklab.io/voucher/{voucher}?key=apikey").buildAndExpand("MYNT").toUri();
        voucherLookUpService.getVoucher(uri);
        Assertions.assertNotNull(voucherLookUpService.getVoucher(uri));
    }
}