package org.pytorch.demo.objectdetection;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.util.Size;
import android.view.TextureView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.annotation.WorkerThread;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageAnalysisConfig;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.core.app.ActivityCompat;
import androidx.camera.core.CameraInfo;
import android.widget.Button;

import org.pytorch.IValue;
import org.pytorch.LiteModuleLoader;
import org.pytorch.Module;
import org.pytorch.Tensor;
import org.pytorch.torchvision.TensorImageUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashSet;
//import androidx.camera.core.CameraSelector;

public class AbstractCameraXActivity extends BaseModuleActivity {
    private static final int REQUEST_CODE_CAMERA_PERMISSION = 200;
    private static final String[] PERMISSIONS = {Manifest.permission.CAMERA};

    private long mLastAnalysisResultTime;

//    protected abstract int getContentViewLayoutId();

//    protected abstract TextureView getCameraPreviewTextureView();

    //    protected abstract ArrayList analyzeImage2(ImageProxy image, int rotationDegrees );
    private ArrayList<String> list;
    //    protected String isEqual;
    private String isEqual;

//    protected abstract void getIsEqual();

    private Module mModule = null;
    private ResultView mResultView;  // ResultView = 사용자 정의 클래스일 것, package org.pytorch.demo.objectdetection 에서 정의된 클래스, 화면을 보여주는 역할
// ResultView는 옆에 있는 java 폴더의 objectdetection에 포함된 클래스였음

    private TextView question;
    private TextView answer;
    private String answer2;
    private ImageView icon;
    private ImageButton btn_play, btn_stop;
    private Button btn_next;

    private ImageView center;
    private ImageView dice;

    private String[] alp;

    private ArrayList result_alp;
    private String textToDisplay;

    private String ans_correct;
    private String ans_incorrect;
    private String prac_test;
    private TextView hint_letter;
    private int resId;
    //    private TextView textView;
    MediaPlayer mediaPlayer;

    private TextView correct;

    private String value;

    private ArrayList<Integer> cls_list = new ArrayList<>();

    private String str;

    private ArrayList<String> stringList;
    private ArrayList<String> classList = new ArrayList<>();
    private String combinedString;

    private StringBuilder builder = new StringBuilder();

    private String multiplyString(String underbar, int count) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < count; i++) {
            result.append(underbar);
        }
        return result.toString();
    }


//    public class AnalysisResult {
//        private final ArrayList<Result> mResults;
//
//        public AnalysisResult(ArrayList<Result> results) {
//            mResults = results;
//
//            ArrayList<String> dataList = new ArrayList<>();
//            for (Result result : mResults) {
//                String text = PrePostProcessor.mClasses[result.classIndex];
//                dataList.add(text);
//            }
//            StringBuilder stringBuilder = new StringBuilder();
//            for (String item : dataList) {
//                stringBuilder.append(item).append("\n");
//            }
//            textToDisplay = stringBuilder.toString();
////            TextView textView = findViewById(R.id.detected);
////            textView.setText(textToDisplay);
//        }
//        // TextView에 문자열 설정
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_act);
        answer2 = "incorrect";
//        isEqual = "correct";
        startBackgroundThread();
//        getIsEqual();
        //mCameraFacing  = Camera.CameraInfo.CAMERA_FACING_FRONT;

        Intent intent = getIntent();                // SubActivity에서 intent 값을 받아옴
        str = intent.getStringExtra("str");  // prac_fox, test_fox

        stringList = new ArrayList<>();
        String str_sub = str.substring(5);
        String[] words = str_sub.split(""); // 공백을 기준으로 문자열 분할

        for (String word : words) {
            stringList.add(word); // ArrayList에 추가
        }


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS,
                    REQUEST_CODE_CAMERA_PERMISSION);
        } else {
            setupCameraX();


            answer = findViewById(R.id.detected);


//            Log.d("classList", "cl" + classList);


            final Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                int counter = 0;

                @Override
                public void run() {
                    answer.setText(combinedString);
                    counter++;
                    handler.postDelayed(this, 200); // 0.2초마다 업데이트
                }
            };
            handler.post(runnable);



