package ${daoPackage};

import ${doPackage}.${table.className}DO;
import java.util.List;
import org.apache.ibatis.annotations.Param;
<#assign hasBigDecimal=0, hasDate=0 >
<#list columns as c>
    <#if c.attrType == 'BigDecimal' >
        <#assign hasBigDecimal=hasBigDecimal+1>
    <#elseif c.attrType == 'Date' >
        <#assign hasDate = hasDate + 1>
    </#if>
</#list>
<#if (hasBigDecimal>0) >
import java.math.BigDecimal;
</#if>
<#if (hasDate>0)>
import java.util.Date;
</#if>
import java.util.Map;


public interface ${table.className}Mapper {

	int save(${table.className}DO ${table.classname}DO);

<#if pk??>
    int deleteBy${pk.attrName}(@Param("${pk.attrname}")${pk.attrType} ${pk.attrname});

    int deleteBatchBy${pk.attrName}(@Param("${pk.attrname}s")List<${pk.attrType}> ${pk.attrname}s);

    int updateBy${pk.attrName}(${table.className}DO ${table.classname}DO);

    ${table.className}DO getBy${pk.attrName}(@Param("${pk.attrname}")${pk.attrType} ${pk.attrname});

    List<${table.className}DO> listBy${pk.attrName}(@Param("${pk.attrname}s")List<${pk.attrType}> ${pk.attrname}s);

</#if>
<#list uks?keys as key>
    ${table.className}DO getBy${key}(<#assign idx=0><#list uks[key] as uk>@Param("${uk.attrname}")${uk.attrType} ${uk.attrname}<#assign idx=idx+1><#if (idx<uks[key]?size)>,</#if></#list>);

    List<${table.className}DO> listBy${key}(<#assign idx=0><#list uks[key] as uk>@Param("${uk.attrname}s")List<${uk.attrType}> ${uk.attrname}s<#assign idx=idx+1><#if (idx<uks[key]?size)>,</#if></#list>);

</#list>
    List<${table.className}DO> listByParam(Map params);

    Integer countByParam(Map params);
}
