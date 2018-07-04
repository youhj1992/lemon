<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${daoPackage}.${table.className}Mapper">

    <resultMap type="${doPackage}.${table.className}DO" id="${table.classname}Map">
    <#list columns as column>
        <result property="${column.attrname}" column="${column.columnName}"/>
    </#list>
    </resultMap>

    <sql id="columns"> <#assign idx=1><#list columns as c>${c.columnName}<#if (idx<columns?size)>
        ,</#if><#assign idx=idx+1></#list> </sql>

    <insert id="save" <#if pk.extra == 'auto_increment'>useGeneratedKeys="true" keyProperty="${pk.attrname}"</#if>
            parameterType="${doPackage}.${table.className}DO">
        INSERT INTO ${table.tableSchema}.${table.tableName}(<#assign idx=1><#list table.mainColumns as c>${c.columnName}<#if (idx<table.mainColumns?size)>,</#if><#assign idx=idx+1></#list>)
        VALUES(<#assign idx=1><#list table.mainColumns as c>${pre}${c.attrname}${end}<#if (idx<table.mainColumns?size)>,</#if><#assign idx=idx+1></#list>)
    </insert>

    <delete id="deleteBy${pk.attrName}">
        DELETE FROM ${table.tableSchema}.${table.tableName} WHERE ${pk.columnName}=${pre}${pk.attrname}${end}
    </delete>

    <delete id="deleteBatchBy${pk.attrName}">
        DELETE FROM ${table.tableSchema}.${table.tableName} WHERE ${pk.columnName} IN
        <foreach item="item" index="index" collection="${pk.attrname}s" open="(" separator="," close=")">
            ${pre}item${end}
        </foreach>
    </delete>

    <update id="updateBy${pk.attrName}" parameterType="${doPackage}.${table.className}DO">
        UPDATE ${table.tableSchema}.${table.tableName}
        <set>
        <#list columns as c>
            <#if c.extra != 'auto_increment' && c.extra != "on update CURRENT_TIMESTAMP" && c.columnName != "gmt_create" && c.columnName != "create_time">
            <if test="${c.attrname} != null">
                ${c.columnName} = ${pre}${c.attrname}${end},
            </if>
            </#if>
        </#list>
        </set>
        WHERE ${pk.columnName}=${pre}${pk.attrname}${end}
    </update>

    <select id="getBy${pk.attrName}" resultMap="${table.classname}Map">
        SELECT <include refid="columns"/>
        FROM ${table.tableSchema}.${table.tableName}
        WHERE ${pk.columnName} = ${pre}${pk.attrname}${end}
    </select>

    <select id="listBy${pk.attrName}" resultMap="${table.classname}Map">
        SELECT <include refid="columns"/>
        FROM ${table.tableSchema}.${table.tableName}
        WHERE ${pk.columnName} IN
        <foreach item="item" index="index" collection="${pk.attrname}s" open="(" separator="," close=")">
            ${pre}item${end}
        </foreach>
    </select>


<#list uks?keys as key>
    <select id="getBy${key}" resultMap="${table.classname}Map">
        SELECT <include refid="columns"/>
        FROM ${table.tableSchema}.${table.tableName}
        WHERE <#assign idx=0><#list uks[key] as uk>${uk.columnName}=${pre}${uk.attrname}${end}<#assign idx=idx+1><#if (idx<uks[key]?size)> and </#if></#list>
    </select>

    <select id="listBy${key}" resultMap="${table.classname}Map">
        SELECT <include refid="columns"/>
        FROM ${table.tableSchema}.${table.tableName}
        WHERE
        <#assign idx=0><#list uks[key] as uk>
        ${uk.columnName} IN
        <foreach item="item" index="index" collection="${uk.attrname}s" open="(" separator="," close=")">
            ${pre}item${end}
        </foreach>
        <#assign idx=idx+1>
        <#if (idx<uks[key]?size)> and </#if>
    </#list>
    </select>
</#list>

    <select id="listByParam" resultMap="${table.classname}Map" parameterType="java.util.Map">
        SELECT <include refid="columns"/>
        FROM ${table.tableSchema}.${table.tableName}
        <where>
        <#list table.mainColumns as c>
            <if test="${c.attrname} !=null">
                AND ${c.columnName} = ${pre}${c.attrname}${end}
            </if>
        </#list>
        </where>
        <if test="offset != null and limit != null">
            limit ${pre}offset${end}, ${pre}limit${end}
        </if>
    </select>

    <select id="countByParam" resultType="java.lang.Integer" parameterType="java.util.Map">
        SELECT count(*)
        FROM ${table.tableSchema}.${table.tableName}
        <where>
        <#list table.mainColumns as c>
            <if test="${c.attrname} !=null">
                AND ${c.columnName} = ${pre}${c.attrname}${end}
            </if>
        </#list>
        </where>
    </select>

</mapper>