package com.lynjava.ddd.common.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class XPathDemo {
    private static Document doc;
    private static XPath xpath;

    public static void main(String[] args) throws Exception {
        init();
        getRootEle();
        getChildEles();
        getPartEles();
        haveChildsEles();
        getLevelEles();
        getAttrEles();

        //打印根节点下的所有元素节点
        System.out.println(doc.getDocumentElement().getChildNodes().getLength());
        NodeList nodeList = doc.getDocumentElement().getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                System.out.print(nodeList.item(i).getNodeName() + " ");
            }
        }
    }

    // 初始化Document、XPath对象
    public static void init() throws Exception {
        // 创建Document对象
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        DocumentBuilder db = dbf.newDocumentBuilder();
        String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<rss version=\"2.0\">\n" +
                "    <channel>\n" +
                "        <title>Java Tutorials and Examples 2</title>\n" +
                "        <language>en-us</language>\n" +
                "        <item>\n" +
                "            <title><![CDATA[Java Tutorials 2]]></title>\n" +
                "            <link>http://www.javacodegeeks.com/</link>\n" +
                "        </item>\n" +
                "        <item>\n" +
                "            <title><![CDATA[Java Examples 2]]></title>\n" +
                "            <link>http://examples.javacodegeeks.com/</link>\n" +
                "        </item>\n" +
                "    </channel>\n" +
                "    <college name=\"c1\">\n" +
                "        <class name=\"class1\">\n" +
                "            <student name=\"stu1\" sex='male' age=\"21\" />\n" +
                "            <student name=\"stu2\" sex='female' age=\"20\" />\n" +
                "            <student name=\"stu3\" sex='female' age=\"20\" />\n" +
                "        </class>\n" +
                "    </college>\n" +
                "    <bookstore>\n" +
                "        <book>\n" +
                "            <title lang=\"eng\">Harry Potter</title>\n" +
                "            <price>29.99</price>\n" +
                "        </book>\n" +
                "        <book>\n" +
                "            <title lang=\"eng\">Learning XML</title>\n" +
                "            <price>39.95</price>\n" +
                "        </book>\n" +
                "    </bookstore>\n" +
                "</rss>";
        //两种加载资源文件的方法
//        InputStream resourceAsStream = XPathDemo.class.getResourceAsStream("/demo.xml");
//        InputStream resourceAsStream = XpathDemo.class.getClassLoader().getResourceAsStream("demo.xml");
        doc = db.parse(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));

        // 创建XPath对象
        XPathFactory factory = XPathFactory.newInstance();
        xpath = factory.newXPath();
    }

    // 获取根元素
    // 表达式可以更换为/*,/rss
    public static void getRootEle() throws XPathExpressionException {
        Node node = (Node) xpath.evaluate("/rss", doc, XPathConstants.NODE);
        System.out.println(node.getNodeName() + "--------"
                + node.getNodeValue());
    }

    // 获取子元素并打印
    public static void getChildEles() throws XPathExpressionException {
        Node nodei = (Node) xpath.evaluate("/rss/channel/item", doc,
                XPathConstants.NODE);  // 获取第一个item节点
//        List<Node> childrenNodes = getChildrenElementNode(nodei);
        // ，values(0)为
        Map<String, Object> resMap = new HashMap<>();
//        transNodeToMap(nodei, resMap);


        NodeList nodeList = (NodeList) xpath.evaluate("/rss/channel/item", doc,
                XPathConstants.NODESET);  // 获取所有item节点
        System.out.println("==============");
        Map<String, Object> resMap2 = new HashMap<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
//            Node node = nodeList.item(i);
//            System.out.print(node.getNodeName() + " " + node.getTextContent() + ";");
//            test(nodeList.item(i));
//            transNodeToMap(nodeList.item(i), resMap2);
        }
        System.out.println("==============");
    }



    private static void test(Node node) {
        if (node.getChildNodes().getLength()==1 && node.getNodeType()==Node.ELEMENT_NODE) {
            System.out.println(node.getNodeName() + " " + node.getTextContent() + ";");
        } else {
            NodeList nodeList = node.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                if (nodeList.item(i).getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }
                test(nodeList.item(i));
            }
        }
    }

    // 获取部分元素
    // 只获取元素名称为title的元素
    public static void getPartEles() throws XPathExpressionException {
        NodeList nodeList = (NodeList) xpath.evaluate("//*[name() = 'title']",
                doc, XPathConstants.NODESET);
        for (int i = 0; i < nodeList.getLength(); i++) {
            System.out.print(nodeList.item(i).getNodeName() + "-->"
                    + nodeList.item(i).getTextContent());
        }
        System.out.println();
    }

    // 获取包含子节点的元素
    public static void haveChildsEles() throws XPathExpressionException {
        NodeList nodeList = (NodeList) xpath.evaluate("//*[*]", doc,
                XPathConstants.NODESET);
        for (int i = 0; i < nodeList.getLength(); i++) {
            System.out.print(nodeList.item(i).getNodeName() + " ");
        }
        System.out.println();
    }

    // 获取指定层级的元素
    public static void getLevelEles() throws XPathExpressionException {
        NodeList nodeList = (NodeList) xpath.evaluate("/*/*/*/*", doc,
                XPathConstants.NODESET);
        for (int i = 0; i < nodeList.getLength(); i++) {
            System.out.print(nodeList.item(i).getNodeName() + "-->"
                    + nodeList.item(i).getTextContent() + " ");
        }
        System.out.println("-----------------------------");
    }

    // 获取指定属性的元素
    // 获取所有大于指定价格的书箱
    public static void getAttrEles() throws XPathExpressionException {
        NodeList nodeList = (NodeList) xpath.evaluate("//bookstore/book[price>35.00]/title", doc,
                XPathConstants.NODESET);
        for (int i = 0; i < nodeList.getLength(); i++) {
            System.out.print(nodeList.item(i).getNodeName() + "-->"
                    + nodeList.item(i).getTextContent() + " ");
        }
        System.out.println();
    }
}
