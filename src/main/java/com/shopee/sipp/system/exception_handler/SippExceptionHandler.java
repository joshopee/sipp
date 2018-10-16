package com.shopee.sipp.system.exception_handler;

import com.shopee.sipp.system.exception.bri.BriInquiryException;
import com.shopee.sipp.system.exception.bri.BriPaymentException;
import com.shopee.sipp.system.exception.mandiri.MandiriInquiryException;
import com.shopee.sipp.system.exception.mandiri.MandiriPaymentException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class SippExceptionHandler {


    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MandiriInquiryException.class)
    public @ResponseBody
    String handleException(MandiriInquiryException e, HttpServletResponse response){
        return e.getErrorPayload();
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MandiriPaymentException.class)
    public @ResponseBody
    String handleException(MandiriPaymentException e, HttpServletResponse response){
        return e.getErrorPayload();
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BriInquiryException.class)
    public @ResponseBody
    String handleException(BriInquiryException e, HttpServletResponse response){
        return e.getErrorPayload();
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BriPaymentException.class)
    public @ResponseBody
    String handleException(BriPaymentException e, HttpServletResponse response){
        return e.getErrorPayload();
    }

}
