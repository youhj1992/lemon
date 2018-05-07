package org.lemon.yhj.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * class相关工具类
 */
public class ClassUtils {

    private static final String CLASS_POSTFIX = ".class";

    /**
     * 用于获取指定包名下的所有类名.
     * @param pkgName 包名
     * @param isRecursive 标识是否要遍历该包路径下子包的类名
     * @param annotation 筛选出带此注解的类,如果为null则返回全部类
     * @return
     */
    public static List<Class<?>> getClassList(String pkgName , boolean isRecursive, Class<? extends Annotation> annotation) {
        List<Class<?>> classList = new ArrayList<>();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            // 按文件的形式去查找
            String strFile = StringUtils.replace(pkgName, ".", "/");
            Enumeration<URL> urls = loader.getResources(strFile);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    String protocol = url.getProtocol();
                    String pkgPath = url.getPath();
                    //System.out.println("protocol:" + protocol +" path:" + pkgPath);
                    if ("file".equals(protocol)) {
                        // 本地自己可见的代码
                        findClassName(classList, pkgName, pkgPath, isRecursive, annotation);
                    } else if ("jar".equals(protocol)) {
                        // 引用第三方jar的代码
                        findClassName(classList, pkgName, url, isRecursive, annotation);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return classList;
    }

    private static void findClassName(List<Class<?>> clazzList, String pkgName, String pkgPath, boolean isRecursive, Class<? extends Annotation> annotation) {
        if(clazzList == null){
            return;
        }
        File[] files = filterClassFiles(pkgPath);// 过滤出.class文件及文件夹
        //System.out.println("files:" +((files == null)?"null" : "length=" + files.length));
        for (File f : files) {
            String fileName = f.getName();
            if (f.isFile()) {
                // .class 文件的情况
                String clazzName = getClassName(pkgName, fileName);
                addClassName(clazzList, clazzName, annotation);
            } else {
                // 文件夹的情况
                if(isRecursive){
                    // 需要继续查找该文件夹/包名下的类
                    String subPkgName = pkgName +"."+ fileName;
                    String subPkgPath = pkgPath +"/"+ fileName;
                    findClassName(clazzList, subPkgName, subPkgPath, true, annotation);
                }
            }
        }
    }

    /**
     * 第三方Jar类库的引用。<br/>
     * @throws IOException
     * */
    private static void findClassName(List<Class<?>> clazzList, String pkgName, URL url, boolean isRecursive, Class<? extends Annotation> annotation) throws IOException {
        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
        JarFile jarFile = jarURLConnection.getJarFile();
        //System.out.println("jarFile:" + jarFile.getName());
        Enumeration<JarEntry> jarEntries = jarFile.entries();
        while (jarEntries.hasMoreElements()) {
            JarEntry jarEntry = jarEntries.nextElement();
            String jarEntryName = jarEntry.getName(); // 类似：sun/security/internal/interfaces/TlsMasterSecret.class
            String clazzName = jarEntryName.replace("/", ".");
            int endIndex = clazzName.lastIndexOf('.');
            String prefix = null;
            if (endIndex > 0) {
                String prefix_name = clazzName.substring(0, endIndex);
                if(clazzName.endsWith(CLASS_POSTFIX)) {
                    clazzName = prefix_name;
                }
                endIndex = prefix_name.lastIndexOf('.');
                if(endIndex > 0){
                    prefix = prefix_name.substring(0, endIndex);
                }
            }
            if (prefix != null && jarEntryName.endsWith(CLASS_POSTFIX)) {
//				System.out.println("prefix:" + prefix +" pkgName:" + pkgName);
                if(prefix.equals(pkgName) || (isRecursive && prefix.startsWith(pkgName))){
                    //System.out.println("jar entryName:" + jarEntryName);
                    addClassName(clazzList, clazzName, annotation);
                }
            }
        }
    }

    private static File[] filterClassFiles(String pkgPath) {
        if(pkgPath == null){
            return new File[0];
        }
        // 接收 .class 文件 或 类文件夹
        return new File(pkgPath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(CLASS_POSTFIX)) || file.isDirectory();
            }
        });
    }

    private static String getClassName(String pkgName, String fileName) {
        int endIndex = fileName.lastIndexOf('.');
        String clazz = null;
        if (endIndex >= 0) {
            clazz = fileName.substring(0, endIndex);
        }
        String clazzName = null;
        if (clazz != null) {
            clazzName = pkgName + "." + clazz;
        }
        return clazzName;
    }

    private static void addClassName(List<Class<?>> clazzList, String clazzName, Class<? extends Annotation> annotation) {
        if (clazzList != null && clazzName != null) {
            Class<?> clazz = null;
            try {
                clazz = Class.forName(clazzName);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
//			System.out.println("isAnnotation=" + clazz.isAnnotation() +" author:" + clazz.isAnnotationPresent(author.class));

            if (clazz != null && (annotation == null || clazz.isAnnotationPresent(annotation))) {
                clazzList.add(clazz);
                //System.out.println("add:" + clazz);
            }
        }
    }

    /**
     * 自动检查指定包（包括子包）下的所有类,如果某个类（包括非匿名内部类）没有实现Serializable,或者没有默认构造函数,则抛出RuntimeException。
     * <br/> 这个方法用于扫描dto的时候很有用,可以减少开发人员的低级错误。
     * @param packageNames
     */
    public static void checkSerializable(String... packageNames){
        for(String pkgName : packageNames) {
            List<Class<?>> classes = ClassUtils.getClassList(pkgName, true, null);
            for(Class<?> clazz : classes){
                if(shouldSkip(clazz)){
                    continue;
                } else if(!(Serializable.class.isAssignableFrom(clazz))){
                    throw new RuntimeException("class: ["+clazz.getName()+"] must implements Serializable");
                } else {
                    try {
                        clazz.getDeclaredConstructor(null);//判断是否有默认构造函数
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException("class: ["+clazz.getName()+"] must have default Constructor");
                    }
                }
            }
        }
    }

    private static boolean shouldSkip(Class<?> clazz){
        String csn = clazz.getName();
        if(Modifier.isAbstract(clazz.getModifiers()) || clazz.isInterface()){
            return true;
        }
        if(csn.contains("$") && NumberUtils.isNumber(csn.substring(csn.lastIndexOf('$') + 1))){
            //类名以$1、$2等数字结尾是匿名内部类。
            return true;
        }
        if(Enum.class.isAssignableFrom(clazz)){//跳过enum枚举类
            return true;
        }
        return false;
    }

}
