package com.coursera.dailyselfie;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class Utils {
	
	public static String mBitmapStoragePath;
	
	/**
	 * Source code comes from Adam Porter material course
	 */
	public static void initStoragePath (Context context) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			try {
				String root = context.getExternalFilesDir(null)
						.getCanonicalPath();
				if (null != root) {
					File bitmapStorageDir = new File(root, Constants.DIR);
					bitmapStorageDir.mkdirs();
					mBitmapStoragePath = bitmapStorageDir.getCanonicalPath();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Source code comes from http://developer.android.com/training/camera/photobasics.html
	 * @return File
	 * @throws IOException
	 */
	public static final File createImage() throws IOException {
		
		String timeStamp = new  SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = Constants.PATH_PREFFIX + timeStamp;

		File image = new File(mBitmapStoragePath + "/" + imageFileName +".jpg");
		image.createNewFile();
		
		return image;
	}
	
	/**
	 * Source code comes from http://developer.android.com/training/camera/photobasics.html
	 * @return Bitmap
	 */
	public static final Bitmap getScaledBitmap(String imagePath, int dimension) {

		int targetW = dimension;
		int targetH= dimension;
		
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        return BitmapFactory.decodeFile(imagePath, bmOptions);
    }
	
	public static final Bitmap getBitmapFromPath(String path) {
		return BitmapFactory.decodeFile(path);
	}
	
	public static final void setSharedPreferences(Activity activity, String key, String value) {
		SharedPreferences sharedPrefs = activity.getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPrefs.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public static final String getSharedPreferences(Activity activity, String key) {
		SharedPreferences sharedPrefs = activity.getPreferences(Context.MODE_PRIVATE);
		return sharedPrefs.getString(key, null);
	}

    /**
	 * Source code comes from Adam Porter material course
	 */
	public static final boolean storeSelfies(Bitmap bitmap, String path) {
		boolean ret = true;;
		
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			try {

				BufferedOutputStream bos = new BufferedOutputStream(
						new FileOutputStream(path));
				bitmap.compress(CompressFormat.JPEG, 100, bos);
				bos.flush();
				bos.close();
			} catch (FileNotFoundException e) {
				ret = false;
			} catch (IOException e) {
				ret = false;
			}
			return ret;
		}
		return false;
	}

}
