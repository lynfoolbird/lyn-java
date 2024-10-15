package com.lynjava.ddd.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.IOUtils;
import com.lynjava.ddd.common.model.GwMessageAttrDTO;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * freemarker工具，处理模板转换
 *
 * @author Arlo
 * @date 2021/4/29
 **/
public class FreeMarkerUtils {

    private static final Configuration CONFIGURATION = new Configuration(Configuration.VERSION_2_3_28);

    static {
        StringTemplateLoader templateLoader = new StringTemplateLoader();
        CONFIGURATION.setTemplateLoader(templateLoader);
        CONFIGURATION.setNumberFormat("#");
    }

    /**
     * 用于预加载模板
     * @param name
     * @param template
     */
    public static void loadTemplate(String name, String template) {
        ((StringTemplateLoader)CONFIGURATION.getTemplateLoader()).putTemplate(name, template);
    }

    public static void removeTemplate(String name) {
        ((StringTemplateLoader) CONFIGURATION.getTemplateLoader()).removeTemplate(name);
    }
    /**
     * 获取模板渲染
     *
     * @param fileName 模板名称
     * @param map      模板参数
     * @return
     */
    public static String getTemplate(String fileName, Map<String, Object> map) throws IOException {
        StringWriter out = new StringWriter();
        CONFIGURATION.setDefaultEncoding("UTF-8");
        CONFIGURATION.setClassForTemplateLoading(FreeMarkerUtils.class, "/ftl/");
        try {
            Template temp = CONFIGURATION.getTemplate(fileName);
            temp.process(map, out);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException("读取模板数据文件失败:" + e.getMessage());
        } catch (TemplateException e) {
            throw new RuntimeException("模板数据文件解析失败:" + e.getMessage());
        } finally {
            IOUtils.close(out);
        }
        return out.getBuffer().toString();
    }

    /**
     * 模板渲染
     *
     * @param template  模板文本
     * @param params 模板参数
     * @return
     */

    public static String processFreemarker(String template, Map<String, Object> params)
            throws IOException, TemplateException {
        StringWriter result = new StringWriter();
        Template tpl = new Template( "UTIL_TEMP",template, CONFIGURATION);
        tpl.process(params, result);
        return result.toString();
    }

    public static String processFreemarker(String name, String template, Map<String, Object> params)
            throws IOException, TemplateException {
        Template tpl;
        try {
            tpl = CONFIGURATION.getTemplate(name);
        } catch (IOException e) {
            tpl = new Template( "UTIL_TEMP",template, CONFIGURATION);
        }
        StringWriter result = new StringWriter();
        tpl.process(params, result);
        return result.toString();
    }

