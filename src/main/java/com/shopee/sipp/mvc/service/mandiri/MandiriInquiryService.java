package com.shopee.sipp.mvc.service.mandiri;


import com.shopee.sipp.mvc.libs.bank_helper.mandiri_helper.MandiriResponsePayloadManager;
import com.shopee.sipp.mvc.libs.mandiri_helper.parsing_handler.MandiriRequestParsingHandler;
import com.shopee.sipp.mvc.model.virtual_account.CloseVAInfo;
import com.shopee.sipp.mvc.payload.virtual_account.mandiri.inquiry.request.InquiryRequestPayload;
import com.shopee.sipp.mvc.service.CloseAmountVAService;
import com.shopee.sipp.system.exception.SippException;
import com.shopee.sipp.system.exception.mandiri.MandiriException;
import com.shopee.sipp.system.exception.mandiri.MandiriInquiryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//여기가 첫번쨰 진입점임!!
@Service
public class MandiriInquiryService {

    @Autowired
    MandiriVAErrorService mandiriVAErrorService;
    @Autowired
    CloseAmountVAService closeAmountVaService;

    /**INQUIRY 받고 처리.*/
    public String call(String payload) throws MandiriInquiryException {

        try{
            InquiryRequestPayload inquiryRequestPayload = MandiriRequestParsingHandler.parseInquiryRequest(payload); //리퀘스트 페이로드 분석
            String vaNo = inquiryRequestPayload.billKey1;
            long trxTime = System.currentTimeMillis();


            //vano 분석해서 핸폰 가상계좌인지, 일반 계좌인지 분석.

            //일부러 에러 발생--
            if(vaNo.equals("87008001000111")) throw new MandiriInquiryException(MandiriException.ERRCODE_BILL_PAID);
            if(vaNo.equals("87008001000112")) throw new MandiriInquiryException(MandiriException.ERRCODE_BILL_NOFOUND);
            if(vaNo.equals("87008001000113")) {
                try{
                    Thread.sleep(3000);
                }catch(Exception e){
                    ;
                }
                throw new MandiriInquiryException(MandiriException.ERRCODE_TIMEOUT);
            }
            if(vaNo.equals("87008001000114")) throw new MandiriInquiryException(MandiriException.ERRCODE_DB_ERROR);
//            if(vaNo.equals("87008001000115")) throw new MandiriInquiryException(MandiriException.ERRCODE_LINKDOWN);
//            if(vaNo.equals("87008001000116")) throw new MandiriInquiryException(MandiriException.ERRCODE_PAY_BLOCKED, "Expired");
            if(vaNo.equals("87008001000117")) throw new MandiriInquiryException(MandiriException.ERRCODE_PAY_BLOCKED, "Invalid bill. call to Shopee CS");
            if(vaNo.equals("87008001000119")) throw new MandiriInquiryException(MandiriException.ERRCODE_SYTEM_ERROR);
            //--여기까지

            String response = null;
            CloseVAInfo closeVAInfo = closeAmountVaService.getVAInfo(vaNo); //va조회

            mandiriVAErrorService.checkMandiriErrForInquiryProcess(closeVAInfo, trxTime);

            response = MandiriResponsePayloadManager.makeInquirySuccssResponse(closeVAInfo);
            return response;
        }
        catch(MandiriInquiryException e){
            throw e;
        }
        catch(SippException e){
            throw new MandiriInquiryException(MandiriException.ERRCODE_SYTEM_ERROR, "System err(I2)");
        }
    }

    //파싱


}
