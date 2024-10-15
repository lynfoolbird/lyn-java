package com.lynjava.ddd.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EscapeUtils {

    private static final String[] ESCAPES = new String[]{"\\", "\"", "\n", "\r", "\t", "\b", "\f"};


    private static final String[] REPLACES = new String[]{"\\\\", "\\\"", "\\n", "\\r", "\\t", "\\b", "\\f"};

    private static final Map<String, String> ESCAPE_MAP = new HashMap<String, String>() {{
        put("\\", "\\\\");
        put("\"", "\\\"");
        put("\n", "\\n");
        put("\r", "\\r");
        put("\t", "\\t");
        put("\b", "\\b");
        put("\f", "\\f");
    }};

    public static Object escapeXmlContent(Object object) {
        return escapeContent(object, false);
    }

    public static Object escapeJsonContent(Object object) {
        return escapeContent(object, true);
    }

    private static Object escapeContent(Object object, Boolean isJsonEscape) {
        if (object instanceof List) {
            List<Object> list = (List<Object>) object;
            List<Object> target = new ArrayList<>();
            for (Object value : list) {
                if (value instanceof List) {
                    target.add(escapeContent(value, isJsonEscape));
                } else if (value instanceof Map) {
                    target.add(escapeContent(value, isJsonEscape));
                } else if (value instanceof String) {
                    if (isJsonEscape) {
                        target.add(escapeJsonStr(value.toString()));
                    } else {
                        target.add(escapeXmlStr(value.toString()));
                    }
                } else {
                    target.add(value);
                }
            }
            return target;
        } else if (object instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) object;
            for (String key : map.keySet()) {
                Object value = map.get(key);
                if (value instanceof Map) {
                    map.put(key, escapeContent(value, isJsonEscape));
                } else if (value instanceof List) {
                    map.put(key, escapeContent(value, isJsonEscape));
                } else if (value instanceof String) {
                    if (isJsonEscape) {
                        map.put(key, escapeJsonStr((String) value));
                    } else {
                        map.put(key, escapeXmlStr((String) value));
                    }
                }
            }
            return map;
        } else if (object instanceof String) {
            if (isJsonEscape) {
                return escapeJsonStr((String) object);
            } else {
                return escapeXmlStr((String) object);
            }
        } else {
            return object;
        }
    }

    private static String escapeXmlStr(String strValue) {
        if (StringUtils.isBlank(strValue)) {
            return strValue;
        }
        StringBuilder value = new StringBuilder(strValue);
        for (int k=0, len=value.length(); k<len; k++) {
            switch (value.charAt(k)) {
                case '&':
                    value.replace(k, k+1, "&amp;");
                    break;
                case '<':
                    value.replace(k, k+1, "&lt;");
                    break;
                case '>':
                    value.replace(k, k+1, "&gt;");
                    break;
                case '\"':
                    value.replace(k, k+1, "&quot;");
                    break;
                case '\'':
                    value.replace(k, k+1, "&apos;");
                    break;
                case '\\':
                    // 遇到\则判断是否是\\0x，如果是则插入\进行特殊处理
                    if (k+3 < len && value.charAt(k+1)=='\\'
                    && value.charAt(k+2)=='0'
                    && value.charAt(k+3)=='x') {
                        value.insert(k + 4, '\\');
                        k = k+4;
                        len = value.length();
                    }
                    break;
                default:
                    char c = value.charAt(k);
                    if (c<32 && c!='\n' && c!='\r' && c!='\t') {
                        String str = Integer.toHexString(value.charAt(k));
                        if (str.length() == 2) {
                            str = "\\\\0x" + str;
                        } else if (str.length() == 1) {
                            str = "\\\\0x0" + str;
                        }
                        value.replace(k, k + 1, str);
                        k = k + 5;
                        len = value.length();
                    }
                    break;
            }
        }
        return value.toString();
    }

    private static String escapeJsonStr(String strValue) {
        if (StringUtils.isBlank(strValue)) {
            return strValue;
        }
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<strValue.length(); i++) {
            char c = strValue.charAt(i);
            if (ESCAPE_MAP.containsKey(String.valueOf(c))) {
                sb.append(ESCAPE_MAP.get(String.valueOf(c)));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}
