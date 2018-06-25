package org.lemon.yhj.image;

import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.lemon.yhj.annotation.log.Log;

import javax.imageio.ImageIO;
import java.io.File;

/**
 * 照片尺寸转换器
 */
public class PhotoConvertUtil {

    public static void main(String[] args) {
        String source = "/Users/youhaijun/Documents/backup/头像.jpeg";
//        String water = "/Users/youhaijun/Documents/backup/touxiang.jpg";

        for (Size s : Size.values()) {
            String dest = "/Users/youhaijun/Documents/backup/" + s.w + "-" + s.h + ".jpg";
            convertKeepAspectRatio(source, dest, s);
//            addWaterMark(source,dest,water,s);
        }
    }

    /**
     * 转换图片-不保持长宽比
     * @param source
     * @param dest
     * @param destSize
     */
    public static void convert(String source, String dest, Size destSize){
        try {
            File sourceFile = new File(source);
            File destFile = new File(dest);
//            if(!destFile.exists())
            Thumbnails.of(sourceFile).size(destSize.w, destSize.h).keepAspectRatio(false).toFile(destFile);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 转换图片尺寸-保持图片长宽比
     * @param source
     * @param dest
     * @param destSize
     */
    @Log
    public static void convertKeepAspectRatio(String source, String dest, Size destSize){
        try {
            File sourceFile = new File(source);
            File destFile = new File(dest);
            Thumbnails.of(sourceFile).size(destSize.w, destSize.h).toFile(destFile);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 转换图片，并在右下加水印
     * 保持图片长宽比
     * @param source
     * @param dest
     * @param waterMark 水印图片地址
     * @param size
     */
    public static void addWaterMark(String source, String dest, String waterMark, Size size){
        try {
            File sourceFile = new File(source);
            File destFile = new File(dest);
            File waterMarkFile = new File(waterMark);
            //给图片加水印，watermark(位置，水印图，透明度)Positions.CENTER表示加在中间
            Thumbnails.of(sourceFile).size(400,400)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(waterMarkFile),0.5f)
                    .outputQuality(0.8f).toFile(destFile);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private enum Size {

        /**
         * 1寸                           2.5*3.5cm                  413*295
         * 身份证大头照                   3.3*2.2                    390*260
         * 2寸                           3.5*5.3cm                  626*413
         * 小2寸（护照）                  4.8*3.3cm                  567*390
         * 5 寸                          5x3.5 12.7*8.9             1200x840以上 100万像素
         * 6 寸                          6x4 15.2*10.2              1440x960以上  130万像素
         * 7 寸                          7x5 17.8*12.7              1680x1200以上 200万像素
         * 8 寸                          8x6   20.3*15.2            1920x1440以上 300万像素
         * 10寸                          10x8 25.4*20.3             2400x1920以上 400万像素
         * 12寸                          12x1030.5*20.3             2500x2000以上 500万像素
         * 15寸                          15x10 38.1*25.4            3000x2000
         */
        ONE_CUN(295, 413, "1寸-证件照"),
        IDCARD_CUN(260, 390, "身份证照片"),
        TWO_CUN(413, 626, "标准2寸照片"),
        TWO_MINI_CUN(390, 567, "小2寸-中国护照"),
        FIVE_CUN(840, 1200, "5寸-最常见的照片大小"),
        SIX_CUN(960, 1440, "6寸-国际上比较通用的照片大小"),
        SEVEN_CUN(1200, 1680, "7寸-放大"),
        EIGHT_CUN(1440, 1920, "8寸-大概是A4打印纸的一半"),
        TEN_CUN(1920, 2400, "10寸"),
        TWELVE_CUN(2000, 2500, "12寸-大概是A4大小"),
        FIVETEN_CUN(2000, 3000, "15寸"),
        ;

        private int w;//宽度，单位mm
        private int h;//高度，单位mm
        private String desc;

        Size(int w, int h, String desc) {
            this.w = w;
            this.h = h;
            this.desc = desc;
        }
    }
}
