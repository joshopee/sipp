package com.shopee.sipp.mvc.libs;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class CommonUtil {
    public static final Map<String, String> parseJsonToStringMap(String json){
        ObjectMapper mapper = new ObjectMapper();
        try{

            return mapper.readValue(json, new TypeReference<Map<String, String>>(){});
        }catch(IOException e){
            return null;
        }
    }
}
