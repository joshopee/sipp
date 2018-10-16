package com.shopee.sipp.mvc.libs.bank_helper.mandiri_helper;

import com.shopee.sipp.mvc.libs.soap_parser.SippSoapXmlParserUtil;
import com.shopee.sipp.mvc.model.virtual_account.CloseVAInfo;
import com.shopee.sipp.mvc.payload.virtual_account.mandiri.inquiry.response.InquiryResponsePayload;
import com.shopee.sipp.mvc.payload.virtual_account.mandiri.inquiry.response.PaymentResponsePayload;
import com.shopee.sipp.mvc.payload.virtual_account.mandiri.inquiry.response.child.InquiryResponseBillDetailPayload;
import com.shopee.sipp.mvc.payload.virtual_account.mandiri.inquiry.response.child.ResponseStatusPayload;
import com.shopee.sipp.system.exception.SippException;

public class MandiriResponsePayloadManager {
    final static String INQUIRY_RESPONSE_FRAME = new String("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soapenv:Body><inquiryResponse xmlns=\"bankmandiri.h2h.billpayment.ws\">#{inquiryResult}</inquiryResponse></soapenv:Body></soapenv:Envelope>");
    final static String REPLACETAG_INQUIRY_RESPONSE_FRAME = "#{inquiryResult}";

    final static String PAYMENT_RESPONSE_FRAME = new String("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soapenv:Body><paymentResponse xmlns=\"bankmandiri.h2h.billpayment.ws\">#{paymentResult}</paymentResponse></soapenv:Body></soapenv:Envelope>");
    final static String REPLACETAG_PAYMENT_RESPONSE_FRAME = "#{paymentResult}";


    /**페이먼트 성공했을때 리스판스*/
    public final static String makePaymentSuccssResponse(CloseVAInfo closeVAInfo) throws SippException{
        //TRANSACTION--VAService
        PaymentResponsePayload responsePayload = new PaymentResponsePayload();
        responsePayload.billInfo1 = closeVAInfo.va+"";
//        responsePayload.billInfo2 = vaInfo.orderShortName;
        responsePayload.billInfo2 = closeVAInfo.desc1;
        responsePayload.billInfo3 = closeVAInfo.desc2;

        responsePayload.status = ResponseStatusPayload.makeSuccessPayload();

        return SippSoapXmlParserUtil.build(PAYMENT_RESPONSE_FRAME, REPLACETAG_PAYMENT_RESPONSE_FRAME, responsePayload, PaymentResponsePayload.class);
    }


    /**Inquiry 성공 리스판스 */
    public static String makeInquirySuccssResponse(CloseVAInfo closeVAInfo) throws SippException {
        InquiryResponsePayload responsePayload = new InquiryResponsePayload();

        responsePayload.currency = "360"; //default
        responsePayload.billInfo1 = closeVAInfo.va+"";
        responsePayload.billInfo2 = closeVAInfo.desc1; //name
        responsePayload.billInfo3 = closeVAInfo.desc2; //email
        //desc3 -> 간다널명

        responsePayload.billDetails = new InquiryResponseBillDetailPayload();
        responsePayload.billDetails.items.add(InquiryResponseBillDetailPayload.buildNode("01", "Total", closeVAInfo.desc3, closeVAInfo.amount+""));
//        responsePayload.billDetails.items.add(InquiryResponseBillDetailPayload.buildNode("01", vaInfo.orderName, vaInfo.orderShortName, vaInfo.orderAmount+""));
        responsePayload.status = ResponseStatusPayload.makeSuccessPayload();

        return SippSoapXmlParserUtil.build(INQUIRY_RESPONSE_FRAME, REPLACETAG_INQUIRY_RESPONSE_FRAME, responsePayload, InquiryResponsePayload.class);
    }



}
