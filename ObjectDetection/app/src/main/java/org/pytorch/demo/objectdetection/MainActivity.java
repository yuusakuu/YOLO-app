package org.pytorch.demo.objectdetection;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btn_move, btn_test, btn_aco, btn_recy, btn_exp;

    private String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //list_page = findViewById(R.id.list);
        //str = et_test.getText().toString();
//        btn_move = (Button) findViewById(R.id.startButton);
//        btn_move.setOnClickListener(v -> {
//            str = "prac";
//            Intent intent = new Intent( MainActivity.this, NounActivity.class);
//            intent.putExtra("str", str);
//            startActivity(intent);
//            }
//        );
//
//
//        btn_test = (Button) findViewById(R.id.startButton);
//        btn_test.setOnClickListener(v -> {
//            str = "test";
//            Intent intent = new Intent( MainActivity.this, ObjectDetectionActivity.class);
//            intent.putExtra("str", str);
//            startActivity(intent);
//
//            }
//        );

//        btn_aco = (Button) findViewById(R.id.acoButton);
//        btn_aco.setOnClickListener(v -> {
//                    str = "test";
//                    Intent intent = new Intent( MainActivity.this, AcodianActivity.class);
//                    intent.putExtra("str", str);
//                    startActivity(intent);
//                }
//        );

        btn_recy = (Button) findViewById(R.id.recyButton);
        btn_recy.setOnClickListener(v -> {
            Intent intent = new Intent( MainActivity.this, recy.class);
            startActivity(intent);
        });




    }



}
