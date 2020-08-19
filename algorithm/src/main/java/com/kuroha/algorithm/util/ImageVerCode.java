package com.kuroha.algorithm.util;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * 
 * @author QIQI 2017年3月17日14:13:19 验证码图片生成类
 */
public class ImageVerCode {

	/**
	 * 
	 * @param height
	 *            图片高度
	 * @param width
	 *            图片宽度
	 * @param response
	 *            相应
	 * @return 验证码
	 * @throws IOException
	 */
	public static String getVerImageCode(int height, int width, String sessionName, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "No-cache");
		response.setDateHeader("Expires", 0);
		// 指定生成的响应图片,一定不能缺少这句话,否则错误.
		response.setContentType("image/jpeg");

		HttpSession session = request.getSession();
		String code = StringUtil.getUUID(4);
		BufferedImage image = verImageCode(height, width, code);
		session.setAttribute(sessionName, code);
		// 输出图片
		ImageIO.write(image, "JPEG", response.getOutputStream());
		return code;
	}
	/**
	 *
	 * @param height 图片高度
	 * @param width 图片宽度
	 * @return 验证码
	 * @throws IOException
	 */
	public static byte[] getVerImageCode(int height, int width, String code) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BufferedImage image = verImageCode(height, width, code);
		// 输出图片
		ImageIO.write(image, "JPEG", baos);
		return baos.toByteArray();
	}

	private static BufferedImage verImageCode(int height, int width, String code) {
		// 创建BufferedImage对象,其作用相当于一图片
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 创建Graphics对象,其作用相当于画笔
		Graphics g = image.getGraphics();
		// 创建Grapchics2D对象
		Graphics2D g2d = (Graphics2D) g;
		Random random = new Random();
		// 定义字体样式
		Font mfont = new Font("楷体", Font.BOLD, 20);
		g.setColor(getRandColor(200, 250));
		// 绘制背景
		g.fillRect(0, 0, width, height);
		// 设置字体
		g.setFont(mfont);
		g.setColor(getRandColor(180, 200));

		// 绘制100条颜色和位置全部为随机产生的线条,该线条为2f
		for (int i = 0; i < 100; i++) {
			int x = random.nextInt(width - 1);
			int y = random.nextInt(height - 1);
			int x1 = random.nextInt(6) + 1;
			int y1 = random.nextInt(12) + 1;
			// 定制线条样式
			BasicStroke bs = new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
			Line2D line = new Line2D.Double(x, y, x + x1, y + y1);
			g2d.setStroke(bs);
			// 绘制直线
			g2d.draw(line);
		}
		// 输出由英文，数字，和中文随机组成的验证文字，具体的组合方式根据生成随机数确定。
		String ctmp = "";
		int itmp = 0;
		StringBuilder sb = new StringBuilder();
		// 制定输出的验证码为四位
		for (int i = 0; i < code.length(); i++) {
			ctmp = code.substring(i,i+1);
			Color color = new Color(20 + random.nextInt(110), 20 + random.nextInt(110), random.nextInt(110));
			g.setColor(color);
			// 将生成的随机数进行随机缩放并旋转制定角度 PS.建议不要对文字进行缩放与旋转,因为这样图片可能不正常显示
			/* 将文字旋转制定角度 */
			Graphics2D g2d_word = (Graphics2D) g;
			AffineTransform trans = new AffineTransform();
			trans.rotate((35) * 3.14 / 180, 15 * i + 8, 7);
			/* 缩放文字 */
			float scaleSize = 1.2f;

			trans.scale(scaleSize, scaleSize);
			g2d_word.setTransform(trans);
			g.drawString(ctmp, 15 * i + 18, 14);
		}
		// 释放g所占用的系统资源
		g.dispose();
		return image;
	}

	private static Color getRandColor(int s, int e) {
		Random random = new Random();
		if (s > 255) {
			s = 255;
		}
		if (e > 255) {
			e = 255;
		}
		int r, g, b;
		// 随机生成RGB颜色中的r值
		r = s + random.nextInt(e - s);
		// 随机生成RGB颜色中的g值
		g = s + random.nextInt(e - s);
		// 随机生成RGB颜色中的b值
		b = s + random.nextInt(e - s);
		return new Color(r, g, b);
	}
}
