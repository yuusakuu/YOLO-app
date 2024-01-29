package org.pytorch.demo.objectdetection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class ABCDRecy extends AppCompatActivity {

    private Button Cons, Vow, ABCD;

    RecyclerVierAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abcdrecy);
        init();
        getData();

        final Button Cons = findViewById(R.id.word); // activity_main에서 liveButton을 가져옴
        Cons.setOnClickListener(new View.OnClickListener() {    // buttonLive를 누르면 실행되도록
            public void onClick(View v) {
                final Intent intent = new Intent(ABCDRecy.this, recy.class); // 버튼 누르면 objectdetection 시작
                startActivity(intent);

            }
        });

        final Button Vow = findViewById(R.id.sente); // activity_main에서 liveButton을 가져옴
        Vow.setOnClickListener(new View.OnClickListener() {    // buttonLive를 누르면 실행되도록
            public void onClick(View v) {
                final Intent intent = new Intent(ABCDRecy.this, VowRecy.class); // 버튼 누르면 objectdetection 시작
                startActivity(intent);

            }
        });

//        final Button ABCD = findViewById(R.id.abcd); // activity_main에서 liveButton을 가져옴
//        ABCD.setOnClickListener(new View.OnClickListener() {    // buttonLive를 누르면 실행되도록
//            public void onClick(View v) {
//                final Intent intent = new Intent(recy.this, ABCDRecy.class); // 버튼 누르면 objectdetection 시작
//                startActivity(intent);
//
//            }
//        });

        final Button Test = findViewById(R.id.test); // activity_main에서 liveButton을 가져옴
        Test.setOnClickListener(new View.OnClickListener() {    // buttonLive를 누르면 실행되도록
            public void onClick(View v) {
                final Intent intent = new Intent(ABCDRecy.this, TestActivity.class); // 버튼 누르면 objectdetection 시작
                startActivity(intent);
            }
        });

        final Button buttonLive5 = findViewById(R.id.liveButton5); // activity_main에서 liveButton을 가져옴
        buttonLive5.setOnClickListener(new View.OnClickListener() {    // buttonLive를 누르면 실행되도록
            public void onClick(View v) {
                final Intent intent = new Intent(ABCDRecy.this, AbstractCameraXActivity.class); // 버튼 누르면 objectdetection 시작
                startActivity(intent); // intent = 컴포넌트를 실행하기 위해 시스템에 전달하는 메세지 객체
            }
        });


        final Button back_to_main = findViewById(R.id.back_to_main); // activity_main에서 liveButton을 가져옴
        back_to_main.setOnClickListener(new View.OnClickListener() {    // buttonLive를 누르면 실행되도록
            public void onClick(View v) {
                final Intent intent = new Intent(ABCDRecy.this, MainActivity.class); // 버튼 누르면 objectdetection 시작
                startActivity(intent); // intent = 컴포넌트를 실행하기 위해 시스템에 전달하는 메세지 객체
            }
        });
    }

    private void init(){
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerVierAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void getData(){
        DataMovie data = new DataMovie(R.drawable.prac_fox, "아이언맨");
        adapter.addItem(data);
        data = new DataMovie(R.drawable.prac_pig, "스파이더맨");
        adapter.addItem(data);
        data = new DataMovie(R.drawable.prac_pig, "블랙팬서");
        adapter.addItem(data);
        data = new DataMovie(R.drawable.prac_fox, "닥터스트레인지");
        adapter.addItem(data);
        data = new DataMovie(R.drawable.prac_fox, "헐크");
        adapter.addItem(data);
        data = new DataMovie(R.drawable.prac_pig, "토르");
        adapter.addItem(data);
    }
}