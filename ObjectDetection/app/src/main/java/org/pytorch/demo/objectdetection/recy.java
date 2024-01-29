package org.pytorch.demo.objectdetection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;

import java.util.ArrayList;


public class recy extends AppCompatActivity {

    private Button Cons, Vow, ABCD;
    private ImageButton button;

    RecyclerVierAdapter adapter;
    RecyclerVierAdapterMove adapterMove;

    private ExpandableListView listView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recy);


        Display newDisplay = getWindowManager().getDefaultDisplay();
        int width = newDisplay.getWidth();

        ArrayList<myGroup> DataList = new ArrayList<myGroup>();
        listView = (ExpandableListView)findViewById(R.id.mylist);
        myGroup temp = new myGroup("apple", "사과");
        temp.child.add("He ate apple");
        temp.child2.add("그는 사과를 먹었다");
        temp.child3.add("apple");

        DataList.add(temp);
        temp = new myGroup("ant", "개미");
        temp.child.add("The ant is tiny");
        temp.child2.add("개미는 작다");
        temp.child3.add("ant");

        DataList.add(temp);
        temp = new myGroup("arrow", "화살");
        temp.child.add("She shot the arrow into the air");
        temp.child2.add("그녀는 공기중으로 화살을 쐈다");
        temp.child3.add("arrow");

        DataList.add(temp);
        temp = new myGroup("ax", "도끼");
        temp.child.add("He uses the ax to cut wood");
        temp.child2.add("그는 나무를 자르는데 도끼를 사용한다");
        temp.child3.add("ax");

        DataList.add(temp);
        temp = new myGroup("alligator", "악어");
        temp.child.add("Alligator is a large reptile");
        temp.child2.add("악어는 큰 파충류이다");
        temp.child3.add("alligator");

        DataList.add(temp);

        ExpandAdapter adapter = new ExpandAdapter(getApplicationContext(),R.layout.group_row,R.layout.child_row,DataList);
        listView.setIndicatorBounds(width-50, width); //이 코드를 지우면 화살표 위치가 바뀐다.
        listView.setAdapter(adapter);













//        init();
//        getData();
        init2();
        getData2();

