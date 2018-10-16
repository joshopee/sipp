package com.shopee.sipp.mvc.service;

import com.shopee.sipp.mvc.model.virtual_account.CloseVAInfo;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class CloseAmountVAService {

    public static final int VA_HEALTH_VALID = 2;
    public static final int VA_HEALTH_EXPIRED = 3;
    public static final int VA_HEALTH_EMPTY = 1;
    public static final int VA_HEALTH_ALREADY_PAID = 4;
    public static final int VA_HEALTH_AMOUNT_NOT_MATCHED = 5;

    private static final String QUERY_UPDATE_PROGRESS = "UPDATE va_close SET `progress` = ? WHERE `va` = ?";
    private static final String QUERY_SELECT_VA = "SELECT desc1, desc2, amount, progress, CAST(expiry_datetime as char(19)) expiryDateTime FROM va_close WHERE `va` = ?";
    private static final String QUERY_INSERT_VA = "INSERT INTO va_close(va, desc1, desc2, desc3, amount, expiry_datetime) VALUES(?,?,?,?,?,?)";
    private static final int PLUS_ONE_DAY = (1000 * 60 * 60 * 24);

    @Autowired  JdbcTemplate jdbc;

    //여기에 핸폰 조회 넣어주기.
//    --

    /**VAinfo 조회*/
    public CloseVAInfo getVAInfo(final String vaNo){

        long va = Long.parseLong(vaNo);

        List<Map<String, Object>> vaInfos = jdbc.queryForList(QUERY_SELECT_VA, va);
        if(vaInfos.size() <= 0){
            return null;
        }

        CloseVAInfo closeVAInfo = new CloseVAInfo();
        closeVAInfo.va = va;
        closeVAInfo.desc1 = (String)vaInfos.get(0).get("desc1");
        closeVAInfo.desc2 = (String)vaInfos.get(0).get("desc2");
        closeVAInfo.desc3 = (String)vaInfos.get(0).get("desc3");
        closeVAInfo.amount = (int)vaInfos.get(0).get("amount");
        closeVAInfo.progress = (String)vaInfos.get(0).get("progress");
        closeVAInfo.expiryDateTime = (String)vaInfos.get(0).get("expiryDateTime");

        return closeVAInfo;
    }

    /**VA HEALTH 상태 조회*/
    public int getVAHealthStatus(CloseVAInfo closeVAInfo, long trxTimeMills){
        return getVAHealthStatus(closeVAInfo, trxTimeMills, -100);
    }

    /**페이할떄 VA헬스 체크*/
    public int getVAHealthStatus(CloseVAInfo closeVAInfo, long trxTimeMills, float amount){
        if(closeVAInfo == null){
            return VA_HEALTH_EMPTY;
        }

        //결제 완료됫냐
        if(closeVAInfo.progress.equals("F")){
            return VA_HEALTH_ALREADY_PAID;
        }

        //만료되엇는지
        DateTime dt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(closeVAInfo.expiryDateTime);
        if(dt.getMillis() < trxTimeMills){
            return VA_HEALTH_EXPIRED;
        }

        //amout체크
        if(amount != -100 && amount != closeVAInfo.amount){
            return VA_HEALTH_AMOUNT_NOT_MATCHED;
        }


        return VA_HEALTH_VALID;
    }


    public String makeNewVAByStatic(long va, final String desc1, final String desc2, final String desc3, final int amount){
        jdbc.update(QUERY_INSERT_VA, va, desc1, desc2, desc3, amount, "2020-10-10 10:00:00");
        return va+"";
    }

    /**쇼피에서 가상계좌 발급 요청*/
    @Transactional
    public String makeNewVA(final String prefix, final String desc1, final String desc2, final String desc3, final int amount){

        StringBuilder sb = new StringBuilder();
        sb.append(prefix);

        for(int i = 0; i < 11; i++){
            sb.append(new Random().nextInt(10));
        }

        String va = sb.toString();
        //innput
        Long iva = Long.parseLong(va);

        jdbc.update(QUERY_INSERT_VA, iva, desc1, desc2, desc3, amount, "2020-10-10 10:00:00");
        return va;
    }

    /**결제 완료 처리*/
    @Transactional
    public void finishPayment(String vaNo) throws Exception{
        Long iva = Long.parseLong(vaNo);
        jdbc.update(QUERY_UPDATE_PROGRESS, "F", iva);
    }

}
