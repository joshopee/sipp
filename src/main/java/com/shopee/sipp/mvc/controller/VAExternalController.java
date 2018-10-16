package com.shopee.sipp.mvc.controller;

import com.shopee.sipp.mvc.service.CloseAmountVAService;
import com.shopee.sipp.mvc.service.OpenAmountVAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController   // This means that this class is a Controller
@RequestMapping(value = "/external/va")
public class VAExternalController {

    @Autowired  CloseAmountVAService closeAmountVaService;
    @Autowired  OpenAmountVAService openAmountVAService;


    @RequestMapping(value = "/bri/close/static", method = RequestMethod.POST)
    public Map<String, Object> makeBriCloseAccountStatic() throws Exception{

        long va = 2614890000000000L;
        for(int i = 1 ; i <= 1000; i++){

            int amount = (new Random().nextInt(10)+1) * 1000;
            try{
                closeAmountVaService.makeNewVAByStatic(va + i, "name_"+i, "email_" + i, "desc_" + i, amount);
            }catch(Exception e){
                ;
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("vaNo", "많아");
        return result;
    }

    @RequestMapping(value = "/bri/open/static", method = RequestMethod.POST)
    public Map<String, Object> makeBriOpenAccountStatic() throws Exception{

        long va = 2614790000000000L;
        for(int i = 1 ; i <= 1000; i++){

            try{
                String vaNo = openAmountVAService.makeNewVA((va+i)+"", "Shopee kredit", "oname" + i);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("vaNo", "많아");
        return result;
    }


    @RequestMapping(value = "/bri/close/random", method = RequestMethod.POST)
    public Map<String, Object> makeBriCloseAccount() throws Exception{

        Random r = new Random();
        int a = r.nextInt(10000);
        int amount = r.nextInt(100) * 1000;
        String va = closeAmountVaService.makeNewVA("26148", "name_"+a, "email_" + a, "desc_" + a, amount);

        Map<String, Object> result = new HashMap<>();
        result.put("vaNo", va);
        return result;
    }

    @RequestMapping(value = "/bri/open/random", method = RequestMethod.POST)
    public Map<String, Object> makeBriOpenAccount() throws Exception{

        StringBuilder sb = new StringBuilder();
        sb.append("26147");

        for(int i = 0; i < 10; i ++){
            sb.append(new Random().nextInt(10));
        }

        String va = sb.toString();

        String vaNo = openAmountVAService.makeNewVA(va, "Shopee kredit", "Josong" + new Random().nextInt(100));
        Map<String, Object> result = new HashMap<>();
        result.put("vaNo", vaNo);
        return result;
    }

}
