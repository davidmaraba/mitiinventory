package com.mitiinventory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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

import com.example.mitiinventory.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddSupplies extends Activity{

	Button btnAddSupply;
	EditText txtSupplyName,txtDescription,txtEncounter,txtRestock;
	ProgressDialog pDialog;
	private static String addSuppliesURL="https://data.mitihealth.org/openmrs/addsupply";
	public static final String  TAG_SUCCESS="Post Success";
	AlertDialogManager alert;
	private JSONParser jsonParser;
	
	JSONArray suppliers = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_supplies);
		
		jsonParser=new JSONParser();
		alert=new AlertDialogManager();
		btnAddSupply=(Button) findViewById(R.id.btn_add_supply);
		txtSupplyName=(EditText) findViewById(R.id.txt_supply_name);
		txtDescription=(EditText) findViewById(R.id.txt_supply_description);
		txtEncounter=(EditText) findViewById(R.id.txt_supply_encounter);
		txtRestock=(EditText) findViewById(R.id.txt_supply_restock);
		btnAddSupply.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 // servercode comes here
                Thread trd = new Thread(new Runnable() {

                    private String result;
					

					@Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Looper.prepare();
                        String url = addSuppliesURL;

                        Log.e("Adding supplies",
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
                        
                      
                        String supplyName=txtSupplyName.getText().toString().trim();
            			String description=txtDescription.getText().toString().trim();
            			String encounter=txtEncounter.getText().toString().trim();
            			String restock=txtRestock.getText().toString().trim();
            			
            			
            			 params.add(new BasicNameValuePair("supply_name", supplyName));
            			    params.add(new BasicNameValuePair("supply_desc", description));
            			    params.add(new BasicNameValuePair("supply_encounter_num", encounter));
            			    params.add(new BasicNameValuePair("supply_restock", restock));
            			    params.add(new BasicNameValuePair(" supply_today", ""));
            			    
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
                            
                           
                            if(result.toLowerCase().contains(TAG_SUCCESS.toLowerCase()))
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
		});
	}
	private Handler closeHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (pDialog != null)
                pDialog.dismiss();

        }
    };

    public void openDialog() {
        // Open the dialog
        pDialog = new ProgressDialog(AddSupplies.this);
        pDialog.setMessage(Html
                .fromHtml("<b>Please wait...</b><br/>Adding Supplies"));
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        closeHandler.sendEmptyMessageDelayed(0, 12000);
        // show toast

    }

}
