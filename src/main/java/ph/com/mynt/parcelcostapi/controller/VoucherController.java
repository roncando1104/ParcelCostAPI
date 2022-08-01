package ph.com.mynt.parcelcostapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import ph.com.mynt.parcelcostapi.constants.ParcelCostConstants;
import ph.com.mynt.parcelcostapi.service.GenerateUUIDService;
import ph.com.mynt.parcelcostapi.service.LoggingService;
import ph.com.mynt.parcelcostapi.service.VoucherLookUpService;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
public class VoucherController {

    private LoggingService loggingService;
    private VoucherLookUpService voucherLookUpService;
    private String myntVoucher;
    private String gfiVoucher;
    private String myntApiUrl;
    private String gfiApiUrl;

    @Autowired
    public VoucherController(LoggingService loggingService,
                             VoucherLookUpService voucherLookUpService,
                             @Value("${spring.boot.config.delivery.myntvoucher}") String myntVoucher,
                             @Value("${spring.boot.config.delivery.gfivoucher}") String gfiVoucher,
                             @Value("${spring.boot.config.mynt.api.url}") String myntApiUrl,
                             @Value("${spring.boot.config.gfi.api.url}") String gfiApiUrl) {
        this.loggingService = loggingService;
        this.voucherLookUpService = voucherLookUpService;
        this.myntVoucher = myntVoucher;
        this.gfiVoucher = gfiVoucher;
        this.myntApiUrl = myntApiUrl;
        this.gfiApiUrl = gfiApiUrl;
    }
    String uuid = new GenerateUUIDService().generateUUID();
    @GetMapping(value = "/template/mynt/voucher")
    public String getVoucherMyntCode(){
        Map<String, String> params = new HashMap<>();
        params.put("voucher", myntVoucher);
        URI uri = UriComponentsBuilder.fromUriString(myntApiUrl).buildAndExpand(params).toUri();
        String voucherReturnValue = voucherLookUpService.getVoucher(uri);
        loggingService.log(uuid, this.getClass().toString() + ParcelCostConstants.VOUCHER_CONTROLLER,
                " Retrieving Voucher Values. ", voucherReturnValue);
        return voucherLookUpService.getVoucher(uri);
    }

    @GetMapping(value = "/template/gfi/voucher")
    public String getVoucherGfiCode(){
        Map<String, String> params = new HashMap<>();
        params.put("voucher", gfiVoucher);
        URI uri = UriComponentsBuilder.fromUriString(gfiApiUrl).buildAndExpand(params).toUri();
        String voucherReturnValue = voucherLookUpService.getVoucher(uri);
        loggingService.log(uuid, this.getClass().toString() + ParcelCostConstants.VOUCHER_CONTROLLER,
                " Retrieving Voucher Values. ", voucherReturnValue);
        return voucherLookUpService.getVoucher(uri);
    }
}
