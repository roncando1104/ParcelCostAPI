package ph.com.mynt.parcelcostapi.service;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.util.Collections;

@Service
public class VoucherLookUpService {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public String getVoucher(URI uri){

        HttpEntity<String> entity;
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        entity = new HttpEntity<>(headers);

        return restTemplate().exchange(uri, HttpMethod.GET, entity, String.class).getBody();
    }

}
