package com.shopee.sipp.mvc.service.mandiri;

import com.shopee.sipp.mvc.libs.bank_helper.mandiri_helper.MandiriResponsePayloadManager;
import com.shopee.sipp.mvc.libs.mandiri_helper.parsing_handler.MandiriRequestParsingHandler;
import com.shopee.sipp.mvc.model.virtual_account.CloseVAInfo;
import com.shopee.sipp.mvc.payload.virtual_account.mandiri.inquiry.request.PaymentRequestPayload;
import com.shopee.sipp.mvc.service.CloseAmountVAService;
import com.shopee.sipp.system.exception.SippException;
import com.shopee.sipp.system.exception.mandiri.MandiriException;
import com.shopee.sipp.system.exception.mandiri.MandiriPaymentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MandiriPaymentService {

    @Autowired
    CloseAmountVAService closeAmountVaService;
    @Autowired
    MandiriVAErrorService mandiriVAErrorService;

    /**INQUIRY 받고 처리.*/
    public String call(String payload) throws MandiriPaymentException {
        try{
            PaymentRequestPayload paymentRequestPayload = MandiriRequestParsingHandler.parsePaymentRequest(payload);

            String vaNo = paymentRequestPayload.billKey1;
            float requestAmount = Float.parseFloat(paymentRequestPayload.paymentAmount);
            long trxMills = System.currentTimeMillis();

            //일부러 에러 발생--
            if(vaNo.equals("87008001000111")) throw new MandiriPaymentException(MandiriException.ERRCODE_BILL_PAID);
            if(vaNo.equals("87008001000112")) throw new MandiriPaymentException(MandiriException.ERRCODE_BILL_NOFOUND);
            if(vaNo.equals("87008001000213")) {
                try{
                    Thread.sleep(3000);
                }catch(Exception e){
                    ;
                }
                throw new MandiriPaymentException(MandiriException.ERRCODE_TIMEOUT);
            }
            if(vaNo.equals("87008001000214")) throw new MandiriPaymentException(MandiriException.ERRCODE_DB_ERROR);
            if(vaNo.equals("87008001000215")) throw new MandiriPaymentException(MandiriException.ERRCODE_LINKDOWN);
            if(vaNo.equals("87008001000216")) throw new MandiriPaymentException(MandiriException.ERRCODE_PAY_BLOCKED, "Expired");
            if(vaNo.equals("87008001000217")) throw new MandiriPaymentException(MandiriException.ERRCODE_PAY_BLOCKED, "Invalid bill. call to Shopee CS");
            if(vaNo.equals("87008001000218")) throw new MandiriPaymentException(MandiriException.ERRCODE_PAY_BLOCKED, "Check amount of the bill");
            if(vaNo.equals("87008001000219")) throw new MandiriPaymentException(MandiriException.ERRCODE_SYTEM_ERROR);
            //--여기까지

            String response = null;
            CloseVAInfo closeVAInfo = closeAmountVaService.getVAInfo(vaNo); //va조회

            mandiriVAErrorService.checkMandiriErrForPaymentProcess(closeVAInfo, trxMills, requestAmount);
            response = MandiriResponsePayloadManager.makePaymentSuccssResponse(closeVAInfo);

            closeAmountVaService.finishPayment(vaNo);

            return response;
        } catch(MandiriPaymentException e){
            throw e;
        }
        catch(SippException e){
            throw new MandiriPaymentException(MandiriException.ERRCODE_SYTEM_ERROR, "System err(P2)");
        }
        catch(Exception e){
            throw new MandiriPaymentException(MandiriException.ERRCODE_SYTEM_ERROR, "System err(P2_1)");
        }


    }

    //파싱


}
