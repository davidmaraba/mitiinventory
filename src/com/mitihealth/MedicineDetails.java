package com.mitihealth;

import com.example.mitiinventory.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MedicineDetails extends Activity{

	TextView txtDescription,txtName,txtEncounter,txtRestock;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.medicines_details);
		
		txtName=(TextView) findViewById(R.id.medicineName);
		txtDescription=(TextView) findViewById(R.id.description);
		txtEncounter=(TextView) findViewById(R.id.encounter_number);
		txtRestock=(TextView) findViewById(R.id.restockValue);
		
		  Intent i = getIntent();
		  
	        String name = i.getStringExtra("name");
	        String description=i.getStringExtra("description");
	        String restock=i.getStringExtra("restock");
	        String encounter=i.getStringExtra("encounter");
	       
	        txtName.setText(name);
	        txtDescription.setText(description);
	        txtEncounter.setText(encounter);
	        txtRestock.setText(restock);
	}

}