//        final Button Cons = findViewById(R.id.word); // activity_main에서 liveButton을 가져옴
//        Cons.setOnClickListener(new View.OnClickListener() {    // buttonLive를 누르면 실행되도록
//            public void onClick(View v) {
//                final Intent intent = new Intent(recy.this, ConsRecy.class); // 버튼 누르면 objectdetection 시작
//                startActivity(intent);
//
//            }
//        });

        final ImageButton Vow = findViewById(R.id.sente); // activity_main에서 liveButton을 가져옴
        Vow.setOnClickListener(new View.OnClickListener() {    // buttonLive를 누르면 실행되도록
            public void onClick(View v) {
                final Intent intent = new Intent(recy.this, VowRecy.class); // 버튼 누르면 objectdetection 시작
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

        final ImageButton Test = findViewById(R.id.test); // activity_main에서 liveButton을 가져옴
        Test.setOnClickListener(new View.OnClickListener() {    // buttonLive를 누르면 실행되도록
            public void onClick(View v) {
                final Intent intent = new Intent(recy.this, TestActivity.class); // 버튼 누르면 objectdetection 시작
                startActivity(intent);
            }
        });

        final ImageButton Profile = findViewById(R.id.profile); // activity_main에서 liveButton을 가져옴
        Profile.setOnClickListener(new View.OnClickListener() {    // buttonLive를 누르면 실행되도록
            public void onClick(View v) {
                final Intent intent = new Intent(recy.this, Profile.class); // 버튼 누르면 objectdetection 시작
                startActivity(intent);
            }
        });


        final Button buttonLive5 = findViewById(R.id.liveButton5); // activity_main에서 liveButton을 가져옴
        buttonLive5.setOnClickListener(new View.OnClickListener() {    // buttonLive를 누르면 실행되도록
            public void onClick(View v) {
                final Intent intent = new Intent(recy.this, AbstractCameraXActivity.class); // 버튼 누르면 objectdetection 시작
                startActivity(intent); // intent = 컴포넌트를 실행하기 위해 시스템에 전달하는 메세지 객체
            }
        });


        final Button back_to_main = findViewById(R.id.back_to_main); // activity_main에서 liveButton을 가져옴
        back_to_main.setOnClickListener(new View.OnClickListener() {    // buttonLive를 누르면 실행되도록
            public void onClick(View v) {
                final Intent intent = new Intent(recy.this, MainActivity.class); // 버튼 누르면 objectdetection 시작
                startActivity(intent); // intent = 컴포넌트를 실행하기 위해 시스템에 전달하는 메세지 객체
            }
        });
    }

//    private void init(){
//        RecyclerView recyclerView = findViewById(R.id.recyclerView);
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(linearLayoutManager);
//
//        adapter = new RecyclerVierAdapter();
//        recyclerView.setAdapter(adapter);
//    }


//    private void getData(){
//        DataMovie data = new DataMovie(R.drawable.fox_icon, "아이언맨");
//        adapter.addItem(data);
//        data = new DataMovie(R.drawable.prac_pig, "스파이더맨");
//        adapter.addItem(data);
//        data = new DataMovie(R.drawable.prac_pig, "블랙팬서");
//        adapter.addItem(data);
//        data = new DataMovie(R.drawable.prac_fox, "닥터스트레인지");
//        adapter.addItem(data);
//        data = new DataMovie(R.drawable.prac_fox, "헐크");
//        adapter.addItem(data);
//        data = new DataMovie(R.drawable.prac_pig, "토르");
//        adapter.addItem(data);
//        data = new DataMovie(R.drawable.prac_fox, "헐크");
//        adapter.addItem(data);
//        data = new DataMovie(R.drawable.prac_pig, "토르");
//        adapter.addItem(data);
//        data = new DataMovie(R.drawable.prac_fox, "헐크");
//        adapter.addItem(data);
//        data = new DataMovie(R.drawable.prac_pig, "토르");
//        adapter.addItem(data);
//        data = new DataMovie(R.drawable.prac_fox, "헐크");
//        adapter.addItem(data);
//        data = new DataMovie(R.drawable.prac_pig, "토르");
//        adapter.addItem(data);
//        data = new DataMovie(R.drawable.prac_fox, "헐크");
//        adapter.addItem(data);
//        data = new DataMovie(R.drawable.prac_pig, "토르");
//        adapter.addItem(data);
//        data = new DataMovie(R.drawable.prac_fox, "헐크");
//        adapter.addItem(data);
//        data = new DataMovie(R.drawable.prac_pig, "토르");
//        adapter.addItem(data);
//    }
    private void init2(){
        RecyclerView recyclerView = findViewById(R.id.alpha_recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapterMove = new RecyclerVierAdapterMove();
        recyclerView.setAdapter(adapterMove);
    }

    private void getData2(){
        DataMove data = new DataMove(R.drawable.a_img);
        adapterMove.addItem(data);
        data = new DataMove(R.drawable.b_img);
        adapterMove.addItem(data);
        data = new DataMove(R.drawable.c_img);
        adapterMove.addItem(data);
        data = new DataMove(R.drawable.d_img);
        adapterMove.addItem(data);
        data = new DataMove(R.drawable.e_img);
        adapterMove.addItem(data);
        data = new DataMove(R.drawable.f_img);
        adapterMove.addItem(data);
        data = new DataMove(R.drawable.g_img);
        adapterMove.addItem(data);
        data = new DataMove(R.drawable.h_img);
        adapterMove.addItem(data);
        data = new DataMove(R.drawable.i_img);
        adapterMove.addItem(data);
        data = new DataMove(R.drawable.j_img);
        adapterMove.addItem(data);
        data = new DataMove(R.drawable.k_img);
        adapterMove.addItem(data);
        data = new DataMove(R.drawable.l_img);
        adapterMove.addItem(data);
        data = new DataMove(R.drawable.m_img);
        adapterMove.addItem(data);
        data = new DataMove(R.drawable.n_img);
        adapterMove.addItem(data);
        data = new DataMove(R.drawable.o_img);
        adapterMove.addItem(data);
        data = new DataMove(R.drawable.p_img);
        adapterMove.addItem(data);
        data = new DataMove(R.drawable.q_img);
        adapterMove.addItem(data);
        data = new DataMove(R.drawable.r_img);
        adapterMove.addItem(data);
        data = new DataMove(R.drawable.s_img);
        adapterMove.addItem(data);
        data = new DataMove(R.drawable.t_img);
        adapterMove.addItem(data);
        data = new DataMove(R.drawable.u_img);
        adapterMove.addItem(data);
        data = new DataMove(R.drawable.v_img);
        adapterMove.addItem(data);
        data = new DataMove(R.drawable.w_img);
        adapterMove.addItem(data);
        data = new DataMove(R.drawable.x_img);
        adapterMove.addItem(data);
        data = new DataMove(R.drawable.y_img);
        adapterMove.addItem(data);
        data = new DataMove(R.drawable.z_img);
        adapterMove.addItem(data);

    }
}