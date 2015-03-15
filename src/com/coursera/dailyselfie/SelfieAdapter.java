package com.coursera.dailyselfie;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SelfieAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mLayoutInflater;
	private List<Selfie> mSelfieList = new ArrayList<Selfie>();
	
	public SelfieAdapter (Context context, List<Selfie> selfies) {
		mContext = context;
		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		if(selfies != null && !selfies.isEmpty())
			mSelfieList.addAll(selfies);
	}
	
	public void addSelfie(Selfie selfie) {
		mSelfieList.add(selfie);
		notifyDataSetChanged();
	}
	
	public void addSelfieArray(ArrayList<Selfie> selfies) {
		
		mSelfieList.clear();
		
		for(Selfie selfie : selfies) {
			mSelfieList.add(selfie);
		}
		
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mSelfieList.size();
	}

	@Override
	public Object getItem(int position) {
		return mSelfieList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View childView = mLayoutInflater.inflate(R.layout.selfie_fragment_list_item, null);
		
		Selfie selfie = (Selfie) getItem(position);
		
		ImageView imageView = (ImageView) childView.findViewById(R.id.selfie_image); 
		TextView textView = (TextView) childView.findViewById(R.id.selfie_name);
		
		imageView.setImageBitmap(selfie.getScaledBitmap());
		textView.setText(selfie.getName());
		
		return childView;
	}
	

}
