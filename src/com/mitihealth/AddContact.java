package com.mitihealth;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class AddContact extends Activity{

	Button btnAddSupply;
	EditText txtSupplyName,txtLocation,txtMobile,txtEmail,txtSupplierId;
	CheckBox chkDeliversToFacility,chkGivesCredit;
	ProgressDialog pDialog;
	private static String addSuppliesURL="https://data.mitihealth.org/openmrs/addsupplier";
	public static final String  TAG_SUCCESS="Post Success";
	AlertDialogManager alert;
	private JSONParser jsonParser;
	
	JSONArray suppliers = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_contact);
		
		jsonParser=new JSONParser();
		alert=new AlertDialogManager();
		btnAddSupply=(Button) findViewById(R.id.btnAdd);
		txtSupplyName=(EditText) findViewById(R.id.supplierName);
		txtLocation=(EditText) findViewById(R.id.txtLocation);
		txtMobile=(EditText) findViewById(R.id.txtMobile);
		txtEmail=(EditText) findViewById(R.id.txtEmail);
		
		chkDeliversToFacility=(CheckBox) findViewById(R.id.chkDelivers);
		chkGivesCredit=(CheckBox) findViewById(R.id.chkCredit);
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

                        Log.e("Adding supplier",
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
            			String mobile=txtMobile.getText().toString().trim();
            			String email=txtEmail.getText().toString().trim();
            			String location=txtLocation.getText().toString().trim();
            			int credit=0,deliver=0;
            			if(chkDeliversToFacility.isChecked())
            				deliver=1;
            			if(chkGivesCredit.isChecked())
            				credit=1;
            			
            			 params.add(new BasicNameValuePair("supplier_name", supplyName));
            			    params.add(new BasicNameValuePair("supplier_phone_number", mobile));
            			    params.add(new BasicNameValuePair("supplier_email",email));
            			   params.add(new BasicNameValuePair("supplier_delivers_to_facility", String.valueOf(deliver)));
            			   params.add(new BasicNameValuePair("supplier_gives_credit", String.valueOf(credit)));
            			    params.add(new BasicNameValuePair("supplier_location", location));

            			   // params.add(new BasicNameValuePair(" supply_today", ""));
            			    
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
                            	  Toast.makeText(AddContact.this, "Contact details added successfully",
                                          Toast.LENGTH_LONG).show();
        						Intent i=new Intent(getApplicationContext(),SupplierManagement.class);
        						
        						//Close all views before launching DSashboard
        						
        						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        						startActivity(i);
        						//close registration screen
        						//alert.showAlertDialog(RegisterActivity.this,"Successfully ","Registered", false);
        						finish();
                            }
                            else
                            	alert.showAlertDialog(AddContact.this,"Supply not added","The supply has not been added. Please re-try later", false);
                            
                            
                           
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
        pDialog = new ProgressDialog(AddContact.this);
        pDialog.setMessage(Html
                .fromHtml("<b>Please wait...</b><br/>Adding details"));
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        closeHandler.sendEmptyMessageDelayed(0, 12000);
        // show toast

    }

}
