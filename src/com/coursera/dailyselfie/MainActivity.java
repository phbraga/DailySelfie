package com.coursera.dailyselfie;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	
	public static final String TAG = "MainActivity";
	
	private BaseFragment mCurrentFragment;
	private String mCurrentPhotoPath;
	private ArrayList<Selfie> mSelfiesArray;

	private AlarmManager mAlarmManager;
	private Intent mAlarmNotificationIntent;
	private PendingIntent mAlarmPendingIntent;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mSelfiesArray = new ArrayList<Selfie>();
        loadFragment();
        alarmConfig();
        
    }

    /**
     * Based on Adam Porter material android course
     */
    public void alarmConfig() {
    	mAlarmNotificationIntent = new Intent(this, SelfieAlarmReceiver.class);
    	mAlarmPendingIntent = PendingIntent.getBroadcast(this, 0, mAlarmNotificationIntent, 0);
    	
		mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		
		mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 
				SystemClock.elapsedRealtime() + Constants.ALARM_DELAY, 
				Constants.ALARM_DELAY, mAlarmPendingIntent);

	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        
        if (id == R.id.action_camera) {
            dispatchTakePictureIntent();
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    /**
	 * Based on source code from http://developer.android.com/training/camera/photobasics.html
	 */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	if(requestCode == Constants.REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
    		
    		mCurrentPhotoPath = Utils.getSharedPreferences(this, Constants.CURRENT_PATH_KEY);
    		if(mCurrentPhotoPath != null) {
	    		Selfie selfie = new Selfie();
	    		selfie.setName(new File(mCurrentPhotoPath).getName());
	    		selfie.setPath(mCurrentPhotoPath);
	    		
	    		Bitmap bitmap = Utils.getBitmapFromPath(mCurrentPhotoPath);
	    		selfie.setBitmap(bitmap);
	    		
	    		Bitmap scaledBitmap = Utils.getScaledBitmap(mCurrentPhotoPath, Constants.SCALE_DIMENSION);
	    		selfie.setScaledBitmap(scaledBitmap);
	    		
	    		Utils.storeSelfies(bitmap, mCurrentPhotoPath);
	    		
	    		mSelfiesArray.add(selfie);
	    		((SelfieFragment) mCurrentFragment).updateData(selfie);
	    		
	    		mCurrentPhotoPath = null;
    		
    		}
    		
    	} else if(requestCode == Constants.REQUEST_TAKE_PHOTO && resultCode == RESULT_CANCELED) {
    		
    		if(mCurrentPhotoPath != null) {
    			new File(mCurrentPhotoPath).delete();
    		}
    	}
    }
    
    private void loadFragment() {
    	FragmentManager fragmentManager = getFragmentManager();
    	fragmentManager.beginTransaction().replace(R.id.content_frame, (mCurrentFragment = new SelfieFragment())).commit();
	}
    
    /**
	 * Based on source code from http://developer.android.com/training/camera/photobasics.html
	 */
    private void dispatchTakePictureIntent() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		
		if(takePictureIntent.resolveActivity(getPackageManager()) != null) {
			
			try {
				
				File selfiePicture = Utils.createImage();
				
				if(selfiePicture != null) {
					mCurrentPhotoPath = selfiePicture.getAbsolutePath();
					Utils.setSharedPreferences(this, Constants.CURRENT_PATH_KEY, mCurrentPhotoPath);
					takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(selfiePicture));
					startActivityForResult(takePictureIntent, Constants.REQUEST_TAKE_PHOTO);
				}
				
			} catch (IOException e) {
				Log.e(TAG, e.getMessage());
			}
		}
	}
	
}
