package com.shopee.sipp.mvc.libs;

public class VANumberUtil {
    public final static String COMPANYCODE_MANDIRI_DYNAMIC = "87008";


    /**shardNo랑 localNo로 VA생성*/
    public final static String makeMandiriVANumber(int shardNo, int localNo){
        StringBuilder sb = new StringBuilder();
        sb.append(COMPANYCODE_MANDIRI_DYNAMIC); //5
        sb.append(java.lang.String.format("%03d", shardNo)); //3
        sb.append(java.lang.String.format("%06d", localNo)); //3

        return sb.toString();
    }

    /**va에서 LocalNo조회*/
    public final static int getLocalNo(String vaNumber){
        String t = vaNumber.substring(8);
        int localNo = Integer.parseInt(t);
        return localNo;
    }

    public final static String getCompanyCode(String vaNumber){
        String compCode = vaNumber.substring(0, 5);
        return compCode;
    }

}
