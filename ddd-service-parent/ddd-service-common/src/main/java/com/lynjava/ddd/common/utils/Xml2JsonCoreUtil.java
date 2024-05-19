package com.lynjava.ddd.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.dom4j.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 处理 核心 xml to json
 */
public class Xml2JsonCoreUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(Xml2JsonCoreUtil.class);

    public static void main(String[] args) {
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<data>\n" +
                "   <aliasList>as1</aliasList>\n" +
                "   <aliasList>as2</aliasList>\n" +
                "   <provinceList name=\"henan\">\n" +
                "    <age>78</age>\n" +
                "    <id>abc</id>\n" +
                "\t<cityList>\n" +
                "\t    <age>78</age>\n" +
                "        <id>c001</id>\n" +
                "\t</cityList>\n" +
                "   </provinceList>\n" +
                "   <provinceList name=\"shannxi\">\n" +
                "    <age>79</age>\n" +
                "    <id>def</id>\n" +
                "   </provinceList>\n" +
                "   <provinceList name=\"sichuan\">\n" +
                "    <age>80</age>\n" +
                "    <id>hij</id>\n" +
                "   </provinceList>\n" +
                "<tage>12</tage>\n" +
                "<tid>123</tid>\n" +
                "</data>";

        String json = xmlToJsonV2(xml);
        String newXml = Json2XmlCoreUtil.jsonToXmlV2(json);
        System.out.println("hello");
    }

    /**
     * dom2j 解析xml转JSON
     *
     * @param xmlStr
     * @return
     * @throws DocumentException
     */
    public static String xmlToJsonV2(String xmlStr) {
        try {
            Document document = DocumentHelper.parseText(xmlStr);
            JSONObject jsonObject = new JSONObject();
            String rootName = document.getRootElement().getName();
            dom4j2JsonV2(document.getRootElement(), jsonObject);
            JSONObject root = new JSONObject();
            root.put(rootName, jsonObject);
            return root.toString();
        } catch (Exception exception) {
            LOGGER.error("xmlToJsonV2 error:", exception);
            throw new RuntimeException("The json text is not formatted correctly");
        }
    }


    /**
     * 通过dom4j2解析xml转JSONV2
     *
     * @param rootElement root节点
     * @param jsonObject  JSON对象
     */
    private static void dom4j2JsonV2(Element rootElement, JSONObject jsonObject) {
        //属性处理
        for (int i = 0; i < rootElement.attributes().size(); i++) {
            Attribute attribute = rootElement.attribute(i);
            jsonObject.put("@" + attribute.getName(), attribute.getValue());
        }
        List<Element> elements = rootElement.elements();
        if (elements.isEmpty() && StringUtils.hasLength(rootElement.getText())) {
            jsonObject.put(rootElement.getName(), rootElement.getData());
        }
        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            if (element.elements().isEmpty()) {
                for (int i1 = 0; i1 < element.attributes().size(); i1++) {
                    Attribute attribute = element.attribute(i1);
                    jsonObject.put("@" + attribute.getName(), attribute.getValue());
                }
                if (!element.getText().isEmpty()) {
                    long tmp = elements.stream()
                            .filter(ele -> Objects.equals(ele.getName(), element.getName()))
                            .count();
                    if (tmp > 1) {
                        Object o = jsonObject.get(element.getName());
                        if (Objects.isNull(o)) {
                            JSONArray arr = new JSONArray();
                            arr.add(element.getData());
                            jsonObject.put(element.getName(), arr);
                        } else if (o instanceof JSONArray) {
                            JSONArray jsonArray = (JSONArray) o;
                            jsonArray.add(element.getData());
                        }
                    } else {
                        jsonObject.put(element.getName(), element.getData());
                    }
                }
            } else {
                JSONObject childJson = new JSONObject();
                dom4j2JsonV2(element, childJson);
                Object o = jsonObject.get(element.getName());
                if (o != null) {
                    JSONArray jsonArray = null;
                    if (o instanceof JSONObject) {
                        jsonArray = new JSONArray();
                        jsonArray.add(o);
                        jsonArray.add(childJson);
                    }
                    if (o instanceof JSONArray) {
                        jsonArray = (JSONArray) o;
                        jsonArray.add(childJson);
                    }
                    jsonObject.put(element.getName(), jsonArray);
                } else {
                    if(!childJson.isEmpty()) {
                        if(element.getParent() != null){
                            JSONArray arr = new JSONArray();
                            arr.add(childJson);
                            jsonObject.put(element.getName(), arr);
                        } else {
                            jsonObject.put(element.getName(), childJson);
                        }
                    }
                }
            }
        }
    }
}

