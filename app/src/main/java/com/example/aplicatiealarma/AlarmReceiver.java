package com.example.aplicatiealarma;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		Log.e(" We are in the receiver", "That is great");

		//fetch extra strings from the intent
		String fetchString = intent.getExtras().get("extra").toString();

		Log.e("What is the key?", fetchString);

		//create an intent to the ringtone service
		Intent serviceIntent = new Intent(context, RingtonePlayingService.class);

		//pass the extra string from the Main Activity to the Ringtone Playing Service
		// andrei are mere

		serviceIntent.putExtra("extra",fetchString);

		//start the ringtone service
		context.startService(serviceIntent);
	}
}
