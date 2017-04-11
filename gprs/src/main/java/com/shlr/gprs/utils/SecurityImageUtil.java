package com.shlr.gprs.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

public class SecurityImageUtil
{
  public static BufferedImage createImage(String securityCode)
  {
    int codeLength = securityCode.length();

    int fSize = 15;
    int fWidth = fSize + 1;

    int width = codeLength * fWidth + 6;

    int height = fSize * 2 + 1;

    BufferedImage image = new BufferedImage(width, height, 1);
    Graphics g = image.createGraphics();

    g.setColor(Color.WHITE);

    g.fillRect(0, 0, width, height);

    g.setColor(Color.LIGHT_GRAY);

    g.setFont(new Font("Arial", 1, height - 2));

    g.drawRect(0, 0, width - 1, height - 1);

    Random rand = new Random();

    g.setColor(Color.LIGHT_GRAY);
    for (int i = 0; i < codeLength * 6; i++) {
      int x = rand.nextInt(width);
      int y = rand.nextInt(height);

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

  public static ByteArrayInputStream getImageAsInputStream(String securityCode)
  {
    BufferedImage image = createImage(securityCode);
    return convertImageToStream(image);
  }

  private static ByteArrayInputStream convertImageToStream(BufferedImage image)
  {
    ByteArrayInputStream inputStream = null;
    ByteArrayOutputStream bs = new ByteArrayOutputStream();
    try
    {
      ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
      ImageIO.write(image, "jpg", imOut);
      inputStream = new ByteArrayInputStream(bs.toByteArray());
    }  catch (IOException e) {
      e.printStackTrace();
    }
    return inputStream;
  }

  public static String getSecurityCode()
  {
    return getSecurityCode(4, SecurityCodeLevel.Medium, false);
  }

  public static String getSecurityCode(int length, SecurityCodeLevel level, boolean isCanRepeat)
  {
    int len = length;

    char[] codes = { '1', '2', '3', '4', '5', '6', '7', '8', '9', 
      'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 
      'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 
      'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 
      'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 
      'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 
      'W', 'X', 'Y', 'Z' };

    if (level == SecurityCodeLevel.Simple)
      codes = Arrays.copyOfRange(codes, 0, 9);
    else if (level == SecurityCodeLevel.Medium) {
      codes = Arrays.copyOfRange(codes, 0, 33);
    }

    int n = codes.length;

    if ((len > n) && (!isCanRepeat)) {
      throw new RuntimeException(
        String.format("����SecurityCode.getSecurityCode(%1$s,%2$s,%3$s)�����쳣����isCanRepeatΪ%3$sʱ���������%1$s���ܴ���%4$s", new Object[] { 
        Integer.valueOf(len), level, Boolean.valueOf(isCanRepeat), Integer.valueOf(n) }));
    }

    char[] result = new char[len];

    if (isCanRepeat)
      for (int i = 0; i < result.length; i++)
      {
        int r = (int)(Math.random() * n);

        result[i] = codes[r];
      }
    else {
      for (int i = 0; i < result.length; i++)
      {
        int r = (int)(Math.random() * n);

        result[i] = codes[r];

        codes[r] = codes[(n - 1)];
        n--;
      }
    }

    return String.valueOf(result);
  }

  public static enum SecurityCodeLevel
  {
    Simple, Medium, Hard;
  }
}