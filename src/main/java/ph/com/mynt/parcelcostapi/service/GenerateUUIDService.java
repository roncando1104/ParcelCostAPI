package ph.com.mynt.parcelcostapi.service;

/**
 * @author Ronald Cando
 */

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GenerateUUIDService {

    public String generateUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

}