//            analyzeImage2();
            btn_next = findViewById(R.id.btn_next);
            Log.d("answer2", "answer2" + answer2);
            btn_next.setOnClickListener(new View.OnClickListener() {    // buttonLive를 누르면 실행되도록
                public void onClick(View v) {
                    if (answer2.equals("correct")) {
                        final Intent intent = new Intent(AbstractCameraXActivity.this, Correct.class); // 버튼 누르면 objectdetection 시작
                        intent.putExtra("str", str);
                        startActivity(intent);
                    } else {
                        final Intent intent = new Intent(AbstractCameraXActivity.this, Incorrect.class); // 버튼 누르면 objectdetection 시작
                        intent.putExtra("str", str);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    protected TextureView getCameraPreviewTextureView() {
//        Intent intent = getIntent();                // SubActivity에서 intent 값을 받아옴
//        str = intent.getStringExtra("str");  // prac_fox, test_fox

        resId = getResources().getIdentifier(str.substring(5), "raw", getPackageName());
        if (resId != 0) {
            mediaPlayer = MediaPlayer.create(this, resId);
            mediaPlayer.start();
        }
//        mediaPlayer = MediaPlayer.create(this, R.raw.str);
//        mediaPlayer.start();

        btn_play = (ImageButton) findViewById(R.id.btn_play);
        btn_play.setOnClickListener(v -> {
            mediaPlayer = MediaPlayer.create(this, resId);
            mediaPlayer.start();
        });

        btn_stop = (ImageButton) findViewById(R.id.btn_stop);
        btn_stop.setOnClickListener(v -> {
            mediaPlayer.stop();
        });

        question = findViewById(R.id.question);    // activity_object_detection.xml의 question에 주기 위한 값
//        Intent intent = getIntent();                // SubActivity에서 intent 값을 받아옴
//        String str = intent.getStringExtra("str");  // 받아온 문자열을 저장
        question.setText(str);                        // 받아온 문자열을 question에 넘겨줌

        hint_letter = findViewById(R.id.hint_letter);


        Button btn_hint = findViewById(R.id.hint);
        btn_hint.setOnClickListener(v -> {
            int len = str.substring(5).length() - 1 ;
            String resultString = multiplyString(" _ ", len);
            String FirstLetter = str.substring(5, 6).toUpperCase();
            hint_letter.setText(FirstLetter + resultString);
        });


//        answer = findViewById(R.id.answer);    // activity_object_detection.xml의 question에 주기 위한 값
//        Intent intent2 = getIntent();                // SubActivity에서 intent 값을 받아옴
//        String str2 = intent2.getStringExtra("str");  // 받아온 문자열을 저장
//        answer.setText(str2);

        //        icon = findViewById(R.id.diceView);    // activity_object_detection.xml의 question에 주기 위한 값
//        Intent intent3 = getIntent();                // SubActivity에서 intent 값을 받아옴
//        String icon_img = intent3.getStringExtra("image");  // 받아온 문자열을 저장
//        icon.setImageBitmap(icon_img);


//        Intent intent = getIntent();                // SubActivity에서 intent 값을 받아옴
//        String str = intent.getStringExtra("str");


        center = (ImageView) findViewById(R.id.od_imageView);

        String imageName = str;
        int resourceId = getResources().getIdentifier(imageName, "drawable", getPackageName());
        if (resourceId != 0) {
            center.setImageResource(resourceId);
        }

//        Intent intent3 = getIntent();                // SubActivity에서 intent 값을 받아옴
//        String str3 = intent3.getStringExtra("str");

        dice = (ImageView) findViewById(R.id.od_diceView);

        String diceName = str + "_dice";
        int resourceId2 = getResources().getIdentifier(diceName, "drawable", getPackageName());
        if (resourceId2 != 0) {
            dice.setImageResource(resourceId2);
        }

//        StringBuilder stringBuilder = new StringBuilder();
//        for (Object alp : result_alp) {
//            stringBuilder.append(alp).append("\n");
//        }
//        String textToDisplay = stringBuilder.toString();
//        // TextView에 문자열 설정
//        TextView textView = findViewById(R.id.detected);
//        textView.setText(textToDisplay);
        TextView textView = findViewById(R.id.detected);
//        textToDisplay = "pig";
        textView.setText(textToDisplay);

//        if ("fox_practice" == str) {
//            Intent intent2 = new Intent( ObjectDetectionsActivity.this, Correct.class);
//            intent2.putExtra("str", str);
//            startActivity(intent2);
//        }

        prac_test = str.substring(0, 4);
//        btn_next = (Button) findViewById(R.id.btn_next);
//        btn_next.setOnClickListener(v -> {
//                    String correct = str.substring(5,8);
//                    String ans = "fox";
//                    ans_correct = prac_test + "_" + correct + "_"+ "correct";
//                    ans_incorrect = prac_test + "_" + correct + "_" + "incorrect";
//
//                    if (ans.equals(correct)) {
//                        Intent intent3 = new Intent( ObjectDetectionsActivity.this, Correct.class);
//                        intent3.putExtra("str",  ans_correct);
//                        startActivity(intent3);
//                    } else {
//                        Intent intent3 = new Intent(ObjectDetectionsActivity.this, Incorrect.class);
//                        intent3.putExtra("str", ans_incorrect);
//                        startActivity(intent3);
//                    }
//                }
//        );
        correct = findViewById(R.id.btn_next);
//        btn_next.setOnClickListener(v -> {
////        String correct = str.substring(5,8);
////        String ans = "fox";
////        ans_correct = prac_test + "_" + correct + "_"+ "correct";
////        ans_incorrect = prac_test + "_" + correct + "_" + "incorrect";
////            if (value == "correct") {
////                Intent intent2 = new Intent(ObjectDetectionsActivity.this, Correct.class);
////                startActivity(intent2);
////            } else {
////                Intent intent2 = new Intent(ObjectDetectionsActivity.this, Incorrect.class);
////                startActivity(intent2);
////            }
//        });
        value = isEqual;


//        answer = findViewById(R.id.answer);    // activity_object_detection.xml의 question에 주기 위한 값
////        Intent intent2 = getIntent();                // SubActivity에서 intent 값을 받아옴
////        String str2 = intent2.getStringExtra("str");  // 받아온 문자열을 저장
//        answer.setText(value);

//        answer2 = getPrivateString();
//        Log.d("myapp", "answer : " + answer2);

//        stringList = new ArrayList<>();
//        String str_sub = str.substring(5);
//        String[] words = str_sub.split(" "); // 공백을 기준으로 문자열 분할
//
//        for (String word : words) {
//            stringList.add(word); // ArrayList에 추가
//        }



        mResultView = findViewById(R.id.resultView);  //activity_object_detection.xml의 resultView 에 주기 위한 변수 선언
        return ((ViewStub) findViewById(R.id.object_detection_texture_view_stub)) // activity_object_detection.xml
                .inflate() // xml에 표기된 레이아웃들을 메모리에 객체화 시키는 행동
                .findViewById(R.id.object_detection_texture_view); // texture_view.xml 에 있는 object_detection_texture_view 값에 넘겨줌?
    }
    public class AnalysisResult {
        private final ArrayList<Result> mResults;

        public AnalysisResult(ArrayList<Result> results) {
            mResults = results;

//            ArrayList<String> dataList = new ArrayList<>();
//            for (Result result : mResults) {
//                String text = PrePostProcessor.mClasses[result.classIndex];
//                dataList.add(text);
//            }
//            StringBuilder stringBuilder = new StringBuilder();
//            for (String item : dataList) {
//                stringBuilder.append(item).append("\n");
//            }
//            textToDisplay = stringBuilder.toString();
////            TextView textView = findViewById(R.id.detected);
////            textView.setText(textToDisplay);
        }
        // TextView에 문자열 설정

    }

    protected void applyToUiAnalyzeImageResult(AnalysisResult result) {   // applyToUiAnalyzeImageResult 함수는 mResults 값을 mResultView에 넣어줌
        mResultView.setResults(result.mResults);    // mResult에 result 값을 넘겨줌        camerax에서 result를 applyToUI 함수에 던짐
        mResultView.invalidate(); // 호출한 클라이언트 윈도우 화면을 무효화, 원하는 윈도우 화면을 강제 갱신   // ResultView 클래스에 정의되지 않은 함수
    }  // 함수의 실행은 AbstractCameraXActivity에서 실행됨     // setResults 는 ResultView 클래스에서 정의된 함수, 단순히 Array 전달
    // 카메라를 통해 이미지가 들어오면 그 이미지를 가지고 함수를 실행하는 듯 함


//    public void setResults(ArrayList<Result> results) {
//        mResults = results;


    private Bitmap GetBinaryBitmap(Bitmap bitmap_src) {

        Bitmap bitmap_new = bitmap_src.copy(bitmap_src.getConfig(), true);

        for (int x = 0; x < bitmap_new.getWidth(); x++) {
            for (int y = 0; y < bitmap_new.getHeight(); y++) {

                int color = bitmap_new.getPixel(x, y);

                color = GetNewColor(color);

                bitmap_new.setPixel(x, y, color);
            }
        }
        return bitmap_new;
    }

    private int GetNewColor(int c) {
        int R = Color.red(c);
        int G = Color.green(c);
        int B = Color.blue(c);

        if (R <= 100 & G <= 100 & B <= 100) {
            return Color.BLACK;
        } else {
            return Color.WHITE;
        }
    }


    private Bitmap imgToBitmap(Image image) {
        Image.Plane[] planes = image.getPlanes();
        ByteBuffer yBuffer = planes[0].getBuffer();
        ByteBuffer uBuffer = planes[1].getBuffer();
        ByteBuffer vBuffer = planes[2].getBuffer();

        int ySize = yBuffer.remaining();
        int uSize = uBuffer.remaining();
        int vSize = vBuffer.remaining();

        byte[] nv21 = new byte[ySize + uSize + vSize];
        yBuffer.get(nv21, 0, ySize);
        vBuffer.get(nv21, ySize, vSize);
        uBuffer.get(nv21, ySize + vSize, uSize);

        YuvImage yuvImage = new YuvImage(nv21, ImageFormat.NV21, image.getWidth(), image.getHeight(), null);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(new Rect(0, 0, yuvImage.getWidth(), yuvImage.getHeight()), 75, out);

        byte[] imageBytes = out.toByteArray();
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }

    @WorkerThread
    @Nullable
    protected AnalysisResult analyzeImage(ImageProxy image, int rotationDegrees) {
        try {
            if (mModule == null) {
                mModule = LiteModuleLoader.load(SubActivity.assetFilePath(getApplicationContext(), "osmo.torchscript.ptl"));
            }
        } catch (IOException e) {
            Log.e("Object Detection", "Error reading assets", e);
            return null;
        }
        Bitmap bitmap = imgToBitmap(image.getImage());
        // 이미지를 이진화하는 코드 추가 시작
        //bitmap = GetBinaryBitmap(bitmap);
        //bitmap = GetNewColor(bitmap);
        // 이미지를 이진화하는 코드 추가 끝
        Matrix matrix = new Matrix();
        matrix.postRotate(90.0f);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, PrePostProcessor.mInputWidth, PrePostProcessor.mInputHeight, true);
        // 이미지를 가져옴

        final Tensor inputTensor = TensorImageUtils.bitmapToFloat32Tensor(resizedBitmap, PrePostProcessor.NO_MEAN_RGB, PrePostProcessor.NO_STD_RGB);
        IValue[] outputTuple = mModule.forward(IValue.from(inputTensor)).toTuple();
        final Tensor outputTensor = outputTuple[0].toTensor();
        final float[] outputs = outputTensor.getDataAsFloatArray();
        // 이미지를 텐서화


        float imgScaleX = (float) bitmap.getWidth() / PrePostProcessor.mInputWidth;
        float imgScaleY = (float) bitmap.getHeight() / PrePostProcessor.mInputHeight;
        float ivScaleX = (float) mResultView.getWidth() / bitmap.getWidth();
        float ivScaleY = (float) mResultView.getHeight() / bitmap.getHeight();

        final ArrayList<Result> results = PrePostProcessor.outputsToNMSPredictions(outputs, imgScaleX, imgScaleY, ivScaleX, ivScaleY, 0, 0);
        result_alp = results;  // outputsToNMSPredictions 의 리턴값 return nonMaxSuppression(results, mNmsLimit, mThreshold); = selected
        return new AnalysisResult(results);  //
    }


    @WorkerThread
    @Nullable
    protected ArrayList analyzeImage2(ImageProxy image, int rotationDegrees) {
        try {
            if (mModule == null) {
                mModule = LiteModuleLoader.load(SubActivity.assetFilePath(getApplicationContext(), "osmo.torchscript.ptl"));
            }
        } catch (IOException e) {
            Log.e("Object Detection", "Error reading assets", e);
            return null;
        }
        Bitmap bitmap = imgToBitmap(image.getImage());
        // 이미지를 이진화하는 코드 추가 시작
        //bitmap = GetBinaryBitmap(bitmap);
        //bitmap = GetNewColor(bitmap);
        // 이미지를 이진화하는 코드 추가 끝
        Matrix matrix = new Matrix();
        matrix.postRotate(90.0f);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, PrePostProcessor.mInputWidth, PrePostProcessor.mInputHeight, true);
        // 이미지를 가져옴

        final Tensor inputTensor = TensorImageUtils.bitmapToFloat32Tensor(resizedBitmap, PrePostProcessor.NO_MEAN_RGB, PrePostProcessor.NO_STD_RGB);
        IValue[] outputTuple = mModule.forward(IValue.from(inputTensor)).toTuple();
        final Tensor outputTensor = outputTuple[0].toTensor();
        final float[] outputs = outputTensor.getDataAsFloatArray();
        // 이미지를 텐서화


        float imgScaleX = (float) bitmap.getWidth() / PrePostProcessor.mInputWidth;
        float imgScaleY = (float) bitmap.getHeight() / PrePostProcessor.mInputHeight;
        float ivScaleX = (float) mResultView.getWidth() / bitmap.getWidth();
        float ivScaleY = (float) mResultView.getHeight() / bitmap.getHeight();

        final ArrayList<Result> results = PrePostProcessor.outputsToNMSPredictions(outputs, imgScaleX, imgScaleY, ivScaleX, ivScaleY, 0, 0);  // results
//        result_alp = results;  // outputsToNMSPredictions 의 리턴값 return nonMaxSuppression(results, mNmsLimit, mThreshold); = selected
//        return new AnalysisResult(results);  //
        return results;
    }

    private void setupCameraX() {
        final TextureView textureView = getCameraPreviewTextureView();
        final PreviewConfig previewConfig = new PreviewConfig.Builder().
                setLensFacing(CameraX.LensFacing.FRONT).build();
        final Preview preview = new Preview(previewConfig);
        preview.setOnPreviewOutputUpdateListener(output -> textureView.setSurfaceTexture(output.getSurfaceTexture()));

        final ImageAnalysisConfig imageAnalysisConfig =
                new ImageAnalysisConfig.Builder().setLensFacing(CameraX.LensFacing.FRONT)
                        .setTargetResolution(new Size(480, 640))
                        .setCallbackHandler(mBackgroundHandler)
                        .setImageReaderMode(ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE)
                        .build();
        final ImageAnalysis imageAnalysis = new ImageAnalysis(imageAnalysisConfig);
        imageAnalysis.setAnalyzer((image, rotationDegrees) -> {
            if (SystemClock.elapsedRealtime() - mLastAnalysisResultTime < 500) {
                return;
            }
            Log.d("image", "image: " + image);
             Log.d("degree", "r_degree: " + rotationDegrees);



            final ArrayList<Result> res = analyzeImage2(image, rotationDegrees);
            if (!res.isEmpty()) {

                Log.d("res", "resultItem: " + res);
//                Result res1 = res.get(0);
//                int cls = res1.getCls();
//                cls_list.clear();

                classList.clear();
                for (int i = 0; i < (res).size(); i++) {

                    int cls = res.get(i).getCls() ;
//                    cls_list.add(cls);
                    char letter = (char) ('a' + cls);
                    String s_letter = Character.toString(letter);
                    classList.add(s_letter);

                    Log.d("cls", "size : " + (res).size());
                    Log.d("cls", "class : " + res.get(i).getCls());
//                    Log.d("cls_list", "class_list : " + cls_list);

//                    Log.d("str", "str: " + str.substring(5));
                }

                builder.setLength(0);

                for (String item : classList) {
                    builder.append(item).append(" "); // 각 항목을 줄 바꿈으로 구분
                }
                combinedString = builder.toString().toUpperCase();

                HashSet<String> set1 = new HashSet<>(stringList);
                HashSet<String> set2 = new HashSet<>(classList);


                // HashSet 비교
                boolean areEqual2 = set1.equals(set2);

//                boolean areEqual = stringList.equals(classList);
                if (areEqual2) {
                    Log.d("equal? ", "equal");
                    Log.d("cls_list", "class_list : " + set1);
                    Log.d("cls_list", "class_list : " + set2);

                    answer2 = "correct";

                } else {
                    Log.d("equal?", "not equal");
                    Log.d("cls_list", "class_list : " + set1);
                    Log.d("cls_list", "class_list : " + set2);
                }
//                Log.d("res", "resultItem: " + res);
//                Log.d("cls", "class1 : " + res.get(0).getCls());
            }
//            } else {
//                Log.d("cls", "class: None" );
//            }
//
//            Log.d("image2", "image2: " + image);
//            Log.d("degree2", "r_degree2: " + rotationDegrees);


//            final AnalysisResult result = analyzeImage(image, rotationDegrees);
////            Log.d("res", "resultItem: " + result.get(0));
////            if (!result.isEmpty()) {
////                Log.d("result", "result:" + result);
////            }
//            Log.d("result", "result:" + result);




//            for (Result resultItem : res) {
//                int classIndex = resultItem.classIndex;
//
//                char letter = (char) ('A' + classIndex);
//                String s_letter = Character.toString(letter);
//
//                Log.d("myapp", "변수값: " + result);
////                answer.setText(s_letter);
//                answer2 = s_letter;
//                Log.d("res", "resultItem: " + resultItem);
//                Log.d("res index", "classIndex: " + classIndex);
//                if (s_letter == "B ") {
//                    isEqual = "correct2";
//                } else {
//                    isEqual = "incorrect";
//                }
//
////                list.add(s_letter);
//////                if ( classIndex == 0 ) {
//////                    String str = "A";
//////                } else ( classIndex == 1 ) {
//////                    String str = "B";
//////                }
////                // 비교할 문자열
////                String target = "FOX";
////
////                // ArrayList의 문자열을 순회하면서 비교합니다.
////
////                for (String str : list) {
////                    if (!str.equals(target)) {
////                        isEqual = "incorrect";
////                        break; // 하나라도 다른 문자열이 있으면 루프를 종료합니다.
////                    }
////                }
//////                if ( isEqual == "correct") {
//////                    Intent intent
//////                }
////
//////                TextView idx = findViewById(org.pytorch.demo.objectdetection.R.id.detected);
//////                idx.setText(letter);
////
////
//            }


//            if (result != null) {
//                mLastAnalysisResultTime = SystemClock.elapsedRealtime();
//                runOnUiThread(() -> applyToUiAnalyzeImageResult(result));
//            }
        });

        CameraX.bindToLifecycle(this, preview, imageAnalysis);
    }
    public String getPrivateString() {
        return answer2;
    }
}
//
//    @WorkerThread
//    @Nullable
//    protected abstract analyzeImage(ImageProxy image, int rotationDegrees);
//
////    @WorkerThread
////    @Nullable
////    protected  analyzeImage2(ImageProxy image, int rotationDegrees);
//
//    @UiThread
//    protected abstract void applyToUiAnalyzeImageResult( result);
//}
