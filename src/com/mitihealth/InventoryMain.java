package com.mitihealth;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.mitiinventory.R;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class InventoryMain extends ListActivity implements TextWatcher {
	
	Button btnSupplierManagement,btnREorder,btnAddItems;
	public static final String TAG_NAME = "name";
	public static final String TAG_ID = "id";

	public static final String TAG_DESCRIPTION = "description";
	public static final String TAG_ENCOUNTER_NUMBER = "encounter_number";
	public static final String TAG_RESTOCK = "restock";
	public static final String TAG_TODAY = "today";
	public static final String TAG_PRICE = "price";
	public static final String TAG_QUANTITY="quantity";
	private ProgressDialog pDialog;
	ArrayList<HashMap<String, String>> medicinesList = new ArrayList<HashMap<String, String>>();
	JSONArray medicines = null;
	public InventoryMainAdapter adapter;
	private EditText txtSearch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inventory_main);
		
		btnSupplierManagement=(Button) findViewById(R.id.btnSupplierManagement);
		btnREorder=(Button) findViewById(R.id.btnReorderItems);
		btnAddItems=(Button) findViewById(R.id.btnAddItems);
		
		btnSupplierManagement.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), SupplierManagement.class));
			}
		});
		btnREorder.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), ReorderMain.class));

			}
		});
		btnAddItems.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), AddItems.class));
				
			}
		});
		
		new LoadSubscriptions().execute();
		txtSearch=(EditText) findViewById(R.id.inputSearch);
        txtSearch.addTextChangedListener(this);
		
	}
	
	class LoadSubscriptions extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(InventoryMain.this);
			pDialog.setMessage("Loading Medicines. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * getting All medicines from url
		 * */
		protected String doInBackground(String... args) {

			try {

				URL get_supplies_url = new URL(
						"https://data.mitihealth.org/openmrs/getmedicines");
				URLConnection tc = get_supplies_url.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(
						tc.getInputStream()));
				String line;

				String home = in.toString();
				Log.d("JSON", home);
				while ((line = in.readLine()) != null) {
					// line = in.readLine();
					medicines = new JSONArray(line);
					for (int i = 0; i < medicines.length(); i++) {
						JSONObject c = (JSONObject) medicines.get(i);

						// Storing each json item in variable
						String id = c.getString(TAG_ID);

						String name = c.getString(TAG_NAME);

						String description = c.getString(TAG_DESCRIPTION);
						String encounter = c.getString(TAG_ENCOUNTER_NUMBER);
						String restock = c.getString(TAG_RESTOCK);
						String today = c.getString(TAG_TODAY);
						String rem=c.getString(TAG_QUANTITY);

						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put(TAG_ID, id);
						map.put(TAG_NAME, name);
						map.put(TAG_DESCRIPTION, description);
						map.put(TAG_ENCOUNTER_NUMBER, encounter);
						map.put(TAG_RESTOCK, restock);
						map.put(TAG_TODAY, today);
						map.put(TAG_QUANTITY, rem);

						medicinesList.add(map);

						Log.d("JSON", line);
					}

				}
				//Log.d("check", line);
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products
			pDialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				

				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					for (int i = 0; i <= medicinesList.size(); i++) {
						LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
								LinearLayout.LayoutParams.MATCH_PARENT,
								LinearLayout.LayoutParams.WRAP_CONTENT);

					}

					adapter = new InventoryMainAdapter(InventoryMain.this,
							medicinesList, getApplicationContext());

					// updating listview
					setListAdapter(adapter);
				}
			});

		}

	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		String text = txtSearch.getText().toString().toLowerCase();
		adapter.getFilter().filter(text);
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}

}
