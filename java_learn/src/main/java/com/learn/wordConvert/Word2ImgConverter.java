package com.learn.wordConvert;
import org.apache.poi.xwpf.usermodel.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * ClassName:App1
 * Package:com.learn.word2img
 * Description:
 *
 * @Date:2024/4/18 9:12
 * @Author:qs@1.com
 */
public class Word2ImgConverter {
    public static void main(String[] args) throws IOException {
        String inputFilePath = "C:\\Users\\Admin\\Desktop\\tms 修改.docx";
        String outputImagePath = "C:\\Users\\Admin\\Desktop\\output.png";

        try {
            FileInputStream fis = new FileInputStream(inputFilePath);
            XWPFDocument document = new XWPFDocument(fis);

            // 创建 BufferedImage 对象
            BufferedImage image = new BufferedImage(793, 1122, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = image.createGraphics();

            // 清除画布
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
            graphics.setColor(Color.BLACK);

            // 绘制文本
            int y = 20;
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                XWPFRun run = paragraph.getRuns().get(0);
                String text = run.getText(0);
                if (text != null) {
                    graphics.setColor(Color.BLACK);
                    graphics.drawString(text, 20, y);
                    y += 20;;
                }
            }

            // 保存图片
            ImageIO.write(image, "png", new File(outputImagePath));

            // 释放资源
            fis.close();
            graphics.dispose();

            System.out.println("Image saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
