package com.mitihealth;



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

public class HomePage extends Activity {

	private Button btn_checkout, btn_clinical, btn_inventory, btn_reports;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homepage_main);

		btn_checkout = (Button) findViewById(R.id.btn_chekOut);
		btn_clinical = (Button) findViewById(R.id.btn_clinical);
		btn_inventory = (Button) findViewById(R.id.btn_inventory);
		btn_reports = (Button) findViewById(R.id.btn_reports);
		
		btn_checkout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//startActivity(new Intent(getApplicationContext(), LoginActivity.class));
				startActivity(new Intent(getApplicationContext(), AddSupplies.class));
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
			Toast.makeText(HomePage.this, "Save is Selected",
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
