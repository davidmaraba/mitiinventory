package com.mitiinventory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.mitiinventory.R;

public class ViewSuppliers extends ListActivity{

	
	private ProgressDialog pDialog;
	
	// suppliers JSONArray
	JSONArray suppliers = null;

	// Inbox JSON url
		
	// JSON Node names
	public static final String TAG_ID = "id";
	public static final String TAG_NAME= "name";
	public static final String TAG_DESCRIPTION = "description";
	public static final String TAG_ENCOUNTER_NUMBER = "encounter_number";
	public static final String TAG_RESTOCK = "restock";
	public static final String TAG_TODAY = "today";

	ArrayList<HashMap<String, String>> suppliersList = new ArrayList<HashMap<String, String>>();
		 
    public   ViewSuppliesAdapter adapter;
    
    
    @Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_data);
		
		
		
        new LoadSubscriptions().execute();
        	
        ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
				String description=((TextView) view.findViewById(R.id.supplies_decription)).getText()
                        .toString();
				String encounter=((TextView) view.findViewById(R.id.supplies_encounter)).getText()
                        .toString();
				String restock=((TextView) view.findViewById(R.id.supplies_restock)).getText()
                        .toString();
				String name=((TextView) view.findViewById(R.id.supplies_name)).getText()
                        .toString();
				 Intent in = new Intent(getApplicationContext(),
	                        MedicineDetails.class);
				 in.putExtra("description",description);
				 in.putExtra("encounter",encounter);
				 in.putExtra("restock",restock);
				 in.putExtra("name",name);
				 startActivity(in);
			}
		});
	}
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if result code 100
        if (resultCode == 100) {
            // if result code 100 is received
            // means user edited/deleted product
            // reload this screen again
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
 
    }
    class LoadSubscriptions extends AsyncTask<String, String, String> {
    	 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ViewSuppliers.this);
            pDialog.setMessage("Loading Supplies. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
 
        /**
         * getting All supplies from url
         * */
        protected String doInBackground(String... args) {
        
try{
				
				URL get_supplies_url= new URL("https://data.mitihealth.org/openmrs/getsupplies");
		        URLConnection tc = get_supplies_url.openConnection();
		        BufferedReader in = new BufferedReader(new InputStreamReader(
		                tc.getInputStream()));
		        String line;
		        
		        String home=in.toString();
		        Log.d("JSON",home);
		        while ((line = in.readLine()) != null) {
		        	//line = in.readLine();
		        	suppliers = new JSONArray(line);
		            for (int i = 0; i < suppliers.length(); i++) {
		                JSONObject c = (JSONObject) suppliers.get(i);
						
						// Storing each json item in variable
						String id = c.getString(TAG_ID);
						
						String name=c.getString(TAG_NAME);
						
						String description = c.getString(TAG_DESCRIPTION);
						String encounter=c.getString(TAG_ENCOUNTER_NUMBER);
						String restock=c.getString(TAG_RESTOCK);
						String today=c.getString(TAG_TODAY);
						
						
						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();
                    

						// adding each child node to HashMap key => value
						map.put(TAG_ID, id);
						map.put(TAG_NAME,name);
						map.put(TAG_DESCRIPTION,description);
						map.put(TAG_ENCOUNTER_NUMBER,encounter);
						map.put(TAG_RESTOCK, restock);
						map.put(TAG_TODAY, today);
						
				suppliersList.add(map);
						
						 Log.d("JSON",line);
					}
		           
		        }
		        Log.d("check",line);
            } catch (JSONException e) {
                e.printStackTrace();
            }
catch (Exception e) {
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
                	for(int i=0;i<=suppliersList.size();i++)
                	{
                		LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                				LinearLayout.LayoutParams.WRAP_CONTENT);
                
                	}
                   
                    adapter=new ViewSuppliesAdapter(ViewSuppliers.this, suppliersList,getApplicationContext());        
                    
                    // updating listview
                    setListAdapter(adapter);
                }
            });
 
        }
 
    }
    
    
}
