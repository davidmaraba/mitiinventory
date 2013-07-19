package com.mitiinventory;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ReorderLevelChecker extends Activity {
	/*private static final String ALARM_REFRESH_ACTION = "it.trento.alchemiasoft.casagranda.simone.alarmmanagertutorial.ALARM_REFRESH_ACTION";
	private static final int ALARM_CODE = 20;

	private static final long DEFAULT_START_DELAY = 1000;
	private static final long DEFAULT_INTERVAL_DELAY = 3000;

	private BroadcastReceiver alarmReceiver;
	private PendingIntent pendingIntent;

	private AlarmManager alarmManager;

	private int alarmCounted;

	// UI references
	private EditText millsStartEditText, millsIntervalEditText, alarmsReceived;

	// The handler that manage the UI updates
	private Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ALARM_CODE:
				alarmsReceived.setText(String.valueOf(alarmCounted));
				Toast.makeText(ReorderLevelChecker.this,
						"Alarm Received", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// We retrieve UI references
		millsStartEditText = (EditText) findViewById(R.id.millsStartEditText);
		millsIntervalEditText = (EditText) findViewById(R.id.millsIntervalsEditText);
		alarmsReceived = (EditText) findViewById(R.id.alarmsReceivedEditText);
	}

	@Override
	protected void onStart() {
		super.onStart();
		// We get the AlarmManager
		alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		// We set default parameters for alarms
		millsStartEditText.setText(String.valueOf(DEFAULT_START_DELAY));
		millsIntervalEditText.setText(String.valueOf(DEFAULT_INTERVAL_DELAY));
		// We prepare the pendingIntent for the AlarmManager
		Intent intent = new Intent(ALARM_REFRESH_ACTION);
		pendingIntent = PendingIntent.getBroadcast(this, 0, intent,
				PendingIntent.FLAG_CANCEL_CURRENT);
		// We create and register a broadcast receiver for alarms
		alarmReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				// We increment received alarms
				alarmCounted++;
				// We notify to handler the arrive of alarm
				Message msg = myHandler.obtainMessage(ALARM_CODE, intent);
				myHandler.sendMessage(msg);
			}
		};
		// We register dataReceiver to listen ALARM_REFRESH_ACTION
		IntentFilter filter = new IntentFilter(ALARM_REFRESH_ACTION);
		registerReceiver(alarmReceiver, filter);
	}

	public void startOneShot(View v) {
		// We get value for one shot alarm
		int startTime = Integer.parseInt(millsStartEditText.getText()
				.toString());
		// We have to register to AlarmManager
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.MILLISECOND, startTime);
		// We set a one shot alarm
		alarmManager.set(AlarmManager.RTC_WAKEUP, calendar
				.getTimeInMillis(), pendingIntent);
	}

	public void startRepeating(View v) {
		// We get value for repeating alarm
		int startTime = Integer.parseInt(millsStartEditText.getText()
				.toString());
		long intervals = Long.parseLong(millsIntervalEditText.getText()
				.toString());
		// We have to register to AlarmManager
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.MILLISECOND, startTime);
		// We set a repeating alarm
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar
				.getTimeInMillis(), intervals, pendingIntent);
	}

	public void stopAlarms(View v) {
		// We cancel alarms that matches with pending intent
		alarmManager.cancel(pendingIntent);
		Toast.makeText(this, "Alarms stopped", Toast.LENGTH_SHORT).show();
	}
	*/
}