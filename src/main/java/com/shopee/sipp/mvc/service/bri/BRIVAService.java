package com.shopee.sipp.mvc.service.bri;

import com.shopee.sipp.mvc.libs.CommonUtil;
import com.shopee.sipp.system.exception.bri.BriException;
import com.shopee.sipp.system.exception.bri.BriInquiryException;
import com.shopee.sipp.system.exception.bri.BriPaymentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class BRIVAService {


    private static Logger logger = LogManager.getLogger(BRIVAService.class);

    @Autowired  BRIInquiryService briInquiryService;
    @Autowired  BRIPaymentService briPaymentService;

    @Transactional
    public String parse(String payload) throws BriException {

        String responsePayload = null;

        if(payload.contains("PaymentAmount")){
            responsePayload = paymentJob(payload);
        }
        else {
            responsePayload = inquiryJob(payload);
        }

        return responsePayload;
    }


    //INquiry
    private String inquiryJob(String payload) throws BriInquiryException {
        try{
            return briInquiryService.call(payload);
        }
        catch(BriInquiryException e){
            throw e;
        }
        catch(Exception e){
            try{
                Map<String, String> inquiryMap = CommonUtil.parseJsonToStringMap(payload);
                String briVaNum = inquiryMap.get("BrivaNum");
                throw new BriInquiryException(briVaNum, BriException.ERRCODE_INQUIRY_GENERAL);
            }catch(Exception ee){
                throw new BriInquiryException("", BriException.ERRCODE_INQUIRY_GENERAL);
            }
        }
    }

    //INquiry
    private String paymentJob(String payload) throws BriPaymentException {
        try{
            return briPaymentService.call(payload);
        }
        catch(BriPaymentException e){
            throw e;
        }
        catch(Exception e){
            try{
                Map<String, String> paymentMap = CommonUtil.parseJsonToStringMap(payload);
                String va = paymentMap.get("BrivaNum");
                throw new BriInquiryException(va, BriException.ERRCODE_UNKNOWN);
            }catch(Exception ee){
                throw new BriPaymentException("", BriException.ERRCODE_UNKNOWN);
            }

        }
    }




}