    public static void main(String[] args) throws Exception {
        String xmlData = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<data>\n" +
                "   <aliasList>as1</aliasList>\n" +
                "   <aliasList>as2</aliasList>\n" +
                "   <provinceList>\n" +
                "    <age>78</age>\n" +
                "    <id>abc</id>\n" +
                "\t<cityList>\n" +
                "\t    <age>78</age>\n" +
                "        <id>c001</id>\n" +
                "\t</cityList>\n" +
                "   </provinceList>\n" +
                "   <provinceList>\n" +
                "    <age>79</age>\n" +
                "    <id>def</id>\n" +
                "   </provinceList>\n" +
                "   <provinceList>\n" +
                "    <age>80</age>\n" +
                "    <id>hij</id>\n" +
                "   </provinceList>\n" +
                "<tage>12</tage>\n" +
                "<tid>123</tid>\n" +
                "</data>";
        Map<String, Object> tmParam = new HashMap<>();

        Object value = XPathUtils.parseNodeList("/data/provinceList", xmlData);
        tmParam.put("dalist", value);

        String tmpa1 ="{\n" +
                "  \"data\": [\n" +
                "  <#list dalist as da>\n" +
                "     {\n" +
                "\t    \"id\": \"${da.id}\",\n" +
                "\t\t\"age\": ${da.age}\n" +
                "\t }\n" +
                "  </#list>\t\n" +
                "  ]\n" +
                "}";
        String tmpa2 = "<data>\n" +
                "<aliasList>no1</aliasList>\n" +
                "<aliasList>no2</aliasList>\n" +
                "<tage>789</tage>\n" +
                "<tid>${dalist}</tid>\n" +
                "</data>";

        String rrr = processFreemarker(tmpa2, tmParam);
        String temp7 = "{\n" +
                "\"data\":{<#if data ??><#if data.tid ??>\"tid\":\"${data.tid}\",</#if> \"provinceList\":[<#if data.provinceList ??><#list data.provinceList as item>{<#assign  realkeys = item?keys/> <#list  realkeys  as key><#if  key=='cityList'>\"cityList\":[<#if item[key] ??><#list item[key] as item>{<#if item.age ??>\"age\":${item.age}</#if> },</#list></#if>]</#if></#list>,<#if id ??>\"id\":\"${id}\"</#if> },</#list></#if>]</#if>}\n" +
                "}";
        Template tpl = new Template( "UTIL_TEMP",temp7, CONFIGURATION);
        Map<String, Object> oo = JSON.parseObject("{\n" +
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
                "\t\t\t\t\t{\n" +
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
                "}", Map.class);
        String  rres = processFreemarker(temp7, oo).replaceAll(",(?=\\s*?[}\\]])", "");

        String temp5 = "{\n" +
                "  <#if resData.id ??>\n" +
                "   \"id\":\"${resData.id}\",\n" +
                "  </#if>\n" +
                "  \"data\": [\n" +
                "  <#if dalist ??> \n" +
                " <#list dalist as item>\n" +
                "     {\n" +
                "\t    <#if item.id ??>\n" +
                "\t    \"id\": ${item.id},\n" +
                "\t\t</#if>\n" +
                "\t\t\"age\": ${item.age},\n" +
                "\t\t\n" +
                "\t\t <#assign  realkeys = item?keys/> \n" +
                "\t\t <#list  realkeys  as key>\n" +
                "\t\t   <#if  key=='daList2'>\n" +
                "\t\t   \n" +
                "\t\t    \"daList2\": [\n" +
                "\t\t      <#list  item[key]   as  item2>\n" +
                "\t\t      {\n" +
                "\t\t        \"addr\": \"${item2.addr}\"\n" +
                "\t\t      }\n" +
                "\t\t      </#list>\n" +
                "\t\t     ]    \n" +
                "\t\t\t \n" +
                "\t\t   </#if>\n" +
                "\t\t </#list>\n" +
                "\t\t  \n" +
                "\t }\n" +
                "  </#list>\n" +
                "</#if>\n" +
                "  ]\n" +
                "}";
        String temp6 = "<?xml version=\\\"1.0\\\" encoding=\\\"utf-8\\\"?>\\n<data>\\n<#if data ??><#if data.provinceList ??><#list data.provinceList as item>\\n<provinceList>\\n<#if item.id ??><id>${item.id}</id>\\n</#if> <#assign  realkeys = item?keys/><#list  realkeys  as key><#if  key==\"cityList\"><#if item[key] ??><#list item[key] as item>\\n<cityList>\\n<#if item.age ??><age>${item.age}</age>\\n</#if> </cityList>\\n</#list>\\n</#if></#if></#list></provinceList>\\n</#list>\\n</#if></#if></data>\\n";

        Configuration cfg = new Configuration();
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        // 解析模板内容
//        cfg.parseTemplate(new StringReader(templateContent), "myTemplate");
        System.out.println("模板语法正确。");
        Map<String, Object> param = new HashMap<>();
        Map<String, Object> item1 = new HashMap<>();
        item1.put("id", "001");
        item1.put("age", 100);
        Map<String, Object> addr = new HashMap<>();
        addr.put("addr", "CHN");
        Map<String, Object> addr2 = new HashMap<>();
        addr2.put("addr", "USA");
        item1.put("daList2", Arrays.asList(addr, addr2));
        Map<String, Object> item2 = new HashMap<>();
//        item2.put("id", "002");
        item2.put("age", 99);
        param.put("dalist", Arrays.asList(item1, item2));
        Map<String, Object> resData = new HashMap<>();
        resData.put("id", "503");
        param.put("resData", resData);
        Map<String, Object> data2 = new HashMap<>();
        data2.put("test", "world");
//        param.put("data2", data2);
        String resultMap = FreeMarkerUtils.processFreemarker(temp5, param);

        String temp1 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<data>\n<#if data ??><#if data.aliasList ??><#list data.aliasList as aliasListItem>\n<aliasList>${aliasListItem}</aliasList>\n</#list>\n</#if><#if data.provinceList ??><#list data.provinceList as provinceListItem>\n<provinceList>\n<#assign  realkeys = provinceListItem?keys/><#list  realkeys  as key><#if  key=='cityList'><#if provinceListItem[key] ??><#list provinceListItem[key] as cityListItem>\n<cityList>\n<#if cityListItem.age ??><age>${cityListItem.age}</age>\n</#if> </cityList>\n</#list>\n</#if></#if></#list><#if provinceListItem.id ??><id>${provinceListItem.id}</id>\n</#if> </provinceList>\n</#list>\n</#if><#if data.tage ??><tage>${data.tage}</tage>\n</#if> <#if data.tid ??><tid>${data.tid}</tid>\n</#if> </#if></data>\n";
        String json1 = "{\n" +
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
                "\t\t\t\t\t{\n" +
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
        Map map1 = JSON.parseObject(json1, Map.class);
        String res1 = processFreemarker(temp1, map1);
    }

