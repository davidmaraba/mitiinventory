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
import com.mitihealth.CheckOutHome.LoadSubscriptions;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class CheckOut extends Activity {
	public static final String TAG_NAME = "name";
	public static final String TAG_ID = "id";
	public static final String TAG_NAMES="names";

	public static final String TAG_DESCRIPTION = "description";
	public static final String TAG_ENCOUNTER_NUMBER = "encounter_number";
	public static final String TAG_RESTOCK = "restock";
	public static final String TAG_TODAY = "today";
	public static final String TAG_PRICE = "price";
	private CustomAutoCompleteTextView txtSearch;
	// Array of strings storing country names
	List<HashMap<String, String>> aList;
	private ProgressDialog pDialog;
	private Button btnCheckOut;
	CustomAutoCompleteTextView autoComplete;
	// mediciness JSONArray
	JSONArray medicines = null;
	// Keys used in Hashmap
	String[] from = { TAG_NAME, TAG_ID, TAG_RESTOCK };
	String[] froms = { TAG_NAMES, TAG_ID, TAG_RESTOCK };

	// Ids of views in listview_layout
	
	int[] to = { R.id.name, R.id.supplies_id, R.id.supplies_restock };
	ListView firstList;
	Button btn_addToCart;
	TableLayout tbl;
	LayoutInflater inflater = null;
	EditText txtAmount;
	Button btnPlus, btnMinus;
	TextView txtNet, txtrow;
	TableLayout scrollablePart;
	View rowClicked;

	double sum = 0;
	private ArrayList<Integer> mData = new ArrayList<Integer>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check_out_main);

		// Each row in the list stores country name, currency and flag
		aList = new ArrayList<HashMap<String, String>>();

		firstList = (ListView) findViewById(R.id.firstList);

		new LoadSubscriptions().execute();

		autoComplete = (CustomAutoCompleteTextView) findViewById(R.id.autocomplete);
		txtAmount = (EditText) findViewById(R.id.txtAmount);

		inflater = LayoutInflater.from(this);
		final TableRow.LayoutParams wrapWrapTableRowParams = new TableRow.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		final int[] fixedColumnWidths = new int[] { 20, 20, 20, 20, 20 };
		final int[] scrollableColumnWidths = new int[] { 20, 20, 20, 30, 30 };
		final int fixedRowHeight = 50;
		final int fixedHeaderHeight = 60;

		btn_addToCart = (Button) findViewById(R.id.btn_addToCart);
		// tbl=(TableLayout) findViewById(R.id.contact_table);
		TableRow row = new TableRow(CheckOut.this);
		// header (fixed vertically)
		TableLayout header = (TableLayout) findViewById(R.id.table_header);
		row.setLayoutParams(wrapWrapTableRowParams);
		row.setGravity(Gravity.CENTER);
		row.setBackgroundColor(Color.GRAY);
		row.addView(makeTableRowWithText("Item", fixedColumnWidths[0],
				fixedHeaderHeight));
		row.addView(makeTableRowWithText("#", fixedColumnWidths[1],
				fixedHeaderHeight));
		row.addView(makeTableRowWithText("Price", fixedColumnWidths[2],
				fixedHeaderHeight));
		row.addView(makeTableRowWithText("+", fixedColumnWidths[3],
				fixedHeaderHeight));
		row.addView(makeTableRowWithText("-", fixedColumnWidths[4],
				fixedHeaderHeight));
		header.addView(row);
		btnMinus = new Button(CheckOut.this);
		btnMinus.setText("-");
		btnPlus = new Button(CheckOut.this);
		//btnPlus.setBackgroundDrawable(R.drawable.plus);
		//btn
		txtNet = (TextView) findViewById(R.id.netTotal);
		final Double val = (double) 200;

		btn_addToCart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(txtAmount.getText().toString().equals("")||autoComplete.getText().toString().equals(""))
				{
					
				}
				else
				{
				TableRow tr = new TableRow(CheckOut.this);
				tr.setLayoutParams(new TableRow.LayoutParams(
						TableRow.LayoutParams.FILL_PARENT,
						TableRow.LayoutParams.WRAP_CONTENT));
				/* Create a Button to be the row-content. */
				TextView name, number, price;
				TableRow row = new TableRow(CheckOut.this);

				// header (fixed horizontally)
				TableLayout fixedColumn = (TableLayout) findViewById(R.id.fixed_column);
				// rest of the table (within a scroll view)
				scrollablePart = (TableLayout) findViewById(R.id.scrollable_part);

				String item[] = autoComplete.getText().toString().split(";");
				String numb;
				if(txtAmount.getText().equals(""))
					numb="1";
				else
				 numb = txtAmount.getText().toString();
				
				int p=R.drawable.plus;
				int m=R.drawable.minus;

				row = new TableRow(CheckOut.this);
				row.setLayoutParams(wrapWrapTableRowParams);
				row.setGravity(Gravity.CENTER);
				row.setBackgroundColor(Color.WHITE);
				row.addView(makeTableRowWithText(item[0],
						scrollableColumnWidths[1], fixedRowHeight));
				row.addView(makeTableRowWithText(numb,
						scrollableColumnWidths[1], fixedRowHeight));
				row.addView(makeTableRowWithText(item[1],
						scrollableColumnWidths[1], fixedRowHeight));
				row.addView(makeButtonRowWithText(p,
						scrollableColumnWidths[1], fixedRowHeight,scrollablePart,row));
				row.addView(makeButtonRowWithText(m,
						scrollableColumnWidths[1], fixedRowHeight,scrollablePart,row));
			
				 //registerForContextMenu(row);
				mData.add(0);
				row.setOnLongClickListener(new View.OnLongClickListener() {
					
					@Override
					public boolean onLongClick(View v) {
						// TODO Auto-generated method stub
						registerForContextMenu(v);
			            openContextMenu(v);
			            rowClicked=v;
						return false;
					}
				});
				
				
				// for (int i = 0; i < scrollablePart.getChildCount(); i++) {
				// txtrow=(TextView) row.getChildAt(3);
				// double val=Double.parseDouble(txtrow.getText().toString());

				// sum += val;

				// Log.d("sum",String.valueOf(sum));

				// }

				scrollablePart.addView(row);
				txtNet.setText(String.valueOf(summation(scrollablePart)));
				}

			}
		});
		btnCheckOut = (Button) findViewById(R.id.btn_CheckOuts);

		btnCheckOut.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent(getApplicationContext(),
						CheckOutSale.class);
				in.putExtra("amount",
						Double.valueOf(txtNet.getText().toString()));
				startActivity(in);

			}
		});

	}

	public double summation(TableLayout tableLayout) {
		double s = 0;
		for (int rowPosition = 0; rowPosition < tableLayout.getChildCount(); rowPosition++) {
			TableRow row1 = (TableRow) tableLayout.getChildAt(rowPosition);
			TextView et = (TextView) row1.getChildAt(2);
			TextView t = (TextView) row1.getChildAt(1);
			Double etr=Double.parseDouble(et.getText().toString());
			Double tr=Double
					.parseDouble(t.getText().toString());
			if(etr.equals(""))
				etr=1.0;
			if(tr.equals(""))
			tr=1.0;
			s += (etr * tr);
		}
		return s;

	}

	private TextView recyclableTextView;
	private Button btnrecyclabale;

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
				TextView et = (TextView) r.getChildAt(2);
				double d=Double.parseDouble(et.getText().toString());
				if(img==R.drawable.plus)
				d+=5;
				else
					d-=5;
				et.setText(String.valueOf(d));
				
				//double ds=summation(tbl);
				txtNet.setText(String.valueOf(summation(tbl)));
				
			}
		});
		return btnrecyclabale;
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateContextMenu(android.view.ContextMenu, android.view.View, android.view.ContextMenu.ContextMenuInfo)
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onContextItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delete:
               // db.deleteTask(info.id);
            	scrollablePart.removeView(rowClicked);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
	}

	/**
	 * A callback method, which is executed when this activity is about to be
	 * killed This is used to save the current state of the activity ( eg :
	 * Configuration changes : portrait -> landscape )
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TextView tvCurrency = (TextView) findViewById(R.id.tv_currency);
		// outState.putString("currency", tvCurrency.getText().toString());
		super.onSaveInstanceState(outState);
	}

	/**
	 * A callback method, which is executed when the activity is recreated ( eg
	 * : Configuration changes : portrait -> landscape )
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TextView tvCurrency = (TextView) findViewById(R.id.tv_currency);
		// tvCurrency.setText(savedInstanceState.getString("currency"));
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	class LoadSubscriptions extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(CheckOut.this);
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

						String name = c.getString(TAG_NAME) + ";"
								+ c.getString(TAG_PRICE);
						String id = c.getString(TAG_ID);
						String names = c.getString(TAG_NAME);

						String description = c.getString(TAG_DESCRIPTION);
						String encounter = c.getString(TAG_ENCOUNTER_NUMBER);
						String restock = c.getString(TAG_RESTOCK);
						String today = c.getString(TAG_TODAY);
						String price = c.getString(TAG_PRICE);

						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value

						map.put(TAG_NAMES, name);
						map.put(TAG_NAME, names);
						map.put(TAG_ID, id);
						
						map.put(TAG_DESCRIPTION, description);
						map.put(TAG_ENCOUNTER_NUMBER, encounter);
						map.put(TAG_RESTOCK, restock);
						map.put(TAG_TODAY, today);
						map.put(TAG_PRICE, price);

						aList.add(map);
						// medicinesList.add(map);

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
					SimpleAdapter adapter = new SimpleAdapter(getBaseContext(),
							aList, R.layout.layout_list, froms, to);
					autoComplete.setAdapter(adapter);

					SimpleAdapter myAdapter = new SimpleAdapter(
							getBaseContext(), aList, R.layout.layout_list,
							from, to);

					firstList.setAdapter(myAdapter);

				}
			});

		}

	}

}
