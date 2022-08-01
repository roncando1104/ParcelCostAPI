package ph.com.mynt.parcelcostapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ParcelCostApplication.class, initializers = ConfigFileApplicationContextInitializer.class)
class ParcelCostApplicationTest {

    @Test
    void main() {
        ParcelCostApplication.main(new String[]{});
        assertNotNull(getClass());
    }
}