package com.shopee.sipp.mvc.service.bri;

import com.shopee.sipp.mvc.model.virtual_account.CloseVAInfo;
import com.shopee.sipp.mvc.model.virtual_account.OpenVAInfo;
import com.shopee.sipp.mvc.service.CloseAmountVAService;
import com.shopee.sipp.mvc.service.OpenAmountVAService;
import com.shopee.sipp.system.exception.bri.BriException;
import com.shopee.sipp.system.exception.bri.BriInquiryException;
import com.shopee.sipp.system.exception.bri.BriPaymentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BRIVAErrorService {

    @Autowired  CloseAmountVAService closeAmountVaService;
    @Autowired  OpenAmountVAService openAmountVAService;

    public void chkErrOnOpenVaInquiry(OpenVAInfo openVAInfo, long trxTimeMills) throws BriInquiryException{
        int vaHealth = openAmountVAService.getVAHealthStatus(openVAInfo, trxTimeMills);

        switch(vaHealth){
            case OpenAmountVAService.VA_HEALTH_EMPTY:
                throw new BriInquiryException(openVAInfo.va+"",  BriException.ERRCODE_INQUIRY_BILL_NOTFOUND);

        }

    }

    public void chkErrOnOpenVaPayment(OpenVAInfo openVAInfo, long trxTimeMills, float amount) throws BriPaymentException{
        int vaHealth = openAmountVAService.getVAHealthStatus(openVAInfo, trxTimeMills, amount);

        switch(vaHealth){
            case OpenAmountVAService.VA_HEALTH_EMPTY:
                throw new BriPaymentException(openVAInfo.va + "", BriException.ERRCODE_INQUIRY_BILL_NOTFOUND);

            case OpenAmountVAService.VA_HEALTH_EXCEED_MAX_AMOUNT:
                throw new BriPaymentException(openVAInfo.va+"", BriException.ERRCODE_BILL_PAID, "Exceed max amount");

        }

    }

    /**Inquiry시 오류 있는지 확인. 있으면 에러 발생*/
    public void chkErrOnCloseVAInquiry(CloseVAInfo closeVAInfo, long trxTimeMills) throws BriInquiryException {

        int vaHealth = closeAmountVaService.getVAHealthStatus(closeVAInfo, trxTimeMills);

        switch(vaHealth){
            case CloseAmountVAService.VA_HEALTH_EMPTY:
                throw new BriInquiryException(closeVAInfo.va+"", BriException.ERRCODE_INQUIRY_BILL_NOTFOUND);

            case CloseAmountVAService.VA_HEALTH_EXPIRED:
                throw new BriInquiryException(closeVAInfo.va+"", BriException.ERRCODE_INQUIRY_EXPIRED);

            case CloseAmountVAService.VA_HEALTH_ALREADY_PAID:
                throw new BriInquiryException(closeVAInfo.va+"", BriException.ERRCODE_INQUIRY_ALREADY_PAID);
        }

    }

    /**Pamynent 시 오류 있는지 확인. 있으면 에러 발생*/
    public void chkErrOnCloseVAPayment(CloseVAInfo closeVAInfo, long trxTimeMills, float amount) throws BriPaymentException {
        int vaHealth = closeAmountVaService.getVAHealthStatus(closeVAInfo, trxTimeMills, amount);

        switch(vaHealth){
            case CloseAmountVAService.VA_HEALTH_EMPTY:
                throw new BriPaymentException(closeVAInfo.va+"", BriException.ERRCODE_INVOICE_NOTFOUND);

            case CloseAmountVAService.VA_HEALTH_EXPIRED:
                throw new BriPaymentException(closeVAInfo.va+"", BriException.ERRCODE_INQUIRY_BILL_NOTFOUND);

            case CloseAmountVAService.VA_HEALTH_AMOUNT_NOT_MATCHED:
                throw new BriPaymentException(closeVAInfo.va+"", BriException.ERRCODE_AMOUNT_DIFF);

            case CloseAmountVAService.VA_HEALTH_ALREADY_PAID: //이건 그냥 조회때는 호출안됨.
                throw new BriPaymentException(closeVAInfo.va+"", BriException.ERRCODE_BILL_PAID);
        }
    }



}
