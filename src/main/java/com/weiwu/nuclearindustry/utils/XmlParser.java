package com.weiwu.nuclearindustry.utils;

import com.weiwu.nuclearindustry.config.SystemConfig;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class XmlParser {

    /**
     * recursive traversal element
     * @param element
     * @param result
     */
    public void queryElement(Element element, List<String> result) {
        List<Element> elements = element.elements();
        result.add(element.getTextTrim());
        for (Element elm : elements) {
            result.add(elm.getText());
            List<Element> elms = elm.elements();
            for (Element childElm : elms) {
                queryElement(childElm, result);
            }
        }
    }

    public void queryElement(Element element, List<String> entries, List<String> result) {
        if (entries.contains(element.getName())) {
            String text = element.getTextTrim();
            if(!text.isEmpty()) result.add(element.getName() + " " + text);
            List<Element> elements = element.elements();
            for (Element elm : elements) {
                if(entries.contains(elm.getName())){
                    String et = elm.getTextTrim();
                    if (!et.isEmpty()) result.add(elm.getName() + " " + et);
                    List<Element> elms = elm.elements();
                    for (Element childElm : elms) {
                        queryElement(childElm, entries, result);
                    }
                }
            }
        }
    }

    public void queryElement(Element element, List<String> entries, Map<String, String> map) {
        if (entries.contains(element.getName())) {
            String text = element.getTextTrim();
            if(!text.isEmpty()) map.put(element.getName(), text);
            List<Element> elements = element.elements();
            for (Element elm : elements) {
                if(entries.contains(elm.getName())){
                    String et = elm.getTextTrim();
                    if (!et.isEmpty()) map.put(elm.getName(), et);
                    List<Element> elms = elm.elements();
                    for (Element childElm : elms) {
                        queryElement(childElm, entries, map);
                    }
                }
            }
        }
    }

    public void queryElement(Element element, Element parent, List<String> entries, Map<String, String> map) {
        if (entries.contains(element.getName())) {
            String text = element.getTextTrim();
            String name = parent != null ? parent.getName() + " " + element.getName() : element.getName();
            if(!text.isEmpty()) map.put(name, text);
            List<Element> elements = element.elements();
            for (Element elm : elements) {
                if(entries.contains(elm.getName())){
                    String et = elm.getTextTrim();
                    String en = element.getName() + " " + elm.getName();
                    if (!et.isEmpty()) map.put(en, et);
                    List<Element> elms = elm.elements();
                    for (Element childElm : elms) {
                        queryElement(childElm, elm, entries, map);
                    }
                }
            }
        }
    }

    public HashMap<String, String> parseGF3XML(String filePath) throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new File(filePath));
        Element rootElement = document.getRootElement();
        List<String> entries = Arrays.stream(SystemConfig.GF3).collect(Collectors.toList());
        HashMap<String, String> hashMap = new HashMap<>();
        if(rootElement != null) queryElement(rootElement, null, entries, hashMap);
        return hashMap;
    }

    public HashMap<String, String> parseGF3XML(File file) throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(file);
        Element rootElement = document.getRootElement();
        List<String> entries = Arrays.stream(SystemConfig.GF3).collect(Collectors.toList());
        HashMap<String, String> hashMap = new HashMap<>();
        if(rootElement != null) queryElement(rootElement, null, entries, hashMap);
        return hashMap;
    }

    public HashMap<String, String> parseGF124567XML(String filePath) throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new File(filePath));
        Element rootElement = document.getRootElement();
        String rootElementName = rootElement.getName();
        Element queriedElement = null;
        if(rootElementName == "GroupProductCheckData"){
            Element productMetaDatas = rootElement.element("ProductMetaDatas");
            queriedElement = productMetaDatas.element("ProductMetaData");
        } else if(rootElementName == "ProductMetaDatas"){
            queriedElement = rootElement.element("ProductMetaData");
        } else if(rootElementName == "ProductMetaData"){
            queriedElement = rootElement;
        }
        List<String> entries = Arrays.stream(SystemConfig.GF124567).collect(Collectors.toList());
        HashMap<String, String> hashMap = new HashMap<>();
        if(queriedElement != null) queryElement(queriedElement, null, entries, hashMap);
        return hashMap;
    }

    public HashMap<String, String> parseGF124567XML(File file) throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(file);
        Element rootElement = document.getRootElement();
        String rootElementName = rootElement.getName();
        Element queriedElement = null;
        if(rootElementName == "GroupProductCheckData"){
            Element productMetaDatas = rootElement.element("ProductMetaDatas");
            queriedElement = productMetaDatas.element("ProductMetaData");
        } else if(rootElementName == "ProductMetaDatas"){
            queriedElement = rootElement.element("ProductMetaData");
        } else if(rootElementName == "ProductMetaData"){
            queriedElement = rootElement;
        }
        List<String> entries = Arrays.stream(SystemConfig.GF124567).collect(Collectors.toList());
        HashMap<String, String> hashMap = new HashMap<>();
        if(queriedElement != null) queryElement(queriedElement, null, entries, hashMap);
        return hashMap;
    }
}