    /**
     * 根据报文类型和报文格式配置生成jsonpath火xpath报文拆解模板
     * @param messageFormat
     * @param messageAttrs
     * @return
     */

    public List<Map<String, String>> buildUnpackTemplate(String messageFormat, List<GwMessageAttrDTO> messageAttrs) {
        List<Map<String, String>> template = new ArrayList<>();
        if (Objects.equals(messageFormat, "application/json")) {
            buildUnpackInfoRecurse(messageAttrs, "$", (parentPath, dto) ->
                    "array".equals(dto.getDataType())
                            ? parentPath + "." + dto.getMetadataKey() + "[*]"
                            : parentPath + "." + dto.getMetadataKey(), null, template);

        } else if (Objects.equals(messageFormat, "application/xml")) {
            buildUnpackInfoRecurse(messageAttrs, "", (parentPath, dto) ->
                    parentPath + "/" + dto.getMetadataKey(), null, template);
        }
        return template;
    }

    private static void buildUnpackInfoRecurse(List<GwMessageAttrDTO> messageAttrs, String parentPath,
                                               BiFunction<String, GwMessageAttrDTO, String> attrPathBuildFun,
                                               GwMessageAttrDTO parentDto, List<Map<String, String>> resList) {
        if (CollectionUtils.isEmpty(messageAttrs)) {
            return;
        }
        String parentFmKey = Objects.isNull(parentDto) ? ""
                : (Objects.equals("array", parentDto.getDataType()) ? parentDto.getMetadataKey() + "Item"
                : parentDto.getFreemarkerKey());

        for(GwMessageAttrDTO dto : messageAttrs) {
            String key = dto.getMetadataKey();
            String freemarkerKey = StringUtils.isEmpty(parentFmKey) ? key
                    : (Objects.equals("array", parentDto.getDataType()) ? parentFmKey + "." + key
                    : parentFmKey + "_" + key);
            dto.setFreemarkerKey(freemarkerKey);
            // 数组只能作为一个整体处理，如果父节点是数组，则不为其子节点生成拆包Map
            if (Objects.nonNull(parentDto) && "array".equals(parentDto.getDataType())) {
                continue;
            }
            String dataType = dto.getDataType();
            Map<String, String> itemMap = new HashMap<>();
            itemMap.put("attributeName", key);
            String attributePath = attrPathBuildFun.apply(parentPath, dto);
            itemMap.put("attributePath", attributePath);
            itemMap.put("freemarkerKey", freemarkerKey);
            if ("array".equals(dataType)) {
                itemMap.put("attributeType", "4");
            } else if ("object".equals(dataType)) {
                itemMap.put("attributeType", "5");
            }
            // 非子节点 对象 不生成拆包Map
            if (CollectionUtils.isNotEmpty(dto.getChildren())) {
                if ("array".equals(dto.getDataType())) {
                    resList.add(itemMap);
                }
                buildUnpackInfoRecurse(dto.getChildren(), attributePath, attrPathBuildFun, dto, resList);
            } else {
                resList.add(itemMap);
            }
        }
    }

    /**
     * 根据报文类型和报文字段配置生成json火xml Freemarker模板
     * @param messageFormat
     * @param messageAttrs
     * @return
     */
    public String buildPackTemplate(String messageFormat, List<GwMessageAttrDTO> messageAttrs) {
        if (Objects.equals(messageFormat, "application/json")) {
            return buildPackTemplateJson(messageAttrs);
        } else if (Objects.equals(messageFormat, "application/xml")) {
            return buildPackTemplateXml(messageAttrs);
        }
        return null;
    }

