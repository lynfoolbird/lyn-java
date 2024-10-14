package com.lynjava.ddd.test.architecture.designpattern.chain.list;

import com.lynjava.ddd.test.architecture.designpattern.chain.list.base.BasePluginConfig;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PackagePluginConfig extends BasePluginConfig  {

    private String packInfo;

    private List<UnpackInfo> unpackInfoList;


    @Data
    public static class UnpackInfo implements Serializable {
        /* 参数名 */
        private String attributeName;

        /* 参数路径 */
        private String attributePath;

        /* 组包需要的key */
        private String freemarkerKey;
        /**
         * 属性类型 0标识 1对象 2集合 3元数据
         */
        private String attributeType;
    }
}
