package org.lemon.yhj.generator.utils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.lemon.yhj.generator.bean.ColumnEntity;
import org.lemon.yhj.generator.bean.GeneratorConfig;
import org.lemon.yhj.generator.bean.TableEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtils {

    private static Map<String, String> typeMap= new HashMap<>();

    static {
        typeMap.put("tinyint","Integer");
        typeMap.put("smallint","Integer");
        typeMap.put("mediumint","Integer");
        typeMap.put("int","Integer");
        typeMap.put("integer","Integer");
        typeMap.put("bigint","Long");
        typeMap.put("bigint unsigned","Long");
        typeMap.put("float","Float");
        typeMap.put("double","Double");
        typeMap.put("decimal","BigDecimal");
        typeMap.put("char","String");
        typeMap.put("varchar","String");
        typeMap.put("tinytext","String");
        typeMap.put("text","String");
        typeMap.put("mediumtext","String");
        typeMap.put("longtext","String");
        typeMap.put("date","Date");
        typeMap.put("datetime","Date");
        typeMap.put("timestamp","Date");

    }

    public static TableEntity getTableInfo(GeneratorConfig config) throws SQLException {
        String tableName = config.getTableName();
        String tableCatalog = config.getTableCatalog();
        Connection conn = config.getConnection();
        //select * from information_schema.TABLES where TABLE_NAME = 'daily' and TABLE_SCHEMA = 'shark';
        String getTableSql = "select table_name as tableName, table_comment as comments from information_schema.tables " +
                "where table_schema = '" + tableCatalog + "' and table_name = '" + tableName + "'";

        QueryRunner qRunner = new QueryRunner();
        TableEntity entity = qRunner.query(conn, getTableSql, new BeanHandler<>(TableEntity.class));
        entity.setTableSchema(config.getTableCatalog());

        String className = tableToJava(entity.getTableName(), config.getTablePrefix());
        entity.setClassName(className);
        entity.setClassname(StringUtils.uncapitalize(className));

        List<ColumnEntity> columnList = getTableColumns(conn, tableCatalog, tableName);
        entity.setColumns(columnList);
        entity.setMainColumns(getMainColumns(columnList));

        entity.setPk(getPk(columnList ,conn, tableCatalog, tableName));

        Map<String, List<ColumnEntity>> maps = getUniqueKeyMap(conn, tableCatalog, tableName, columnList);
        entity.setUniqueKeyMap(maps);

        return entity;
    }

    /**
     * 排除 c.extra != 'auto_increment' && c.extra != "on update CURRENT_TIMESTAMP" && c.columnName != "gmt_create">
     * @param columnList
     * @return
     */
    private static List<ColumnEntity> getMainColumns(List<ColumnEntity> columnList) {
        List<ColumnEntity> list = new ArrayList<>();
        for (ColumnEntity columnEntity : columnList){
            if ( !"auto_increment".equalsIgnoreCase(columnEntity.getExtra())
                    && !"on update CURRENT_TIMESTAMP".equalsIgnoreCase(columnEntity.getExtra())
                    && !"created_at".equalsIgnoreCase(columnEntity.getColumnName())){
                list.add(columnEntity);
            }
        }
        return list;
    }

    private static Map<String,List<ColumnEntity>> handleUniqueName(Map<String, List<ColumnEntity>> uniqueKeyMap) {
        Map<String, List<ColumnEntity>> map = new HashMap<>();
        for (Map.Entry<String, List<ColumnEntity>> entry : uniqueKeyMap.entrySet()) {
            StringBuilder builder = new StringBuilder();
            for (ColumnEntity columnEntity : entry.getValue()) {
                builder.append(columnEntity.getAttrName()).append("And");
            }
            String key = builder.substring(0, builder.lastIndexOf("And"));
            map.put(key, entry.getValue());
        }
        return map;
    }

    private static Map<String,List<ColumnEntity>> getUniqueKeyMap(Connection conn, String schemaName, String tableName, List<ColumnEntity> columns) throws SQLException {
        String sql = "select * from information_schema.KEY_COLUMN_USAGE " +
                "where TABLE_NAME = '" + tableName + "' and TABLE_SCHEMA = '" + schemaName + "' and CONSTRAINT_NAME != 'PRIMARY'";
        QueryRunner queryRunner = new QueryRunner();
        return queryRunner.query(conn, sql, resultSet -> {
            Map<String, List<ColumnEntity>> map = new HashMap<>();
            while (resultSet.next()) {
                String keyName = getUkName(resultSet.getString("CONSTRAINT_NAME"));
                ColumnEntity columnEntity = getByColumnName(resultSet.getString("COLUMN_NAME"), columns);
                if (columnEntity!=null) {
                    if (map.containsKey(keyName)) {
                        map.get(keyName).add(columnEntity);
                    } else {
                        List<ColumnEntity> columnEntities = new ArrayList<>();
                        columnEntities.add(columnEntity);
                        map.put(keyName, columnEntities);
                    }
                }
            }
            return map;
        });
    }

    /**
     * 获取主键
     */
    private static ColumnEntity getPk(List<ColumnEntity> list, Connection conn, String schemaName, String tableName) throws SQLException {
        String sql = "select COLUMN_NAME as columnName from information_schema.KEY_COLUMN_USAGE where " +
                "TABLE_NAME = '" + tableName + "' and TABLE_SCHEMA = '" + schemaName + "' and CONSTRAINT_NAME = 'PRIMARY'";
        QueryRunner queryRunner = new QueryRunner();
        ColumnEntity columnEntity = queryRunner.query(conn, sql, new BeanHandler<>(ColumnEntity.class));
        for (ColumnEntity pkColumn : list) {
            if (pkColumn.getColumnName().equalsIgnoreCase(columnEntity.getColumnName())) {
                return pkColumn;
            }
        }
        return null;
    }

    private static List<ColumnEntity> getTableColumns(Connection conn, String schemaName, String tableName) throws SQLException {

        String getColumnSql = "SELECT COLUMN_NAME as columnName,  DATA_TYPE as dataType, COLUMN_COMMENT as comments, " +
                "extra as extra from information_schema.COLUMNS where TABLE_SCHEMA = '" + schemaName + "' and TABLE_NAME = '" + tableName + "'";

        
        QueryRunner queryRunner = new QueryRunner();
        List<ColumnEntity> columnEntities = queryRunner.query(conn, getColumnSql, resultSet -> {
            List<ColumnEntity> list = new ArrayList<>();
            while (resultSet.next()){
                ColumnEntity columnEntity = new ColumnEntity();
                columnEntity.setColumnName(resultSet.getString("columnName"));
                columnEntity.setDataType(resultSet.getString("dataType"));
                columnEntity.setComments(resultSet.getString("comments"));
                columnEntity.setExtra(resultSet.getString("extra"));
                //列名转换成Java属性名
                String attrName = columnToJava(columnEntity.getColumnName()).replace("uk","");
                columnEntity.setAttrName(attrName);
                columnEntity.setAttrname(StringUtils.uncapitalize(attrName));
                //columnEntity.setExtra(rs.getString("EXTRA"));
                //列的数据类型，转换成Java类型
                String attrType = typeMap.get(StringUtils.lowerCase(columnEntity.getDataType()));
                columnEntity.setAttrType(attrType);
                list.add(columnEntity);
            }
            return list;
        });

       return columnEntities;
    }

    /**
     * 列名转换成Java属性名
     */
    private static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "" );
    }

    /**
     * 表名转换成Java类名
     */
    private static String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replace(tablePrefix, "" );
        }
        return columnToJava(tableName);
    }

    private static ColumnEntity getByColumnName(String columnName, List<ColumnEntity> columns) {
        for (ColumnEntity columnEntity : columns) {
            if (columnEntity.getColumnName().equalsIgnoreCase(columnName)) {
                return columnEntity;
            }
        }
        return null;
    }

    private static String getUkName(String name){
        return columnToJava(name.replace("uk","_"));
    }
}
