package ph.com.mynt.parcelcostapi.service;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class LoggingServiceTest {

    @Test
    void testLogComponent() {
        LoggingService logService = new LoggingService();
        String component = "testComponent";
        Map<String, String> map = new HashMap<>();
        map.put("COMPONENT", component);
        logService.log("", component, "", "");
        assertTrue(StringUtils.isNotEmpty(component));
    }

    @Test
    void testLogUUID() {
        LoggingService logService = new LoggingService();
        String uuid = "testUUID";
        Map<String, String> map = new HashMap<>();
        map.put("UUID", uuid);
        logService.log(uuid, "", "", "");
        assertTrue(StringUtils.isNotEmpty(uuid));
    }

    @Test
    void testLogInfo1() {
        LoggingService logService = new LoggingService();
        String info1 = "testInfo1";
        Map<String, String> map = new HashMap<>();
        map.put("INFO1", info1);
        logService.log("", "", info1, "");
        assertTrue(StringUtils.isNotEmpty(info1));
    }

    @Test
    void testLogInfo2() {
        LoggingService logService = new LoggingService();
        String info2 = "testInfo2";
        Map<String, String> map = new HashMap<>();
        map.put("INFO2", info2);
        logService.log("", "", "", info2);
        assertTrue(StringUtils.isNotEmpty(info2));
    }
}