package com.example.aplicatiealarma;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity
{
    private AlarmManager alarmManager;
    private TimePicker alarmTimePicker;
    private TextView updateText;
    private Context context;
    private PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;
        // initialize our alarm manager
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //initialize our timePicker
        alarmTimePicker =(TimePicker)findViewById(R.id.simpleTimePicker);
        //initialize our text update box
        updateText = (TextView) findViewById(R.id.updateStatus);

        //create an instance of a calendar
        final Calendar calendar = Calendar.getInstance();

        //create an intent to the Alarm Reciever class
        final Intent alarmIntent = new Intent(this.context, AlarmReceiver.class);

        //initialized start and stop buttons
        Button start_alarm = (Button) findViewById(R.id.alarm_on);
        Button stop_alarm = (Button) findViewById(R.id.alarm_off);

        //crete onClick listner to start and stop the alarm

        start_alarm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                calendar.set(Calendar.HOUR_OF_DAY,alarmTimePicker.getHour());
                calendar.set(Calendar.MINUTE, alarmTimePicker.getMinute());
                //method that changes the update textbox

                int hour = alarmTimePicker.getHour();
                int minutes = alarmTimePicker.getMinute();
                String hourString = String.valueOf(hour);
                String minutesString = String.valueOf(minutes);

                if(hour > 12)
                    hourString = String.valueOf(hour - 12);
                if(minutes < 10)
                    //10:7 --> 10:07
                    minutesString = "0" + String.valueOf(minutes);

                set_alarm_text("Alarm set to " + hourString + ":" + minutesString);

                // put in extra string into alarmIntent
                //tells the clock that you pressed the "alarm on" button
                alarmIntent.putExtra("extra","alarm on");


                //create a pending intent that delays the intent until the specified calendar time
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,
                        alarmIntent,PendingIntent.FLAG_UPDATE_CURRENT);

                //set the alarm manager
                alarmManager.set(alarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),
                        pendingIntent);
            }
        });

        stop_alarm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //method that changes the update textbox
                set_alarm_text("Alarm of!");
                //cancel the alarm
                alarmManager.cancel(pendingIntent);

                //put extra string into alarmIntent
                //tells the clock that you pressed the alarm of button
                alarmIntent.putExtra("extra", "alarm off");

                //stop the ringtone
                sendBroadcast(alarmIntent);
            }
        });
    }

    private void set_alarm_text(String output)
    {
        updateText.setText(output);
    }


}
