package org.pytorch.demo.objectdetection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Shop extends AppCompatActivity {

    private Button btn_move, btn_test, btn_aco, btn_recy;

    private String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);

        final ImageButton Profile = findViewById(R.id.profile); // activity_main에서 liveButton을 가져옴
        Profile.setOnClickListener(new View.OnClickListener() {    // buttonLive를 누르면 실행되도록
            public void onClick(View v) {
                final Intent intent = new Intent(Shop.this, Profile.class); // 버튼 누르면 objectdetection 시작
                startActivity(intent);
            }
        });

//        final Button Shop = findViewById(R.id.purchase); // activity_main에서 liveButton을 가져옴
//        Shop.setOnClickListener(new View.OnClickListener() {    // buttonLive를 누르면 실행되도록
//            public void onClick(View v) {
//                final Intent intent = new Intent(Profile.this, Shop.class); // 버튼 누르면 objectdetection 시작
//                startActivity(intent);
//            }
//        });

        final Button Main = findViewById(R.id.main); // activity_main에서 liveButton을 가져옴
        Main.setOnClickListener(new View.OnClickListener() {    // buttonLive를 누르면 실행되도록
            public void onClick(View v) {
                final Intent intent2 = new Intent(Shop.this, recy.class); // 버튼 누르면 objectdetection 시작
                startActivity(intent2);
            }
        });

    }
}
