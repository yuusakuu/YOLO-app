package org.pytorch.demo.objectdetection;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Incorrect extends AppCompatActivity {

    private Button retry;
    private Button menu;
    private ImageView center_img;
    private String Mode;

    private TextView file_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incorrect_view);
        Intent intent = getIntent();                // SubActivity에서 intent 값을 받아옴
        String str = intent.getStringExtra("str");

//        center_img = (ImageView)findViewById(R.id.imageView4);
//
//        String imageName = str;
//        int resourceId = getResources().getIdentifier(imageName, "drawable", getPackageName());
//        if (resourceId != 0) {
//            center_img.setImageResource(resourceId);
//        }


//        file_name = findViewById(R.id.file_name);
//        file_name.setText(str.substring(0,4));


        retry = (Button) findViewById(R.id.retryButton);
        retry.setOnClickListener(v -> {
                    Intent intent2 = new Intent( Incorrect.this, AbstractCameraXActivity.class);
                    startActivity(intent2);
                }
        );


        menu = (Button) findViewById(R.id.menuButton);
        menu.setOnClickListener(v -> {
            Mode = str.substring(0,4);
            Intent intent3 = new Intent( Incorrect.this, TestActivity.class);
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
