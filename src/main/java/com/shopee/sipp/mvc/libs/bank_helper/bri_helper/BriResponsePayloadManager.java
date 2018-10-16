package com.shopee.sipp.mvc.libs.bank_helper.bri_helper;

import com.google.gson.Gson;
import com.shopee.sipp.mvc.model.virtual_account.CloseVAInfo;
import com.shopee.sipp.mvc.model.virtual_account.OpenVAInfo;
import com.shopee.sipp.system.exception.SippException;

import java.util.HashMap;
import java.util.Map;

public class BriResponsePayloadManager {

    static Gson gson = new Gson();
    /**페이먼트 성공했을때 리스판스*/
    public final static String makeCloseAmountPaymentSuccssResponse(CloseVAInfo closeVAInfo) throws SippException{

//{ "StatusPayment":{ "ErrorDesc":"Sukses", "ErrorCode":"00", "isError":"0"}, ,"Info1":"Tagihan" "Info2":"567890123400012001", "Info3":"John Doe", "Info4":"25000", "Info5":"" }

        Map<String, Object> returnMap = new HashMap<>();
        Map<String, String> statusMap = new HashMap<>();
        returnMap.put("StatusPayment", statusMap);

        statusMap.put("ErrorDesc", "Success");
        statusMap.put("ErrorCode", "00");
        statusMap.put("isError", "0");

        returnMap.put("Info1", closeVAInfo.desc1); //제목
        returnMap.put("Info2", closeVAInfo.va+""); //VA
        returnMap.put("Info3", closeVAInfo.desc2); //제목
        returnMap.put("Info4", closeVAInfo.amount+""); //가격
        returnMap.put("Info5", ""); //공란

        String json = gson.toJson(returnMap);
        return json;
    }

    /**Inquiry 성공 리스판스 */
    public static String makeCloseAmountInquirySuccssResponse(CloseVAInfo closeVAInfo) throws SippException {
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, String> billDetailMap = new HashMap<>();
        returnMap.put("BillDetail", billDetailMap);

        billDetailMap.put("BillAmount", closeVAInfo.amount+"");
        billDetailMap.put("BillName", closeVAInfo.desc2);
        billDetailMap.put("BrivaNum", closeVAInfo.va+"");

        returnMap.put("Info1", closeVAInfo.desc1); //제목
        returnMap.put("Info2", ""); //제목
        returnMap.put("Info3", ""); //제목
        returnMap.put("Info4", ""); //제목
        returnMap.put("Info5", ""); //제목
        returnMap.put("StatusBill", "0"); //제목
        returnMap.put("Currency", "IDR"); //제목

        String json = gson.toJson(returnMap);
        return json;
    }

    public final static String makeOpenAmountPaymentSuccssResponse(OpenVAInfo openVAInfo, int successAmount) throws SippException{

//{ "StatusPayment":{ "ErrorDesc":"Sukses", "ErrorCode":"00", "isError":"0"}, ,"Info1":"Tagihan" "Info2":"567890123400012001", "Info3":"John Doe", "Info4":"25000", "Info5":"" }

        Map<String, Object> returnMap = new HashMap<>();
        Map<String, String> statusMap = new HashMap<>();
        returnMap.put("StatusPayment", statusMap);

        statusMap.put("ErrorDesc", "Success");
        statusMap.put("ErrorCode", "00");
        statusMap.put("isError", "0");

        returnMap.put("Info1", openVAInfo.desc1); //제목
        returnMap.put("Info2", openVAInfo.va); //VA
        returnMap.put("Info3", openVAInfo.desc2); //제목
        returnMap.put("Info4", successAmount); //가격
        returnMap.put("Info5", ""); //공란

        String json = gson.toJson(returnMap);
        return json;
    }

    /**Inquiry 성공 리스판스 */
    public static String makeOpenAmountInquirySuccssResponse(OpenVAInfo openVAInfo) throws SippException {
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, String> billDetailMap = new HashMap<>();
        returnMap.put("BillDetail", billDetailMap);

        billDetailMap.put("BillAmount", "0");
        billDetailMap.put("BillName", openVAInfo.desc2);
        billDetailMap.put("BrivaNum", openVAInfo.va+"");

        returnMap.put("Info1", openVAInfo.desc1); //제목
        returnMap.put("Info2", ""); //제목
        returnMap.put("Info3", ""); //제목
        returnMap.put("Info4", ""); //제목
        returnMap.put("Info5", ""); //제목
        returnMap.put("StatusBill", "0"); //제목
        returnMap.put("Currency", "IDR"); //제목

        String json = gson.toJson(returnMap);
        return json;
    }



}
