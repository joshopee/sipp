package com.shopee.sipp.mvc.service.mandiri;

import com.shopee.sipp.mvc.model.virtual_account.CloseVAInfo;
import com.shopee.sipp.mvc.service.CloseAmountVAService;
import com.shopee.sipp.system.exception.mandiri.MandiriException;
import com.shopee.sipp.system.exception.mandiri.MandiriInquiryException;
import com.shopee.sipp.system.exception.mandiri.MandiriPaymentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MandiriVAErrorService {

    @Autowired
    CloseAmountVAService closeAmountVaService;

    /**Inquiry시 오류 있는지 확인. 있으면 에러 발생*/
    public void checkMandiriErrForInquiryProcess(CloseVAInfo closeVAInfo, long trxTimeMills) throws MandiriInquiryException{
        try{
            checkMandiriErr(closeVAInfo, trxTimeMills, -100);
        }catch(MandiriException e){
            throw new MandiriInquiryException(e.getErrCode(), e.getErrMsg());
        }
    }

    /**Pamynent 시 오류 있는지 확인. 있으면 에러 발생*/
    public void checkMandiriErrForPaymentProcess(CloseVAInfo closeVAInfo, long trxTimeMills, float amount) throws MandiriPaymentException {
        try{
            checkMandiriErr(closeVAInfo, trxTimeMills, amount);
        }catch(MandiriException e){
            throw new MandiriPaymentException(e.getErrCode(), e.getErrMsg());
        }
    }

    /**VA 리턴 상태 모델 가져오기. 페이시사용.*/
    private void checkMandiriErr(CloseVAInfo closeVAInfo, long trxTimeMills, float amount) throws MandiriException {

        int vaHealth = closeAmountVaService.getVAHealthStatus(closeVAInfo, trxTimeMills, amount);

        switch(vaHealth){
            case CloseAmountVAService.VA_HEALTH_EMPTY:
                throw new MandiriException(MandiriException.ERRCODE_BILL_NOFOUND);

            case CloseAmountVAService.VA_HEALTH_EXPIRED:
                throw new MandiriException(MandiriException.ERRCODE_PAY_BLOCKED, "Expired bill");

            case CloseAmountVAService.VA_HEALTH_AMOUNT_NOT_MATCHED:
                throw new MandiriException(MandiriException.ERRCODE_PAY_BLOCKED, "Amount of bill doesn't match");

            case CloseAmountVAService.VA_HEALTH_ALREADY_PAID: //이건 그냥 조회때는 호출안됨.
                throw new MandiriException(MandiriException.ERRCODE_BILL_PAID, "Already paid bill");
        }
    }


}
