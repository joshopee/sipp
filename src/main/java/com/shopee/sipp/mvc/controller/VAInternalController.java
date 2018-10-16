package com.shopee.sipp.mvc.controller;

import com.shopee.sipp.mvc.service.bri.BRIVAService;
import com.shopee.sipp.mvc.service.mandiri.MandiriVAService;
import com.shopee.sipp.system.exception.bri.BriException;
import com.shopee.sipp.system.exception.mandiri.MandiriException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/internal/va")
public class VAInternalController {
    private static Logger logger = LogManager.getLogger(VAInternalController.class);

    @Autowired  MandiriVAService mandiriVAService;
    @Autowired  BRIVAService brivaService;

    @RequestMapping(value = "/mandiri", method = RequestMethod.POST)
    public String mandiriVA(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody String requestBody) throws MandiriException {
        response.addHeader("accept", "text/xml, text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2");

        String responsePayload = mandiriVAService.parse(requestBody);
        return responsePayload;
    }


    @RequestMapping(value = "/bri", method = RequestMethod.POST)
    public String briVA(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody String requestBody) throws BriException {
        response.addHeader("accept", "application/json, text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2");

        String responsePayload = brivaService.parse(requestBody);
        return responsePayload;
    }

}
