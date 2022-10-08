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

    private static final SAXReader saxReader = new SAXReader();
    private Element rootElement; //root element
    private Element resultElement; //element need to parse

    public XmlParser(){}

    private void makeElement(File file) throws DocumentException {
        Document document = saxReader.read(file);
        rootElement = document.getRootElement();
    }

    /**
     * recursive traversal element
     * @param element
     * @param result
     */
    public void queryElement(Element element, List<String> result) {
        List<Element> elements = element.elements();
        result.add(element.getTextTrim());
        for (Element elm : elements) {
            result.add(elm.getTextTrim());
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

    private void findElement(Element rootElement, String elementName){
        String name = rootElement.getName();
        if(name == elementName || resultElement != null){
            if(resultElement == null){
                resultElement = rootElement;
            }
            return;
        }
        List<Element> elements = rootElement.elements();
        for (Element element : elements){
            findElement(element, elementName);
        }
    }

    public HashMap<String, String> mapFilter(HashMap<String, String> hashMap){
        HashMap<String, String> map = new HashMap<>();
        hashMap.forEach((key, value)->{
            String mKey = SystemConfig.GF3Filter.get(key);
            if(mKey != null){
                map.put(mKey, value);
            } else {
                String[] strings = key.split(" ");
                String upperKey = NameUtil.lowerToUpperCamelCase(strings[1]);
                map.put(strings[0] + " " + upperKey, value);
            }
        });
        return map;
    }

    public HashMap<String, String> parseXml(File file) throws DocumentException {
        makeElement(file);
        String fileName = file.getName();
        HashMap<String, String> hashMap = new HashMap<>();
        if(fileName.startsWith("GF3")){
            List<String> entries = Arrays.stream(SystemConfig.GF3).collect(Collectors.toList());
            if(rootElement != null) queryElement(rootElement, null, entries, hashMap);
            hashMap = mapFilter(hashMap);
        }
        if(fileName.startsWith("GF1") || fileName.startsWith("GF2") || fileName.startsWith("GF4") ||
                fileName.startsWith("GF5") || fileName.startsWith("GF6") || fileName.startsWith("GF7") ||
                fileName.startsWith("ZY") || fileName.startsWith("zy")){
            findElement(rootElement, "ProductMetaData");
            List<String> entries = Arrays.stream(SystemConfig.GF124567).collect(Collectors.toList());
            if(resultElement != null) queryElement(resultElement, null, entries, hashMap);
        }
        return hashMap;
    }
}
