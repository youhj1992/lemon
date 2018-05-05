package org.lemon.yhj.image;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import sun.misc.BASE64Decoder;

/**
 * HTML页面截屏工具类
 * 截屏依赖无头浏览器phantomjs
 * 下载浏览器：http://phantomjs.org/download.html
 * 参考：http://bingo.ren/51.html
 */
public class HtmlScreenShotUtil {
    public static final String PHANTOMJS_PATH = "D:/tools/phantomjs-2.1.1-windows/phantomjs-2.1.1-windows/";

    /**
     * phantomJS 截图保存绝对路径
     */
    public static final String PHANTOMJS_OUTPUTPATH = PHANTOMJS_PATH;

    /**
     * phantomJS 脚本绝对路径 bin/phantomjs.exe
     */
    public static final String PHANTOMJS_SHELLPATH = PHANTOMJS_PATH + "bin/phantomjs";

    /**
     * phantomJS js绝对路径，此js作为浏览器执行的参数，将页面open后下载
     */
//    public static final String PHANTOMJS_JSPATH =  "E:/lemon/lemontree/src/main/resources/js/responsive-screenshot.js";//输出日志，图片js保存
    public static final String PHANTOMJS_JSPATH = PHANTOMJS_PATH + "responsive-screenshot.js";//输出base64后的图片字符串

    /**
     * 网页截屏，并保存图片,
     * 如果图片使用page.render(output)保存，返回浏览器console日志
     * 如果使用page.renderBase64('JPEG'),返回图片base64流
     *
     * @param url 页面地址
     */
    public static String screenShot(String url, String fileName) {
        Runtime rt = Runtime.getRuntime();
        StringBuilder sb = new StringBuilder();
        try {
            String cmd = PHANTOMJS_SHELLPATH + " " + PHANTOMJS_JSPATH + " " + url + " " + fileName;
            Process process = rt.exec(cmd);
            InputStream is = process.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String tmp = "";
            try {
                while ((tmp = br.readLine()) != null) {
                    sb.append(tmp);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void main(String[] args) throws InterruptedException {
//        String result = HtmlScreenShotUtil.screenShot("http://www.jrfazh.cn/wl/dui20/?cid=L4-2008&a_cid=2155124421&a_tuiaId=taw-36297390580709");
//        long start = System.currentTimeMillis();
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        for (int i = 0; i <= 50; i++) {
//            int index = i;
//            executorService.execute(new Runnable() {
//                @Override
//                public void run() {
//                    screenShot(index);
//                    System.out.println(index);
//                }
//            });
//        }
//        System.out.println("close pool");
//        executorService.shutdown();
//        while (true) {
//            System.out.println("is clear?");
//            if (executorService.isTerminated()) {
//                long period = System.currentTimeMillis() - start;
//                System.out.println("prriod:" + period / 1000.0);
//                break;
//            }
//            Thread.sleep(1000);
//        }
        screenShot(0);
    }

    private static void screenShot(int i) {
//        String[] urls = {"http://www.shunyuanhui.cn/wsjf-2/", "https://m.bz365.com/mobile/goods/detail.action?goodsId=110101&yychannels=157423501", "https://touch.qunar.com/activity/newgift/12345/?bd_source=dalibao_duibaxb"};
        int index = new Random().nextInt(3);
        String fileName = "D:/" + i + "_" + index + "_" + new DateTime().toString("yyyy-MM-dd_HH_mm_ss") + ".jpeg";
        String result = HtmlScreenShotUtil.screenShot("http://yun.dui88.com/h5-tuia/preview/target-2.html", fileName);
        System.out.println(result);
//        byte[] base64 = new byte[0];
//        try {
//            base64 = new BASE64Decoder().decodeBuffer(result.replace("success", ""));
//            File file = new File(fileName);
//            if (!file.exists()) {
//                file.createNewFile();
//            }
////            FileOutputStream fo = new FileOutputStream(new DateTime().toString("yyyy-MM-dd_HH_mm_ss") + ".JPEG");
////            fo.write(base64);
////            fo.close();
//            RandomAccessFile rf = new RandomAccessFile(file, "rw");
//            rf.write(base64);
//            rf.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
