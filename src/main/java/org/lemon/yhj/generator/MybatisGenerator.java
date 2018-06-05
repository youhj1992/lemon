package org.lemon.yhj.generator;


import org.lemon.yhj.generator.bean.GeneratorConfig;
import org.lemon.yhj.generator.bean.TableEntity;
import org.lemon.yhj.generator.code.JavaDOGenerator;
import org.lemon.yhj.generator.code.JavaDaoGenerator;
import org.lemon.yhj.generator.code.SQLXmlGenerator;
import org.lemon.yhj.generator.freemarker.FreemarkerService;
import org.lemon.yhj.generator.utils.DBUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 代码生成器
 */
public class MybatisGenerator {

    /**
     * 代码生成器主类
     *
     * @return
     */
    public static void genCode(GeneratorConfig config) {
        check(config);

        FreemarkerService freemarkerService = new FreemarkerService();
        TableEntity tableEntity;
        try {
            tableEntity = DBUtils.getTableInfo(config);
        } catch (SQLException e) {
            throw new GeneratorException(e);
        }

        //生成do文件
        JavaDOGenerator doGenerator = new JavaDOGenerator(freemarkerService, tableEntity, config);
        doGenerator.gen();
        //生成dao文件
        JavaDaoGenerator daoGenerator = new JavaDaoGenerator(freemarkerService, tableEntity, config);
        daoGenerator.gen();
        //生成xml文件
        SQLXmlGenerator sqlXmlGenerator = new SQLXmlGenerator(freemarkerService, tableEntity, config);
        sqlXmlGenerator.gen();
    }

    private static void check(GeneratorConfig config) {
        if (config == null)
            throw new GeneratorException("生成代码前请设置");

        config.check();
    }

    public static Connection getConnection(String url, String user, String pwd){
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user,pwd);
        } catch (ClassNotFoundException e) {
            throw new GeneratorException(e);
        } catch (SQLException e) {
            throw new GeneratorException(e);
        }
        return connection;
    }
}
