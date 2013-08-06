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
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CheckOutHome extends Activity implements TextWatcher {

	ArrayAdapter arrayAdapter, secondArrayAdapter;
	ListView firstList, secondList;
	Button btnAddToCart;
	private ProgressDialog pDialog;
	private Button btnCheckOut;

	// mediciness JSONArray
	JSONArray medicines = null;
	// Inbox JSON url

	// JSON Node names
	public static final String TAG_ID = "id";
	public static final String TAG_NAME = "name";
	public static final String TAG_DESCRIPTION = "description";
	public static final String TAG_ENCOUNTER_NUMBER = "encounter_number";
	public static final String TAG_RESTOCK = "restock";
	public static final String TAG_TODAY = "today";
	private CustomAutoCompleteTextView txtSearch;

	private TextView txtTotal;
	ArrayList<HashMap<String, String>> medicinesList = new ArrayList<HashMap<String, String>>();

	public CheckOutHomeAdapter adapter, adapterSearch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check_out_main);

		firstList = (ListView) findViewById(R.id.firstList);
		//secondList = (ListView) findViewById(R.id.secondList);

		new LoadSubscriptions().execute();

		txtSearch =  (CustomAutoCompleteTextView) findViewById(R.id.inputSearch);
		//txtSearch.addTextChangedListener(this);
		adapter = new CheckOutHomeAdapter(CheckOutHome.this,
				medicinesList, getApplicationContext());
		adapterSearch=new CheckOutHomeAdapter(CheckOutHome.this,
				medicinesList, getApplicationContext());
		
		OnItemClickListener itemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
 
                /** Each item in the adapter is a HashMap object.
                *  So this statement creates the currently clicked hashmap object
                * */
                HashMap<String, String> hm = (HashMap<String, String>) arg0.getAdapter().getItem(position);
 
                /** Getting a reference to the TextView of the layout file activity_main to set Currency */
               // TextView tvCurrency = (TextView) findViewById(R.id.tv_currency) ;
 
                /** Getting currency from the HashMap and setting it to the textview */
              //  tvCurrency.setText("Currency : " + hm.get("name"));
            }
        };
        txtSearch.setOnItemClickListener(itemClickListener);
        txtSearch.setAdapter(adapterSearch);

		btnAddToCart = (Button) findViewById(R.id.btn_addToCart);
		// final String [] my=CheckOutAdapter.selects;
		// arrayAdapter=new
		// ArrayAdapter(this,android.R.layout.simple_list_item_1,my);
		// arrayAdapter=new
		// ArrayAdapter(this,android.R.layout.simple_list_item_1,my);

		// Toast.makeText(this,String.valueOf(my),Toast.LENGTH_LONG).show();
		btnAddToCart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String[] my = CheckOutHomeAdapter.selects.split(",");
				//String[] my={"0","1","2","3"};
				// my=ArrayUtils.removeElement(my,"my");
				/*ArrayList<String> mannschaftsnamen = new ArrayList<String>();
				EditText et;
			    for (int i = 0; i < firstList.getCount(); i++) {
			        v = firstList.getAdapter().getView(i, null, null);
			        et = (EditText) v.findViewById(i);
			        Toast.makeText(CheckOutHome.this, et.getText().toString(),
							Toast.LENGTH_SHORT).show();
			       // mannschaftsnamen.add(et.getText().toString());
			    }*/
				arrayAdapter = new ArrayAdapter(CheckOutHome.this,
						android.R.layout.simple_list_item_1, my);

				// Toast.makeText(this,String.valueOf(my),Toast.LENGTH_LONG).show();
				Toast.makeText(CheckOutHome.this, CheckOutHomeAdapter.selects,
						Toast.LENGTH_SHORT).show();
				secondList.setAdapter(arrayAdapter);
				Log.d("Array content", String.valueOf(CheckOutAdapter.selects));
			}
		});

		txtTotal = (TextView) findViewById(R.id.netTotal);
		
		//final double totals=Double.parseDouble(txtTotal.getText().toString());
		final double totals=400;
	/*	txtTotal.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				//txtTotal.setText(getS);
				
			}
		});*/

		btnCheckOut=(Button) findViewById(R.id.btn_CheckOuts);
		
		btnCheckOut.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in=new Intent(getApplicationContext(), CheckOutSale.class);
				in.putExtra("amount",totals);
				startActivity(in);
				
			}
		});
		
	}

	class LoadSubscriptions extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(CheckOutHome.this);
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

						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put(TAG_ID, id);
						map.put(TAG_NAME, name);
						map.put(TAG_DESCRIPTION, description);
						map.put(TAG_ENCOUNTER_NUMBER, encounter);
						map.put(TAG_RESTOCK, restock);
						map.put(TAG_TODAY, today);

						medicinesList.add(map);

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
					for (int i = 0; i <= medicinesList.size(); i++) {
						LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
								LinearLayout.LayoutParams.MATCH_PARENT,
								LinearLayout.LayoutParams.WRAP_CONTENT);

					}

					/*adapter = new CheckOutHomeAdapter(CheckOutHome.this,
							medicinesList, getApplicationContext());*/

					// updating listview
					// firstList.setListAdapter(adapter);
					firstList.setAdapter(adapter);
				}
			});

		}

	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		String text = txtSearch.getText().toString().toLowerCase();
		adapterSearch.getFilter().filter(text);
		txtSearch.setAdapter(adapterSearch);

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
