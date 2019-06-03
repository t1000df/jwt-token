package br.com.postalisonline.identitymanager.api.configuration;

import java.util.HashMap;
import java.util.Map;

public class KeysConfig {
	
	private static Map<String, String> keys;
	
	static {
		keys = new HashMap<String, String>();
		
		keys.put("628DE15F6BED726FFA98ADB4FC9F1-BF38D7FE7151892BEB4911DEF7A76", "postal-saude");
	}
	
	public static String clientKey(String apiKey) {
		return keys.get(apiKey);
	}
	
	public static Boolean exists(String key) {
		return keys.containsKey(key);
	}
	
	private KeysConfig() {
		
	}

}
