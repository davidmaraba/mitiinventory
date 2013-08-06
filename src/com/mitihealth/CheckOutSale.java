package com.mitihealth;

import com.example.mitiinventory.R;

import android.app.Activity;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CheckOutSale extends Activity {
	TextView txtAmount, txtReturn,txtCreditAmount;
	EditText txtAmountGiven;
	Button btnMakeSales;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check_out_sale);

		Intent intents = getIntent();
		final double amountDue = intents.getDoubleExtra("amount", 0);

		String amount = String.valueOf(amountDue);
		txtReturn = (TextView) findViewById(R.id.txtReturnAmount);
		txtAmount = (TextView) findViewById(R.id.txtAmountDue);
		txtCreditAmount=(TextView) findViewById(R.id.txtCreditAmount);

		txtAmount.setText(amount);
		txtAmountGiven = (EditText) findViewById(R.id.txtAmountGiven);

		txtAmountGiven.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				txtReturn.setText(calculateBalance());

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				

			}
		});
		btnMakeSales=(Button) findViewById(R.id.btnMakeSale);
		
		btnMakeSales.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			double bal=Double.parseDouble(calculateBalance());
				
				if(bal<0)
				{
					View cashSalesLayout=findViewById(R.id.cashSalesLayout);
					View creditSalesLayout=findViewById(R.id.creditSales);
					cashSalesLayout.setVisibility(View.GONE);
					
					txtCreditAmount.setText(calculateBalance());
					creditSalesLayout.setVisibility(View.VISIBLE);
					
				}
				else
					startActivity(new Intent(getApplicationContext(), MainActivity.class));
				
			}
		});
		

	}
	public String calculateBalance()
	{
		double netAmountGiven ;
		Intent intents = getIntent();
		final double amountDue = intents.getDoubleExtra("amount", 0);
		
		if(txtAmountGiven.getText().toString()!="" &&txtAmountGiven.getText().length()>0 )
		{
			netAmountGiven = Double.parseDouble(txtAmountGiven
					.getText().toString());
			double balance=netAmountGiven - amountDue;
			return String.valueOf(balance);
		}
		else
			return "-"+amountDue;
			
		
	}

}
