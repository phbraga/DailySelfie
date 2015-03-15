package com.coursera.dailyselfie;

import android.graphics.Bitmap;

public class Selfie {
	
	private String name;
	private String bitmapPath;
	private Bitmap bitmap;
	private Bitmap scaledBitmap;
	
	public String getBitmapPath() {
		return bitmapPath;
	}
	
	public void setBitmapPath(String bitmapPath) {
		this.bitmapPath = bitmapPath;
	}
	
	public Bitmap getScaledBitmap() {
		return scaledBitmap;
	}
	
	public void setScaledBitmap(Bitmap scaledBitmap) {
		this.scaledBitmap = scaledBitmap;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPath() {
		return bitmapPath;
	}
	
	public void setPath(String path) {
		this.bitmapPath = path;
	}
	
	public Bitmap getBitmap() {
		return bitmap;
	}
	
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
}
