package hu.tvarga.recyclerviewtest;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class MySimpleArrayAdapter extends ArrayAdapter<POJOForList> {
	private Context context;
	private int resource;

	public MySimpleArrayAdapter(Context context, int resource, List<POJOForList> objects) {
		super(context, resource, objects);
		this.context = context;
		this.resource = resource;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final POJOForList pojo = getItem(position);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(resource, parent, false);
		TextView tv = (TextView) rowView.findViewById(R.id.list_item_tv_label);
		EditText et = (EditText) rowView.findViewById(R.id.list_item_et_input);
		tv.setText(pojo.getLabel());
		et.setText(pojo.getValue() + "");

		et.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
					pojo.setValue(Integer.valueOf(((EditText) v).getText().toString()));
					return true;
				}
				return false;

			}
		});

		return rowView;
	}
}
