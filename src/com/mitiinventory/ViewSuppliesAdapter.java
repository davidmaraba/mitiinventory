package com.mitiinventory;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.mitiinventory.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class ViewSuppliesAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	
	private Context context;
	
	public ViewSuppliesAdapter(Activity a,
			ArrayList<HashMap<String, String>> d, Context context) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		this.context = context;
		
	}

	public ViewSuppliesAdapter(Context context) {
		this.context = context;
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.layout_list, null);

		
		TextView name = (TextView) vi.findViewById(R.id.supplies_name); // supplies name
		TextView id=(TextView) vi.findViewById(R.id.supplies_id);
		TextView restock=(TextView) vi.findViewById(R.id.supplies_restock);
		TextView description=(TextView) vi.findViewById(R.id.supplies_decription);
		TextView encounter=(TextView) vi.findViewById(R.id.supplies_encounter);

		


		HashMap<String, String> supply = new HashMap<String, String>();
		supply = data.get(position);
		
		
		String supplyName=supply.get(ViewSuppliers.TAG_NAME);
		String supplyId=supply.get(ViewSuppliers.TAG_ID);
		String supplyDescription=supply.get(ViewSuppliers.TAG_DESCRIPTION);
		String supplyEncounter=supply.get(ViewSuppliers.TAG_ENCOUNTER_NUMBER);
		String resockSupply=supply.get(ViewSuppliers.TAG_RESTOCK);
		String todaySupply=supply.get(ViewSuppliers.TAG_TODAY);
		
		name.setText(supplyName);
		id.setText(supplyId);
		restock.setText(resockSupply);
		description.setText(supplyDescription);
		encounter.setText(supplyEncounter);
		
		

		return vi;
	}
}
