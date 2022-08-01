package ph.com.mynt.parcelcostapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ph.com.mynt.parcelcostapi.constants.ParcelCostConstants;
import ph.com.mynt.parcelcostapi.model.VolumePriceRule;
import ph.com.mynt.parcelcostapi.model.WeightPriceRule;
import ph.com.mynt.parcelcostapi.service.ComputeCostService;
import ph.com.mynt.parcelcostapi.service.GenerateUUIDService;
import ph.com.mynt.parcelcostapi.service.LoggingService;
import ph.com.mynt.parcelcostapi.utility.NumberFormatUtil;

import java.text.NumberFormat;

@RestController
public class PriceRuleController {

    private KieSession kieSession;
    private ComputeCostService computeCostService;
    private NumberFormatUtil numberFormatUtil;
    private LoggingService loggingService;
    private String jsonResponse;
    String uuid = new GenerateUUIDService().generateUUID();

    @Autowired
    public PriceRuleController(KieSession kieSession, ComputeCostService computeCostService,
                               NumberFormatUtil numberFormatUtil, LoggingService loggingService) {
        this.kieSession = kieSession;
        this.computeCostService = computeCostService;
        this.numberFormatUtil = numberFormatUtil;
        this.loggingService = loggingService;
    }

    @PostMapping("/weight")
    public ResponseEntity<String> inputWeight(@RequestBody WeightPriceRule priceRule) throws JsonProcessingException {
        loggingService.log(uuid, this.getClass().toString(), " - IN " + ParcelCostConstants.PRICE_RULE_CONTROLLER_WEIGTH, priceRule.toString());
        kieSession.insert(priceRule);
        kieSession.fireAllRules();
        String weight = String.valueOf(priceRule.getWeight());
        String voucher = priceRule.getVoucher();
        String requestStatus = priceRule.getStatus();
        if(ParcelCostConstants.INVALID_VOUCHER_CODE.equalsIgnoreCase(voucher)){
            jsonResponse = constructInvalidTransaction("error", "Invalid code");
            loggingService.log(uuid, this.getClass().toString(), ParcelCostConstants.PRICE_RULE_CONTROLLER_WEIGTH, jsonResponse);
            return new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST);
        }else if(ParcelCostConstants.REJECTED_EXCEEDED_FIFTY_KG.equalsIgnoreCase(requestStatus)){
            jsonResponse = constructInvalidTransaction("requestStatus", "Rejected. Weight exceeded 50kg.");
            loggingService.log(uuid, this.getClass().toString(), ParcelCostConstants.PRICE_RULE_CONTROLLER_WEIGTH, jsonResponse);
            return new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST);
        }else if(ParcelCostConstants.REJECTED_BELOW_MINIMUM_KG.equalsIgnoreCase(requestStatus)){
            jsonResponse = constructInvalidTransaction("requestStatus", "Rejected. Weight is lower that 10kg.");
            loggingService.log(uuid, this.getClass().toString(), ParcelCostConstants.PRICE_RULE_CONTROLLER_WEIGTH, jsonResponse);
            return new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST);
        }else{
            double costPerUnit = priceRule.getPrice();
            double cost = computeCostService.getVoucherAndComputeWeightCost(Integer.parseInt(weight), priceRule.getPrice());
            jsonResponse = constructPayload("weightInKg", weight, voucher, "costPerUnit(kg)", numberFormatUtil.doubleNumberFormatter(costPerUnit), numberFormatUtil.doubleNumberFormatter(cost));

            loggingService.log(uuid, this.getClass().toString(), " - OUT " + ParcelCostConstants.PRICE_RULE_CONTROLLER_WEIGTH, jsonResponse);

            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }

    }

    @PostMapping("/volume")
    public ResponseEntity<String> inputVolume(@RequestBody VolumePriceRule priceRule) throws JsonProcessingException {
        loggingService.log(uuid, this.getClass().getSimpleName(), " - IN " + ParcelCostConstants.PRICE_RULE_CONTROLLER_VOLUME, priceRule.toString());
        kieSession.insert(priceRule);
        kieSession.fireAllRules();
        int height = priceRule.getHeight();
        int width = priceRule.getWidth();
        int length = priceRule.getLength();
        String voucher = priceRule.getVoucher();
        if(ParcelCostConstants.INVALID_VOUCHER_CODE.equalsIgnoreCase(voucher)){
            jsonResponse = constructInvalidTransaction("error", "Invalid code");
            loggingService.log(uuid, this.getClass().toString(), ParcelCostConstants.PRICE_RULE_CONTROLLER_VOLUME, jsonResponse);
            return new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST);
        }else{
            String volume = String.valueOf(computeCostService.getTotalVolume(height, width, length));
            double costPerUnit = priceRule.getPrice();
            NumberFormat format = NumberFormat.getInstance();
            format.setMinimumFractionDigits(2);
            double cost = computeCostService.getVoucherAndComputeVolumeCost(height, width, length, priceRule.getPrice());
            jsonResponse = constructPayload("totalVolume(cm3)", volume, voucher, "costPerUnit(cm)", format.format(costPerUnit), numberFormatUtil.doubleNumberFormatter(cost));

            loggingService.log(uuid, this.getClass().toString(), " - OUT " + ParcelCostConstants.PRICE_RULE_CONTROLLER_VOLUME, jsonResponse);
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }
    }

    public String constructPayload(String parcelType, String totalParcel, String voucherCode, String costPerCm, String costPerUnit, String cost) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        String voucherValidity = computeCostService.getVoucherDate();
        String voucherExpiry;
        boolean isPromoExpired = computeCostService.getExpiryDate();
        if(isPromoExpired){
            voucherExpiry = "Voucher code is expired.";
        }else {
            voucherExpiry = "Discount were applied";
        }
        objectNode.put(parcelType, totalParcel);
        objectNode.put(costPerCm, costPerUnit);
        objectNode.put("totalCost(php)", cost);
        objectNode.put("voucherCode", voucherCode);
        objectNode.put("voucherValidity", voucherValidity);
        objectNode.put("voucherStatus", voucherExpiry);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectNode);
    }

    public String constructInvalidTransaction(String fieldname, String value) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put(fieldname, value);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectNode);
    }
}
