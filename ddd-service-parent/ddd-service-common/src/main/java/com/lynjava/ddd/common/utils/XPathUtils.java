package com.lynjava.ddd.common.utils;

import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class XPathUtils {

    private static final XPath xpath = XPathFactory.newInstance().newXPath();

    public static void main(String[] args) throws Exception {
        FieldConfig fieldConfig1 = new FieldConfig();
        fieldConfig1.setMetadataKey("data");
        fieldConfig1.setFreemarkerKey("data");
        FieldConfig fieldConfig2 = new FieldConfig();
        fieldConfig2.setMetadataKey("tid");
        fieldConfig2.setFreemarkerKey("hello");
        FieldConfig fieldConfig3 = new FieldConfig();
        fieldConfig3.setMetadataKey("aliasList");
        fieldConfig3.setDataType("array");
        fieldConfig3.setFreemarkerKey("data_arrList");
        FieldConfig fieldConfig4 = new FieldConfig();
        fieldConfig4.setMetadataKey("provinceList");
        fieldConfig4.setDataType("array");
        fieldConfig4.setFreemarkerKey("data_provinceList");
        FieldConfig fieldConfig41 = new FieldConfig();
        fieldConfig41.setMetadataKey("cityList");
        fieldConfig41.setDataType("array");
        fieldConfig41.setFreemarkerKey("data_provinceList");
        fieldConfig4.setChildFields(Arrays.asList(fieldConfig41));
        List<FieldConfig> children = new ArrayList<>();
        children.add(fieldConfig2);
        children.add(fieldConfig3);
        children.add(fieldConfig4);
        fieldConfig1.setChildFields(children);
        FieldConfig target = filterFieldConfig("data_arrList", Arrays.asList(fieldConfig1));

        String xmlData = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<data>\n" +
                "   <aliasList>as1</aliasList>\n" +
//                "   <aliasList>as2</aliasList>\n" +
                "   <provinceList>\n" +
                "    <age>78</age>\n" +
                "    <id>abc</id>\n" +
                "\t<cityList>\n" +
                "\t    <age>78</age>\n" +
                "        <id>c001</id>\n" +
                "\t</cityList>\n" +
                "   </provinceList>\n" +
                "   <provinceList>\n" +
                "    <age>79</age>\n" +
                "    <id>def</id>\n" +
                "   </provinceList>\n" +
                "   <provinceList>\n" +
                "    <age>80</age>\n" +
                "    <id>hij</id>\n" +
                "   </provinceList>\n" +
                "<tage>12</tage>\n" +
                "<tid>123</tid>\n" +
                "</data>";

        Object oo = parseNode("/data", xmlData, fieldConfig1);

        Object resMap = parseNode("/data/tid", xmlData);

        Object resMap2 = parseNodeList("/data/provinceList", xmlData, fieldConfig4);
        System.out.println("lfalfj");



        String content = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<data>\n" +
                "   <resData>\n" +
                "    <age>78</age>\n" +
                "    <id>abc</id>\n" +
                "   </resData>\n" +
                "   <resData>\n" +
                "    <age>79</age>\n" +
                "    <id>def</id>\n" +
                "   </resData>\n" +
                "   <resData>\n" +
                "    <age>80</age>\n" +
                "    <id>hij</id>\n" +
                "   </resData>\n" +
                "<tage>12</tage>\n" +
                "<tid>123</tid>\n" +
                "</data>";

        Document doc = create(content);
        Node data = (Node) xpath.compile("/data/resData/*")
                .evaluate(doc, XPathConstants.NODE);
//        NodeList data = (NodeList) xpath.compile("/data/resData/*")
//                .evaluate(doc, XPathConstants.NODESET);
//        for (int i = 0; i < data.getLength(); i++) {
//            Node tmp = data.item(i);
//            System.out.println(tmp.getTextContent());
//        }

        String ss = parse("/data", doc);
        System.out.println("hehe");
    }

    private static FieldConfig filterFieldConfig(String freemarkerKey, List<FieldConfig> fieldConfigList)
    {
        if (CollectionUtils.isEmpty(fieldConfigList)) {
            return null;
        }
        for (FieldConfig fieldConfig : fieldConfigList) {
            if (Objects.equals(freemarkerKey, fieldConfig.getFreemarkerKey())) {
                return fieldConfig;
            }
            if (CollectionUtils.isNotEmpty(fieldConfig.getChildFields())) {
                return filterFieldConfig(freemarkerKey, fieldConfig.getChildFields());
            }
        }
        return null;
    }


    public static Object parseString(String expression, String xmlContent) {
        try {
            return xpath.compile(expression)
                    .evaluate(create(xmlContent), XPathConstants.STRING);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object parseNode(String expression, String xmlContent) {
        try {
            Node node = (Node) xpath.compile(expression)
                    .evaluate(create(xmlContent), XPathConstants.NODE);
            Map<String, Object> resMap = new HashMap<>();
            transNodeToMap(node, resMap);
            for (Map.Entry<String, Object> entry : resMap.entrySet()) {
                return entry.getValue();
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object parseNode(String expression, String xmlContent, FieldConfig fieldConfig) {
        try {
            Node node = (Node) xpath.compile(expression)
                    .evaluate(create(xmlContent), XPathConstants.NODE);
            Map<String, Object> resMap = new HashMap<>();
            transNodeToMap(node, fieldConfig, resMap);
            for (Map.Entry<String, Object> entry : resMap.entrySet()) {
                return entry.getValue();
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static Object parseNodeList(String expression, String xmlContent) {
        try {
            NodeList nodeList = (NodeList) xpath.compile(expression)
                    .evaluate(create(xmlContent), XPathConstants.NODESET);
            Map<String, Object> resMap = new HashMap<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                transNodeToMap(nodeList.item(i), resMap);
            }
            for (Map.Entry<String, Object> entry : resMap.entrySet()) {
                return entry.getValue();
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object parseNodeList(String expression, String xmlContent, FieldConfig fieldConfig) {
        try {
            NodeList nodeList = (NodeList) xpath.compile(expression)
                    .evaluate(create(xmlContent), XPathConstants.NODESET);
            Map<String, Object> resMap = new HashMap<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                transNodeToMap(nodeList.item(i), fieldConfig, resMap);
            }
            for (Map.Entry<String, Object> entry : resMap.entrySet()) {
                return entry.getValue();
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 树转Map
     * @param fieldConfigList
     * @param resMap
     */
    private static void transTreeToMap(List<FieldConfig> fieldConfigList, Map<String, Object> resMap) {
        if (CollectionUtils.isEmpty(fieldConfigList)) {
            return;
        }
        for (FieldConfig fieldConfig : fieldConfigList) {
            switch (fieldConfig.getDataType()) {
                case "object":
                    Map<String, Object> innerMap = new HashMap<>();
                    transTreeToMap(fieldConfig.getChildFields(), innerMap);
                    resMap.put(fieldConfig.getMetadataKey(), innerMap);
                    break;
                case "array":
                    Map<String, Object> innerMap2 = new HashMap<>();
                    transTreeToMap(fieldConfig.getChildFields(), innerMap2);
                    resMap.put(fieldConfig.getMetadataKey(), Arrays.asList(innerMap2));
                    break;
                default:
                    resMap.put(fieldConfig.getMetadataKey(), fieldConfig.getMetadataKey() + "-NodeValue");
                    break;
            }
        }
    }

    private static void transNodeToMap(Node node, FieldConfig fieldConfig, Map<String, Object> resMap) {
        if (Objects.isNull(node) || node.getNodeType() != Node.ELEMENT_NODE) {
            return;
        }
        String dataType = Objects.nonNull(fieldConfig) ? fieldConfig.getDataType() : "";
        List<FieldConfig> children = Objects.nonNull(fieldConfig) ? fieldConfig.getChildFields()
                : null;
        String key = node.getNodeName();
        List<Node> childrenEleNodeList = getChildrenElementNode(node);
        if (resMap.containsKey(key)) {
            Object val = resMap.get(key);
            if (val instanceof List) {
                List<Object> list = (List<Object>) val;
                if (CollectionUtils.isEmpty(childrenEleNodeList)) {
                    list.add(node.getTextContent());
                } else {
                    Map<String, Object> tmpMap = new HashMap<>();
                    for (Node tnode : childrenEleNodeList) {
                        transNodeToMap(tnode, filterFieldConfigByNodeName(tnode.getNodeName(), children), tmpMap);
                    }
                    list.add(tmpMap);
                }
            } else {
                List<Object> tlist = new ArrayList<>();
                tlist.add(val);
                if (CollectionUtils.isEmpty(childrenEleNodeList)) {
                    tlist.add(node.getTextContent());
                } else {
                    Map<String, Object> tmpMap = new HashMap<>();
                    for (Node tnode : childrenEleNodeList) {
                        transNodeToMap(tnode, filterFieldConfigByNodeName(tnode.getNodeName(), children), tmpMap);
                    }
                    tlist.add(tmpMap);
                }
                resMap.put(key, tlist);
            }
        } else {
            if (CollectionUtils.isEmpty(childrenEleNodeList)) {
                if ("array".equals(dataType)) {
                    List<Object> tlist = new ArrayList<>();
                    tlist.add(node.getTextContent());
                    resMap.put(key, tlist);
                } else {
                    resMap.put(key, node.getTextContent());
                }
            } else {
                Map<String, Object> tmpMap = new HashMap<>();
                for (Node tnode : childrenEleNodeList) {
                    transNodeToMap(tnode, filterFieldConfigByNodeName(tnode.getNodeName(), children), tmpMap);
                }
                if ("array".equals(dataType)) {
                    List<Object> tlist = new ArrayList<>();
                    tlist.add(tmpMap);
                    resMap.put(key, tlist);
                } else {
                    resMap.put(key, tmpMap);
                }

            }
        }
    }

    private static FieldConfig filterFieldConfigByNodeName(String nodeName, List<FieldConfig> fieldConfigList)
    {
        if (CollectionUtils.isEmpty(fieldConfigList)) {
            return null;
        }
        for (FieldConfig fieldConfig : fieldConfigList) {
            if (Objects.equals(nodeName, fieldConfig.getMetadataKey())) {
                return fieldConfig;
            }
        }
        return null;
    }

    private static void transNodeToMap(Node node, Map<String, Object> resMap) {
        if (Objects.isNull(node) || node.getNodeType() != Node.ELEMENT_NODE) {
            return;
        }
        String key = node.getNodeName();
        List<Node> childrenEleNodeList = getChildrenElementNode(node);
        if (resMap.containsKey(key)) {
            Object val = resMap.get(key);
            if (val instanceof List) {
                List<Object> list = (List<Object>) val;
                if (CollectionUtils.isEmpty(childrenEleNodeList)) {
                    list.add(node.getTextContent());
                } else {
                    Map<String, Object> tmpMap = new HashMap<>();
                    for (Node tnode : childrenEleNodeList) {
                        transNodeToMap(tnode, tmpMap);
                    }
                    list.add(tmpMap);
                }
            } else {
                List<Object> tlist = new ArrayList<>();
                tlist.add(val);
                if (CollectionUtils.isEmpty(childrenEleNodeList)) {
                    tlist.add(node.getTextContent());
                } else {
                    Map<String, Object> tmpMap = new HashMap<>();
                    for (Node tnode : childrenEleNodeList) {
                        transNodeToMap(tnode, tmpMap);
                    }
                    tlist.add(tmpMap);
                }
                resMap.put(key, tlist);
            }
        } else {
            if (CollectionUtils.isEmpty(childrenEleNodeList)) {
                resMap.put(key, node.getTextContent());
            } else {
                Map<String, Object> tmpMap = new HashMap<>();
                for (Node tnode : childrenEleNodeList) {
                    transNodeToMap(tnode, tmpMap);
                }
                resMap.put(key, tmpMap);
            }
        }
    }

    private static List<Node> getChildrenElementNode (Node node) {
        List<Node> nodeList = new ArrayList<>();
        NodeList childNodeList = node.getChildNodes();
        for (int i = 0; i < childNodeList.getLength(); i++) {
            Node tmpNode = childNodeList.item(i);
            if (tmpNode.getNodeType() == Node.ELEMENT_NODE) {
                nodeList.add(tmpNode);
            }
        }
        return nodeList;
    }

    public static String parse(String expression, Document content) {
        try {
            return xpath.compile(expression).evaluate(content, XPathConstants.STRING).toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Document create(String content) {
        try {
            DocumentBuilder documentBuilder =
                    DocumentBuilderFactory.newInstance().newDocumentBuilder();
            return documentBuilder
                    .parse(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Data
    public static class FieldConfig {

        private String freemarkerKey;

        private String metadataKey;

        private String dataType;

        private List<FieldConfig> childFields;
    }

}
