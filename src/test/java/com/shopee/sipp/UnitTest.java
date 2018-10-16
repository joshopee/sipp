package com.shopee.sipp;

import org.junit.Test;

import java.net.URLDecoder;

public class UnitTest {

    @Test
    public void test() throws Exception{

        String xml = "{\"headers\":\"accept\\u003dtext/xml, text/html, image/gif, image/jpeg, *; q\\u003d.2, */*; q\\u003d.2\",\"payload\":\"\\u003c?xml version\\u003d\\\"1.0\\\" encoding\\u003d\\\"UTF-8\\\"?\\u003e\\u003csoapenv:Envelope xmlns:soapenv\\u003d\\\"http://schemas.xmlsoap.org/soap/envelope/\\\" xmlns:xsd\\u003d\\\"http://www.w3.org/2001/XMLSchema\\\" xmlns:xsi\\u003d\\\"http://www.w3.org/2001/XMLSchema-instance\\\"\\u003e\\u003csoapenv:Body\\u003e\\u003cinquiryResponse xmlns\\u003d\\\"bankmandiri.h2h.billpayment.ws\\\"\\u003e\\u003cinquiryResult\\u003e\\u003ccurrency\\u003e360\\u003c/currency\\u003e\\u003cbillInfo1\\u003e87008001000104\\u003c/billInfo1\\u003e\\u003cbillInfo2\\u003eSub 35000\\u003c/billInfo2\\u003e\\u003cbillDetails\\u003e\\u003cBillDetail\\u003e\\u003cbillCode\\u003e01\\u003c/billCode\\u003e\\u003cbillName\\u003eOrdername 35000\\u003c/billName\\u003e\\u003cbillShortName\\u003eSub 35000\\u003c/billShortName\\u003e\\u003cbillAmount\\u003e35000\\u003c/billAmount\\u003e\\u003c/BillDetail\\u003e\\u003c/billDetails\\u003e\\u003cstatus\\u003e\\u003cisError\\u003efalse\\u003c/isError\\u003e\\u003cerrorCode\\u003e00\\u003c/errorCode\\u003e\\u003cstatusDescription\\u003eSuccess\\u003c/statusDescription\\u003e\\u003c/status\\u003e\\u003c/inquiryResult\\u003e\\u003c/inquiryResponse\\u003e\\u003c/soapenv:Body\\u003e\\u003c/soapenv:Envelope\\u003e\",\"type\":\"RESPONSE\"}";
        System.out.println(URLDecoder.decode(xml, "utf-8"));

    }







}
