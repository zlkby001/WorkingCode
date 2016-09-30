package com.ics.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Cryptor {
	public void Decrypt(String EncFilename, String DecFilename) throws Exception{
		//创建Key gen  
		KeyGenerator keyGenerator = null;  
		Cipher cipher = null;  
		  
		keyGenerator = KeyGenerator.getInstance("AES");  
		keyGenerator.init(128, new SecureRandom("TCWG".getBytes()));  
		SecretKey secretKey = keyGenerator.generateKey();  
		byte[] codeFormat = secretKey.getEncoded();  
		SecretKeySpec key = new SecretKeySpec(codeFormat, "AES");  
		cipher = Cipher.getInstance("AES");  
		//初始化  
		cipher.init(Cipher.DECRYPT_MODE, key); 
		
		//解密
		File encFile = new File(EncFilename);
		File decryptFile = new File(DecFilename);  
		InputStream inputStream = null;  
		OutputStream outputStream = null;  
	  
		//decryptFile = File.createTempFile(sourceFile.getName(),fileType);  
		//Cipher cipher = initAESCipher(sKey,Cipher.DECRYPT_MODE);  
		inputStream = new FileInputStream(encFile);  
		outputStream = new FileOutputStream(decryptFile);  
		CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);  
		byte [] buffer = new byte [1024];  
		int r;  
		while ((r = inputStream.read(buffer)) >= 0) {  
		cipherOutputStream.write(buffer, 0, r);  
		}  
		cipherOutputStream.close();
		inputStream.close();
		outputStream.close();
		
	}
}
