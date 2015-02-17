package com.java.imageIO;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

public class WaterMark {
	public void mark(String srcImgPath, String outImgPath, String watermarkStr)
			throws Exception {

		// 读取原图片信息

		File srcImgFile = new File(srcImgPath);

		Image srcImg = ImageIO.read(srcImgFile);

		int srcImgWidth = srcImg.getWidth(null);

		int srcImgHeight = srcImg.getHeight(null);

		// 加水印

		BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight,

		BufferedImage.TYPE_INT_RGB);

		Graphics2D g = bufImg.createGraphics();

		g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);

		Font font = new Font("Courier New", Font.PLAIN, 12);

		g.setFont(font);

		int x = srcImgWidth - getWatermarkLength(watermarkStr, g) - 3;

		int y = srcImgHeight - 3;

		g.drawString(watermarkStr, x, y);

		g.dispose();

		// 输出图片

		FileOutputStream outImgStream = new FileOutputStream(outImgPath);

		ImageIO.write(bufImg, "jpg", outImgStream);

		outImgStream.flush();

		outImgStream.close();

	}

	// 获取水印文字总长度

	public int getWatermarkLength(String str, Graphics2D g) {

		return g.getFontMetrics(g.getFont()).charsWidth(str.toCharArray(), 0,
				str.length());

	}

	public static void main(String[] args) throws Exception {

		new WaterMark().mark("D://login_bg.png", "D://top_right.jpg",
				"weibo.com/zhaojinglun");

		// new WaterMark().mark(args[0], args[1], args[2]);

	}
}
