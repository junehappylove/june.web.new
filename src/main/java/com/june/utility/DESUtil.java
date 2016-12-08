/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */ 

package com.june.utility;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * DES算法工具类 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年8月31日 下午5:54:20
 */
public class DESUtil {
	  String KEY_STRING = Const.KEY_STRING;
     static Key key;       
       
     /**    
      * 根据参数生成KEY    
      *     
      * @param strKey    
      */      
     private static void getKey(String strKey) {       
         try {       
             KeyGenerator _generator = KeyGenerator.getInstance("DES");       
             _generator.init(new SecureRandom(strKey.getBytes()));       
             key = _generator.generateKey();     
             _generator = null;       
         } catch (Exception e) {       
             e.printStackTrace();       
         }       
     }       
       
     /**    
      * 加密String明文输入,String密文输出    
      *     
      * @param strMing    
      * @return    
      */      
     public static String getEncString(String strMing) {    
         DESUtil.getKey(Const.KEY_STRING);// 生成密匙       
         byte[] byteMi = null;       
         byte[] byteMing = null;       
         String strMi = "";       
         BASE64Encoder base64en = new BASE64Encoder();       
         try {       
             byteMing = strMing.getBytes("UTF8");       
             byteMi = getEncCode(byteMing);       
             strMi = base64en.encode(byteMi);       
         } catch (Exception e) {       
             e.printStackTrace();       
         } finally {       
             base64en = null;       
             byteMing = null;       
             byteMi = null;       
         }       
         return strMi;       
     }       
       
     /**    
      * 解密 以String密文输入,String明文输出    
      *     
      * @param strMi    
      * @return    
      */      
     public static String getDesString(String strMi) {   
         DESUtil.getKey(Const.KEY_STRING);// 生成密匙       
         BASE64Decoder base64De = new BASE64Decoder();       
         byte[] byteMing = null;       
         byte[] byteMi = null;       
         String strMing = "";       
         try {       
             byteMi = base64De.decodeBuffer(strMi);       
             byteMing = getDesCode(byteMi);       
             strMing = new String(byteMing, "UTF8");       
         } catch (Exception e) {       
             e.printStackTrace();       
         } finally {       
             base64De = null;       
             byteMing = null;       
             byteMi = null;       
         }       
         return strMing;       
     }       
       
     /**    
      * 加密以byte[]明文输入,byte[]密文输出    
      *     
      * @param byteS    
      * @return    
      */      
     private static byte[] getEncCode(byte[] byteS) {       
         byte[] byteFina = null;       
         Cipher cipher;       
         try {       
             cipher = Cipher.getInstance("DES");       
             cipher.init(Cipher.ENCRYPT_MODE, key);       
             byteFina = cipher.doFinal(byteS);       
         } catch (Exception e) {       
             e.printStackTrace();       
         } finally {       
             cipher = null;       
         }       
         return byteFina;       
     }       
       
     /**    
      * 解密以byte[]密文输入,以byte[]明文输出    
      *     
      * @param byteD    
      * @return    
      */      
     private static byte[] getDesCode(byte[] byteD) {       
         Cipher cipher;       
         byte[] byteFina = null;       
         try {       
             cipher = Cipher.getInstance("DES");       
             cipher.init(Cipher.DECRYPT_MODE, key);       
             byteFina = cipher.doFinal(byteD);       
         } catch (Exception e) {       
             e.printStackTrace();       
         } finally {       
             cipher = null;       
         }       
         return byteFina;       
       
     }       
       
     public static void main(String[] args) {       
          
         String strEnc = DESUtil.getEncString("jdbc:mysql://localhost:3306/chinaws_kaifa?useUnicode=yes&amp;characterEncoding=UTF8");// 加密字符串,返回String的密文       
         System.out.println(strEnc);       
         String strDes = DESUtil.getDesString("XmxrYb+mTim0gviEkr/3wRWg06i5MhLTddSsEZDRp72t1GLnXGk0TBKE2cfU5GLN276EZ3H681j8GhuZwBgEKj9xTKbaamYPBRr5PsXt0Bkbl2d2mWtkeQ==");// 把String 类型的密文解密        
         System.out.println(strDes);       
     }
}
