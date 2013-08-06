package com.mitihealth;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.mitiinventory.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SupplierManagement extends Activity{

	Button btnAddContact;
	private ProgressDialog pDialog;
	
	public static final String TAG_ID = "id";
	public static final String TAG_NAME = "supplier_name";
	public static final String TAG_LOCATION = "location";
	public static final String TAG_PHONE_NUMBER = "phone_number";
	public static final String TAG_EMAIL = "email";
	public static final String TAG_DELIVERS = "delivers_to_facility";
	public static final String TAG_CREDIT="gives_credit";
	JSONArray suppliers = null;
	ArrayList<HashMap<String, String>> medicinesList = new ArrayList<HashMap<String, String>>();
	String[] supplyValues;
	int supplyIds[];
	String[] from = { TAG_NAME, TAG_ID,TAG_LOCATION,TAG_PHONE_NUMBER,TAG_CREDIT,TAG_DELIVERS };
	List<HashMap<String, String>> aList;

	// Ids of views in listview_layout
	int[] to = { R.id.txtSupplierName,R.id.supplierId,R.id.txtSuppTown,R.id.txtSupplierNumber,R.id.chkSupplierCreditDetails,R.id.chkSupplierDetails };
	ListView firstList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.supplier_management);
		
		aList = new ArrayList<HashMap<String, String>>();

		firstList = (ListView) findViewById(R.id.supplierManagementList);
		
		btnAddContact=(Button) findViewById(R.id.btnAddContact);
		
		btnAddContact.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), AddContact.class));
				
			}
		});
		new LoadSubscriptions().execute();
		
	}
	class LoadSubscriptions extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SupplierManagement.this);
			pDialog.setMessage("Loading Suppliers. Please wait...");
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
						"https://data.mitihealth.org/openmrs/getsuppliers");
				URLConnection tc = get_supplies_url.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(
						tc.getInputStream()));
				String line;

				String home = in.toString();
				Log.d("JSON", home);
				while ((line = in.readLine()) != null) {
					// line = in.readLine();
					suppliers = new JSONArray(line);
					for (int i = 0; i < suppliers.length(); i++) {
						JSONObject c = (JSONObject) suppliers.get(i);

						// Storing each json item in variable
						String id = c.getString(TAG_ID);

						String name = c.getString(TAG_NAME);
						String location=c.getString(TAG_LOCATION);
						String phone=c.getString(TAG_PHONE_NUMBER);
						String email=c.getString(TAG_EMAIL);
						String delivers=c.getString(TAG_DELIVERS);
						String credit=c.getString(TAG_CREDIT);


						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						//String[] from = { TAG_NAME, TAG_ID,TAG_LOCATION,TAG_PHONE_NUMBER,TAG_CREDIT,TAG_DELIVERS };
						
						String D="",S="";
						
						if(delivers.equals("1"))
							D="[D]";
						if(credit.equals("1"))
							S="[$]";


						map.put(TAG_NAME, name);
						map.put(TAG_ID, id);
						map.put(TAG_LOCATION, "-"+location);
						map.put(TAG_PHONE_NUMBER, phone);
						map.put(TAG_CREDIT, S);
						map.put(TAG_DELIVERS, D);

						aList.add(map);
						// medicinesList.add(map);

						Log.d("JSON", line);
					}

				}
				Log.d("check", line);
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
					SimpleAdapter adapter = new SimpleAdapter(getBaseContext(),
							aList, R.layout.layout_list, from, to);
					//autoComplete.setAdapter(adapter);

					SimpleAdapter myAdapter = new SimpleAdapter(
							getBaseContext(), aList, R.layout.supplier_management_layout,
							from, to);

					firstList.setAdapter(myAdapter);

				}
			});

		}

	}
}
