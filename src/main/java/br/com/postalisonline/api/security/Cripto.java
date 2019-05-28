package br.com.postalisonline.api.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @author alessandro.lima@4quality
 *
 */
public class Cripto {
	
	private Cripto() {
		
	}
	
	private static MessageDigest sha256 = null;
	private static MessageDigest sha1 = null;
	
	static {
		try {
			sha256 = MessageDigest.getInstance("SHA-256");
			sha1 = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {

		}
	}
	
	public static String criptografar(String texto) {
		String senhaCriptografada = null;
		if (sha1 != null) {
			byte[] arraySenhaCriptografada = sha1.digest(texto.getBytes());
			senhaCriptografada = new String(hexCodes(arraySenhaCriptografada));
		}
		return senhaCriptografada;
	}

	public static String criptografar256(byte[] texto) {
		String textoSHA256 = null;

		if (sha256 != null) {
			textoSHA256 = new String(hexCodes(sha256.digest(texto)));
		}
		return textoSHA256;
	}

	private static char[] hexCodes(byte[] texto) {
		char[] hexOutput = new char[texto.length * 2];
		String hexString;
		for (int i = 0; i < texto.length; i++) {
			hexString = "00" + Integer.toHexString(texto[i]);
			hexString.toUpperCase().getChars(hexString.length() - 2, hexString.length(), hexOutput, i * 2);
		}
		return hexOutput;
	}

}
