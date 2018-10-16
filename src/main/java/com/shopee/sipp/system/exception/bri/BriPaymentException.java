package com.shopee.sipp.system.exception.bri;

public class BriPaymentException extends BriException {
    private static final String ERR_MSG_FRAME = "{ \"StatusPayment\":{ \"ErrorDesc\":\"#{errMsg}\", \"ErrorCode\":\"#{errCode}\", \"isError\":\"1\"}, ,\"Info1\":\"\" \"Info2\":\"#{va}\", \"Info3\":\"\", \"Info4\":\"\", \"Info5\":\"\" }";

    String va;
    public BriPaymentException(String va, String errCode) {
        super(errCode);
        this.va = va;
    }

    public BriPaymentException(String va, String errCode, String errMsg) {
        super(errCode, errMsg);
        this.va = va;
    }

    public String getErrorPayload() {
        return ERR_MSG_FRAME.replace("#{errCode}", getErrCode()).replace("#{errMsg}", getErrMsg()).replace("#{va}", va);
    }
}
