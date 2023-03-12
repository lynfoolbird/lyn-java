package com.lynjava.ddd.test.tools.jsonpath;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.ReadContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.JSONArray;

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
        System.out.println(json.toJSONString());
        List<String> filers = Arrays.asList("author=='guojingming'");
        String path = new JsonPathBuilder().appendHierarchy("store")
                .appendHierarchy("books")
                .appendFilter(filers)
                .build();
        String ss = new JsonPathBean(path, "READ", null).modifyJson(json.toJSONString());
        System.out.println(ss);
        JsonPathBean bean1 = new JsonPathBean("$.store.books[*].author", "REPLACE", "liuliu");
        System.out.println(bean1.modifyJson(json.toJSONString()));
        JsonPathBean bean2 = new JsonPathBean("$.store.books[?(@.price>9)].author", "PUT", "chenzhongshi");
        System.out.println(bean2.modifyJson(json.toJSONString()));
        JsonPathBean bean3 = new JsonPathBean("$.store.books[?(@.author=='guojingming')].author", "REPLACE", "jaipingwa");
        System.out.println(bean3.modifyJson(json.toJSONString()));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JsonPathBean {
        private String path;
        private String operateType;
        private Object value;
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
            } else if (this.operateType =="DEL") {
                dc.delete(this.path);
            }
            return dc.jsonString();
        }
    }

    public static class JsonPathBuilder {
        private StringBuilder path = new StringBuilder("$");

        public JsonPathBuilder appendHierarchy(String hierarchy) {
            this.path.append("." + hierarchy);
            return this;
        }

        public JsonPathBuilder appendFilter(List<String> filters) {
            String childPathStr = "";
            String reg = "(@.KEYKEY OPOP VALUEVALUE)";
            for (int i=0; i<filters.size(); i++) {
                childPathStr += reg.replace("KEYKEY OPOP VALUEVALUE", filters.get(i));
                if (i<filters.size()-1) {
                    childPathStr = childPathStr + "&&";
                }
            }
            this.path.append("[?(DDDDD)]".replaceAll("DDDDD", childPathStr));
            return this;
        }

        public String build() {
            return this.path.toString();
        }
    }
}
