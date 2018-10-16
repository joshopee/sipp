package com.shopee.sipp.mvc.libs.sipp_webfilter;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by josong on 2016-12-16.
 */
public class SippWebFilterUtil {

    /**ByteArrayServletStream*/
    public static class ByteArrayServletStream extends ServletOutputStream {
        ByteArrayOutputStream baos;

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setWriteListener(WriteListener listener) {

        }

        ByteArrayServletStream(ByteArrayOutputStream baos){
            this.baos = baos;
        }

        @Override
        public void write(int param) throws IOException {
            baos.write(param);
        }
    }

    /**바이트를 바이트어레이러*/
    public static class ByteArrayPrintWriter{
        private ByteArrayOutputStream baos = new ByteArrayOutputStream();
        private PrintWriter pw = new PrintWriter(baos);
        private ServletOutputStream sos = new ByteArrayServletStream(baos);

        public PrintWriter getWriter(){
            return pw;
        }

        public ServletOutputStream getStream(){
            return sos;
        }

        byte[] toByteArray(){
            return baos.toByteArray();
        }
    }

    /**CharResponseWrapper*/
    public static class CharResponseWrapper extends HttpServletResponseWrapper {
        private ByteArrayPrintWriter output;
        private boolean usingWriter;

        public CharResponseWrapper(HttpServletResponse response){
            super(response);
            usingWriter = false;
            output = new ByteArrayPrintWriter();
        }

        public byte[] getByteArray(){
            return output.toByteArray();
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException
        {
            // will error out, if in use
            if (usingWriter) {
                super.getOutputStream();
            }
            usingWriter = true;
            return output.getStream();
        }

        @Override
        public PrintWriter getWriter() throws IOException {
            // will error out, if in use
            if (usingWriter) {
                super.getWriter();
            }
            usingWriter = true;
            return output.getWriter();
        }

        public String toString(){
            return output.toString();
        }
    }
}
