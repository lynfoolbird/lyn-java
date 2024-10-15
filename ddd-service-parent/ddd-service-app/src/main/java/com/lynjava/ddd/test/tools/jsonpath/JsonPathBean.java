package com.lynjava.ddd.test.tools.jsonpath;

import com.alibaba.fastjson.JSON;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.ReadContext;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.JSONArray;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JsonPathBean {
    private static final Configuration CONF = Configuration.builder().options(Option.AS_PATH_LIST).build();
    private String path;
    private String operateType;
    private Object value;
    public String process(String oriJson) {
        DocumentContext dc = JsonPath.using(CONF).parse(oriJson);
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
        } else if (this.operateType == "DEL") {
            dc.delete(this.path);
        }
        return dc.jsonString();
    }

    public enum OperateType {
        /**
         *
         */
        REPLACE("REPLACE"),
        PUT("PUT"),
        ADD("ADD"),
        DEL("DEL"),
        READ("READ");
        private String code;
        OperateType(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
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
