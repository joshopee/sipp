package com.shopee.sipp.mvc.libs.soap_parser;


import com.shopee.sipp.system.exception.SippException;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class SippSoapXmlParser {

    public final static String AUTO_GENERATED_CHILD_XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";

    SAXParserFactory factory;
    SAXParser saxParser;


    public static final void Parse(String payload, final XMLParsingListener xmlParsingListener) throws SippException {

        final SippSoapXmlParser sippSoapXMLParser = new SippSoapXmlParser();
        sippSoapXMLParser.init();
        sippSoapXMLParser.parse(payload, xmlParsingListener);


    }

    public void init() throws SippException {
        try{
            this.factory = SAXParserFactory.newInstance();
            this.saxParser = factory.newSAXParser();
        }catch(Exception e){
            throw new SippException();
        }

    }

    public void parse(String payload, final XMLParsingListener xmlParsingListener){
        DefaultHandler handler = new DefaultHandler(){
            Stack<String> tags = new Stack<>();

            //현재 패러너트 태그 불러옴
            private String getCurrentTag(){
                if(tags.size() <= 0){
                    return null;
                }else{
                    return tags.peek();
                }
            }

            private String[] getParentTags(){
                return tags.toArray(new String[tags.size()]);
            }

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

                Map<String, String> mapAttributes = null;
                //애트리뷰트 ㅣㅇㅆ음 넣기
                if(attributes.getLength() > 0){
                    mapAttributes = new HashMap<>();
                    for(int i = 0; i < attributes.getLength(); i++){
                        mapAttributes.put(attributes.getQName(i), attributes.getValue(i));
                    }
                }
                //태그 시작했음 알림
                xmlParsingListener.onStartTag(getParentTags(), qName, mapAttributes);

                //패런트 태그 추가.
                tags.push(qName);
            }

            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {
                xmlParsingListener.onEndTag(getParentTags(), qName);
                //패런트 태그 없애기
                tags.pop();
            }

            @Override
            public void characters(char[] ch, int start, int length) throws SAXException {
                super.characters(ch, start, length);
                String value = new String(ch, start, length).trim();
                if(value.equals("")) return;

                xmlParsingListener.onValue(getParentTags(), getCurrentTag(), new String(ch, start, length).trim());
            }
        };

        try{
            saxParser.parse(new InputSource(new StringReader(payload)), handler);
        }catch(Exception e){
            ;
        }
    }

    public interface XMLParsingListener{
        void onStartTag(String[] parentTags, String tagName, Map<String, String> attributes);
        void onValue(String[] parentTags, String tagName, String value);
        void onEndTag(String[] parentTags, String tagName) throws SAXException;
    }
}
