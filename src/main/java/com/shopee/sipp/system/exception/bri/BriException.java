package com.shopee.sipp.system.exception.bri;

import com.shopee.sipp.system.exception.SippException;

public class BriException extends SippException {

    public final static String ERRCODE_INQUIRY_ALREADY_PAID     ="1";
    public final static String ERRCODE_INQUIRY_BILL_NOTFOUND    ="2";
    public final static String ERRCODE_INQUIRY_EXPIRED          ="3";
    public final static String ERRCODE_INQUIRY_GENERAL          ="9";

    public final static String ERRCODE_INVOICE_NOTFOUND         = "01";
    public final static String ERRCODE_BILLDATA_NOTFOUND        = "02";
    public final static String ERRCODE_BILL_PAID                = "03";
    public final static String ERRCODE_AMOUNT_DIFF              = "04"; //결제 금액이 일치 하지 않음
    public final static String ERRCODE_TIMEOUT                  = "10";
    public final static String ERRCODE_NO_AUTH_API              = "11";
    public final static String ERRCODE_CONN_API_FAILED          = "12";
    public final static String ERRCODE_NO_APP_USER_ID           = "13";
    public final static String ERRCODE_NO_APP_PASSWORD          = "14";
    public final static String ERRCODE_DB_TIMEOUT               = "20";
    public final static String ERRCODE_DB_ERROR                 = "21";
    public final static String ERRCODE_UNKNOWN                  = "99";


    String errCode;
    String errMsg;

    public BriException(String errCode) {
        this.errCode = errCode;
        initDefaultErrorMsg();
    }

    public BriException(String errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public void initDefaultErrorMsg(){
        switch(errCode){
            case ERRCODE_INVOICE_NOTFOUND:  errMsg = "No invoice";   break;
            case ERRCODE_BILLDATA_NOTFOUND:   errMsg = "No billdata";   break;
            case ERRCODE_BILL_PAID:     errMsg = "Bill already paid";   break;
            case ERRCODE_AMOUNT_DIFF:   errMsg = "Amount is different";   break;
            case ERRCODE_TIMEOUT:      errMsg = "Timeout";   break;
            case ERRCODE_NO_AUTH_API:       errMsg = "No auth Api";   break;
            case ERRCODE_CONN_API_FAILED:      errMsg = "API connection failed";   break;
            case ERRCODE_NO_APP_USER_ID:   errMsg = "No app user id";   break;
            case ERRCODE_NO_APP_PASSWORD:   errMsg = "No app password";   break;
            case ERRCODE_DB_TIMEOUT:   errMsg = "DB timeout";   break;
            case ERRCODE_DB_ERROR:   errMsg = "DB error";   break;
            case ERRCODE_UNKNOWN:   errMsg = "Unknown error";   break;
        }
    }


}
