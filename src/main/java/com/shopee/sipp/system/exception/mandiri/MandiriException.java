package com.shopee.sipp.system.exception.mandiri;

import com.shopee.sipp.system.exception.SippException;

public class MandiriException extends SippException {

    public final static String ERRCODE_SYTEM_ERROR      = "01";
    public final static String ERRCODE_BILL_NOFOUND     = "B5";
    public final static String ERRCODE_BILL_PAID        = "B8";
    public final static String ERRCODE_PAY_BLOCKED      = "C0";
    public final static String ERRCODE_DB_ERROR         = "87";
    public final static String ERRCODE_TIMEOUT          = "89";
    public final static String ERRCODE_LINKDOWN         = "91";
    public final static String ERRCODE_CANT_CANCLE      = "86";

    String errCode;
    String errMsg;

    public MandiriException(String errCode) {
        this.errCode = errCode;
        initDefaultErrorMsg();
    }

    public MandiriException(String errCode, String errMsg) {
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
            case ERRCODE_BILL_NOFOUND:  errMsg = "Bill no found";   break;
            case ERRCODE_SYTEM_ERROR:   errMsg = "General system err";   break;
            case ERRCODE_BILL_PAID:     errMsg = "Bill already paid";   break;
            case ERRCODE_PAY_BLOCKED:   errMsg = "Pay blocked";   break;
            case ERRCODE_DB_ERROR:      errMsg = "Db error";   break;
            case ERRCODE_TIMEOUT:       errMsg = "Timeout";   break;
            case ERRCODE_LINKDOWN:      errMsg = "Link down";   break;
            case ERRCODE_CANT_CANCLE:   errMsg = "Can not cancel";   break;
        }
    }


}
