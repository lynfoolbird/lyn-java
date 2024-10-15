package com.lynjava.ddd.common.utils;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.collections4.MapUtils;
import org.json.XML;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 平安xml报文转成Map，key为xpath路径，value为对应的值
 * xml树型结构转成扁平结构Map
 */
public class PingAnXml2MapUtil {

    private final static String ATTR = "attr";
    private final static String STRUCT = "struct";

    private final static String STRUCT_ARRAY = "struct[*]";

    private final static String ARRAY = "array";

    private final static String CONTENT = "content";

    private final static List<String> MAIN_STRUCT = Arrays.asList("/service", "/service/SYS_HEAD", "/service/APP_HEAD", "/service/LOCAL_HEAD", "/service/BODY");

    public static void main(String[] args) {
        String xml = getXmlBody();
        Map<String, Object> map = xml2map(xml);
        String requestBody = getJsonBody();
        Map<String, Object> resMap = PingAnJsonPathUtil.parse(requestBody, requestBody.hashCode());
        System.out.println("end");
    }

    private static String getJsonBody() {
        return "{\n" +
                "    \"body\": {\n" +
                "        \"grayMsgArray\": [\n" +
                "            {\n" +
                "                \"name\": \"name01\",\n" +
                "                \"titles\": [\n" +
                "                    {\n" +
                "                        \"id\": \"t1\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": \"t2\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    \"sysHead\": {\n" +
                "        \"id\": \"001\"\n" +
                "    }\n" +
                "}";
    }
    private static String getXmlBody() {
        return  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<service version=\"2.0\">\n" +
                "<SYS_HEAD>\n" +
                "   <SERVICE_CODE attr=\"s,30\">01001000001</SERVICE_CODE>\n" +
                "   <SERVICE_SCENE attr=\"s,2\">30</SERVICE_SCENE>\n" +
                "</SYS_HEAD>\n" +
                "<APP_HEAD>\n" +
                "   <TRAN_CHANNEL_TYPE attr=\"s,20\"></TRAN_CHANNEL_TYPE>\n" +
                "   <EXTRA_FLAG attr=\"s,4\"></EXTRA_FLAG>\n" +
                "   <AUTH_USER_ID_ARRAY attr=\"array\">\n" +
                "       <struct>\n" +
                "          <AUTH_LEVEL attr=\"s,2\"></AUTH_LEVEL>\n" +
                "          <AUTH_USER_ID attr=\"s,50\"></AUTH_USER_ID>\n" +
                "       </struct>\n" +
                "   </AUTH_USER_ID_ARRAY>\n" +
                "</APP_HEAD>\n" +
                "<LOCAL_HEAD>\n" +
                "   <REFERRER_NO attr=\"s,32\"></REFERRER_NO>\n" +
                "   <DEBUG>\n" +
                "      <HTTP_HEAD>\n" +
                "        <x-g-automaticrequestflag attr=\"s,500\">automan</x-g-automaticrequestflag>\n" +
                "        <x-g-rid attr=\"s,500\">.20000009AM066381271495000520043IZ5L</x-g-rid>\n" +
                "      </HTTP_HEAD>\n" +
                "   </DEBUG>\n" +
                "   <GRAY_MSG_ARRAY attr=\"array\">\n" +
                "      <struct>\n" +
                "         <LBL_NAME attr=\"s,200\">x-g-automaticrequestflag</LBL_NAME>\n" +
                "         <LBL_VALUE attr=\"s,200\">automan hello </LBL_VALUE>\n" +
                "      </struct>\n" +
                "      <struct>\n" +
                "         <LBL_NAME attr=\"s,200\">x-g-rid</LBL_NAME>\n" +
                "         <LBL_VALUE attr=\"s,200\">20000009AM066381271495000520043I25L</LBL_VALUE>\n" +
                "      </struct>\n" +
                "   </GRAY_MSG_ARRAY>\n" +
                "</LOCAL_HEAD>\n" +
                "<BODY>\n" +
                "  <ORDER_PRODUCT_CODE attr=\"s,20\"></ORDER_PRODUCT_CODE>\n" +
                "  <PAYROLL_TYPE attr=\"s,10\"></PAYROLL_TYPE>\n" +
                "  <TRANSFER_ARRAY attr=\"array\">\n" +
                "     <struct>\n" +
                "        <OUT_COR_BRANCH_ID attr=\"s,20\"></OUT_COR_BRANCH_ID>\n" +
                "        <SAFE_AUTH_NO attr=\"s,30\"></SAFE_AUTH_NO>\n" +
                "        <COMMISSION_ARRAY attr=\"array\">\n" +
                "           <struct>\n" +
                "             <DEP_BOOK_ID attr=\"s,30\"></DEP_BOOK_ID>\n" +
                "             <COMMISSION_CCY attr=\"s,3\"></COMMISSION_CCY>\n" +
                "           </struct> \n" +
                "        </COMMISSION_ARRAY>\n" +
                "\t\t<PAYMENT_SOLUTION_ARRAY attr=\"array\">\n" +
                "           <struct>\n" +
                "             <OUT_ACCT_FREEZE_TYPE attr=\"s,10\">1</OUT_ACCT_FREEZE_TYPE>\n" +
                "\t         <EREEZE_NO attr=\"s,20\">24070500000764</EREEZE_NO>\n" +
                "           </struct>\n" +
                "        </PAYMENT_SOLUTION_ARRAY>\n" +
                "     </struct>\n" +
                "  </TRANSFER_ARRAY>\n" +
                "</BODY>\n" +
                "</service>\n" +
                "\n" +
                "\n";
//        JSONObject json = XML.toJSONObject(xml, true);
//        System.out.println(json);
    }

