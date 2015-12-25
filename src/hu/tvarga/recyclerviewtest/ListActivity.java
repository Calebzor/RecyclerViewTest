package hu.tvarga.recyclerviewtest;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ListActivity extends Activity {

    private ArrayList<POJOForList> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        list = new ArrayList<>();
        addNumberOfElementsToList(30);


        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter mAdapter = new MyRecyclerViewAdapter(list);
        mRecyclerView.setAdapter(mAdapter);


        Button bottomButton = (Button) findViewById(R.id.activity_list_btn_bottom_button);
        bottomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("hu.tvarga.debug", "clicked");
//                InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });


//
//        ListView lv = (ListView) findViewById(R.id.activity_list_lv_list_view);
//        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, R.layout.list_item, list);
//        lv.setAdapter(adapter);
//
//        View rootView = findViewById(R.id.activity_list_root);
//        View focusedView = ((ViewGroup) rootView).getFocusedChild();
//        rootView.clearFocus();
//		TextView tv = (TextView) findViewById(R.id.activity_list_rl_header_text);
//		tv.requestFocus();
//		tv.requestFocusFromTouch();
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
