package ph.com.mynt.parcelcostapi.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ph.com.mynt.parcelcostapi.controller.VoucherController;
import ph.com.mynt.parcelcostapi.utility.VoucherExpiryUtility;

@Service
public class ComputeCostService {

    private VoucherController voucherController;
    private VoucherExpiryUtility voucherExpiryUtility;
    private static final String EXPIRY = "expiry";
    private double voucherDisc;
    String voucherExpiry;

    @Autowired
    public ComputeCostService(VoucherController voucherController, VoucherExpiryUtility voucherExpiryUtility) {
        this.voucherController = voucherController;
        this.voucherExpiryUtility = voucherExpiryUtility;
    }

    public double getTotalCostForParcel(int weight, double cost){
        return cost * weight;
    }

    public double getTotalCostForParcel(int height, int width, int length, double cost){
        double volume = height * width * length;
        return  volume * cost;
    }

    public int getTotalVolume(int height, int width, int length){
        return height * width * length;
    }

    public double getVoucherAndComputeWeightCost(int weight, double cost){
        JSONObject object;
        double totalCost;
        object = new JSONObject(voucherController.getVoucherMyntCode());
        voucherExpiry = object.getString(EXPIRY);

        boolean isVoucherExpired = voucherExpiryUtility.isPromoExpired(voucherExpiry);
        if(isVoucherExpired){
            totalCost = getTotalCostForParcel(weight, cost);
        }else {
            totalCost = getTotalCostForParcel(weight, cost) - voucherDisc;
        }
        return totalCost;
    }

    public double getVoucherAndComputeVolumeCost(int height, int width, int length, double cost){
        JSONObject object;
        double totalCost;
        object = new JSONObject(voucherController.getVoucherGfiCode());
        voucherExpiry = object.getString(EXPIRY);

        boolean isVoucherExpired = voucherExpiryUtility.isPromoExpired(voucherExpiry);
        if(isVoucherExpired){
            totalCost = getTotalCostForParcel(height, width, length, cost);
        }else {
            totalCost = getTotalCostForParcel(height, width, length, cost) - voucherDisc;
        }
        return totalCost;
    }

    public boolean getExpiryDate(){
        JSONObject object;
        object = new JSONObject(voucherController.getVoucherMyntCode());
        voucherExpiry = object.getString(EXPIRY);
        return voucherExpiryUtility.isPromoExpired(voucherExpiry);

    }

    public String getVoucherDate(){
        JSONObject object;
        object = new JSONObject(voucherController.getVoucherMyntCode());
        return object.getString(EXPIRY);
    }
}
