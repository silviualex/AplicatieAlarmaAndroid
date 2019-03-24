package com.example.aplicatiealarma;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class RingtonePlayingService extends Service
{
	private MediaPlayer mediaPlayer;
	private boolean isRunning;
	private int startID;
	@Nullable
	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		Log.i("Local service", "Received start id" + startId + ": " + intent);

		String state = intent.getExtras().getString("extra");
		assert state != null;
		switch (state)
		{
			case "alarm on":
			{
				startId = 1;
				break;
			}
			case "alarm off":
			{
				startId = 0;
				break;
			}
			default:
			{
				startId = 0;
				break;
			}
		}
			if (!this.isRunning && startId == 1)
			{
				Log.e("There is no music,", "and you want to start");
				mediaPlayer = MediaPlayer.create(this, R.raw.sharp_edges);
				mediaPlayer.start();
				this.isRunning = true;
				this.startID = 0;
			}
			else if (this.isRunning && startId == 0)
			{
				//if there is no music playing and the user pressed "alarm off", music will stop playing
				Log.e("There is  music,", "and you want to end");
				mediaPlayer.stop();
				mediaPlayer.reset();
				this.isRunning = false;
				this.startID = 0;
			} else if (!this.isRunning && startId == 0)
			{
				Log.e("There is no music,", "and you want to end");
				this.isRunning = false;
				this.startID = 0;
			} else if (this.isRunning && startId == 1)
			{
				Log.e("There is  music,", "and you want to start");
				this.isRunning = true;
				this.startID = 1;
			} else
			{
				Log.e("Why are you here?,", "my friend");
			}
		return START_NOT_STICKY;
	}
	public void onDestroy()
	{
		mediaPlayer.stop();
		Toast.makeText(this, "on Destroy called", Toast.LENGTH_SHORT).show();
	}
}
