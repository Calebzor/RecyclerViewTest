package hu.tvarga.recyclerviewtest;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ListActivity extends Activity {

	private ArrayList<POJOForList> list;
	private Button bottomButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		list = new ArrayList<POJOForList>();
		addNumberOfElementsToList(30);



		bottomButton = (Button) findViewById(R.id.activity_list_btn_bottom_button);
		setBottomButtonOnClickListener();

		setUpRecyclerView();

	}

	private void setBottomButtonOnClickListener() {
		bottomButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(v.getContext(), "Clicked", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void setUpRecyclerView() {
		RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
		mRecyclerView.setHasFixedSize(true);
		RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(mLayoutManager);
		RecyclerView.Adapter<?> mAdapter = new MyRecyclerViewAdapter(list, bottomButton);
		mRecyclerView.setAdapter(mAdapter);
	}

	private void addNumberOfElementsToList(int elementsToCreate) {
		for (int i = 1; i <= elementsToCreate; i++) {
			POJOForList p = new POJOForList();
			p.setLabel("p" + i);
			p.setValue(i);
			list.add(p);
		}
	}

}
