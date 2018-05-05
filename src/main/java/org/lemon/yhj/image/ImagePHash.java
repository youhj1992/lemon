package org.lemon.yhj.image;

import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/*
* function: 用汉明距离进行图片相似度检测的Java实现
* pHash-like image hash.
* Based On: http://www.hackerfactor.com/blog/index.php?/archives/432-Looks-Like-It.html
* 参考：http://blog.csdn.net/sunhuaqiang1/article/details/70232679
*/
public class ImagePHash {

    private ColorConvertOp colorConvert = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
    private int size = 32;
    private int smallerSize = 8;

    public ImagePHash() {
        initCoefficients();
    }

    private ImagePHash(int size, int smallerSize) {
        this.size = size;
        this.smallerSize = smallerSize;

        initCoefficients();
    }

    private int distance(String s1, String s2) {
        int counter = 0;
        for (int k = 0; k < s1.length(); k++) {
            if (s1.charAt(k) != s2.charAt(k)) {
                counter++;
            }
        }
        return counter;
    }

    // Returns a 'binary string' (like. 001010111011100010) which is easy to do a hamming distance on.
    private String getHash(InputStream is) throws Exception {
        BufferedImage img = ImageIO.read(is);

        // 1. Reduce size(缩小尺寸).pHash以小图片开始，但图片大于8*8，32*32是最好的。这样做的目的是简化了DCT的计算，而不是减小频率。
        img = resize(img, size, size);

        // 2. Reduce color(简化色彩).将图片转化成灰度图像，进一步简化计算量
        img = grayscale(img);

        double[][] vals = new double[size][size];

        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                vals[x][y] = getBlue(img, x, y);
            }
        }

        // 3. Compute the DCT(计算DCT).计算图片的DCT变换，得到32*32的DCT系数矩阵
        long start = System.currentTimeMillis();
        double[][] dctVals = applyDCT(vals);
        //        System.out.println("DCT_COST_TIME: " + (System.currentTimeMillis() - start));

        // 4. Reduce the DCT.虽然DCT的结果是32*32大小的矩阵，但我们只要保留左上角的8*8的矩阵，这部分呈现了图片中的最低频率
        //5. Compute the average value.如同均值哈希一样，计算DCT的均值。
        double total = 0;

        for (int x = 0; x < smallerSize; x++) {
            for (int y = 0; y < smallerSize; y++) {
                total += dctVals[x][y];
            }
        }
        total -= dctVals[0][0];

        double avg = total / (double) ((smallerSize * smallerSize) - 1);

        // 6. Further reduce the DCT.这是最主要的一步，根据8*8的DCT矩阵，设置0或1的64位的hash值，大于等于DCT均值的设为”1”，小于DCT均值的设为“0”。组合在一起，就构成了一个64位的整数，这就是这张图片的指纹
        String hash = "";

        for (int x = 0; x < smallerSize; x++) {
            for (int y = 0; y < smallerSize; y++) {
                if (x != 0 && y != 0) {
                    hash += (dctVals[x][y] > avg ? "1" : "0");
                }
            }
        }

        return hash;
    }

    /***
     * Reduce size(缩小尺寸)
     */
    private BufferedImage resize(BufferedImage image, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }


    /***
     * Reduce color(简化色彩)
     */
    private BufferedImage grayscale(BufferedImage img) {
        colorConvert.filter(img, img);
        return img;
    }

    /***
     * 获取灰度
     * @param img
     * @param x
     * @param y
     * @return
     */
    private static int getBlue(BufferedImage img, int x, int y) {
        return (img.getRGB(x, y)) & 0xff;
    }


    private double[] c;

    private void initCoefficients() {
        c = new double[size];

        for (int i = 1; i < size; i++) {
            c[i] = 1;
        }
        c[0] = 1 / Math.sqrt(2.0);
    }

    private double[][] applyDCT(double[][] f) {
        int N = size;

        double[][] F = new double[N][N];
        for (int u = 0; u < N; u++) {
            for (int v = 0; v < N; v++) {
                double sum = 0.0;
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        sum += Math.cos(((2 * i + 1) / (2.0 * N)) * u * Math.PI) * Math.cos(((2 * j + 1) / (2.0 * N)) * v * Math.PI) * (f[i][j]);
                    }
                }
                sum *= ((c[u] * c[v]) / 4.0);
                F[u][v] = sum;
            }
        }
        return F;
    }

    /**
     * @param img1
     * @param img2
     * @param tv 标准阈值， 一般认为汉明距离<=5比较相似
     * @return boolean
     */
    public boolean imgChk(String img1, String img2, int tv) {
        ImagePHash p = new ImagePHash();
        String image1;
        String image2;

        try {
            image1 = p.getHash(new FileInputStream(new File(img1)));
            image2 = p.getHash(new FileInputStream(new File(img2)));
            int dt = p.distance(image1, image2);
            System.out.println("[" + img1 + "] : [" + img2 + "] Score is " + dt);
            if (dt <= tv)
                return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {

        ImagePHash p = new ImagePHash();
        String imagePath = "D:/image/";
        System.out.println(p.imgChk(imagePath + "0.png", imagePath + "1.png", 3));
        System.out.println(p.imgChk(imagePath + "0.png", imagePath + "2.png", 3));
        System.out.println(p.imgChk(imagePath + "0.png", imagePath + "3.png", 3));
        System.out.println(p.imgChk(imagePath + "0.png", imagePath + "4.png", 3));
        System.out.println(p.imgChk(imagePath + "0.png", imagePath + "5.png", 3));
        System.out.println(p.imgChk(imagePath + "0.png", imagePath + "6.png", 3));
        System.out.println(p.imgChk(imagePath + "black.jpg", imagePath + "white.jpg", 3));

    }
}