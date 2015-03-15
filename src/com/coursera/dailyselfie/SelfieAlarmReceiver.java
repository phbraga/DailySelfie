/**
 * Based on Adam Porter material android course
 */

package com.coursera.dailyselfie;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SelfieAlarmReceiver extends BroadcastReceiver {
	
	private Intent mNotificationIntent;
	private PendingIntent mContentIntent;
	private NotificationManager mNotificationManager;
	
	private final CharSequence contentTitle = "Daily Selfie";
	private final CharSequence contentText = "Time for another selfie";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		mNotificationIntent = new Intent(context, MainActivity.class);
		
		mContentIntent = PendingIntent.getActivity(context, 0, mNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		Notification.Builder notificationBuilder = new Notification.Builder(context)
				.setContentText(contentText)
				.setContentTitle(contentTitle)
				.setSmallIcon(android.R.drawable.ic_menu_camera)
				.setContentIntent(mContentIntent)
				.setAutoCancel(true);
		
		mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		
		mNotificationManager.notify(Constants.NOTIFICATION_ID, notificationBuilder.build());
	}

}
