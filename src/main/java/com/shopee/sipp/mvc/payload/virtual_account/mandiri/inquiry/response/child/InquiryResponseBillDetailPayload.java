package com.shopee.sipp.mvc.payload.virtual_account.mandiri.inquiry.response.child;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name="BillDetail")
@XmlAccessorType(XmlAccessType.FIELD)
public class InquiryResponseBillDetailPayload {

    @XmlElement(name="BillDetail")
    public List<BillDetail> items = new ArrayList<>();

    public final static BillDetail buildNode(String billCode, String billName, String billShortName, String billAmount) {
        BillDetail billDetail = new BillDetail();
        billDetail = new BillDetail();
        billDetail.billAmount = billAmount;
        billDetail.billCode = billCode;
        billDetail.billName = billName;
        billDetail.billShortName = billShortName;
        return billDetail;
    }

    public static class BillDetail{
        public String billCode, billName, billShortName, billAmount;
    }

}
