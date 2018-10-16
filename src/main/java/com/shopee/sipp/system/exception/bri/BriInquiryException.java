package com.shopee.sipp.system.exception.bri;

public class BriInquiryException extends BriException {
    private static final String ERR_MSG_FRAME = "{\"BillDetail\":{\"BillAmount\":\"0\",\"BillName\":\"\",\"BrivaNum\":\"#{va}\"},\"Info1\":\"\",\"Info2\":\"\",\"Info3\":\"\",\"Info4\":\"\",\"Info5\":\"\",\"StatusBill\":\"#{errCode}\",\"Currency\":\"\"}";

    String va;
    public BriInquiryException(String va, String errCode) {
        super(errCode);
        this.va = va;
    }

    public String getErrorPayload() {
        return ERR_MSG_FRAME.replace("#{errCode}", getErrCode()).replace("#{va}", va);
    }
}
