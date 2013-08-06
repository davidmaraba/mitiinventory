package com.mitihealth;


import com.example.mitiinventory.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddMain extends Activity{

	Button btnAddMedicines,btnAddSupplies;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_main);
		
		btnAddSupplies=(Button) findViewById(R.id.btn_add_supplies);
		btnAddMedicines=(Button) findViewById(R.id.btn_add_medicine);
		
		btnAddSupplies.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), AddSupplies.class));
				
			}
		});
		btnAddMedicines.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),AddMedicines.class));
				
			}
		});
	}

}
