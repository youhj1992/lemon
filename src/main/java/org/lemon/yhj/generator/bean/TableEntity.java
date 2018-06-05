package org.lemon.yhj.generator.bean;

import java.util.List;
import java.util.Map;

/**
 * 表数据
 */
public class TableEntity {

    private String tableSchema;
    //表的名称
    private String tableName;
    //表的备注
    private String comments;

    //表的列名(所有列名)
    private List<ColumnEntity> columns;

    //表的所有列，不包含 anto_increment 和 on update CURRENT_TIMESTAMP 和 gmt_create
    private List<ColumnEntity> mainColumns;

    //类名(第一个字母大写)，如：sys_user => SysUser
    private String className;
    //类名(第一个字母小写)，如：sys_user => sysUser
    private String classname;

    private ColumnEntity pk; //表的主键

    private Map<String, List<ColumnEntity>> uniqueKeyMap; //表的唯一键列表, key是唯一索引名称，value是唯一索引包含的列

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ColumnEntity getPk() {
        return pk;
    }

    public void setPk(ColumnEntity pk) {
        this.pk = pk;
    }

    public List<ColumnEntity> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnEntity> columns) {
        this.columns = columns;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public Map<String, List<ColumnEntity>> getUniqueKeyMap() {
        return uniqueKeyMap;
    }

    public void setUniqueKeyMap(Map<String, List<ColumnEntity>> uniqueKeyMap) {
        this.uniqueKeyMap = uniqueKeyMap;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public List<ColumnEntity> getMainColumns() {
        return mainColumns;
    }

    public void setMainColumns(List<ColumnEntity> mainColumns) {
        this.mainColumns = mainColumns;
    }

    @Override
    public String toString() {
        return "TableEntity{" +
                "tableName='" + tableName + '\'' +
                ", comments='" + comments + '\'' +
                ", columns=" + columns +
                ", className='" + className + '\'' +
                ", classname='" + classname + '\'' +
                ", pk=" + pk +
                ", uniqueKeyMap=" + uniqueKeyMap +
                '}';
    }
}
