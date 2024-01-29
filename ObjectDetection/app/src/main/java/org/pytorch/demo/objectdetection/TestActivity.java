package org.pytorch.demo.objectdetection;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.pytorch.IValue;
import org.pytorch.LiteModuleLoader;
import org.pytorch.Module;
import org.pytorch.Tensor;
import org.pytorch.torchvision.TensorImageUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class TestActivity extends AppCompatActivity implements Runnable {
    private int mImageIndex = 0;
    private String[] mTestImages = {"quiz.png"};
    private String icon1, icon2, icon3, icon4, icon5;

//    private String[] mTestQuestion = {"pig", "fox", "oa"};
    private String[] mTestQuestion() {
        try {
            // 리소스에서 텍스트 파일 가져오기
            Resources resources = getResources();
            InputStream inputStream = resources.openRawResource(R.raw.question); // "my_text_file"는 파일 이름

            // InputStream에서 텍스트를 읽기 위해 Scanner 사용
            Scanner scanner = new Scanner(inputStream);
            StringBuilder textBuilder = new StringBuilder();

            while (scanner.hasNextLine()) {
                textBuilder.append(scanner.nextLine()).append("\n");
            }

            // 텍스트를 줄 단위로 분할하여 배열에 저장
            String text = textBuilder.toString();
            String[] textArray = text.split("\n");

            // 파일과 스캐너 닫기
            inputStream.close();
            scanner.close();

            return textArray;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private ImageView mImageView;
    private ImageView mDiceView;
    private ResultView mResultView;
    private Button mButtonDetect;
    private ProgressBar mProgressBar;
    private Bitmap mBitmap = null;
    private Module mModule = null;
    private float mImgScaleX, mImgScaleY, mIvScaleX, mIvScaleY, mStartX, mStartY;
    private TextView mode;
    private String model_name = "osmo.torchscript.ptl";
    private String model_class = "classes.txt";
    private int num_class = 26;
    private String mode_name;
    private String str = null;

    private String answer = null;
    private String answer2;
    private ImageView center;

    private String str_sum;
    private String mode_text;

    private Button Noun, Verb, Prase;



    public static String assetFilePath(Context context, String assetName) throws IOException {
        File file = new File(context.getFilesDir(), assetName);
        if (file.exists() && file.length() > 0) {
            return file.getAbsolutePath();
        }

        try (InputStream is = context.getAssets().open(assetName)) {
            try (OutputStream os = new FileOutputStream(file)) {
                byte[] buffer = new byte[4 * 1024];
                int read;
                while ((read = is.read(buffer)) != -1) {
                    os.write(buffer, 0, read);
                }
                os.flush();
            }
            return file.getAbsolutePath();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }

        setContentView(R.layout.activity_test);

        try {
            mBitmap = BitmapFactory.decodeStream(getAssets().open(mTestImages[mImageIndex]));
        } catch (IOException e) {
            Log.e("Object Detection", "Error reading assets", e);
            finish();
        }

//        mImageView = findViewById(R.id.imageView); // setContentView(R.layout.activity_main) 을 참고해서 activity_main 에 있는 imageView 를 찾아옴
//        mImageView.setImageBitmap(mBitmap); // 버튼을 눌렀을 때 실행해라
//        mResultView = findViewById(R.id.resultView);
//        mResultView.setVisibility(View.INVISIBLE);

//        mDiceView = findViewById(R.id.diceView); // setContentView(R.layout.activity_main) 을 참고해서 activity_main 에 있는 imageView 를 찾아옴
//        mDiceView.setImageBitmap(mBitmap); // 버튼을 눌렀을 때 실행해라
//        mResultView = findViewById(R.id.resultView);
//        mResultView.setVisibility(View.INVISIBLE);


        mode = findViewById(R.id.run_mode);
        Intent intent = getIntent();
        mode_name = intent.getStringExtra("str");  // prac / test
        if ( mode_name == null ) {
            mode_name = "prac";
        }
        if ( "test".equals(mode_name)) {
            mode_text = "테스트하기";
        } else {
            mode_text = "연습하기";
        }
        mode.setText(mode_text);


        final Button Prac = findViewById(R.id.prac); // activity_main에서 liveButton을 가져옴
        Prac.setOnClickListener(new View.OnClickListener() {    // buttonLive를 누르면 실행되도록
            public void onClick(View v) {
                final Intent intent = new Intent(TestActivity.this, TestActivity.class); // 버튼 누르면 objectdetection 시작
                mode_text = "prac";
                intent.putExtra("str", mode_text);
                startActivity(intent);
            }
        });

        final Button Tst = findViewById(R.id.tst); // activity_main에서 liveButton을 가져옴
        Tst.setOnClickListener(new View.OnClickListener() {    // buttonLive를 누르면 실행되도록
            public void onClick(View v) {
                final Intent intent = new Intent(TestActivity.this, TestActivity.class); // 버튼 누르면 objectdetection 시작
                mode_text = "test";
                intent.putExtra("str", mode_text);
                startActivity(intent);
            }
        });



        final ImageButton Noun = findViewById(R.id.word); // activity_main에서 liveButton을 가져옴
        Noun.setOnClickListener(new View.OnClickListener() {    // buttonLive를 누르면 실행되도록
            public void onClick(View v) {
                final Intent intent = new Intent(TestActivity.this, recy.class); // 버튼 누르면 objectdetection 시작
                startActivity(intent);

            }
        });

        final ImageButton Verb = findViewById(R.id.sente); // activity_main에서 liveButton을 가져옴
        Verb.setOnClickListener(new View.OnClickListener() {    // buttonLive를 누르면 실행되도록
            public void onClick(View v) {
                final Intent intent = new Intent(TestActivity.this, VowRecy.class); // 버튼 누르면 objectdetection 시작
                startActivity(intent);

            }
        });

        final ImageButton Profile = findViewById(R.id.profile); // activity_main에서 liveButton을 가져옴
        Profile.setOnClickListener(new View.OnClickListener() {    // buttonLive를 누르면 실행되도록
            public void onClick(View v) {
                final Intent intent = new Intent(TestActivity.this, Profile.class); // 버튼 누르면 objectdetection 시작
                startActivity(intent);

            }
        });
//        final Button Prase = findViewById(R.id.prase); // activity_main에서 liveButton을 가져옴
//        Prase.setOnClickListener(new View.OnClickListener() {    // buttonLive를 누르면 실행되도록
//            public void onClick(View v) {
//                final Intent intent = new Intent(TestActivity.this, ABCDRecy.class); // 버튼 누르면 objectdetection 시작
//                startActivity(intent);
//
//            }
//        });
        icon1 = mTestQuestion()[0] + "_icon";
        int resID1 = getResources().getIdentifier(icon1, "drawable", getPackageName());
        final Button buttonLive = findViewById(R.id.liveButton); // activity_main에서 liveButton을 가져옴
        buttonLive.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, resID1);
        buttonLive.setOnClickListener(new View.OnClickListener() {    // buttonLive를 누르면 실행되도록
            public void onClick(View v) {
                answer = mode_name + "_" + mTestQuestion()[0];  //prac_pig, test_pig

                final Intent intent = new Intent(TestActivity.this, AbstractCameraXActivity.class); // 버튼 누르면 objectdetection 시작
                intent.putExtra("str", answer);
                startActivity(intent);

            }
        });

        final Button buttonLive_show = findViewById(R.id.liveButton_show); // activity_main에서 liveButton을 가져옴
        buttonLive_show.setOnClickListener(new View.OnClickListener() {    // buttonLive를 누르면 실행되도록
            public void onClick(View v) {
                final Intent intent = new Intent(TestActivity.this, AbstractCameraXActivity.class); // 버튼 누르면 objectdetection 시작
                startActivity(intent); // intent = 컴포넌트를 실행하기 위해 시스템에 전달하는 메세지 객체
            }
        });


        final Button back_to_main = findViewById(R.id.back_to_main); // activity_main에서 liveButton을 가져옴
        back_to_main.setOnClickListener(new View.OnClickListener() {    // buttonLive를 누르면 실행되도록
            public void onClick(View v) {
                final Intent intent = new Intent(TestActivity.this, MainActivity.class); // 버튼 누르면 objectdetection 시작
                startActivity(intent); // intent = 컴포넌트를 실행하기 위해 시스템에 전달하는 메세지 객체
            }
        });

//        mButtonDetect = findViewById(R.id.detectButton);
//        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
//        mButtonDetect.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                mButtonDetect.setEnabled(false);
//                mProgressBar.setVisibility(ProgressBar.VISIBLE);
//                mButtonDetect.setText(getString(R.string.run_model));
//
//                mImgScaleX = (float) mBitmap.getWidth() / PrePostProcessor.mInputWidth;
//                mImgScaleY = (float) mBitmap.getHeight() / PrePostProcessor.mInputHeight;
//
//                mIvScaleX = (mBitmap.getWidth() > mBitmap.getHeight() ? (float) mImageView.getWidth() / mBitmap.getWidth() : (float) mImageView.getHeight() / mBitmap.getHeight());
//                mIvScaleY = (mBitmap.getHeight() > mBitmap.getWidth() ? (float) mImageView.getHeight() / mBitmap.getHeight() : (float) mImageView.getWidth() / mBitmap.getWidth());
//
//                mStartX = (mImageView.getWidth() - mIvScaleX * mBitmap.getWidth()) / 2;
//                mStartY = (mImageView.getHeight() - mIvScaleY * mBitmap.getHeight()) / 2;
//
//                Thread thread = new Thread(SubActivity.this);
//                thread.start();
//            }
//        });

        try {
            mModule = LiteModuleLoader.load(SubActivity.assetFilePath(getApplicationContext(), "osmo.torchscript.ptl"));
            BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("classes.txt")));
            String line;
            List<String> classes = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                classes.add(line);
            }
            PrePostProcessor.mClasses = new String[classes.size()];
            classes.toArray(PrePostProcessor.mClasses);
        } catch (IOException e) {
            Log.e("Object Detection", "Error reading assets", e);
            finish();
        }

        ////
        icon2 = mTestQuestion()[1] + "_icon";
        int resID2 = getResources().getIdentifier(icon2, "drawable", getPackageName());
        final Button buttonLive2 = findViewById(R.id.liveButton2); // activity_main에서 liveButton을 가져옴
        buttonLive2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, resID2);
        buttonLive2.setOnClickListener(new View.OnClickListener() {    // buttonLive를 누르면 실행되도록
            public void onClick(View v) {
                answer = mode_name + "_" + mTestQuestion()[1] ;
                ;

                final Intent intent = new Intent(TestActivity.this, AbstractCameraXActivity.class); // 버튼 누르면 objectdetection 시작
                intent.putExtra("str", answer);
                startActivity(intent);
//                final Intent intent2 = new Intent(SubActivity.this, ObjectDetectionActivity.class);
//                intent2.putExtra("str", answer);
//                startActivity(intent); // startActivity 를 통해 액티비티 이동이 이루어짐,  intent = 컴포넌트를 실행하기 위해 시스템에 전달하는 메세지 객체
//                startActivity(intent2);
//                final Intent intent3 = new Intent(SubActivity.this, ObjectDetectionActivity.class);
//                intent3.putExtra("str", answer2);
//                startActivity(intent3);
            }
        });

        ////
        icon3 = mTestQuestion()[2] + "_icon";
        int resID3 = getResources().getIdentifier(icon3, "drawable", getPackageName());
        final Button buttonLive3 = findViewById(R.id.liveButton3); // activity_main에서 liveButton을 가져옴
        buttonLive3.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, resID3);
        buttonLive3.setOnClickListener(new View.OnClickListener() {    // buttonLive를 누르면 실행되도록
            public void onClick(View v) {
                answer = mode_name + "_" + mTestQuestion()[2];
                ;

                final Intent intent = new Intent(TestActivity.this, AbstractCameraXActivity.class); // 버튼 누르면 objectdetection 시작
                intent.putExtra("str", answer);
                startActivity(intent);
//                final Intent intent2 = new Intent(SubActivity.this, ObjectDetectionActivity.class); // 버튼 누르면 objectdetection 시작
//                intent2.putExtra("str", answer);
//                startActivity(intent); // startActivity 를 통해 액티비티 이동이 이루어짐,  intent = 컴포넌트를 실행하기 위해 시스템에 전달하는 메세지 객체
//                startActivity(intent2);
//                final Intent intent3 = new Intent(SubActivity.this, ObjectDetectionActivity.class);
//                intent3.putExtra("str", answer2);
//                startActivity(intent3);
            }
        });


        ////
        icon4 = mTestQuestion()[3] + "_icon";
        int resID4 = getResources().getIdentifier(icon4, "drawable", getPackageName());
        final Button buttonLive4 = findViewById(R.id.liveButton4); // activity_main에서 liveButton을 가져옴
        buttonLive4.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, resID4);
        buttonLive4.setOnClickListener(new View.OnClickListener() {    // buttonLive를 누르면 실행되도록
            public void onClick(View v) {
                answer = mode_name + "_" + mTestQuestion()[3];

                final Intent intent = new Intent(TestActivity.this, AbstractCameraXActivity.class); // 버튼 누르면 objectdetection 시작
                intent.putExtra("str", answer);
                startActivity(intent);

//                str = mTestQuestion[3];
//                answer = mTestQuestion[0];
//
//                final Intent intent = new Intent(SubActivity.this, ObjectDetectionActivity.class); // 버튼 누르면 objectdetection 시작
//                intent.putExtra("str", answer);
//                startActivity(intent);
//                final Intent intent2 = new Intent(SubActivity.this, ObjectDetectionActivity.class);
//                intent2.putExtra("str", answer);
//                startActivity(intent); // startActivity 를 통해 액티비티 이동이 이루어짐,  intent = 컴포넌트를 실행하기 위해 시스템에 전달하는 메세지 객체
//                startActivity(intent2);
//                final Intent intent3 = new Intent(SubActivity.this, ObjectDetectionActivity.class);
//                intent3.putExtra("str", answer2);
//                startActivity(intent3);
            }
        });
        icon5 = mTestQuestion()[4] + "_icon";
        int resID5 = getResources().getIdentifier(icon5, "drawable", getPackageName());
        final Button buttonLive5 = findViewById(R.id.liveButton5); // activity_main에서 liveButton을 가져옴
        buttonLive5.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, resID5);
        buttonLive5.setOnClickListener(new View.OnClickListener() {    // buttonLive를 누르면 실행되도록
            public void onClick(View v) {
                answer = mode_name + "_" + mTestQuestion()[4];
                final Intent intent = new Intent(TestActivity.this, AbstractCameraXActivity.class); // 버튼 누르면 objectdetection 시작
                intent.putExtra("str", answer);
                startActivity(intent);
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        mBitmap = (Bitmap) data.getExtras().get("data");
                        Matrix matrix = new Matrix();
                        matrix.postRotate(90.0f);
                        mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
                        mImageView.setImageBitmap(mBitmap);
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();
                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                mBitmap = BitmapFactory.decodeFile(picturePath);
                                Matrix matrix = new Matrix();
                                matrix.postRotate(90.0f);
                                mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
                                mImageView.setImageBitmap(mBitmap);
                                cursor.close();
                            }
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void run() {
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(mBitmap, PrePostProcessor.mInputWidth, PrePostProcessor.mInputHeight, true);
        final Tensor inputTensor = TensorImageUtils.bitmapToFloat32Tensor(resizedBitmap, PrePostProcessor.NO_MEAN_RGB, PrePostProcessor.NO_STD_RGB);
        IValue[] outputTuple = mModule.forward(IValue.from(inputTensor)).toTuple();
        final Tensor outputTensor = outputTuple[0].toTensor();
        final float[] outputs = outputTensor.getDataAsFloatArray();
        final ArrayList<Result> results =  PrePostProcessor.outputsToNMSPredictions(outputs, mImgScaleX, mImgScaleY, mIvScaleX, mIvScaleY, mStartX, mStartY);

        runOnUiThread(() -> {
            mButtonDetect.setEnabled(true);
            mButtonDetect.setText(getString(R.string.detect));
            mProgressBar.setVisibility(ProgressBar.INVISIBLE);
            mResultView.setResults(results);
            mResultView.invalidate();
            mResultView.setVisibility(View.VISIBLE);
        });
    }
}
