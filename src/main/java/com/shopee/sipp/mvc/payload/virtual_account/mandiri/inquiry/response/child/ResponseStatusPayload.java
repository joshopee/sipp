package com.shopee.sipp.mvc.payload.virtual_account.mandiri.inquiry.response.child;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="status")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseStatusPayload {

    public String isError, errorCode, statusDescription;

    public final static ResponseStatusPayload makeSuccessPayload(){
        ResponseStatusPayload p = new ResponseStatusPayload();
        p.isError = "False";
        p.errorCode = "00";
        p.statusDescription = "Success";
        return p;
    }

}
