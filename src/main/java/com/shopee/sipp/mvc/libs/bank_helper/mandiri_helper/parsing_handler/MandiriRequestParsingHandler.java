package com.shopee.sipp.mvc.libs.mandiri_helper.parsing_handler;

import com.shopee.sipp.system.exception.SippException;
import com.shopee.sipp.mvc.libs.soap_parser.SippSoapXmlParser;
import com.shopee.sipp.mvc.payload.virtual_account.mandiri.inquiry.request.InquiryRequestPayload;
import com.shopee.sipp.mvc.payload.virtual_account.mandiri.inquiry.request.PaymentRequestPayload;
import org.xml.sax.SAXException;

import java.util.Map;

/**RAW PARSER*/
public class MandiriRequestParsingHandler {

    /**페이머트 리퀘스트 파싱*/
    public final static PaymentRequestPayload parsePaymentRequest(String payload) throws SippException {
        final PaymentRequestPayload paymentRequestPayload = new PaymentRequestPayload();

        SippSoapXmlParser.Parse(payload, new SippSoapXmlParser.XMLParsingListener() {

            boolean doParse = false;
            @Override
            public void onStartTag(String[] parentTagNames, String tagName, Map<String, String> attributes) {
                if(tagName.equals("payment")){
                    doParse = true;
                }
            }

            @Override
            public void onValue(String[] parentTagNames, String tagName, String value) {
                switch (tagName){
                    case "language":
                        paymentRequestPayload.language = value;
                        break;
                    case "trxDateTime":
                        paymentRequestPayload.trxDateTime = value;
                        break;
                    case "transmissionDateTime":
                        paymentRequestPayload.transmissionDateTime = value;
                        break;
                    case "companyCode":
                        paymentRequestPayload.companyCode = value;
                        break;
                    case "channelID":
                        paymentRequestPayload.channelID = value;
                        break;
                    case "billKey1":
                        paymentRequestPayload.billKey1 = value;
                        break;
                    case "billKey2":
                        paymentRequestPayload.billKey2 = value;
                        break;
                    case "billKey3":
                        paymentRequestPayload.billKey3 = value;
                        break;
                    case "reference1":
                        paymentRequestPayload.reference1 = value;
                        break;
                    case "reference2":
                        paymentRequestPayload.reference2 = value;
                        break;
                    case "reference3":
                        paymentRequestPayload.reference3 = value;
                        break;
                    case "paymentAmount":
                        paymentRequestPayload.paymentAmount = value;
                        break;
                    case "currency":
                        paymentRequestPayload.currency = value;
                        break;
                    case "transactionID":
                        paymentRequestPayload.transactionID = value;
                        break;
                    case "string":
                        if(parentTagNames[parentTagNames.length-2].equals("paidBills")){
                            paymentRequestPayload.paidBillsCodes.add(value);
                        }
                        break;
                }
            }

            @Override
            public void onEndTag(String[] parentTagNames, String tagName) throws SAXException{
                //종료시키는거 만들어야댐
                ;
                if(tagName.equals("payment")){
                    throw new SAXException();
                }
            }
        });

        return paymentRequestPayload;
    }

    /**Inquiry 리퀘스트 파싱*/
    public final static InquiryRequestPayload parseInquiryRequest(String payload) throws SippException{
        final InquiryRequestPayload inquiryRequestPayload = new InquiryRequestPayload();

        SippSoapXmlParser.Parse(payload, new SippSoapXmlParser.XMLParsingListener() {

            boolean doParse = false;
            @Override
            public void onStartTag(String[] parentTagNames, String tagName, Map<String, String> attributes) {
                if(tagName.equals("<request>")){
                    doParse = true;
                }
            }

            @Override
            public void onValue(String[] parentTagNames, String tagName, String value) {
                switch (tagName){
                    case "language":
                        inquiryRequestPayload.language = value;
                        break;
                    case "trxDateTime":
                        inquiryRequestPayload.trxDateTime = value;
                        break;
                    case "transmissionDateTime":
                        inquiryRequestPayload.transmissionDateTime = value;
                        break;
                    case "companyCode":
                        inquiryRequestPayload.companyCode = value;
                        break;
                    case "channelID":
                        inquiryRequestPayload.channelID = value;
                        break;
                    case "billKey1":
                        inquiryRequestPayload.billKey1 = value;
                        break;
                    case "billKey2":
                        inquiryRequestPayload.billKey2 = value;
                        break;
                    case "billKey3":
                        inquiryRequestPayload.billKey3 = value;
                        break;
                    case "reference1":
                        inquiryRequestPayload.reference1 = value;
                        break;
                    case "reference2":
                        inquiryRequestPayload.reference2 = value;
                        break;
                    case "reference3":
                        inquiryRequestPayload.reference3 = value;
                        break;
                }
            }

            @Override
            public void onEndTag(String[] parentTagNames, String tagName) throws SAXException {
                //종료시키는거 만들어야댐
                if(tagName.equals("<request>")){
                    doParse = true;
                    throw new SAXException();
                }
            }
        });

        return inquiryRequestPayload;
    }
}
