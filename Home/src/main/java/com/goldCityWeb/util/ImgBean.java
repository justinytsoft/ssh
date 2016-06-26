package com.goldCityWeb.util;
import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;


public class ImgBean {

	private Font font = new Font("宋体", Font.PLAIN, 40);// 添加字体的属性设置

	private Graphics2D g = null;

	private int fontsize = 0;

	private int x = 0;

	private int y = 0;

	/**
	 * 导入本地图片到缓冲区
	 */
	public BufferedImage loadImageLocal(String imgName) {
		try {
			return ImageIO.read(new File(imgName));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * 导入网络图片到缓冲区
	 */
	public BufferedImage loadImageUrl(String imgName) {
		try {
			URL url = new URL(imgName);
			return ImageIO.read(url);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * 生成新图片到本地
	 */
	public void writeImageLocal(String newImage, BufferedImage img) {
		if (newImage != null && img != null) {
			try {
				File outputfile = new File(newImage);
				ImageIO.write(img, "jpg", outputfile);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * 设定文字的字体等
	 */
	public void setFont(String fontStyle, int fontSize) {
		this.fontsize = fontSize;
		this.font = new Font(fontStyle, Font.PLAIN, fontSize);
	}

	/**
	 * 修改图片,返回修改后的图片缓冲区（只输出一行文本）
	 */
	public BufferedImage modifyImage(BufferedImage img, Object content, Integer x, Integer y) {

		try {
			g = img.createGraphics();
			g.setBackground(Color.BLACK);
			g.setColor(Color.WHITE);//设置字体颜色
			if (this.font != null)
				g.setFont(this.font);
			if (content != null) {
				g.drawString(content.toString(), x, y);
			}
			g.dispose();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return img;
	}
	
	public static void main(String[] args) {

		String pre = SettingUtils.getCommonSetting("upload.image.path");
		String cover = "/mould/mould1.png";
		String merge = pre + "/mould/mould9.png";
		String title = "安格奥尔";
		String sub_title = "的卫生法三国杀";

		Integer type = 2;
		ImgBean imgBean = new ImgBean();
		BufferedImage d = imgBean.loadImageLocal(pre+cover);
		int w = d.getWidth();
		int h = d.getHeight();
		int x1 = 0;
		int y1 = 0;
		int x2 = 0;
		int y2 = 0;
		
		if(type==1){
			x1 = w / 2;
			y1 = h - 100;
			x2 = w / 2;
			y2 = h - 50;
		}else if(type==2){
			x1 = 40;
			y1 = 100;
			x2 = w / 2;
			y2 = h - 100;
		}else{
			x1 = 40;
			y1 = 100;
			x2 = 40;
			y2 = 150;
		}
		
		imgBean.writeImageLocal(merge,imgBean.modifyImage(d,title,x1,y1));
		imgBean.writeImageLocal(merge,imgBean.modifyImage(d,sub_title,x2,y2));
	}

	/**
	 * 修改图片,返回修改后的图片缓冲区（输出多个文本段） xory：true表示将内容在一行中输出；false表示将内容多行输出
	 */
	public BufferedImage modifyImage(BufferedImage img, Object[] contentArr,
			int x, int y, boolean xory) {
		try {
			int w = img.getWidth();
			int h = img.getHeight();
			g = img.createGraphics();
			g.setBackground(Color.WHITE);
			g.setColor(Color.RED);
			if (this.font != null)
				g.setFont(this.font);
			// 验证输出位置的纵坐标和横坐标
			if (x >= h || y >= w) {
				this.x = h - this.fontsize + 2;
				this.y = w;
			} else {
				this.x = x;
				this.y = y;
			}
			if (contentArr != null) {
				int arrlen = contentArr.length;
				if (xory) {
					for (int i = 0; i < arrlen; i++) {
						g.drawString(contentArr[i].toString(), this.x, this.y);
						this.x += contentArr[i].toString().length()
								* this.fontsize / 2 + 5;// 重新计算文本输出位置
					}
				} else {
					for (int i = 0; i < arrlen; i++) {
						g.drawString(contentArr[i].toString(), this.x, this.y);
						this.y += this.fontsize + 2;// 重新计算文本输出位置
					}
				}
			}
			g.dispose();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return img;
	}

	/**
	 * 修改图片,返回修改后的图片缓冲区（只输出一行文本）
	 * 
	 * 时间:2007-10-8
	 * 
	 * @param img
	 * @return
	 */
	public BufferedImage modifyImageYe(BufferedImage img) {

		try {
			int w = img.getWidth();
			int h = img.getHeight();
			g = img.createGraphics();
			g.setBackground(Color.WHITE);
			g.setColor(Color.blue);//设置字体颜色
			if (this.font != null)
				g.setFont(this.font);
			g.drawString("www.hi.baidu.com?xia_mingjian", w - 85, h - 5);
			g.dispose();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return img;
	}

	public BufferedImage modifyImagetogeter(BufferedImage b, BufferedImage d) {

		try {
			int w = b.getWidth();
			int h = b.getHeight();
			
			g = d.createGraphics();
			g.drawImage(b, 100, 10, w, h, null);
			g.dispose();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return d;
	}

}