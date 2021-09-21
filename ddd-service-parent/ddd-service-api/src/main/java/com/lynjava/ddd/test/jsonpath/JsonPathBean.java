package com.lynjava.ddd.test.jsonpath;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.*;
import net.minidev.json.JSONArray;

public class JsonPathBean {
    private String path;

    private String operateType;

    private Object value;

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
        System.out.println(json.toJSONString());
        JsonPathBean bean1 = new JsonPathBean("$.store.books[*].author", "REPLACE", "liuliu");
        System.out.println(bean1.modifyJson(json.toJSONString()));
        JsonPathBean bean2 = new JsonPathBean("$.store.books[?(@.price>9)].author", "PUT", "chenzhongshi");
        System.out.println(bean2.modifyJson(json.toJSONString()));
        JsonPathBean bean3 = new JsonPathBean("$.store.books[?(@.author=='guojingming')].author", "REPLACE", "jaipingwa");
        System.out.println(bean3.modifyJson(json.toJSONString()));
    }

    public JsonPathBean(String path, String operateType, String value) {
        this.path = path;
        this.operateType = operateType;
        this.value = value;
    }

    public String modifyJson(String oriJson) {
        Configuration conf = Configuration.builder().options(Option.AS_PATH_LIST).build();
        DocumentContext dc = JsonPath.using(conf).parse(oriJson);
        JsonPath p = JsonPath.compile(this.path);
        if (this.operateType == "REPLACE") {
            dc.set(p, this.value);
        } else  if (this.operateType == "PUT") {
            ReadContext rc = JsonPath.parse(oriJson);
            JSONArray arr = rc.read(this.path);
            if (arr.size()<1) {
                JsonPath p1 = JsonPath.compile(this.path.substring(0, this.path.lastIndexOf(".")));
                dc.put(p1, this.path.substring(this.path.lastIndexOf(".") + 1), this.value);
            } else {
                dc.set(p, this.value);
            }
        } else if (this.operateType == "ADD") {
            dc.add(p, this.value);
        } else if (this.operateType == "READ") {
            ReadContext rdc = JsonPath.parse(oriJson);
            Object obj = rdc.read(this.path);
            return null == obj ? "" : JSON.toJSONString(obj);
        }
        return dc.jsonString();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
