package com.shopee.sipp.system.exception.mandiri;

public class MandiriInquiryException extends MandiriException {
    private static final String ERR_MSG_FRAME = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soapenv:Body><inquiryResponse xmlns=\"bankmandiri.h2h.billpayment.ws\"><inquiryResult><status><isError>True</isError><errorCode>#{errCode}</errorCode><statusDescription>#{errMsg}</statusDescription></status></inquiryResult></inquiryResponse></soapenv:Body></soapenv:Envelope>";

    public MandiriInquiryException(String errCode) {
        super(errCode);
    }

    public MandiriInquiryException(String errCode, String errMsg) {
        super(errCode, errMsg);
    }

    public String getErrorPayload() {
        return ERR_MSG_FRAME.replace("#{errCode}", getErrCode()).replace("#{errMsg}", getErrMsg());
    }
}
