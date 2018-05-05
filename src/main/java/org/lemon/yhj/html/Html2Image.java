package org.lemon.yhj.html;

import gui.ava.html.image.generator.HtmlImageGenerator;
import org.lemon.yhj.utils.UrlProcessUtil;

/**
 * Created by Administrator on 2017/9/28.
 */
public class Html2Image {


    public static void loadFromUrl(String url){
        HtmlImageGenerator generator = new HtmlImageGenerator();
        generator.loadUrl(url);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        generator.saveAsImage("d:/hello-world1.png");
        generator.saveAsHtmlWithMap("hello-world.html", "hello-world2.png");
    }

    public static void main(String[] args) {
        loadFromUrl(UrlProcessUtil.processUrl("http://activity.tuiatest.cn/land/landPage?id=25"));
    }
}
