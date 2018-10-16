package com.shopee.sipp.mvc.service;

import com.shopee.sipp.mvc.model.virtual_account.OpenVAInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
public class OpenAmountVAService {

    public static final int VA_HEALTH_VALID = 2;
    public static final int VA_HEALTH_EMPTY = 1;
    public static final int VA_HEALTH_EXCEED_MAX_AMOUNT = 4;

    private static final String QUERY_UPDATE_AMOUNT = "UPDATE va_open SET `current_amount` = `current_amount` + ? WHERE va = ?";
    private static final String QUERY_SELECT_OPENVA = "SELECT desc1, desc2, current_amount currentAmount, max_amount maxAmount, CAST(expiry_datetime as char(19)) expiryDateTime FROM va_open WHERE va = ?";
    private static final String QUERY_INSERT_VA = "INSERT INTO va_open(va, desc1, desc2, current_amount, max_amount) VALUES(?,?,?,?,?)";
    private static final int PLUS_ONE_DAY = (1000 * 60 * 60 * 24);

    @Autowired  JdbcTemplate jdbc;

    /**VAinfo 조회*/
    public OpenVAInfo getOpenVAInfo(final String vaNo){

        long va = Long.parseLong(vaNo);
        List<Map<String, Object>> vaInfos = jdbc.queryForList(QUERY_SELECT_OPENVA, va);
        if(vaInfos.size() <= 0){
            return null;
        }

        OpenVAInfo openVAInfo = new OpenVAInfo();
        openVAInfo.va = va;
        openVAInfo.desc1 = (String)vaInfos.get(0).get("desc1");
        openVAInfo.desc2 = (String)vaInfos.get(0).get("desc2");
        openVAInfo.currentAmount = (int)vaInfos.get(0).get("currentAmount");
        openVAInfo.maxAmount = (int)vaInfos.get(0).get("maxAmount");
        openVAInfo.expiryDateTime = (String)vaInfos.get(0).get("expiryDateTime");

        return openVAInfo;
    }

    /**VA HEALTH 상태 조회*/
    public int getVAHealthStatus(OpenVAInfo openVAInfo, long trxTimeMills){
        return getVAHealthStatus(openVAInfo, trxTimeMills, -100);
    }

    /**페이할떄 VA헬스 체크*/
    public int getVAHealthStatus(OpenVAInfo openVAInfo, long trxTimeMills, float amount){
        if(openVAInfo == null){
            return VA_HEALTH_EMPTY;
        }

        if(amount != -100 && openVAInfo.maxAmount != 0){
            if(openVAInfo.currentAmount + amount > openVAInfo.maxAmount){
                return VA_HEALTH_EXCEED_MAX_AMOUNT;
            }
        }

        return VA_HEALTH_VALID;
    }


    /**쇼피에서 가상계좌 발급 요청*/
    @Transactional
    public String makeNewVA(final String va, final String desc1, final String desc2){
        long lva = Long.parseLong(va);
        jdbc.update(QUERY_INSERT_VA, lva, desc1, desc2, 0, 0);
        return va+"";
    }

    /**결제 완료 처리*/
    @Transactional
    public void finishPayment(String vaNo, int amount) throws Exception{
        long lva = Long.parseLong(vaNo);
        jdbc.update(QUERY_UPDATE_AMOUNT, amount, lva);
    }

}
