package ph.com.mynt.parcelcostapi.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Format {

    @Override
    public String toString() {
        try {
            return new ObjectMapper().enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS).writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

}
