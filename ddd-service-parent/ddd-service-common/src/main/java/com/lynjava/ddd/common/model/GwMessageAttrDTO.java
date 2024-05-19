package com.lynjava.ddd.common.model;

import java.util.List;

/**
 * 网关报文配置属性DTO（包含元数据相关信息）
 */
public class GwMessageAttrDTO extends GwMessageAttr {

    /**
     * 元数据名
     */
    private String metadataKey;

    /**
     * 属性名称
     */
    private String metadataName;

    /**
     * 数据类型
     * 0：String，1：Integer，2 ：Double，3 ：long，4：BigDecimal，5 ：Boolean，6：Date，7：List，8：Object
     */
    private String dataType;

    /**
     * 数据长度
     */
    private String dataLength;

    /**
     * 精度
     */
    private String dataPrecision;

    private String freemarkerKey;

    public String getFreemarkerKey() {
        return freemarkerKey;
    }

    public void setFreemarkerKey(String freemarkerKey) {
        this.freemarkerKey = freemarkerKey;
    }

    /**
     * 下层字段列表
     */
    private List<GwMessageAttrDTO> children;

    public String getMetadataKey() {
        return metadataKey;
    }

    public void setMetadataKey(String metadataKey) {
        this.metadataKey = metadataKey;
    }

    public String getMetadataName() {
        return metadataName;
    }

    public void setMetadataName(String metadataName) {
        this.metadataName = metadataName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataLength() {
        return dataLength;
    }

    public void setDataLength(String dataLength) {
        this.dataLength = dataLength;
    }

    public String getDataPrecision() {
        return dataPrecision;
    }

    public void setDataPrecision(String dataPrecision) {
        this.dataPrecision = dataPrecision;
    }

    public List<GwMessageAttrDTO> getChildren() {
        return children;
    }

    public void setChildren(List<GwMessageAttrDTO> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "GwMessageAttrDTO{" +
                "metadataKey='" + metadataKey + '\'' +
                ", metadataName='" + metadataName + '\'' +
                ", dataType='" + dataType + '\'' +
                ", dataLength='" + dataLength + '\'' +
                ", dataPrecision='" + dataPrecision + '\'' +
                '}';
    }
}