    private static String buildPackTemplateJson(List<GwMessageAttrDTO> messageAttrs) {
        StringBuilder tmp = new StringBuilder();
        tmp.append("{").append("\n");
        buildPackTemplateJsonRecurse(messageAttrs, null, tmp);
        tmp.append("}");
        return tmp.toString();
    }

    private static void buildPackTemplateJsonRecurse(List<GwMessageAttrDTO> messageAttrs,  GwMessageAttrDTO parentDto, StringBuilder template) {
        if (CollectionUtils.isEmpty(messageAttrs)) {
            return;
        }
        String parentDataType = Objects.isNull(parentDto) ? null : parentDto.getDataType();
        String parentItemName = Objects.nonNull(parentDto) && "array".equals(parentDto.getDataType())
                ? parentDto.getMetadataKey() + "Item" : "gwItem";
        for (int i=0; i<messageAttrs.size(); i++) {
            GwMessageAttrDTO dto = messageAttrs.get(i);
            switch (dto.getDataType()) {
                case "array":
                    String itemName = dto.getMetadataKey() + "Item";
                    // List 元素是基本类型或字符串场景，parentDataType类型判断是处理List嵌套场景
                    if (CollectionUtils.isEmpty(dto.getChildren())) {
                        if ("array".equals(parentDataType)) {
                            template.append("<#assign  realkeys = " + parentItemName + "?keys/> ")
                                    .append("<#list  realkeys  as key>")
                                    .append("<#if  key=='" + dto.getMetadataKey() + "'>");
                        }
                        template.append("\"" + dto.getMetadataKey() + "\"").append(":")
                                .append("[");
                        String fm = "array".equals(parentDataType) ? parentItemName + "[key]" : dto.getFreemarkerKey();
                        template.append("<#if " + fm + " ??>");
                        template.append("<#list " + fm + " as " + itemName + ">");
                        template.append("\"${" + itemName + "}\"")
                                .append(",");

                        template.append("</#list>");
                        template.append("</#if>");
                        template.append("]");
                        if ("array".equals(parentDataType)) {
                            template.append("</#if>")
                                    .append("</#list>");
                        }
                        template.append(",");
                        break;
                    }
                    // List元素类型是对象场景，parentDataType类型判断是处理List嵌套场景
                    if ("array".equals(parentDataType)) {
                        template.append("<#assign  realkeys = " + parentItemName + "?keys/> ")
                                .append("<#list  realkeys  as key>")
                                .append("<#if  key=='" + dto.getMetadataKey() + "'>");
                    }
                    template.append("\"" + dto.getMetadataKey() + "\"").append(":")
                            .append("[").append("\n");
                    String fm = "array".equals(parentDataType) ? parentItemName + "[key]" : dto.getFreemarkerKey();
                    template.append("<#if " + fm + " ??>");
                    template.append("<#list " + fm + " as " + itemName + ">");
                    template.append("{").append("\n");
                    buildPackTemplateJsonRecurse(dto.getChildren(), dto, template);
                    template.append("}");
                    template.append(",");
                    template.append("</#list>");
                    template.append("</#if>").append("\n");
                    template.append("]");
                    if ("array".equals(parentDataType)) {
                        template.append("</#if>")
                                .append("</#list>");
                    }
                    template.append(",")
                            .append("\n");
                    break;
                case "object":
                    template.append("\"" + dto.getMetadataKey() + "\"").append(":")
                            .append("{")
                            .append("\n");
//                    template.append("<#if " + dto.getFreemarkerKey() + " ??>");
                    buildPackTemplateJsonRecurse(dto.getChildren(), dto, template);
//                    template.append("</#if>");
                    template.append("}")
                            .append(",")
                            .append("\n");
                    break;
                case "string":
                    template.append("<#if " + dto.getFreemarkerKey() + " ??>");
                    template.append("\"" + dto.getMetadataKey() + "\"").append(":")
                            .append("\"")
                            .append("${" + dto.getFreemarkerKey() + "}")
                            .append("\"")
                            .append(",");
                    template.append("</#if>")
                            .append("\n");
                    break;
                default:
                    template.append("<#if " + dto.getFreemarkerKey() + " ??>");
                    template.append("\"" + dto.getMetadataKey() + "\"").append(":")
                            .append("${" + dto.getFreemarkerKey() + "}")
                            .append(",");
                    template.append("</#if>")
                            .append("\n");
                    break;
            }
            if (Objects.equals(i, messageAttrs.size()-1)) {
                template.deleteCharAt(template.lastIndexOf(","));
            }
        }
    }

