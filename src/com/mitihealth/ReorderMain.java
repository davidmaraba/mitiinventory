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

import android.R.anim;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ReorderMain extends Activity{
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
	final TableRow.LayoutParams wrapWrapTableRowParams = new TableRow.LayoutParams(
			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	final int[] fixedColumnWidths = new int[] { 10,30, 20, 20, 20, 20,20,20,20 };
	final int[] scrollableColumnWidths = new int[] {5,20, 10, 15, 10, 10, 10 ,10,10};
	final int fixedRowHeight = 50;
	final int fixedHeaderHeight = 60;
	TableLayout scrollablePart;
	
	public static final String TAG_ID_SUPPLIER = "id";
	public static final String TAG_NAME_SUPPLIER = "supplier_name";
	public static final String TAG_LOCATION = "location";
	public static final String TAG_PHONE_NUMBER = "phone_number";
	public static final String TAG_EMAIL = "email";
	public static final String TAG_DELIVERS = "delivers_to_facility";
	public static final String TAG_CREDIT="gives_credit";
	JSONArray suppliers = null;
	ArrayList<HashMap<String, String>> supplierList = new ArrayList<HashMap<String, String>>();
	String[] supplyValues;
	int supplyIds[];
	String[] from = { TAG_NAME_SUPPLIER, TAG_ID_SUPPLIER,TAG_LOCATION,TAG_PHONE_NUMBER,TAG_CREDIT,TAG_DELIVERS };
	List<HashMap<String, String>> aList;

	// Ids of views in listview_layout
	int[] to = { R.id.txtSupplierName,R.id.supplierId,R.id.txtSuppTown,R.id.txtSupplierNumber,R.id.chkSupplierCreditDetails,R.id.chkSupplierDetails };
	ListView firstList;
	TextView txtReorderAmount;
	Button btnDone;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reorder_main);
		aList = new ArrayList<HashMap<String, String>>();
		
		firstList = (ListView) findViewById(R.id.reorderList);
		txtReorderAmount=(TextView) findViewById(R.id.txtEstPrice);
		
		TableRow row = new TableRow(ReorderMain.this);
		// header (fixed vertically)
		TableLayout header = (TableLayout) findViewById(R.id.table_header);
		row.setLayoutParams(wrapWrapTableRowParams);
		row.setGravity(Gravity.CENTER);
		row.setBackgroundColor(Color.GRAY);
		row.addView(makeTableRowWithText("", fixedColumnWidths[0],
				fixedHeaderHeight));
		row.addView(makeTableRowWithText("Item", fixedColumnWidths[1],
				fixedHeaderHeight));
		row.addView(makeTableRowWithText("Left", fixedColumnWidths[2],
				fixedHeaderHeight));
		row.addView(makeTableRowWithText("Est Price", fixedColumnWidths[3],
				fixedHeaderHeight));
		row.addView(makeTableRowWithText("+", fixedColumnWidths[4],
				fixedHeaderHeight));
		row.addView(makeTableRowWithText("-", fixedColumnWidths[5],
				fixedHeaderHeight));
		row.addView(makeTableRowWithText("Reorder", fixedColumnWidths[6],
				fixedHeaderHeight));
		row.addView(makeTableRowWithText("+", fixedColumnWidths[7],
				fixedHeaderHeight));
		row.addView(makeTableRowWithText("-", fixedColumnWidths[8],
				fixedHeaderHeight));
		header.addView(row);
		
	 
		new LoadSubscriptions().execute();
		
		btnDone=(Button) findViewById(R.id.btnDone);
		btnDone.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), InventoryMain.class));
			}
		});
		
	}
	
	private TextView recyclableTextView;
	private Button btnrecyclabale;
	private CheckBox chkRecyclable;

	public TextView makeTableRowWithText(String text,
			int widthInPercentOfScreenWidth, int fixedHeightInPixels) {
		int screenWidth = getResources().getDisplayMetrics().widthPixels;
		recyclableTextView = new TextView(this);
		recyclableTextView.setText(text);
		recyclableTextView.setTextColor(Color.BLACK);
		recyclableTextView.setTextSize(20);
		recyclableTextView.setWidth(widthInPercentOfScreenWidth * screenWidth
				/ 100);
		recyclableTextView.setHeight(fixedHeightInPixels);
		return recyclableTextView;
	}
	public CheckBox makeCheckBox(
			int widthInPercentOfScreenWidth, int fixedHeightInPixels,final TableRow r) {
		int screenWidth = getResources().getDisplayMetrics().widthPixels;
		//btnrecyclabale = new Button(this);
		//btnrecyclabale.setText(text);
		chkRecyclable=new CheckBox(this);
		//btnrecyclabale.setBackgroundResource(img);
		//btnrecyclabale.setTextColor(Color.BLACK);
		//btnrecyclabale.setTextSize(25);
		chkRecyclable
				.setWidth(widthInPercentOfScreenWidth * screenWidth / 100);
		chkRecyclable.setHeight(fixedHeightInPixels);
		chkRecyclable.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked)
				{
					r.setBackgroundColor(0xff00ff00);
					txtReorderAmount.setText(String.valueOf(summation(scrollablePart)));
					
				}
				if(!isChecked)
				{
				r.setBackgroundColor(0xffffffff);
				txtReorderAmount.setText(String.valueOf(summation(scrollablePart)));
				}
				
			}
		});
		return chkRecyclable;
	}
	public double summation(TableLayout tableLayout) {
		double s = 0;
		for (int rowPosition = 0; rowPosition < tableLayout.getChildCount(); rowPosition++) {
			TableRow row1 = (TableRow) tableLayout.getChildAt(rowPosition);
			CheckBox chk=(CheckBox) row1.getChildAt(0);
			TextView et = (TextView) row1.getChildAt(3);
			TextView t = (TextView) row1.getChildAt(6);
			Double etr=Double.parseDouble(et.getText().toString());
			Double tr=Double
					.parseDouble(t.getText().toString());
			if(chk.isChecked())
			{
			if(etr.equals(""))
				etr=1.0;
			if(tr.equals(""))
			tr=1.0;
			s += (etr * tr);
			}
		}
		return s;

	}
	public Button makeButtonRowWithText(final int img,
			int widthInPercentOfScreenWidth, int fixedHeightInPixels,final TableLayout tbl,final TableRow r) {
		int screenWidth = getResources().getDisplayMetrics().widthPixels;
		btnrecyclabale = new Button(this);
		//btnrecyclabale.setText(text);
		btnrecyclabale.setBackgroundResource(img);
		btnrecyclabale.setTextColor(Color.BLACK);
		btnrecyclabale.setTextSize(25);
		btnrecyclabale
				.setWidth(widthInPercentOfScreenWidth * screenWidth / 100);
		btnrecyclabale.setHeight(fixedHeightInPixels);
		btnrecyclabale.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TextView price = (TextView) r.getChildAt(3);
				TextView amount=(TextView) r.getChildAt(6);
				//TableRow row1 = (TableRow) tableLayout.getChildAt(rowPosition);
				//CheckBox chk=(CheckBox) row1.getChildAt(0);
				
				double p=Double.parseDouble(price.getText().toString());
				int a=Integer.parseInt(amount.getText().toString());
				if(img==R.drawable.plus)
				{
					
				p+=5;
				price.setText(String.valueOf(p));
				}
				else if(img==R.drawable.minus)
				{
					p-=5;
				price.setText(String.valueOf(p));
				}
				else if(img==R.drawable.pluss)
				{
					a+=5;
					amount.setText(String.valueOf(a));
				}
				else if(img==R.drawable.minuss)
				{
					a-=5;
					amount.setText(String.valueOf(a));
				}
				txtReorderAmount.setText(String.valueOf(summation(scrollablePart)));
				//double ds=summation(tbl);
				//txtNet.setText(String.valueOf(summation(tbl)));
				
			}
		});
		return btnrecyclabale;
	}

	class LoadSubscriptions extends AsyncTask<String, String, String> {
		int p=R.drawable.plus;
		int m=R.drawable.minus;
		int ps=R.drawable.pluss;
		int ms=R.drawable.minuss;

		TableRow row ;//= new TableRow(ReorderMain.this);
		HashMap<String, String> map;


		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ReorderMain.this);
			pDialog.setMessage("Loading Items. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * getting All medicines from url
		 * */
		BufferedReader in;
		URLConnection tc;
		protected String doInBackground(String... args) {

			try {

				URL get_supplies_url = new URL(
						"https://data.mitihealth.org/openmrs/getmedicines");
				 tc = get_supplies_url.openConnection();
				 in = new BufferedReader(new InputStreamReader(
						tc.getInputStream()));
				String line;

				String home = in.toString();
				Log.d("JSON", home);
				while ((line = in.readLine()) != null) {
					// line = in.readLine();
					medicines = new JSONArray(line);
					

				}
				URL get_supplies = new URL(
						"https://data.mitihealth.org/openmrs/getsuppliers");
				URLConnection tc = get_supplies.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(
						tc.getInputStream()));
				String lines;

				String homes = in.toString();
				Log.d("JSON", homes);
				while ((lines = in.readLine()) != null) {
					// line = in.readLine();
					suppliers = new JSONArray(lines);
					for (int i = 0; i < suppliers.length(); i++) {
						JSONObject c = (JSONObject) suppliers.get(i);

						// Storing each json item in variable
						String id = c.getString(TAG_ID);

						String name = c.getString(TAG_NAME_SUPPLIER);
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


						map.put(TAG_NAME_SUPPLIER, name);
						map.put(TAG_ID_SUPPLIER, id);
						map.put(TAG_LOCATION, "-"+location);
						map.put(TAG_PHONE_NUMBER, phone);
						map.put(TAG_CREDIT, S);
						map.put(TAG_DELIVERS, D);

						aList.add(map);
					}
				}
			}
			 catch (JSONException e) {
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
					try
					{
					for (int i = 0; i < medicines.length(); i++) {
						JSONObject c = (JSONObject) medicines.get(i);
						
						Log.d("drugs",c.toString());

						// Storing each json item in variable
						String id = c.getString(TAG_ID);

						String name = c.getString(TAG_NAME);

						String description = c.getString(TAG_DESCRIPTION);
						String encounter = c.getString(TAG_ENCOUNTER_NUMBER);
						String restock = c.getString(TAG_RESTOCK);
						String today = c.getString(TAG_TODAY);
						String rem=c.getString(TAG_QUANTITY);
						String price=c.getString(TAG_PRICE);
						int remInt=Integer.parseInt(rem);
						
						scrollablePart = (TableLayout) findViewById(R.id.scrollable_part);
						row = new TableRow(ReorderMain.this);
						row.setLayoutParams(wrapWrapTableRowParams);
						row.setGravity(Gravity.CENTER);
						row.setBackgroundColor(Color.WHITE);
						row.addView(makeCheckBox(scrollableColumnWidths[1], fixedRowHeight, row));
						row.addView(makeTableRowWithText(name,
								scrollableColumnWidths[1], fixedRowHeight));
						row.addView(makeTableRowWithText(rem,
								scrollableColumnWidths[1], fixedRowHeight));
						row.addView(makeTableRowWithText(price,
								scrollableColumnWidths[1], fixedRowHeight));
						
						row.addView(makeButtonRowWithText(p,
								scrollableColumnWidths[1], fixedRowHeight,scrollablePart,row));
						row.addView(makeButtonRowWithText(m,
								scrollableColumnWidths[1], fixedRowHeight,scrollablePart,row));
						row.addView(makeTableRowWithText(String.valueOf(Integer.parseInt(rem)*2),
								scrollableColumnWidths[1], fixedRowHeight));
						row.addView(makeButtonRowWithText(ps,
								scrollableColumnWidths[1], fixedRowHeight,scrollablePart,row));
						row.addView(makeButtonRowWithText(ms,
								scrollableColumnWidths[1], fixedRowHeight,scrollablePart,row));
						if(Double.parseDouble(rem)<Double.parseDouble(restock))
						scrollablePart.addView(row);
						
						
					}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					/*SimpleAdapter adapter = new SimpleAdapter(getBaseContext(),
							aList, R.layout.layout_list, from, to);*/
					//autoComplete.setAdapter(adapter);

					SimpleAdapter myAdapter = new SimpleAdapter(
							getBaseContext(), aList, R.layout.supplier_management_layout,
							from, to);

					firstList.setAdapter(myAdapter);

					

					/*adapter = new InventoryMainAdapter(InventoryMain.this,
							medicinesList, getApplicationContext());*/

					// updating listview
					//setListAdapter(adapter);
				}
			});

		}

	}
	class LoadSuppliers extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ReorderMain.this);
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


						map.put(TAG_NAME_SUPPLIER, name);
						map.put(TAG_ID_SUPPLIER, id);
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
