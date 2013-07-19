package com.mitiinventory;

import com.example.mitiinventory.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewMain extends Activity{

	Button btn_view_supplies,btn_view_medicines;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.views_main);
		
		btn_view_supplies=(Button) findViewById(R.id.btn_view_supplies);
		btn_view_medicines=(Button) findViewById(R.id.btn_view_medicine);
		
		btn_view_medicines.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), ViewMedicines.class));
			}
		});
		btn_view_supplies.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), ViewSuppliers.class));
			}
		});
		
	}

}
