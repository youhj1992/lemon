package org.lemon.yhj.generator.bean;

import org.apache.commons.lang.StringUtils;
import org.lemon.yhj.generator.GeneratorException;
import org.lemon.yhj.generator.MybatisGenerator;

import java.sql.Connection;

/**
 * 代码生成器配置类
 */
public class GeneratorConfig {

    /**
     * 表名称 前缀，例如tb_activity, tb为前缀
     */
    private String tablePrefix;

    /**
     * 数据库连接
     */
    private Connection connection;

    /**
     * 要生成代码的表明
     */
    private String tableName;

    private String tableCatalog;

    /**
     * dao的包名，例如：cn.com.duiba.tuia.ssp.center.biz.model
     */
    private String daoPackage;

    /**
     * Mybatis的xml的相对路径，相对于src/main/resources
     */
    private String xmlLocation;

    /**
     * DO的包 例如：cn.com.duiba.tuia.ssp.center.biz.dao
     */
    private String DOpackage;

    /**
     * 模块名称
     */
    private String moduleName;

    public GeneratorConfig(String dbUrl, String dbUserName, String dbPasswd) {
        this.connection = MybatisGenerator.getConnection(dbUrl, dbUserName, dbPasswd);
    }

    public void check() {
        if (connection == null)
            throw new GeneratorException("connect to mysql failed");

        if (StringUtils.isEmpty(tableName))
            throw new GeneratorException("tableName is null");

        if (StringUtils.isEmpty(daoPackage))
            throw new GeneratorException("daoPackage is null");

        if (StringUtils.isEmpty(DOpackage))
            throw new GeneratorException("DOpackage is null");

        if (StringUtils.isEmpty(xmlLocation))
            throw new GeneratorException("xmlLocation is null");

    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDaoPackage() {
        return daoPackage;
    }

    public void setDaoPackage(String daoPackage) {
        this.daoPackage = daoPackage;
    }

    public String getXmlLocation() {
        return xmlLocation;
    }

    public void setXmlLocation(String xmlLocation) {
        this.xmlLocation = xmlLocation;
    }

    public String getDOpackage() {
        return DOpackage;
    }

    public void setDOpackage(String DOpackage) {
        this.DOpackage = DOpackage;
    }

    public String getTableCatalog() {
        return tableCatalog;
    }

    public void setTableCatalog(String tableCatalog) {
        this.tableCatalog = tableCatalog;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
