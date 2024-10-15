package com.lynjava.ddd.test.tools.jsonpath;

import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.List;

public class JsonPathDemo {
    public static void main(String[] args) {
        JSONObject json = new JSONObject();

        JSONObject store = new JSONObject();
        json.put("store", store);
        com.alibaba.fastjson.JSONArray books = new com.alibaba.fastjson.JSONArray();
        JSONObject book1 = new JSONObject();
        book1.put("author", "hanhan");
        book1.put("price", 9);
        JSONObject book2 = new JSONObject();
        book2.put("author", "guojingming");
        book2.put("price", 10);
        books.add(book1);
        books.add(book2);
        store.put("books", books);
        System.out.println("json is " + json.toJSONString());
        List<String> filers = Arrays.asList("author=='guojingming'");
        String path = new JsonPathBean.JsonPathBuilder().appendHierarchy("store")
                .appendHierarchy("books")
                .appendFilter(filers)
                .build();
        String res = JsonPathBean.builder()
                .path(path).operateType(JsonPathBean.OperateType.READ.getCode()).value(null)
                .build()
                .process(json.toJSONString());
        System.out.println("res is " + res);
        String ss = new JsonPathBean(path, "READ", null).process(json.toJSONString());
        System.out.println(ss);
        JsonPathBean bean1 = new JsonPathBean("$.store.books[*].author", "REPLACE", "liuliu");
        System.out.println(bean1.process(json.toJSONString()));
        JsonPathBean bean2 = new JsonPathBean("$.store.books[?(@.price>9)].author", "PUT", "chenzhongshi");
        System.out.println(bean2.process(json.toJSONString()));
        JsonPathBean bean3 = new JsonPathBean("$.store.books[?(@.author=='guojingming')].author", "REPLACE", "jaipingwa");
        System.out.println(bean3.process(json.toJSONString()));
    }
}
