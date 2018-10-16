package com.shopee.sipp.mvc.libs.sipp_webfilter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class TestWriteWrapper  extends HttpServletResponseWrapper {
    public TestWriteWrapper(HttpServletResponse response) {
        super(response);
    }
}
