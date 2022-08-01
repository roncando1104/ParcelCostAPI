package ph.com.mynt.parcelcostapi.service;

/**
 * @author Ronald Cando
 */

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class LoggingService {

    private static final Logger logger = LoggerFactory.getLogger(LoggingService.class);

    public String log(String uuid, String component, String info1, String info2) {
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isNotEmpty(component)) {
            map.put("COMPONENT", component);
        }
        if (StringUtils.isNotEmpty(uuid)) {
            map.put("UUID", uuid);
        }
        if (StringUtils.isNotEmpty(info1)) {
            map.put("INFO1", info1);
        }
        if (StringUtils.isNotEmpty(info2)) {
            map.put("INFO2", info2);
        }
        String stringMap = map.toString();
        logger.info(stringMap);
        return stringMap;
    }

}
