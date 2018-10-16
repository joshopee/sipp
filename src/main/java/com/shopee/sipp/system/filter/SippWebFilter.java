package com.shopee.sipp.system.filter;

import com.shopee.sipp.mvc.libs.sipp_webfilter.SippHttpRequestWrapper;
import com.shopee.sipp.mvc.libs.sipp_webfilter.SippWebFilterUtil;
import com.shopee.sipp.mvc.service.util.HTTPLogService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SippWebFilter implements Filter {

    private static Logger logger = LogManager.getLogger(SippWebFilter.class);

    @Autowired
    HTTPLogService httpLogService;

    private FilterConfig filterConfig = null;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                filterConfig.getServletContext());
    }

    @Override
    public void destroy(){
        filterConfig = null;
        logger.info("Destroyed");
    }

    //리퀘스트 인컴
    private void onRequest(Map<String, String> headers, String requestPayload){
        httpLogService.inputHttpInLog(headers.toString(), requestPayload);
    }

    //리턴 된 후
    private void onResponse(Map<String, String> headers, String responsePayload){
        httpLogService.inputHttpOutLog(headers.toString(), responsePayload);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //준비
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        //입, 출 wrapper 준비
        SippHttpRequestWrapper readWrapper = new SippHttpRequestWrapper(httpRequest);
        SippWebFilterUtil.CharResponseWrapper responseWrapper = new SippWebFilterUtil.CharResponseWrapper(httpResponse);

        String requestPayLoad = new String(readWrapper.getInputStream().readAllBytes());
        onRequest(getHeaders(httpRequest), requestPayLoad);

        //End point 동작 시작
        chain.doFilter(readWrapper, responseWrapper);
        String responsePayload = new String(responseWrapper.getByteArray());

        //리턴 ㄱㄱ
        httpResponse.getOutputStream().write(responsePayload.getBytes());
        httpResponse.getOutputStream().flush();

        onResponse(getHeaders(response), responsePayload);

        //끝
        requestPayLoad = null;
        responsePayload = null;
        readWrapper = null;
        responseWrapper = null;

    }

    //헤더 가져오기
    private Map<String, String> getHeaders(Object requestOrResponse){
        Map<String, String> headers = new HashMap<>();

        HttpServletRequest request = null;
        HttpServletResponse response = null;

        if(requestOrResponse instanceof HttpServletRequest){


            request = (HttpServletRequest)requestOrResponse;
            Iterator<String> itr = request.getHeaderNames().asIterator();

            while(itr.hasNext()){
                String headerName = itr.next();
                String headerValue = request.getHeader(headerName);

                headers.put(headerName, headerValue);
            }
        }
        else if(requestOrResponse instanceof HttpServletResponse){
            response = (HttpServletResponse)requestOrResponse;

            Iterator<String> itr = response.getHeaderNames().iterator();

            while(itr.hasNext()){
                String headerName = itr.next();
                String headerValue = response.getHeader(headerName);
                headers.put(headerName, headerValue);
            }
        }


        return headers;
    }



}
