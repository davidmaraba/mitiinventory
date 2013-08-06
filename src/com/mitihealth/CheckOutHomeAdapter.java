package com.mitihealth;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.mitiinventory.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

public class CheckOutHomeAdapter extends BaseAdapter implements Filterable {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private ArrayList<HashMap<String, String>> Newdata;
	private static LayoutInflater inflater = null;
	// public static String[] selects=new String[100];
	public static String selects;// ="my";
	private Context context;
   public static String supplyName;

	public CheckOutHomeAdapter(Activity a,
			ArrayList<HashMap<String, String>> d, Context context) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Newdata = d;
		this.context = context;

	}

	public CheckOutHomeAdapter(Context context) {
		this.context = context;
	}

	public int getCount() {
		return Newdata.size();
		// return data.size();
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

		TextView name = (TextView) vi.findViewById(R.id.name); // supplies
																		// name
		TextView id = (TextView) vi.findViewById(R.id.supplies_id);
		TextView restock = (TextView) vi.findViewById(R.id.supplies_restock);
		TextView description = (TextView) vi
				.findViewById(R.id.supplies_decription);
		TextView encounter = (TextView) vi
				.findViewById(R.id.supplies_encounter);
		//EditText amount = (EditText) vi.findViewById(R.id.txtAmount);

		//final CheckBox chk = (CheckBox) vi.findViewById(R.id.checkBoxChild);

		HashMap<String, String> supply = new HashMap<String, String>();
		supply = Newdata.get(position);
		// supply = data.get(position);

		supplyName = supply.get(ViewSuppliers.TAG_NAME).trim();
		final String supplyId = supply.get(ViewSuppliers.TAG_ID);
		String supplyDescription = supply.get(ViewSuppliers.TAG_DESCRIPTION);
		String supplyEncounter = supply.get(ViewSuppliers.TAG_ENCOUNTER_NUMBER);
		String resockSupply = supply.get(ViewSuppliers.TAG_RESTOCK);
		String todaySupply = supply.get(ViewSuppliers.TAG_TODAY);

		//final int amounts = Integer.parseInt(amount.getText().toString());
		name.setText(supplyName);
		id.setText(supplyId);
		restock.setText(resockSupply);
		description.setText(supplyDescription);
		encounter.setText(supplyEncounter);
		final int m = Integer.parseInt(supplyId);

		/*chk.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (chk.isChecked()) {
					selects += supplyName + " " + amounts + " " + supplyId
							+ ",";
					// selects[m]=supplyName;
					// selects=selects+","+nms;
				} else if (!chk.isChecked()) {
					selects = selects.replace(supplyName, "");
				}
			}
		});*/

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
		//return results;
	}
}
