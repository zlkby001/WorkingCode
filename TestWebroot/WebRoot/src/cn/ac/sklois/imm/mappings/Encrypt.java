package cn.ac.sklois.imm.mappings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Encrypt {
public void Encrypt(String ToEncFilename, String EncFilename) throws Exception{
		
		
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
		cipher.init(Cipher.ENCRYPT_MODE, key);  
		 
		File toEncfile = new File(ToEncFilename);
		File encrypfile = new File(EncFilename + ".enc");;
		InputStream inputStream = null;  
		OutputStream outputStream = null;  
 
		inputStream = new FileInputStream(toEncfile);  
		//encrypfile = File.createTempFile(EncFilename, ".enc");  
		outputStream = new FileOutputStream(encrypfile);  
		 //Cipher cipher = initAESCipher(sKey,Cipher.ENCRYPT_MODE);  
		//以加密流写入文件  
		CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);  
		byte[] cache = new byte[1024];  
		int nRead = 0;  
		while ((nRead = cipherInputStream.read(cache)) != -1) {  
			outputStream.write(cache, 0, nRead);  
			outputStream.flush();  
		}  
		cipherInputStream.close();  
		inputStream.close();
		outputStream.close();
	
	}
}
