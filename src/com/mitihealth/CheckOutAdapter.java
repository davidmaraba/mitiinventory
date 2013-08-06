package com.mitihealth;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import com.example.mitiinventory.R;


public class CheckOutAdapter extends BaseAdapter implements Filterable {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private ArrayList<HashMap<String, String>> Newdata;
	private static LayoutInflater inflater = null;
	public static String[] selects;//=new String[100];
	private Context context;
	
	public CheckOutAdapter(Activity a,
			ArrayList<HashMap<String, String>> d, Context context) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Newdata = d;
		this.context = context;
		//selects=new String[100];
		
	}

	public CheckOutAdapter(Context context) {
		this.context = context;
		//selects=new String[100];
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
			vi = inflater.inflate(R.layout.layout_list, null);

		
		TextView name = (TextView) vi.findViewById(R.id.name); // supplies name
		TextView id=(TextView) vi.findViewById(R.id.supplies_id);
		TextView restock=(TextView) vi.findViewById(R.id.supplies_restock);
		TextView description=(TextView) vi.findViewById(R.id.supplies_decription);
		TextView encounter=(TextView) vi.findViewById(R.id.supplies_encounter);
		
		//final CheckBox chk=(CheckBox) convertView.findViewById(R.id.checkBoxChild);
	

		HashMap<String, String> supply = new HashMap<String, String>();
		supply = Newdata.get(position);
		
		
		final String medName=supply.get(ViewMedicines.TAG_NAME);
		String medId=supply.get(ViewMedicines.TAG_ID);
		String supplyDescription=supply.get(ViewMedicines.TAG_DESCRIPTION);
		String supplyEncounter=supply.get(ViewMedicines.TAG_ENCOUNTER_NUMBER);
		String resockSupply=supply.get(ViewMedicines.TAG_RESTOCK);
		String todaySupply=supply.get(ViewMedicines.TAG_TODAY);
		
		final int m=Integer.parseInt(medId);
		
	/*chk.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(chk.isChecked())
				{
					selects[m]=medName;
				}
				else if(!chk.isChecked())
				{
					//selects=selects.replace(nms,"");
				}
			}
		});*/
		
		name.setText(medName);
		id.setText(medId);
		restock.setText(resockSupply);
		description.setText(supplyDescription);
		encounter.setText(supplyEncounter);
		

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
