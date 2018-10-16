package com.shopee.sipp.mvc.payload.virtual_account.mandiri.inquiry.response;


import com.shopee.sipp.mvc.payload.virtual_account.mandiri.inquiry.response.child.ResponseStatusPayload;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="paymentResult")
@XmlAccessorType(XmlAccessType.FIELD)
public class PaymentResponsePayload {
    public String
            billInfo1
            , billInfo2
            , billInfo3
            , billInfo4
            , billInfo5
            , billInfo6
            , billInfo7
            , billInfo8
            , billInfo9
            , billInfo10
            , billInfo11
    ;

    public ResponseStatusPayload status;
}
