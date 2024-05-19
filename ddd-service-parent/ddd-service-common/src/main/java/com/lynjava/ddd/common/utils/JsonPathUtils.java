package com.lynjava.ddd.common.utils;

import org.noear.snack.ONode;

public class JsonPathUtils {

    public static Object parse(String json, String jsonPath) {
        ONode node = ONode.load(json);
        return node.select(jsonPath).toData();
    }

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
        Object obj = parse(json, path);
    }

}
