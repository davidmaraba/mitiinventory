package com.mitihealth;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import com.example.mitiinventory.R;


public class InventoryMainAdapter extends BaseAdapter implements Filterable {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	
	private Context context;
	private Button btnPlus;
	private EditText txtSearch;
	private ArrayList<HashMap<String, String>> Newdata;
	public InventoryMainAdapter(Activity a,
			ArrayList<HashMap<String, String>> d, Context context) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Newdata = d;
		
		this.context = context;
		
	}

	public InventoryMainAdapter(Context context) {
		this.context = context;
	}

	public int getCount() {
		return Newdata.size();
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
			vi = inflater.inflate(R.layout.inventory_list, null);

		
		TextView name = (TextView) vi.findViewById(R.id.txtMedName); // supplies name
		TextView id=(TextView) vi.findViewById(R.id.inId);
		TextView restock=(TextView) vi.findViewById(R.id.txtRemAmount);
		//TextView description=(TextView) vi.findViewById(R.id.supplies_decription);
		//TextView encounter=(TextView) vi.findViewById(R.id.supplies_encounter);
		btnPlus=(Button) vi.findViewById(R.id.btnPlus);


		HashMap<String, String> supply = new HashMap<String, String>();
		supply = Newdata.get(position);
		
		
		String medName=supply.get(InventoryMain.TAG_NAME);
		String medId=supply.get(InventoryMain.TAG_ID);
		//String supplyDescription=supply.get(ViewMedicines.TAG_DESCRIPTION);
		//String supplyEncounter=supply.get(ViewMedicines.TAG_ENCOUNTER_NUMBER);
		//String resockSupply=supply.get(ViewMedicines.TAG_RESTOCK);
		//String todaySupply=supply.get(ViewMedicines.TAG_TODAY);
		String rem=supply.get(InventoryMain.TAG_QUANTITY);
		String reorder=supply.get(InventoryMain.TAG_RESTOCK);
		
		name.setText(medName);
		id.setText(medId);
		restock.setText(rem);
		//description.setText(supplyDescription);
		//encounter.setText(supplyEncounter);
		if(Double.parseDouble(rem)<Double.parseDouble(reorder)&&!medName.equals("Panadol"))
		{
			vi.setBackgroundColor(0xffff0000);
		}
		btnPlus.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//startActivity(new Intent(,InventoryMain.class));
				//Intent intent=new Intent(context,AddItems.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			   // context.startActivity(intent);

			}
		});

		return vi;
	}

	@Override
	public Filter getFilter() {
		return new Filter() {
			@Override
			protected FilterResults performFiltering(CharSequence charSequence) {
				FilterResults results = new FilterResults();

				// If there's nothing to filter on, return the original data for
				// your list
				if (charSequence == null || charSequence.length() == 0) {
					results.values = data;
					results.count = data.size();
				} else {
					ArrayList<HashMap<String, String>> filterResultsData = new ArrayList<HashMap<String, String>>();

					for (HashMap<String, String> datas : data) {
						// In this loop, you'll filter through originalData and
						// compare each item to charSequence.
						// If you find a match, add it to your new ArrayList
						// I'm not sure how you're going to do comparison, so
						// you'll need to fill out this conditional
						if (datas
								.toString()
								.trim()
								.toLowerCase()
								.contains(
										charSequence.toString().toLowerCase()
												.trim())) {
							filterResultsData.add(datas);
						}
					}

					results.values = filterResultsData;
					results.count = Newdata.size();
				}

				return results;
			}

			@Override
			protected void publishResults(CharSequence charSequence,
					FilterResults filterResults) {
				Newdata = (ArrayList<HashMap<String, String>>) filterResults.values;
				notifyDataSetChanged();
			}
		};
	}
}
