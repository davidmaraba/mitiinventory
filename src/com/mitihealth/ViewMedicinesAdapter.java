package com.mitihealth;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mitiinventory.R;


public class ViewMedicinesAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	
	private Context context;
	
	public ViewMedicinesAdapter(Activity a,
			ArrayList<HashMap<String, String>> d, Context context) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		this.context = context;
		
	}

	public ViewMedicinesAdapter(Context context) {
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

		
		TextView name = (TextView) vi.findViewById(R.id.name); // supplies name
		TextView id=(TextView) vi.findViewById(R.id.supplies_id);
		TextView restock=(TextView) vi.findViewById(R.id.supplies_restock);
		TextView description=(TextView) vi.findViewById(R.id.supplies_decription);
		TextView encounter=(TextView) vi.findViewById(R.id.supplies_encounter);


		HashMap<String, String> supply = new HashMap<String, String>();
		supply = data.get(position);
		
		
		String medName=supply.get(ViewMedicines.TAG_NAME);
		String medId=supply.get(ViewMedicines.TAG_ID);
		String supplyDescription=supply.get(ViewMedicines.TAG_DESCRIPTION);
		String supplyEncounter=supply.get(ViewMedicines.TAG_ENCOUNTER_NUMBER);
		String resockSupply=supply.get(ViewMedicines.TAG_RESTOCK);
		String todaySupply=supply.get(ViewMedicines.TAG_TODAY);
		
		name.setText(medName);
		id.setText(medId);
		restock.setText(resockSupply);
		description.setText(supplyDescription);
		encounter.setText(supplyEncounter);
		

		return vi;
	}
}
