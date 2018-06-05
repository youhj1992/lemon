package ${doPackage};

import java.io.Serializable;
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

<#if table.comments?? >
/**
* ${table.comments}
*/
</#if>
public class ${table.className}DO implements Serializable {

    private static final long serialVersionUID = 1L;

<#list columns as column>
    <#if column.comments?? >
    /**
    * ${column.comments}
    */
    </#if>
    private ${column.attrType} ${column.attrname};

</#list>

<#list columns as column>
    public void set${column.attrName}(${column.attrType} ${column.attrname}) {
        this.${column.attrname} = ${column.attrname};
    }

    public ${column.attrType} get${column.attrName}() {
        return ${column.attrname};
    }

</#list>
}

