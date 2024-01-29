package org.pytorch.demo.objectdetection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class Recycler extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler);


        myRecyclerViewAdapter adapter;
        RecyclerView recyclerView;
        ArrayList<DataModel> dataModels = new ArrayList();
//        for (int i = 0; i<20; i++) {
//            dataModels.add("TEST DATA" + i);
//        }
        dataModels.add(new DataModel("pig1", R.drawable.pig_icon));
        dataModels.add(new DataModel("pig2", R.drawable.pig_icon));

        recyclerView = findViewById(R.id.recyclerview);
        adapter = new myRecyclerViewAdapter(this, dataModels);
        //===== [Click 이벤트 구현을 위해 추가된 코드] ==============
        adapter.setOnItemClickListener(new myRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position, String data) {

                Toast.makeText(getApplicationContext(), "Position:" + position + ", Data:" + data, Toast.LENGTH_SHORT).show();
            }
        });
        //==========================================================



        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));


    }
}
