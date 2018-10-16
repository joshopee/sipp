package com.shopee.sipp.mvc.payload.virtual_account.mandiri.inquiry.response;

import com.shopee.sipp.mvc.payload.virtual_account.mandiri.inquiry.response.child.InquiryResponseBillDetailPayload;
import com.shopee.sipp.mvc.payload.virtual_account.mandiri.inquiry.response.child.ResponseStatusPayload;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="inquiryResult")
@XmlAccessorType(XmlAccessType.FIELD)
public class InquiryResponsePayload {
    public String
            currency
            , billInfo1
            , billInfo2
            , billInfo3
            , billInfo4
            , billInfo5
            , billInfo6
            , billInfo7
            , billInfo8
            , billInfo9
    ;

    public InquiryResponseBillDetailPayload billDetails;
    public ResponseStatusPayload status;
}
