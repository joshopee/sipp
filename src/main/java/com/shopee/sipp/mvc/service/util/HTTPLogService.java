package com.shopee.sipp.mvc.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class HTTPLogService {

    @Autowired
    JdbcTemplate jdbc;

    public void inputHttpInLog(String header, String body){
        inputLog("IN", header, body);
    }

    public void inputHttpOutLog(String header, String body){
        inputLog("OUT", header, body);
    }

    private void inputLog(String type, String header, String payload){

        int updated = jdbc.update("INSERT INTO log(`type`, header, payload) VALUES(?, ?, ?)", type, header, payload);
        int i = 0;
        i++;

    }
}
