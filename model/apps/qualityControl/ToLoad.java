package com.model.apps.qualityControl;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ToLoad {
	ImageView imageView;
	String url;
	boolean loaded = false;
	public ToLoad(ImageView imageView, String url) {
		this.imageView = imageView;
		this.url = url;
	}
	
	boolean load() {
		imageView.setImage(new Image(url));
		return true;
	}
	
	public void setLoaded() {
		loaded = true;
	}

	public boolean isLoaded() {

		return loaded;
	}

}
