package org.lemon.yhj.generator.utils;

import org.apache.commons.lang.StringUtils;
import org.lemon.yhj.generator.GeneratorException;
import org.lemon.yhj.generator.bean.GeneratorConfig;

import java.io.File;
import java.net.URL;

public class PathUtils {

    private static String getProjectHome(){
        ClassLoader loader = PathUtils.class.getClassLoader();
        URL url = loader.getResource("");
        String file = url.getFile();
        File testPath = new File(file);
        return testPath.getParentFile().getParent();
    }

    /**
     * 根据包名找到包的实际路径
     * @param packageName 包名，例如：cn.com.duiba.shark.gitlab.dao
     * @return 包的文件路径
     */
    public static String getPathByPackage(String packageName, GeneratorConfig config){
        if (StringUtils.isEmpty(packageName))
            throw new GeneratorException("包名不能为空");
        if (StringUtils.isEmpty(config.getModuleName())) {
            return "src/main/java/" + StringUtils.replace(packageName,"." ,"/");
        } else {
            return config.getModuleName() + File.separator + "src/main/java/" + StringUtils.replace(packageName,"." ,"/");
        }

    }
}
