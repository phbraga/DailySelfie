package com.coursera.dailyselfie;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;

public class BaseFragment extends Fragment {
	private static final String TAG = "BaseFragment";

	protected Activity mThisActivity = null;

	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "onResume");
	}

	public Activity getThisActivity() {
		return mThisActivity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mThisActivity = getActivity();
	}
}
