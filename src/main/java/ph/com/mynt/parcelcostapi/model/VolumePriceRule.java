package ph.com.mynt.parcelcostapi.model;

import org.springframework.context.annotation.Configuration;

@Configuration
public class VolumePriceRule extends Format {
    private double volume;
    private int height;
    private int width;
    private int length;
    private double price;

    private String voucher;

    public double getVolume() {
        return volume;
    }
    public void setVolume(double volume) {
        this.volume = volume;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
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
}
