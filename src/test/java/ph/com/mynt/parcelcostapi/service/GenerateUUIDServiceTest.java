package ph.com.mynt.parcelcostapi.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class GenerateUUIDServiceTest {

    @Test
    void testGenerateUUID() {
        GenerateUUIDService generateUUIDService = new GenerateUUIDService();
        Assertions.assertNotNull(generateUUIDService.generateUUID());
    }
}