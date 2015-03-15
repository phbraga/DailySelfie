package com.coursera.dailyselfie;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SelfieFragment extends BaseFragment {

	private ListView mListView;
	private SelfieAdapter mAdapter;
	private FileFilter mFileFilter;
	private ArrayList<Selfie> mSelfieList;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mThisActivity = getActivity();
		mSelfieList = new ArrayList<Selfie>();
		mAdapter = new SelfieAdapter(getActivity(), null);

        mFileFilter = new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().contains(Constants.PATH_PREFFIX);
			}
		};
		
		Utils.initStoragePath(getActivity());
        verifySelfies();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.selfie_fragment, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mListView = (ListView) getView().findViewById(R.id.listview);
		mListView.setAdapter(mAdapter);
		mListView.invalidate();

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				showFullScreenSelfie(mAdapter.getItem(position), mThisActivity);
			}
		});
	}
	
	public void verifySelfies() {
		File selfiesDirectory = new File(Utils.mBitmapStoragePath);
		
		if(selfiesDirectory.exists() && mSelfieList != null) {
			mSelfieList.clear();
			
			for( File file : selfiesDirectory.listFiles(mFileFilter)) {
				
				Selfie selfie = new Selfie();
				selfie.setName(file.getName());
				selfie.setPath(file.getAbsolutePath());
				selfie.setBitmap(Utils.getBitmapFromPath(file.getAbsolutePath()));
				selfie.setScaledBitmap(Utils.getScaledBitmap(selfie.getPath(), Constants.SCALE_DIMENSION));
				
				updateData(selfie);
			}
		}
	}

	public void updateData(Selfie selfie) {
		mSelfieList.add(selfie);
		mAdapter.addSelfie(selfie);
	}
	
	public void showFullScreenSelfie(Object objSelfie, Activity activity) {
		
		Selfie selfie = objSelfie instanceof Selfie ? (Selfie) objSelfie : null;
		
		if(selfie != null) {
			Intent fullScreenIntent = new Intent(getActivity(), FullScreenActivity.class);
			fullScreenIntent.putExtra(Constants.KEY_FULLSCREEN_NAME, selfie.getName());
			fullScreenIntent.putExtra(Constants.KEY_FULLSCREEN_PATH, selfie.getPath());
			startActivity(fullScreenIntent);
	    }
		
	}
}
