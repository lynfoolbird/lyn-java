package com.lynjava.ddd.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.ParseContext;
import com.lynjava.ddd.common.context.JsonParseContext;
import com.lynjava.ddd.common.context.ThreadContext;
import org.apache.commons.collections4.MapUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class JsonPathUtils {

    private JsonPathUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static void main(String[] args) {
        String json = "{\n" +
                "\t\"data\": {\n" +
                "\t\t\"tage\": 789,\n" +
                "\t\t\"tid\": \"abc\",\n" +
                "\t\t\"aliasList\": [\n" +
                "\t\t\t\"no1\",\n" +
                "\t\t\t\"no2\"\n" +
                "\t\t],\n" +
                "\t\t\"provinceList\": [\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"id\": \"001\",\n" +
                "\t\t\t\t\"age\": 1,\n" +
                "\t\t\t\t\"cityList\": [\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"id\": \"C101\",\n" +
                "\t\t\t\t\t\t\"age\": 11\n" +
                "\t\t\t\t\t},\n" +
                "                    {\n" +
                "\t\t\t\t\t\t\"id\": \"C102\",\n" +
                "\t\t\t\t\t\t\"age\": 12\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t]\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"id\": \"002\",\n" +
                "\t\t\t\t\"age\": 2,\n" +
                "\t\t\t\t\"cityList\": [\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"id\": \"C202\",\n" +
                "\t\t\t\t\t\t\"age\": 21\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t]\n" +
                "\t\t\t}\n" +
                "\t\t]\n" +
                "\t}\n" +
                "}";

        String path = "$.data.provinceList[0].cityList[*].id[0]";  //可以取到单值
        Object obj = parse(json, path, 1);
    }


    private static DocumentContext getDocumentContext(String json, int code) {
        String strCode = String.valueOf(code);
        Object obj = ThreadContext.get(strCode);
        DocumentContext parse;
        if (Objects.nonNull(obj)) {
            parse = (DocumentContext) obj;
        } else {
            ParseContext parseContext = JsonParseContext.getParseContext();
            parse = parseContext.parse(json);
            ThreadContext.set(strCode, parse);
        }
        return parse;
    }

    // 单个路径解析(read方法中存在lockunlock操作，所以每次调用都会加锁释放锁)
    public static Object parse(String json, String jsonPath, int code) {
        DocumentContext documentContext = getDocumentContext(json, code);
        return documentContext.read(jsonPath);
    }

    // 将层级Map转换为平铺的Map
    // 整体解析，根据path取值，效率更高
    public static Map<String, Object> parse(String json, int code) {
        DocumentContext documentContext = getDocumentContext(json, code);
        Map<String, Object > docValueMap = new HashMap<>(512);
        unpackDoc(docValueMap, documentContext.json(), "$", false);
        return docValueMap;
    }

    private static void unpackDoc(Map<String, Object> parsedMap, LinkedHashMap<String, Object> originMap,
                                  String parentPrefixPath, boolean isParentArray) {
        if (MapUtils.isEmpty(originMap)) {
            return;
        }
        for (String originKey : originMap.keySet()) {
            String prefixPath = parentPrefixPath + "." + originKey;
            Object originValue = originMap.get(originKey);
            if (originValue instanceof JSONArray) {
                String arrayPrefixPath = prefixPath + "[*]";
                JSONArray valueArray = (JSONArray) originValue;
                List<Object> arrayValueList = (List<Object>) parsedMap.getOrDefault(arrayPrefixPath, new ArrayList<>());
                for (int arrayIndex = 0; arrayIndex < valueArray.size(); arrayIndex++) {
                    LinkedHashMap<String, Object> valueObj = (LinkedHashMap<String, Object>) valueArray.get(arrayIndex);
                    unpackDoc(parsedMap, valueObj, arrayPrefixPath, true);
                    if (valueObj instanceof LinkedHashMap) {
                        for (Map.Entry<String, Object> entry : valueObj.entrySet()) {
                            if (Objects.nonNull(entry.getValue()) && entry.getValue() instanceof BigDecimal) {
                                valueObj.put(entry.getKey(), entry.getValue().toString());
                            }
                        }
                    }
                    arrayValueList.add(valueObj);
                }
                parsedMap.put(arrayPrefixPath, arrayValueList);
            } else if (!(originValue instanceof LinkedHashMap)) {
                Object value = Objects.nonNull(originValue) && (originValue instanceof BigDecimal) ? originValue.toString() : originValue;
                if (isParentArray) {
                    List<Object> arrayValue = (List<Object>) parsedMap.getOrDefault(prefixPath, new ArrayList<>());
                    arrayValue.add(value);
                    parsedMap.put(prefixPath, arrayValue);
                } else {
                    parsedMap.put(prefixPath, value);
                }
            } else {
                unpackDoc(parsedMap, (LinkedHashMap<String, Object>) originMap.get(originKey), prefixPath, false);
            }
        }
    }
}
