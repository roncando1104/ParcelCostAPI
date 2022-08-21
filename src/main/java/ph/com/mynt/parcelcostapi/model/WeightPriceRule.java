package ph.com.mynt.parcelcostapi.model;

import org.springframework.context.annotation.Configuration;

@Configuration
public class WeightPriceRule extends Format{

    private int weight;
    private double price;
    private String voucher;
    private String status;

    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getVoucher() {
        return voucher;
    }
    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
