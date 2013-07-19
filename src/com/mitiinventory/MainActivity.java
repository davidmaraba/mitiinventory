package com.mitiinventory;

import com.example.mitiinventory.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final int ALARM_CODE = 20;

	private static final long DEFAULT_START_DELAY = 1000;
	private static final long DEFAULT_INTERVAL_DELAY = 3000;

	private BroadcastReceiver alarmReceiver;
	private PendingIntent pendingIntent;

	private AlarmManager alarmManager;

	private int alarmCounted;
	private Button btn_setReorderLevel, btnViews, btn_addInventory,btn_deleteInventory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btn_setReorderLevel = (Button) findViewById(R.id.btn_reorderLevel);
		btnViews = (Button) findViewById(R.id.btn_checkRemaining);
		btn_addInventory = (Button) findViewById(R.id.btn_addInventory);
		btn_deleteInventory=(Button) findViewById(R.id.btn_deleteInventory);

		btn_addInventory.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), AddMain.class));

			}
		});
		btnViews.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),
						ViewMain.class));
			}
		});
		btn_deleteInventory.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), DeleteMain.class));
				
			}
		});
		btn_setReorderLevel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),
						LoginActivity.class));

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.layout.menu, menu);
		return true;
	}

	/**
	 * Event Handling for Individual menu item selected Identify single menu
	 * item by it's id
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_set_timer:
			// Single menu item is selected do something
			// Ex: launching new activity/screen or show alert message
			// Toast.makeText(MainActivity.this, "Bookmark is Selected",
			// Toast.LENGTH_SHORT).show();
			setRorderLevelChecker();
			return true;

		case R.id.menu_stop_timer:
			Toast.makeText(MainActivity.this, "Save is Selected",
					Toast.LENGTH_SHORT).show();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void setRorderLevelChecker() {
		AlertDialog.Builder builder = new Builder(this);
		final EditText text = new EditText(this);

		builder.setTitle("Set Checker")
				.setMessage("Check time intervals (mins)").setView(text);
		builder.setPositiveButton("Set", new OnClickListener() {

			public void onClick(DialogInterface di, int i) {
				final String name = text.getText().toString();
				// do something with it
			}
		});
		builder.setNegativeButton("Cancel", new OnClickListener() {

			public void onClick(DialogInterface di, int i) {
			}
		});
		builder.create().show();
	}

}
