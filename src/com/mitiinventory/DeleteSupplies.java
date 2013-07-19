package com.mitiinventory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mitiinventory.R;

public class DeleteSupplies extends ListActivity{

	String pid=null;
	String deleteSuppliesURL="https://data.mitihealth.org/openmrs/deletesupply";
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		 AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		 
		 switch (item.getItemId()) {
		case R.id.delete:
			deletion(pid);
			break;

		default:
			break;
		}
		return super.onContextItemSelected(item);
	}

	private void deletion(String pids) {
		// TODO Auto-generated method stub
		Deletions(pids);
		
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		 MenuInflater inflater = getMenuInflater();
		    inflater.inflate(R.menu.context_menu, menu);
	}
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
		 
    public   DeleteSuppliesAdapter adapter;
    
    
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
     // Get listview
        ListView lv = getListView();
        lv.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View view) {
				// TODO Auto-generated method stub
				pid=((TextView) view.findViewById(R.id.supplies_id)).getText()
                .toString();
				registerForContextMenu( view );
                openContextMenu( view );  
                
                /* */
				return false;
			}
		});
        /*lv.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // TODO Auto-generated method stub

                  //registerForContextMenu( view );
                  openContextMenu( view );  
                  
                  pid = ((TextView) view.findViewById(R.id.supplies_id)).getText()
	                        .toString();
                  //view.setOnCreateContextMenuListener(this);

                return false;
            }
        });*/
        
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
            pDialog = new ProgressDialog(DeleteSupplies.this);
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
                   
                    adapter=new DeleteSuppliesAdapter(DeleteSupplies.this, suppliersList,getApplicationContext());        
                    
                    // updating listview
                    setListAdapter(adapter);
                   registerForContextMenu(getListView());
                }
            });
 
        }
 
    }
    
    public void Deletions(final String pids) {
    	
    	
    	Thread trd = new Thread(new Runnable() {

            private String result;
			

			@Override
            public void run() {
                // TODO Auto-generated method stub
                Looper.prepare();
                String url = deleteSuppliesURL.trim();

                Log.e("Deleting supplies",
                        "gone to connectin class with url "
                                + url);

                HttpResponse response = null;
                // code to do the HTTP request
                InputStream is = null;
                Log.d("res",
                        "in addListenerToButton func 5");
                // Creating HTTP client
                final HttpParams httpParams = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(
                        httpParams, 10000);
                Log.d("res",
                        "in addListenerToButton func 6");

                HttpClient httpClient = new DefaultHttpClient(
                        httpParams);
                Log.d("res",
                        "in addListenerToButton func 7");

                // Creating HTTP Post
                HttpPost httpPost = new HttpPost(url);
                //Log.e("Registration", "go on..........with vic as. " + vic);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                
             
    			Log.d("pid","pid "+pids);
    			
    			 params.add(new BasicNameValuePair("id",pids));
    			   
    			    
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(
                            params));
                } catch (UnsupportedEncodingException e) {
                    // writing error to Log
                    e.printStackTrace();
                }

                try {
                    response = httpClient.execute(httpPost);

                    HttpEntity entity = response
                            .getEntity();

                    is = entity.getContent();

                    // writing response to log
                    Log.d("res", "1 " + response.toString());

                } catch (ConnectTimeoutException ctEx) {

                    Toast.makeText(getApplicationContext(),
                            "Server not responding",
                            Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    // writing exception to log
                    Log.d("res", "2 " + e.toString());
                    // e.printStackTrace();
                }

                try {
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(is,
                                    "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    
                    result = sb.toString().trim();
                    pDialog.dismiss();
                    
                  
                   Log.d("res", "4" + result);
                    
                   
                   /* if(result.toLowerCase().contains(TAG_SUCCESS.toLowerCase()))
                    {
                    	  Toast.makeText(AddSupplies.this, "Supplies added to inventory successfully",
                                  Toast.LENGTH_LONG).show();
						Intent i=new Intent(getApplicationContext(),ViewSuppliers.class);
						
						//Close all views before launching DSashboard
						
						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(i);
						//close registration screen
						//alert.showAlertDialog(RegisterActivity.this,"Successfully ","Registered", false);
						finish();
                    }
                    else
                    	alert.showAlertDialog(AddSupplies.this,"Supply not added","The supply has not been added. Please re-try later", false);
                    
                    */
                   
                    // call the method that will return a
                    // value

                } catch (Exception e) {
                    Log.e("log_tag",
                            "Error converting result "
                                    + e.toString());
                }
                Looper.loop();

            }

        });
        trd.start();
        openDialog();
 
    }
    public void openDialog() {
        // Open the dialog
        pDialog = new ProgressDialog(DeleteSupplies.this);
        pDialog.setMessage(Html
                .fromHtml("<b>Please wait...</b><br/>Deleting Supply"));
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        closeHandler.sendEmptyMessageDelayed(0, 12000);
        // show toast

    }
    private Handler closeHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (pDialog != null)
                pDialog.dismiss();

        }
    };
    
}
