package com.testone.demo.activity.smiley;

import java.io.Serializable;

public class ConversationSmiley implements Serializable {

	private static final long serialVersionUID = 5147631164760276419L;

	private int imageID;
	private String key;
	private String replaceKey;
	private int index;
	private String text;
	private String internationalText;

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	private String resName;

	public int getImageID() {
		return imageID;
	}

	public void setImageID(int imageID) {
		this.imageID = imageID;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getReplaceKey() {
		return replaceKey;
	}

	public void setReplaceKey(String replaceKey) {
		this.replaceKey = replaceKey;
	}

	public String getInternationalText() {
		return internationalText;
	}

	public void setInternationalText(String internationalText) {
		this.internationalText = internationalText;
	}
}
