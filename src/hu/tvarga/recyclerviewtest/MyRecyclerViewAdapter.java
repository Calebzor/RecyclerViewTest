package hu.tvarga.recyclerviewtest;

import java.util.ArrayList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.DataObjectHolder> {
	private static Integer focusedPosition;
	private ArrayList<POJOForList> dataset;
	protected final Button bottomButton;

	public MyRecyclerViewAdapter(ArrayList<POJOForList> myDataset, Button bottomButton) {
		dataset = myDataset;
		this.bottomButton = bottomButton;
	}

	@Override
	public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

		return new DataObjectHolder(view, new MyCustomOnKeyListener(), new MyCustomOnFocusChangeListener());
	}

	@Override
	public void onViewDetachedFromWindow(DataObjectHolder holder) {
		int recycledPosition = holder.getLayoutPosition();
		if (isOldFocusedPositionGettingRecycled(recycledPosition)) {
			hideSoftKeyboard(holder);
		}
		super.onViewDetachedFromWindow(holder);
	}

	private boolean isOldFocusedPositionGettingRecycled(int recycledPosition) {
		return focusedPosition != null && recycledPosition == focusedPosition;
	}

	private void hideSoftKeyboard(DataObjectHolder holder) {
		View itemView = holder.itemView;
		InputMethodManager imm = (InputMethodManager) itemView.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(itemView.getWindowToken(), 0);
	}

	@Override
	public void onBindViewHolder(DataObjectHolder holder, int position) {
		holder.label.setText(dataset.get(position).getLabel());
		holder.et.setText(String.format("%d", dataset.get(position).getValue()));
		holder.myCustomOnKeyListener.updatePosition(position);
		holder.myCustomOnFocusChangeListener.updatePosition(position);
	}

	@Override
	public int getItemCount() {
		return dataset.size();
	}

	public static class DataObjectHolder extends RecyclerView.ViewHolder {
		TextView label;
		EditText et;
		MyCustomOnKeyListener myCustomOnKeyListener;
		MyCustomOnFocusChangeListener myCustomOnFocusChangeListener;

		public DataObjectHolder(View itemView, MyCustomOnKeyListener myCustomOnKeyListener,
				MyCustomOnFocusChangeListener myCustomOnFocusChangeListener) {
			super(itemView);
			initUIRefs(itemView);
			this.myCustomOnKeyListener = myCustomOnKeyListener;
			this.myCustomOnFocusChangeListener = myCustomOnFocusChangeListener;
			setListeners();
		}

		private void setListeners() {
			et.setOnKeyListener(myCustomOnKeyListener);
			et.setOnFocusChangeListener(myCustomOnFocusChangeListener);
		}

		private void initUIRefs(View itemView) {
			label = (TextView) itemView.findViewById(R.id.list_item_tv_label);
			et = (EditText) itemView.findViewById(R.id.list_item_et_input);
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
				String typedInValue = et.getText().toString();
				Integer valueToBeStored = 0;
				if (typedInValue.equals("")) {
					et.setText("0");
				} else {
					valueToBeStored = Integer.valueOf(typedInValue);
				}
				dataset.get(position).setValue(valueToBeStored);
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
				bottomButton.setVisibility(View.GONE);
			} else {
				bottomButton.setVisibility(View.VISIBLE);
				EditText et = (EditText) v;
				et.setText(String.valueOf(dataset.get(position).getValue()));
			}
		}
	}

}