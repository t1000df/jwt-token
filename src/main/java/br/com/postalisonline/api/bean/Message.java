package br.com.postalisonline.api.bean;

public class Message {
	
	public Message(String value) {
		this.text = value;
	}
	
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	

}
