package org.lemon.yhj.lemon;


import org.lemon.yhj.generator.MybatisGenerator;
import org.lemon.yhj.generator.bean.GeneratorConfig;

/**
 * 测试Mybatis代码生成工具
 */
public class CodeGenerator {

    public static void main(String[] args) {
        //数据库的连接信息、用户名、密码
        String DB_URL = "jdbc:mysql://47.97.189.222:3309/guide?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&connectTimeout=5000&socketTimeout=60000";
        String user = "guide";
        String pwd = "bxm.iueM^EY!8F";

        GeneratorConfig config = new GeneratorConfig(DB_URL, user, pwd);
        //设置生成的 XXXXMapper.java所在的包名
        config.setDaoPackage("com.bxm.adsprod.dal.activity.land");
        //设置生成的 XXXXDO.java所在的包名
        config.setDOpackage("com.bxm.adsprod.dal.activity.land");
        //设置生成的XXX.java存放的模块名
        config.setModuleName("lemon");
        //设置生成的XXXXMapper.xml存在的相对路径，相对工程根目录，
        config.setXmlLocation("lemon/src/main/resources/mybatis/activity");
        //设置数据库名，库名填错会找不到表
        config.setTableCatalog("guide");
        //设置表名，生成器根据设置的表生成代码
        config.setTableName("tbl_land_user_info");
        //设置数据表前缀，比如tb_activity的前缀为tb，设置了此项之后生成的do、dao、mapper.xml不包含前缀tb
        config.setTablePrefix("tbl");

        //代码生成
        MybatisGenerator.genCode(config);
    }
}