    private static String buildPackTemplateXml(List<GwMessageAttrDTO> messageAttrs) {
        StringBuilder tmp = new StringBuilder();
        tmp.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
                .append("\n");
        buildPackTemplateXmlRecurse(messageAttrs, null, tmp);
        return tmp.toString();
    }

    private static void buildPackTemplateXmlRecurse(List<GwMessageAttrDTO> messageAttrs, GwMessageAttrDTO parentDto, StringBuilder template) {
        if (CollectionUtils.isEmpty(messageAttrs)) {
            return;
        }
        String parentDataType = Objects.isNull(parentDto) ? null : parentDto.getDataType();
        String parentItemName = Objects.nonNull(parentDto) && "array".equals(parentDto.getDataType())
                ? parentDto.getMetadataKey() + "Item" : "gwItem";
        for (int i=0; i<messageAttrs.size(); i++) {
            GwMessageAttrDTO dto = messageAttrs.get(i);
            switch (dto.getDataType()) {
                case "array":
                    String itemName = dto.getMetadataKey() + "Item";
                    // List 元素是基本类型或字符串场景，parentDataType类型判断是处理List嵌套场景
                    if (CollectionUtils.isEmpty(dto.getChildren())) {
                        if ("array".equals(parentDataType)) {
                            template.append("<#assign  realkeys = " + parentItemName +"?keys/>")
                                    .append("<#list  realkeys  as key>")
                                    .append("<#if  key=='" + dto.getMetadataKey() + "'>");
                        }
                        String fm = "array".equals(parentDataType) ? parentItemName + "[key]" : dto.getFreemarkerKey();
                        template.append("<#if " + fm + " ??>");
                        template.append("<#list " +  fm + " as " + itemName + ">")
                                .append("\n");
                        template.append("<" + dto.getMetadataKey() + ">");
                        template.append("${" + itemName + "}");
                        template.append("</" + dto.getMetadataKey() + ">")
                                .append("\n");
                        template.append("</#list>")
                                .append("\n");
                        template.append("</#if>");
                        if ("array".equals(parentDataType)) {
                            template.append("</#if>")
                                    .append("</#list>");
                        }
                        break;
                    }
                    // List 元素是对象场景，parentDataType类型判断是处理List嵌套场景
                    if ("array".equals(parentDataType)) {
                        template.append("<#assign  realkeys = " + parentItemName + "?keys/>")
                                .append("<#list  realkeys  as key>")
                                .append("<#if  key=='" + dto.getMetadataKey() + "'>");
                    }
                    String fm = "array".equals(parentDataType) ? parentItemName + "[key]" : dto.getFreemarkerKey();
                    template.append("<#if " + fm + " ??>");
                    template.append("<#list " +  fm + " as " + itemName + ">")
                            .append("\n");
                    template.append("<" + dto.getMetadataKey() + ">")
                            .append("\n");
                    buildPackTemplateXmlRecurse(dto.getChildren(), dto, template);
                    template.append("</" + dto.getMetadataKey() + ">")
                            .append("\n");
                    template.append("</#list>")
                            .append("</#if>");
                    if ("array".equals(parentDataType)) {
                        template.append("</#if>")
                                .append("</#list>");
                    }
                    template.append("\n");
                    break;
                case "object":
                    template.append("<" + dto.getMetadataKey() + ">")
                            .append("\n");
//                    template.append("<#if " + dto.getFreemarkerKey() + " ??>");
                    buildPackTemplateXmlRecurse(dto.getChildren(), dto, template);
//                    template.append("</#if>");
                    template.append("</" + dto.getMetadataKey() + ">")
                            .append("\n");
                    break;
                default:
                    template.append("<#if " + dto.getFreemarkerKey() + " ??>");
                    template.append("<" + dto.getMetadataKey() + ">")
                            .append("${" + dto.getFreemarkerKey() + "}")
                            .append("</" + dto.getMetadataKey() + ">");
                    template.append("</#if>").append("\n");
                    break;
            }
        }
    }

}