    // xml转json时，若xml中数组只有一个元素则转换的json中对应的是对象而非数组
    private static void transStructOld(Map<String, Object> map) {
        if (MapUtils.isEmpty(map)) {
            return;
        }
        for (String key : map.keySet()) {
            Object val = map.get(key);
            if (val instanceof Map) {
                Map<String, Object> valMap = (Map<String, Object>) val;
                if (Objects.equals("array", valMap.get("attr"))
                && valMap.get("struct") instanceof  Map) {
                    valMap.put("struct", Arrays.asList(valMap.get("struct")));
                }
                transStruct(valMap);
            } else if (val instanceof List) {
                for (Object element : (List)val) {
                    if (element instanceof Map) {
                        transStruct((Map<String, Object>) element);
                    }
                }
            }
        }
    }

    private static void transStruct(Map<String, Object> map) {
        if (MapUtils.isEmpty(map)) {
            return;
        }
        for (String key : map.keySet()) {
            Object val = map.get(key);
            if (val instanceof Map) {
                Map<String, Object> valMap = (Map<String, Object>) val;
                Object attr = valMap.get(ATTR);
                if (attr != null) {
                    if (Objects.equals(ARRAY, attr)) {
                        boolean isMap = valMap.get(STRUCT) instanceof Map;
                        Object o = isMap ? Arrays.asList(valMap.get(STRUCT)) : valMap.get(STRUCT);
                        valMap.clear();
                        valMap.put(STRUCT, o);
                        transStruct(valMap);
                    } else {
                        map.put(key, valMap.get(CONTENT));
                    }
                } else {
                    transStruct(valMap);
                }
            } else if (val instanceof List) {
                for (Object element : (List)val) {
                    if (element instanceof Map) {
                        transStruct((Map<String, Object>) element);
                    }
                }
            }
        }
    }


    public static Map<String, Object> xml2map(String xmlString) {
        Map<String, Object> map = XML.toJSONObject(xmlString, true).toMap();
        Map<String, Object> serviceMap = (Map<String, Object>) map.get("service");
        serviceMap.remove("version");
        transStruct(serviceMap);
        Map<String, Object> newMap = new HashMap<>(512);
        xml2map(serviceMap, "/service", newMap);
        return newMap;
    }
    public static void xml2map(Object object, String curPath, Map<String,Object> newMap) {
        if (object instanceof Map) {
            Map<String, Object> valueMap = (Map<String, Object>) object;
            for (Map.Entry<String, Object> entry : valueMap.entrySet()) {
                String keyPath = StrUtil.equals(entry.getKey(), STRUCT) ? STRUCT_ARRAY : entry.getKey();
                xml2map(entry.getValue(), curPath + "/" + keyPath, newMap);
            }
            if (!MAIN_STRUCT.contains(curPath) && !valueMap.containsKey(STRUCT)) {
                newMap.put(curPath, object);
            }
        } else if (object instanceof List) {
            List<Map<String, Object>> structs = new ArrayList<>();
            for (Object o : (List<Object>)object) {
                Map<String, Object> subMap = new HashMap<>();
                xml2map(o, curPath, subMap);
                structs.add(subMap);
            }
            Map<String, List<Object>> miniMap = struct2array(structs);
            newMap.putAll(miniMap);
            newMap.put(curPath, object);
        } else {
            newMap.put(curPath, object);
        }
    }


    private static Map<String, List<Object>> struct2array(List<Map<String, Object>> structs) {
        Map<String, List<Object>> miniMap = new HashMap<>();
        for (int i=0; i<structs.size(); i++) {
            for (Map.Entry<String, Object> entry : structs.get(i).entrySet()) {
                if (miniMap.containsKey(entry.getKey())) {
                    List<Object> objects = miniMap.get(entry.getKey());
                    objects.add(entry.getValue());
                } else {
                    List<Object> objects = new ArrayList<>();
                    // 如果数组长度小于当前索引，则对前面的值补null值
                    if (i > 0) {
                        for (int j=0; j<=i-1; j++) {
                            objects.add(null);
                        }
                    }
                    objects.add(entry.getValue());
                    miniMap.put(entry.getKey(), objects);
                }
            }
            // 如果前面数组有key，该数组无对应key，则添加默认值
            for (String s : miniMap.keySet()) {
                if (!structs.get(i).containsKey(s)) {
                    miniMap.get(s).add(null);
                }
            }
        }
        return miniMap;
    }

}







