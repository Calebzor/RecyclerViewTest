package hu.tvarga.recyclerviewtest;

import java.util.ArrayList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.DataObjectHolder> {
	private static Integer focusedPosition;
	private ArrayList<POJOForList> mDataset;

	public MyRecyclerViewAdapter(ArrayList<POJOForList> myDataset) {
		mDataset = myDataset;
	}

	@Override
	public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

		return new DataObjectHolder(view, new MyCustomOnKeyListener(), new MyCustomOnFocusChangeListener());
	}

	@Override
	public void onViewDetachedFromWindow(DataObjectHolder holder) {
		int recycledPosition = holder.getLayoutPosition();
		if (focusedPosition != null && recycledPosition == focusedPosition) {
			View itemView = holder.itemView;
			InputMethodManager imm = (InputMethodManager) itemView.getContext()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(itemView.getWindowToken(), 0);
		}
		super.onViewDetachedFromWindow(holder);
	}

	@Override
	public void onBindViewHolder(DataObjectHolder holder, int position) {
		holder.label.setText(mDataset.get(position).getLabel());
		holder.et.setText(String.format("%d", mDataset.get(position).getValue()));
		holder.myCustomOnKeyListener.updatePosition(position);
		holder.myCustomOnFocusChangeListener.updatePosition(position);

		// Log.d("hu.tvarga.debug", "pos: " + position + " last focused: " +
		// focusedPosition);
		// RecyclerView.ViewHolder focusedViewHolder =
		// mRecyclerView.findViewHolderForLayoutPosition(focusedPosition);
		// if (focusedViewHolder == null) {
		// InputMethodManager imm = (InputMethodManager)
		// mRecyclerView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		// imm.hideSoftInputFromWindow(mRecyclerView.getWindowToken(), 0);
		// }
	}

	@Override
	public int getItemCount() {
		return mDataset.size();
	}

	public static class DataObjectHolder extends RecyclerView.ViewHolder {
		TextView label;
		EditText et;
		MyCustomOnKeyListener myCustomOnKeyListener;
		MyCustomOnFocusChangeListener myCustomOnFocusChangeListener;

		public DataObjectHolder(View itemView, MyCustomOnKeyListener myCustomOnKeyListener,
				MyCustomOnFocusChangeListener myCustomOnFocusChangeListener) {
			super(itemView);
			label = (TextView) itemView.findViewById(R.id.list_item_tv_label);
			et = (EditText) itemView.findViewById(R.id.list_item_et_input);
			this.myCustomOnKeyListener = myCustomOnKeyListener;
			this.myCustomOnFocusChangeListener = myCustomOnFocusChangeListener;
			et.setOnKeyListener(myCustomOnKeyListener);
			et.setOnFocusChangeListener(myCustomOnFocusChangeListener);
		}

	}

	private class MyCustomOnKeyListener implements View.OnKeyListener {
		private int position;

		public void updatePosition(int position) {
			this.position = position;
		}

		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
				EditText et = (EditText) v;
				String value = et.getText().toString();
				Integer valueToBeStored = 0;
				if (value.equals("")) {
					et.setText("0");
				} else {
					valueToBeStored = Integer.valueOf(value);
				}
				mDataset.get(position).setValue(valueToBeStored);
				return true;
			}
			return false;
		}
	}

	private class MyCustomOnFocusChangeListener implements View.OnFocusChangeListener {
		private int position;

		public void updatePosition(int position) {
			this.position = position;
		}

		@Override
		public void onFocusChange(View v, boolean hasFocus) {

			if (hasFocus) {
				focusedPosition = position;
			} else {
				EditText et = (EditText) v;
				et.setText(String.valueOf(mDataset.get(position).getValue()));
			}
		}
	}

}