//package org.lemon.yhj.image;
//
//import java.io.File;
//import java.io.IOException;
//import java.sql.Driver;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import org.aspectj.util.FileUtil;
//import org.joda.time.DateTime;
//import org.openqa.selenium.OutputType;
//import org.openqa.selenium.TakesScreenshot;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.phantomjs.PhantomJSDriver;
//import org.openqa.selenium.phantomjs.PhantomJSDriverService;
//import org.openqa.selenium.remote.DesiredCapabilities;
//
///**
// * Created by Administrator on 2017/11/1.
// */
//public class SeleniumImage {
//
//    public static final String PHANTOMJS_PATH = "D:/tools/phantomjs-2.1.1-windows/phantomjs-2.1.1-windows/";
//
//    /**
//     * phantomJS 脚本绝对路径 bin/phantomjs.exe
//     */
//    public static final String PHANTOMJS_SHELLPATH = PHANTOMJS_PATH + "bin/phantomjs.exe";
//
//    public static void screenShot(String[] urls) {
//        DesiredCapabilities capabilities = new DesiredCapabilities();
//        //支持js
//        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, PHANTOMJS_SHELLPATH);
//        capabilities.setJavascriptEnabled(true);
//        //ssl证书支持
//        capabilities.setCapability("acceptSslCerts", true);
//        //截屏支持
//        capabilities.setCapability("takesScreenshot", true);
//        //css搜索支持
//        capabilities.setCapability("cssSelectorsEnabled", true);
//        //设置UA
//        capabilities.setCapability("phantomjs.page.settings.userAgent", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1HH=17Runtime=fobkjmjepodfhggkgoeackdaihhmeliaALICDN/ DOL/HELLO_GWF_s_7997_r2x9ak474125_151");
//
//        ExecutorService executor = Executors.newFixedThreadPool(10);
//        for (int i = 0; i < urls.length; i++) {
//            final int index = i;
//            executor.submit(new Runnable() {
//                @Override
//                public void run() {
//                    WebDriver webDriver = new PhantomJSDriver(capabilities);
//                    webDriver.get(urls[index]);
//                    File img = new File("D:/" + index + "-" + new DateTime().toString("yyyy-MM-dd_HH_mm_ss") + ".png");
//                    File screenshotAs = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
//                    try {
//                        FileUtil.copyFile(screenshotAs, img);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }finally {
//                        webDriver.quit();
//                    }
//                }
//            });
//        }
//
//    }
//
//
//    public static void main(String[] args) {
//
//        String[] urls = {
//                "http://www.toutiaopage.com/tetris/page/64005833991/",
//                "http://www.ef.com.cn/online/lp/PPCET/SM/BSG/mobile201702.aspx?ctr=cn&lng=cs&ptn=cnbd&etag=efcn_ppc_-c1d4aef12b5c447393f2437c3700e918-guangzhou-cnppc-mb-yz1-",
//                "http://www.ftoul.com/landingpage?type=48&un=ta01",
//                "https://m.qmcai.com/hd/qd/actWheel9291/index.html",
//                "http://uri6.com/QneERj",
//                "http://www.icartoons.cn/index.php?m=activity&c=nvsheng&a=tangsan",
//                "https://wap.cgbchina.com.cn/creditCardApplyIn.do?cardType=Q3cRfycdpBBBB&seqno=Kfxft-PQRcvTwyUdpfesStuB9b5Da5B49_bBbD4ab&tongluCode=-aNsfyRpdcfaBbb9B",
//                "http://www.shunyuanhui.cn/wsjf-2/",
//                "https://activity.tuia.cn/land/landPage?id=79",
//        };
//
//        screenShot(urls);
//    }
//}
