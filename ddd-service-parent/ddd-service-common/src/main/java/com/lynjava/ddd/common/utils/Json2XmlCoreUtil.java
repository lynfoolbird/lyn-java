package com.lynjava.ddd.common.utils;

public class Json2XmlCoreUtil {
    /**
     * json转xml
     *
     * @param json   待转换的json串
     * @return
     */
    public static String jsonToXmlV2(String json) {
        XmlConverter xmlConvert = new XmlConverter("@", "#text", false);
        return xmlConvert.jsonToXmlV2(json);
    }
}
