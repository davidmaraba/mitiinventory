package com.mitihealth;


import com.example.mitiinventory.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DeleteMain extends Activity{

	Button btnDeleteSupplies, btnDeleteMedicines;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deletes_main);
		
		btnDeleteMedicines=(Button) findViewById(R.id.btn_delete_medicine);
		btnDeleteSupplies=(Button) findViewById(R.id.btn_delete_supplies);
		
		btnDeleteSupplies.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), DeleteSupplies.class));
				
			}
		});
	}

}
