package com.shopee.sipp.mvc.payload.virtual_account.mandiri.inquiry.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PaymentRequestPayload {
    public String
            language
            , trxDateTime
            , transmissionDateTime
            , companyCode
            , channelID
            , billKey1
            , billKey2
            , billKey3
            , reference1
            , reference2
            , reference3;

    public String transactionID;
    public String currency;
    public String paymentAmount;
    public List<String> paidBillsCodes = new ArrayList<>();


}
