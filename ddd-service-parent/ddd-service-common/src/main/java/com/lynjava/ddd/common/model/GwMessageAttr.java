package com.lynjava.ddd.common.model;

/**
 * 网关报文配置属性信息
 */
public class GwMessageAttr  {

    /**
     * 父节点Id
     */
    private String parentId;

    /**
     * 所属接口/服务Id
     */
    private String relationId;

    /**
     * 元数据id
     */
    private String metadataId;

    /**
     * 请求响应类型：0-request，1-response，见ReqResTypeEnum枚举
     */
    private String reqOrRes;

    /**
     * 属性来源：接口/服务，见MessageAttrSourceEnum枚举
     */
    private String attrSource;

    /**
     * 字段属性校验规则JSON
     */
    private String attrCheckRuleJson;

    /**
     * 是否被删除 0:未删除 1:已删除
     */
    private String isDeleted;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getMetadataId() {
        return metadataId;
    }

    public void setMetadataId(String metadataId) {
        this.metadataId = metadataId;
    }

    public String getReqOrRes() {
        return reqOrRes;
    }

    public void setReqOrRes(String reqOrRes) {
        this.reqOrRes = reqOrRes;
    }

    public String getAttrSource() {
        return attrSource;
    }

    public void setAttrSource(String attrSource) {
        this.attrSource = attrSource;
    }

    public String getAttrCheckRuleJson() {
        return attrCheckRuleJson;
    }

    public void setAttrCheckRuleJson(String attrCheckRuleJson) {
        this.attrCheckRuleJson = attrCheckRuleJson;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "GwMessageAttr{" +
                "parentId='" + parentId + '\'' +
                ", relationId='" + relationId + '\'' +
                ", metadataId='" + metadataId + '\'' +
                ", reqOrRes='" + reqOrRes + '\'' +
                ", attrSource='" + attrSource + '\'' +
                ", attrCheckRuleJson='" + attrCheckRuleJson + '\'' +
                ", isDeleted='" + isDeleted + '\'' +
                '}';
    }
}
