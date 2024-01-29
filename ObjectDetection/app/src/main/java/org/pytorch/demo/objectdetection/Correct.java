package org.pytorch.demo.objectdetection;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Correct extends AppCompatActivity {
    private Button next;
    private Button menu;
    private ImageView center_img;
    private TextView ans;
    private String Mode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.correct_view);
        Intent intent = getIntent();
        String str = intent.getStringExtra("str");

//        center_img = findViewById(R.id.correct_img);
//        String imageName = str;
//        int resourceId = getResources().getIdentifier(imageName, "drawable", getPackageName());
//        if (resourceId != 0) {
//            center_img.setImageResource(resourceId);
//        }

        //list_page = findViewById(R.id.list);
        //str = et_test.getText().toString();
        next = (Button) findViewById(R.id.nextButton);
        next.setOnClickListener(v -> {
                    Intent intent2 = new Intent( Correct.this, AbstractCameraXActivity.class);
                    startActivity(intent2);
                }
        );

//        menu = (Button) findViewById(R.id.menuButton);
//        menu.setOnClickListener(v -> {
//            Intent intent3 = new Intent( Correct.this, SubActivity.class);
//                    if (Mode == "prac") {
//
//                        intent3.putExtra("str", Mode);
//                        startActivity(intent3);
//                    } else {
//
//                        intent3.putExtra("str", Mode);
//                        startActivity(intent3);
//                    }
////            intent3.putExtra("str", Mode);
////            startActivity(intent3);
//        }
        menu = (Button) findViewById(R.id.menuButton);
        menu.setOnClickListener(v -> {
                    Mode = str.substring(0,4);
                    Intent intent3 = new Intent( Correct.this, TestActivity.class);
                    if (Mode == "prac") {
                        intent3.putExtra("str", Mode);
                        startActivity(intent3);
                    } else {
                        intent3.putExtra("str", Mode);
                        startActivity(intent3);
                    }
                }
        );
    }

}
