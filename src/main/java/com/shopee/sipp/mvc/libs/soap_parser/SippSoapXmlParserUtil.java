package com.shopee.sipp.mvc.libs.soap_parser;

import com.shopee.sipp.system.exception.SippException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

public class SippSoapXmlParserUtil {
    public final static String AUTO_GENERATED_CHILD_XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";


    public static String build(String frameXml, String replaceKey, Object data, Class<?> cls) throws SippException {
        try{
            JAXBContext jaxbContext = JAXBContext.newInstance(cls);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            StringWriter sw = new StringWriter();

            jaxbMarshaller.marshal(data, sw);
            String xmlData = sw.toString();
            sw.flush();
            sw.close();

            xmlData = xmlData.replace(AUTO_GENERATED_CHILD_XML_HEADER, "");
            String xmlResponse = frameXml.replace(replaceKey, xmlData);
            return xmlResponse;
        }catch(Exception e){
            throw new SippException();
        }

    }


}
