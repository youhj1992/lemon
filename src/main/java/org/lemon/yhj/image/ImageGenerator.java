package org.lemon.yhj.image;

import chrriis.dj.nativeswing.swtimpl.NativeComponent;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAdapter;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserEvent;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Created by Administrator on 2017/9/28.
 */
public class ImageGenerator extends JPanel {
    private static final long serialVersionUID = 1L;
    // 行分隔符
    final static public String LS = System.getProperty("line.separator", "/n");
    // 文件分割符
    final static public String FS = System.getProperty("file.separator", "//");
    // 当网页超出目标大小时  截取
    final static public int maxWidth = 2000;
    final static public int maxHeight = 1400;

    /**
     * @param file  预生成的图片全路径
     * @param url   网页地址
     * @return  boolean
     * @author AndyBao
     * @version V4.0, 2016年9月28日 下午3:55:52
     */
    public  ImageGenerator(final String file,final String url,final String WithResult){
        super(new BorderLayout());
        JPanel webBrowserPanel = new JPanel(new BorderLayout());
        final JWebBrowser webBrowser = new JWebBrowser(null);
        webBrowser.setBarsVisible(false);
        webBrowser.navigate(url);
        webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
        add(webBrowserPanel, BorderLayout.CENTER);
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 4));
        webBrowser.addWebBrowserListener(new WebBrowserAdapter() {
                                             // 监听加载进度
                                             public void loadingProgressChanged(WebBrowserEvent e) {
                                                 // 当加载完毕时
                                                 if (e.getWebBrowser().getLoadingProgress() == 100) {
                                                     String result = (String) webBrowser.executeJavascriptWithResult(WithResult);
                                                     int index = result == null ? -1 : result.indexOf(":");
                                                     NativeComponent nativeComponent = webBrowser
                                                             .getNativeComponent();
                                                     Dimension originalSize = nativeComponent.getSize();
                                                     Dimension imageSize = new Dimension(Integer.parseInt(result
                                                             .substring(0, index)), Integer.parseInt(result
                                                             .substring(index + 1)));
                                                     imageSize.width = Math.max(originalSize.width,
                                                             imageSize.width + 50);
                                                     imageSize.height = Math.max(originalSize.height,
                                                             imageSize.height + 50);
                                                     nativeComponent.setSize(imageSize);
                                                     BufferedImage image = new BufferedImage(imageSize.width,
                                                             imageSize.height, BufferedImage.TYPE_INT_RGB);
                                                     nativeComponent.paintComponent(image);
                                                     nativeComponent.setSize(originalSize);
                                                     // 当网页超出目标大小时
                                                     try {
                                                         Thread.sleep(10000);
                                                     } catch (InterruptedException e1) {
                                                         e1.printStackTrace();
                                                     }
                                                     if (imageSize.width > maxWidth
                                                             || imageSize.height > maxHeight) {
                                                         //截图部分图形
                                                         image = image.getSubimage(0, 0, maxWidth, maxHeight);
                                                         //  此部分为使用缩略图
                           /* int width = image.getWidth(), height = image
                                .getHeight();
                             AffineTransform tx = new AffineTransform();
                            tx.scale((double) maxWidth / width, (double) maxHeight
                                    / height);
                            AffineTransformOp op = new AffineTransformOp(tx,
                                    AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                            //缩小
                            image = op.filter(image, null);  */
                                                     }
                                                     try {
                                                         // 输出图像
                                                         ImageIO.write(image, "PNG", new File(file));
                                                     } catch (IOException ex) {
                                                         ex.printStackTrace();
                                                     }
                                                     // 退出操作
                                                     System.exit(0);
                                                 }
                                             }
                                         }
        );
        add(panel, BorderLayout.SOUTH);

    }

    //以javascript脚本获得网页全屏后大小
    public static String getScreenWidthHeight(){

        StringBuffer  jsDimension = new StringBuffer();
        jsDimension.append("var width = 0;").append(LS);
        jsDimension.append("var height = 0;").append(LS);
        jsDimension.append("if(document.documentElement) {").append(LS);
        jsDimension.append(
                "  width = Math.max(width, document.documentElement.scrollWidth);")
                .append(LS);
        jsDimension.append(
                "  height = Math.max(height, document.documentElement.scrollHeight);")
                .append(LS);
        jsDimension.append("}").append(LS);
        jsDimension.append("if(self.innerWidth) {").append(LS);
        jsDimension.append("  width = Math.max(width, self.innerWidth);")
                .append(LS);
        jsDimension.append("  height = Math.max(height, self.innerHeight);")
                .append(LS);
        jsDimension.append("}").append(LS);
        jsDimension.append("if(document.body.scrollWidth) {").append(LS);
        jsDimension.append(
                "  width = Math.max(width, document.body.scrollWidth);")
                .append(LS);
        jsDimension.append(
                "  height = Math.max(height, document.body.scrollHeight);")
                .append(LS);
        jsDimension.append("}").append(LS);
        jsDimension.append("return width + ':' + height;");

        return jsDimension.toString();
    }

    public static boolean printUrlScreen2jpg(final String file,final String url,final int width,final int height){

        NativeInterface.open();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                String withResult="var width = "+width+";var height = "+height+";return width +':' + height;";
                if(width==0||height==0)
                    withResult=getScreenWidthHeight();

                // SWT组件转Swing组件，不初始化父窗体将无法启动webBrowser
                JFrame frame = new JFrame("网页截图");
                // 加载指定页面，最大保存为640x480的截图
                frame.getContentPane().add(
                        new ImageGenerator(file,url, withResult),
                        BorderLayout.CENTER);
                frame.setSize(640, 480);
                // 仅初始化，但不显示
                frame.invalidate();

                frame.pack();
                frame.setVisible(false);
            }
        });
        NativeInterface.runEventPump();

        return true;
    }

    /**
     * frame中的控件自适应frame大小：改变大小位置和字体
     * @param frame 要控制的窗体
     */
    public static void modifyComponentSize(JFrame frame,float proportionW,float proportionH){

        try
        {
            Component[] components = frame.getRootPane().getContentPane().getComponents();
            for(Component co:components)
            {
                //              String a = co.getClass().getName();//获取类型名称
                //              if(a.equals("javax.swing.JLabel"))
                //              {
                //              }
                float locX = co.getX() * proportionW;
                float locY = co.getY() * proportionH;
                float width = co.getWidth() * proportionW;
                float height = co.getHeight() * proportionH;
                co.setLocation((int)locX, (int)locY);
                co.setSize((int)width, (int)height);
                int size = (int)(co.getFont().getSize() * proportionH);
                Font font = new Font(co.getFont().getFontName(), co.getFont().getStyle(), size);
                co.setFont(font);
            }
        }
        catch (Exception e)
        {
            // TODO: handle exception
        }
    }

    public static void main(String[] args) {

        ImageGenerator.printUrlScreen2jpg(System.currentTimeMillis()+".jpg", "https://activity.tuia.cn/land/landPage?id=131", 414, 736);
    }
}
