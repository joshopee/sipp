package com.shopee.sipp.mvc.service.bri;

import com.shopee.sipp.mvc.libs.CommonUtil;
import com.shopee.sipp.mvc.libs.bank_helper.bri_helper.BriResponsePayloadManager;
import com.shopee.sipp.mvc.model.virtual_account.CloseVAInfo;
import com.shopee.sipp.mvc.model.virtual_account.OpenVAInfo;
import com.shopee.sipp.mvc.service.CloseAmountVAService;
import com.shopee.sipp.mvc.service.OpenAmountVAService;
import com.shopee.sipp.system.exception.SippException;
import com.shopee.sipp.system.exception.bri.BriException;
import com.shopee.sipp.system.exception.bri.BriInquiryException;
import com.shopee.sipp.system.exception.bri.BriPaymentException;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class BRIPaymentService {

    @Autowired  OpenAmountVAService openAmountVAService;
    @Autowired  CloseAmountVAService closeAmountVAService;
    @Autowired  BRIVAErrorService brivaErrorService;

    /**INQUIRY 받고 처리.*/
    public String call(String payload) throws BriPaymentException {
        Map<String, String> paymentMap = CommonUtil.parseJsonToStringMap(payload);
        String idApp = paymentMap.get("IdApp");
        String passApp = paymentMap.get("PassApp");
        String transmissionDateTime = paymentMap.get("TransmisiDateTime"); //20160920084104
        String bankId = paymentMap.get("BankID");
        String terminalId = paymentMap.get("TerminalID");
        String va = paymentMap.get("BrivaNum");
        String paymentAmount = paymentMap.get("PaymentAmount");
        String transactionId = paymentMap.get("TransaksiID");

        long trxTime = DateTimeFormat.forPattern("yyyyMMddHHmmss").parseDateTime(transmissionDateTime).getMillis();
        float amount = Float.parseFloat(paymentAmount);
        int iAmount = (int)amount;

        try{
            if(!idApp.equals("TEST")) throw new BriInquiryException(va, BriInquiryException.ERRCODE_NO_APP_USER_ID);
            if(!passApp.equals("b7aee423dc7489dfa868426e5c950c677925f3b9")) throw new BriInquiryException(va, BriInquiryException.ERRCODE_NO_APP_PASSWORD);

            String companyCode = va.substring(0, 5);
            if(companyCode.equals("26148")){
                //close amount
                CloseVAInfo closeVAInfo = closeAmountVAService.getVAInfo(va);

                if(va.equals("2614890000000101")) throw new BriPaymentException(va, BriException.ERRCODE_INVOICE_NOTFOUND);
                if(va.equals("2614890000000102")) throw new BriPaymentException(va, BriException.ERRCODE_BILLDATA_NOTFOUND);
                if(va.equals("2614890000000103")) throw new BriPaymentException(va, BriException.ERRCODE_BILL_PAID);
                if(va.equals("2614890000000104")) throw new BriPaymentException(va, BriException.ERRCODE_AMOUNT_DIFF);
                if(va.equals("2614890000000105")) throw new BriPaymentException(va, BriException.ERRCODE_TIMEOUT);
                if(va.equals("2614890000000106")) throw new BriPaymentException(va, BriException.ERRCODE_NO_AUTH_API);
                if(va.equals("2614890000000107")) throw new BriPaymentException(va, BriException.ERRCODE_CONN_API_FAILED);
                if(va.equals("2614890000000108")) throw new BriPaymentException(va, BriException.ERRCODE_DB_TIMEOUT);
                if(va.equals("2614890000000109")) throw new BriPaymentException(va, BriException.ERRCODE_DB_ERROR);
                if(va.equals("2614890000000110")) throw new BriPaymentException(va, BriException.ERRCODE_UNKNOWN);

                brivaErrorService.chkErrOnCloseVAPayment(closeVAInfo, trxTime, amount);

                closeAmountVAService.finishPayment(va);

                String resonse = BriResponsePayloadManager.makeCloseAmountPaymentSuccssResponse(closeVAInfo);
                return resonse;
            }else if(companyCode.equals("26147")){

                if(va.equals("2614790000000101")) throw new BriPaymentException(va, BriException.ERRCODE_INVOICE_NOTFOUND);
                if(va.equals("2614790000000102")) throw new BriPaymentException(va, BriException.ERRCODE_BILLDATA_NOTFOUND);
                if(va.equals("2614790000000103")) throw new BriPaymentException(va, BriException.ERRCODE_BILL_PAID);
                if(va.equals("2614790000000104")) throw new BriPaymentException(va, BriException.ERRCODE_AMOUNT_DIFF);
                if(va.equals("2614790000000105")) throw new BriPaymentException(va, BriException.ERRCODE_TIMEOUT);
                if(va.equals("2614790000000106")) throw new BriPaymentException(va, BriException.ERRCODE_NO_AUTH_API);
                if(va.equals("2614790000000107")) throw new BriPaymentException(va, BriException.ERRCODE_CONN_API_FAILED);
                if(va.equals("2614790000000108")) throw new BriPaymentException(va, BriException.ERRCODE_DB_TIMEOUT);
                if(va.equals("2614790000000109")) throw new BriPaymentException(va, BriException.ERRCODE_DB_ERROR);
                if(va.equals("2614790000000110")) throw new BriPaymentException(va, BriException.ERRCODE_UNKNOWN);


                //open amount
                OpenVAInfo openVAInfo = openAmountVAService.getOpenVAInfo(va);
                brivaErrorService.chkErrOnOpenVaPayment(openVAInfo, trxTime, amount);
                openAmountVAService.finishPayment(va, iAmount);

                String resonse = BriResponsePayloadManager.makeOpenAmountPaymentSuccssResponse(openVAInfo, iAmount);
                return resonse;
            }

            throw new SippException();

        } catch(BriPaymentException e){
            throw e;
        }
        catch(SippException e){
            throw new BriPaymentException(va, BriException.ERRCODE_UNKNOWN, "System err(P2)");
        }
        catch(Exception e){
            throw new BriPaymentException(va, BriException.ERRCODE_UNKNOWN, "System err(P2_1)");
        }

    }

    //파싱


}
