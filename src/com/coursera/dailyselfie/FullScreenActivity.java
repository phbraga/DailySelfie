package com.coursera.dailyselfie;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

public class FullScreenActivity extends Activity {
	
//	private ImageView mImageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selfie_fullscreen);
		
		ImageView mImageView = (ImageView) findViewById(R.id.selfie_fullscreen);
		
		Intent intent = getIntent();
		
		if(intent != null) {
			
			Bundle bundle = intent.getExtras();
			
			if(bundle != null) {
			
				String name = bundle.getString(Constants.KEY_FULLSCREEN_NAME);
				String path = bundle.getString(Constants.KEY_FULLSCREEN_PATH);
				setTitle(name);
				new LoadImageTask(this, mImageView).execute(path);
			}
		}
		
	}
	
	static class LoadImageTask extends AsyncTask<String, String, Bitmap> {

		private Activity mThisActivity;
		private ImageView mImageView;
		
		public LoadImageTask(Activity activity, ImageView imageView) {
			mThisActivity = activity;
			mImageView = imageView;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mThisActivity.setProgressBarIndeterminate(true);
		}
		
		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap bitmap = null;
			String path = params[0];
			
			if(path != null && !path.isEmpty()) {
				bitmap = Utils.getBitmapFromPath(path);
			}
			
			return bitmap;
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			mImageView.setImageBitmap(result);
			mThisActivity.setProgressBarIndeterminate(false);
		}
		
	}

}
