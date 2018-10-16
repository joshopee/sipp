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
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

//여기가 첫번쨰 진입점임!!
@Service
public class BRIInquiryService {

    @Autowired  BRIVAErrorService brivaErrorService;
    @Autowired  CloseAmountVAService closeAmountVaService;
    @Autowired  OpenAmountVAService openAmountVAService;


    /**INQUIRY 받고 처리.*/
    public String call(String payload) throws BriInquiryException {
        Map<String, String> inquiryMap = CommonUtil.parseJsonToStringMap(payload);
        String idApp = inquiryMap.get("IdApp");
        String passApp = inquiryMap.get("PassApp");
        String transmisiDateTime = inquiryMap.get("TransmisiDateTime"); //20160920084020
        String bankId = inquiryMap.get("BankID"); //002(bri)
        String terminalId = inquiryMap.get("TerminalID"); //1 ...
        String briVaNum = inquiryMap.get("BrivaNum");
        long trxTime = DateTimeFormat.forPattern("yyyyMMddHHmmss").parseDateTime(transmisiDateTime).getMillis();
        String companyCode = briVaNum.substring(0, 5);

        try{
            if(companyCode.equals("26148")){
                //close amount
                if(briVaNum.equals("2614890000000011")) throw new BriInquiryException(briVaNum, BriInquiryException.ERRCODE_INQUIRY_ALREADY_PAID);
                if(briVaNum.equals("2614890000000012")) throw new BriInquiryException(briVaNum, BriInquiryException.ERRCODE_INQUIRY_BILL_NOTFOUND);
                if(briVaNum.equals("2614890000000013")) throw new BriInquiryException(briVaNum, BriInquiryException.ERRCODE_INQUIRY_EXPIRED);
                if(briVaNum.equals("2614890000000014")) throw new BriInquiryException(briVaNum, BriInquiryException.ERRCODE_INQUIRY_GENERAL);

                CloseVAInfo closeVAInfo = closeAmountVaService.getVAInfo(briVaNum); //va조회
                brivaErrorService.chkErrOnCloseVAInquiry(closeVAInfo, trxTime);

                String response = BriResponsePayloadManager.makeCloseAmountInquirySuccssResponse(closeVAInfo);
                return response;
            }else if(companyCode.equals("26147")){
                //open amount

                if(briVaNum.equals("2614790000000011")) throw new BriInquiryException(briVaNum, BriInquiryException.ERRCODE_INQUIRY_ALREADY_PAID);
                if(briVaNum.equals("2614790000000012")) throw new BriInquiryException(briVaNum, BriInquiryException.ERRCODE_INQUIRY_BILL_NOTFOUND);
                if(briVaNum.equals("2614790000000013")) throw new BriInquiryException(briVaNum, BriInquiryException.ERRCODE_INQUIRY_EXPIRED);
                if(briVaNum.equals("2614790000000014")) throw new BriInquiryException(briVaNum, BriInquiryException.ERRCODE_INQUIRY_GENERAL);


                OpenVAInfo openVAInfo = openAmountVAService.getOpenVAInfo(briVaNum);
                brivaErrorService.chkErrOnOpenVaInquiry(openVAInfo, trxTime);
                String resonse = BriResponsePayloadManager.makeOpenAmountInquirySuccssResponse(openVAInfo);
                return resonse;
            }

            throw new SippException();
        }
        catch(BriInquiryException e){
            throw e;
        }
        catch(SippException e){
            throw new BriInquiryException(briVaNum, BriException.ERRCODE_INQUIRY_GENERAL);
        }
    }

    //파싱


}
