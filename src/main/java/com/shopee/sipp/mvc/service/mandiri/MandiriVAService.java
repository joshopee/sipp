package com.shopee.sipp.mvc.service.mandiri;

import com.shopee.sipp.system.exception.mandiri.MandiriException;
import com.shopee.sipp.system.exception.mandiri.MandiriInquiryException;
import com.shopee.sipp.system.exception.mandiri.MandiriPaymentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MandiriVAService {

    private static Logger logger = LogManager.getLogger(MandiriVAService.class);

    @Autowired  MandiriInquiryService mandiriInquiryService;
    @Autowired  MandiriPaymentService mandiriPaymentService;

    @Transactional
    public String parse(String payload) throws MandiriException {

        String responsePayload = null;

        if(payload.contains("reverse xmlns=\"bankmandiri.h2h.billpayment.ws\"")){
            ;
        }
        else if(payload.contains("payment xmlns=\"bankmandiri.h2h.billpayment.ws\"")){
            responsePayload = paymentJob(payload);
        }
        else if(payload.contains("inquiry xmlns=\"bankmandiri.h2h.billpayment.ws\"")){
            responsePayload = inquiryJob(payload);
        }

        return responsePayload;
    }


    //INquiry
    private String inquiryJob(String payload) throws MandiriInquiryException{
        try{
            return mandiriInquiryService.call(payload);
        }
        catch(MandiriInquiryException e){
            throw e;
        }
        catch(Exception e){
            throw new MandiriInquiryException(MandiriException.ERRCODE_SYTEM_ERROR, "I0");
        }
    }

    //INquiry
    private String paymentJob(String payload) throws MandiriPaymentException {
        try{
            return mandiriPaymentService.call(payload);
        }
        catch(MandiriPaymentException e){
            throw e;
        }
        catch(Exception e){
            throw new MandiriPaymentException(MandiriException.ERRCODE_SYTEM_ERROR, "P0");
        }
    }



}
