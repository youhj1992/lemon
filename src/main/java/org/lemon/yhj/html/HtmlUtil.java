package org.lemon.yhj.html;

import java.io.IOException;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.lemon.yhj.utils.UrlProcessUtil;

/**
 * Created by Administrator on 2017/9/20.
 */
public class HtmlUtil {
    /***
     * @Description: getDocFromUrl 从url获取Document
     *
     * @Param: [url]
     * @Return: org.jsoup.nodes.Document
     * @throws:
     * @author: youhaijun
     * @Date: 2017/9/21
     */
    public static Document getDocFromUrl(String url) {

        String htmlUrl = UrlProcessUtil.processUrl(url);
        try {
            Document document = Jsoup.connect(htmlUrl).get();
            return document;
        } catch (IOException e) {
            return null;
        }
    }

    public static void printHerf(Document doc) {
        Elements links = doc.select("a[href]");
        Elements media = doc.select("[src]");
        Elements imports = doc.select("link[href]");

        print("\nMedia: (%d)", media.size());
        for (Element src : media) {
            if (src.tagName().equals("img"))
                print(" * %s: <%s> %sx%s (%s)",
                        src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
                        trim(src.attr("alt"), 20));
            else
                print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
        }

        print("\nImports: (%d)", imports.size());
        for (Element link : imports) {
            print(" * %s <%s> (%s)", link.tagName(), link.attr("abs:href"), link.attr("rel"));
        }

        print("\nLinks: (%d)", links.size());
        for (Element link : links) {
            print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
        }
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width - 1) + ".";
        else
            return s;
    }

    private void commonMethod(Document doc) {

        /*****获取单一元素******/
        //与JS类似的根据ID选择的选择器<div id="content"></div>
        Element content = doc.getElementById("content");

        /*****一下方法的返回值都是Elements集合******/

        //获取所有的a标签<a href="#"></a>
        content.getElementsByTag("a");
        //类选择器<div></div>
        doc.getElementsByClass("divClass");
        //获取Document的所有元素
        doc.getAllElements();
        //根据属性获取元素<a href="#"></a>
        doc.getElementsByAttribute("href");
        //根据属性前缀获取元素 <li data-name="Peter Liu" data-city="ShangHai" data-lang="CSharp" data-food="apple">
        doc.getElementsByAttributeStarting("data-");
        //根据key-value选择如<a href="http://xdemo.org"></a>
        doc.getElementsByAttributeValue("href", "http://xdemo.org");
        //和上面的正好相反
        doc.getElementsByAttributeValueNot("href", "http://xdemo.org");
        //根据key-value,其中value可能是key对应属性的一个子字符串，选择如<a href="http://xdemo.org"></a>
        doc.getElementsByAttributeValueContaining("href", "xdemo");
        //根据key-value,其中key对应值的结尾是value，选择如<a href="http://xdemo.org"></a>
        doc.getElementsByAttributeValueEnding("href", "org");
        //和上面的正好相反
        doc.getElementsByAttributeValueStarting("href", "http://xdemo");
        //正则匹配，value需要满足正则表达式，<a href="http://xdemo.org"></a>,如href的值含有汉字
        doc.getElementsByAttributeValueMatching("href", Pattern.compile("[\u4e00-\u9fa5]"));
        //同上
        doc.getElementsByAttributeValueMatching("href", "[\u4e00-\u9fa5]");
        //根据元素所在的z-index获取元素
        doc.getElementsByIndexEquals(0);
        //获取z-index大于x的元素
        doc.getElementsByIndexGreaterThan(0);
        //和上面的正好相反
        doc.getElementsByIndexLessThan(10);

        //遍历标签
        for (Element link : content.getElementsByTag("a")) {
            String linkHref = link.attr("href");
            String linkText = link.text();
        }

        /**************一些其他常用的方法**************/
        //获取网页标题
        doc.title();
        //获取页面的所有文本
        doc.text();

        //为元素添加一个css class
        content.addClass("newClass");
        //根据属性获取值
        content.attr("id");
        //获取所有子元素
        content.children();
        //获取元素内的所有文本
        content.text();
        //获取同级元素
        content.siblingElements();
    }


    public static void main(String[] args) {
        Document doc = getDocFromUrl("www.baidu.com");
        printHerf(doc);

    }

}
