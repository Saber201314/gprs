package com.shlr.gprs.utils;

import java.util.Arrays;

public class SecurityCode
{
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