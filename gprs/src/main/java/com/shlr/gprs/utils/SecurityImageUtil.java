package com.shlr.gprs.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

public class SecurityImageUtil {
	
	static Random random=new Random();
	public static BufferedImage createImage(String securityCode) {
		int codeLength = securityCode.length();

		int fSize = 15;
		int fWidth = fSize + 7;

		int width = codeLength * fWidth + 6;

		int height = fSize * 2 + 1;

		BufferedImage image = new BufferedImage(width, height, 1);
		Graphics g = image.createGraphics();

		g.setColor(Color.WHITE);

		g.fillRect(0, 0, width, height);

		g.setColor(Color.LIGHT_GRAY);

		g.setFont(new Font("Arial", 1, height - 2));

		g.drawRect(0, 0, width - 1, height - 1);


		g.setColor(Color.LIGHT_GRAY);
		for (int i = 0; i < codeLength * 6; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);

			g.drawRect(x, y, 1, 1);
		}

		int codeY = height - 10;

		g.setColor(new Color(19, 148, 246));
		g.setFont(new Font("Georgia", 1, fSize));
		for (int i = 0; i < codeLength; i++) {
			g.drawString(String.valueOf(securityCode.charAt(i)), i * 16 + 5, codeY);
		}

		g.dispose();

		return image;
	}
	/**
     * 输出指定验证码图片流
     * @param w
     * @param h
     * @param os
     * @param code
     * @throws IOException
     */
    public static BufferedImage outputImage(int w, int h, String code) throws IOException{
        int verifySize = code.length();
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        Color[] colors = new Color[5];
        Color[] colorSpaces = new Color[] { Color.WHITE, Color.CYAN,
                Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE,
                Color.PINK, Color.YELLOW };
        float[] fractions = new float[colors.length];
        for(int i = 0; i < colors.length; i++){
            colors[i] = colorSpaces[random.nextInt(colorSpaces.length)];
            fractions[i] = random.nextFloat();
        }
        Arrays.sort(fractions);
         
        g2.setColor(Color.GRAY);// 设置边框色
        g2.fillRect(0, 0, w, h);
         
        Color c = getRandColor(200, 250);
        g2.setColor(c);// 设置背景色
        g2.fillRect(0, 2, w, h-4);
         
        //绘制干扰线
        g2.setColor(getRandColor(160, 200));// 设置线条的颜色
        for (int i = 0; i < 20; i++) {
            int x = random.nextInt(w - 1);
            int y = random.nextInt(h - 1);
            int xl = random.nextInt(6) + 1;
            int yl = random.nextInt(12) + 1;
            g2.drawLine(x, y, x + xl + 40, y + yl + 20);
        }
         
        // 添加噪点
        float yawpRate = 0.05f;// 噪声率
        int area = (int) (yawpRate * w * h);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(w);
            int y = random.nextInt(h);
            int rgb = getRandomIntColor();
            image.setRGB(x, y, rgb);
        }
         
        shear(g2, w, h, c);// 使图片扭曲
 
        g2.setColor(getRandColor(100, 160));
        int fontSize = h-4;
        Font font = new Font("Algerian", Font.ITALIC, fontSize);
        g2.setFont(font);
        char[] chars = code.toCharArray();
        for(int i = 0; i < verifySize; i++){
            AffineTransform affine = new AffineTransform();
            affine.setToRotation(Math.PI / 4 * random.nextDouble() * (random.nextBoolean() ? 1 : -1), (w / verifySize) * i + fontSize/2, h/2);
            g2.setTransform(affine);
            g2.drawChars(chars, i, 1, ((w-10) / verifySize) * i + 5, h/2 + fontSize/2 - 10);
        }
         
        g2.dispose();
        return image;
    }
    private static void shear(Graphics g, int w1, int h1, Color color) {
        shearX(g, w1, h1, color);
        shearY(g, w1, h1, color);
    }
    private static void shearX(Graphics g, int w1, int h1, Color color) {
    	 
        int period = random.nextInt(2);
 
        boolean borderGap = true;
        int frames = 1;
        int phase = random.nextInt(2);
 
        for (int i = 0; i < h1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                            + (6.2831853071795862D * (double) phase)
                            / (double) frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }
 
    }
 
    private static void shearY(Graphics g, int w1, int h1, Color color) {
 
        int period = random.nextInt(40) + 10; // 50;
 
        boolean borderGap = true;
        int frames = 20;
        int phase = 7;
        for (int i = 0; i < w1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                            + (6.2831853071795862D * (double) phase)
                            / (double) frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h1, i, h1);
            }
 
        }
 
    }
    private static Color getRandColor(int fc, int bc) {
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
    private static int getRandomIntColor() {
        int[] rgb = getRandomRgb();
        int color = 0;
        for (int c : rgb) {
            color = color << 8;
            color = color | c;
        }
        return color;
    }
    private static int[] getRandomRgb() {
    	
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            rgb[i] = random.nextInt(255);
        }
        return rgb;
    }

	public static ByteArrayInputStream getImageAsInputStream(String securityCode) {
		BufferedImage image=null;
		try {
			image = outputImage(200,50,securityCode);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertImageToStream(image);
	}

	private static ByteArrayInputStream convertImageToStream(BufferedImage image) {
		ByteArrayInputStream inputStream = null;
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		try {
			ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
			ImageIO.write(image, "jpg", imOut);
			inputStream = new ByteArrayInputStream(bs.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputStream;
	}

	public static String getSecurityCode() {
		return getSecurityCode(4, SecurityCodeLevel.Medium, false);
	}

	public static String getSecurityCode(int length, SecurityCodeLevel level, boolean isCanRepeat) {
		int len = length;

		char[] codes = { '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
				'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
				'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

		if (level == SecurityCodeLevel.Simple)
			codes = Arrays.copyOfRange(codes, 0, 9);
		else if (level == SecurityCodeLevel.Medium) {
			codes = Arrays.copyOfRange(codes, 0, 33);
		}

		int n = codes.length;

		if ((len > n) && (!isCanRepeat)) {
			throw new RuntimeException(String.format(
					"����SecurityCode.getSecurityCode(%1$s,%2$s,%3$s)�����쳣����isCanRepeatΪ%3$sʱ���������%1$s���ܴ���%4$s",
					new Object[] { Integer.valueOf(len), level, Boolean.valueOf(isCanRepeat), Integer.valueOf(n) }));
		}

		char[] result = new char[len];

		if (isCanRepeat)
			for (int i = 0; i < result.length; i++) {
				int r = (int) (Math.random() * n);

				result[i] = codes[r];
			}
		else {
			for (int i = 0; i < result.length; i++) {
				int r = (int) (Math.random() * n);

				result[i] = codes[r];

				codes[r] = codes[(n - 1)];
				n--;
			}
		}

		return String.valueOf(result);
	}

	public static enum SecurityCodeLevel {
		Simple, Medium, Hard;
	}
}