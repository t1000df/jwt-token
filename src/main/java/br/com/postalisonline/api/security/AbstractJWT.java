package br.com.postalisonline.api.security;

import javax.xml.bind.DatatypeConverter;

/**
 * 
 * @author alessandro.lima@4quality
 *
 */
public abstract class AbstractJWT {
	
	protected String keyPrivate() {
		
		String keySecret = "M@r148877kahjajhajhal-jjhkakfahfalfahfal-jajfaoafjfaçjafçlfajfaaç";
		String encoded = DatatypeConverter.printBase64Binary(keySecret.getBytes());
		
		return encoded;
	}

}
