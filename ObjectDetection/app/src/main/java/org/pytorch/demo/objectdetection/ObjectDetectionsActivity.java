//package org.pytorch.demo.objectdetection;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.ImageFormat;
//import android.graphics.Matrix;
//import android.graphics.Rect;
//import android.graphics.YuvImage;
//import android.media.Image;
//import android.media.MediaPlayer;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.TextureView;
//import android.view.View;
//import android.view.ViewStub;
//
//import androidx.annotation.Nullable;
//import androidx.annotation.WorkerThread;
//import androidx.camera.core.ImageProxy;
//
//import org.pytorch.IValue;
//import org.pytorch.LiteModuleLoader;
//import org.pytorch.Module;
//import org.pytorch.Tensor;
//import org.pytorch.torchvision.TensorImageUtils;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.nio.ByteBuffer;
//import java.util.ArrayList;
//
//import android.graphics.Color;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//
//public class ObjectDetectionsActivity extends AbstractCameraXActivity<ObjectDetectionsActivity.AnalysisResult> {
//    private Module mModule = null;
//    private ResultView mResultView;  // ResultView = 사용자 정의 클래스일 것, package org.pytorch.demo.objectdetection 에서 정의된 클래스, 화면을 보여주는 역할
//// ResultView는 옆에 있는 java 폴더의 objectdetection에 포함된 클래스였음
//
//    private TextView question;
//    private TextView answer;
//    private ImageView icon;
//    private ImageButton btn_play, btn_stop;
//    private Button btn_next;
//
//    private ImageView center;
//    private ImageView dice;
//
//    private String[] alp;
//
//    private ArrayList result_alp;
//    private String textToDisplay;
//
//    private String ans_correct;
//    private String ans_incorrect;
//    private String prac_test;
//    private TextView hint_letter;
//    private int resId;
////    private TextView textView;
//    MediaPlayer mediaPlayer;
//
//    private TextView correct;
//
//    private String value;
//
//    public class AnalysisResult {
//        private final ArrayList<Result> mResults;
//        public AnalysisResult(ArrayList<Result> results) {
//            mResults = results;
//
//        ArrayList<String> dataList = new ArrayList<>();
//        for (Result result : mResults) {
//            String text = PrePostProcessor.mClasses[result.classIndex];
//            dataList.add(text);
//        }
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
//
//
//
//
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////    }
//
//
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////
//////        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//////            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
//////        }
//////
//////        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//////            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
//////        }
////
////        setContentView(R.layout.activity_object_detection);
////
////        Intent intent = getIntent();                // SubActivity에서 intent 값을 받아옴
////        String str = intent.getStringExtra("str");
////
////
////        center = (ImageView)findViewById(R.id.od_imageView);
////
////        String imageName = str;
////        int resourceId = getResources().getIdentifier(imageName, "drawable", getPackageName());
////        if (resourceId != 0) {
////            center.setImageResource(resourceId);
////        }
////
//////        Intent intent3 = getIntent();                // SubActivity에서 intent 값을 받아옴
//////        String str3 = intent3.getStringExtra("str");
////
////        dice = (ImageView)findViewById(R.id.od_diceView);
////
////        String diceName = str + "_dice";
////        int resourceId2 = getResources().getIdentifier(diceName, "drawable", getPackageName());
////        if (resourceId2 != 0) {
////            dice.setImageResource(resourceId2);
////        }
////        final Button buttonLive = findViewById(R.id.liveButton); // activity_main에서 liveButton을 가져옴
////        buttonLive.setOnClickListener(new View.OnClickListener() {    // buttonLive를 누르면 실행되도록
////            public void onClick(View v) {
////                final Intent intent = new Intent(ObjectDetectionActivity.this, TextViewActivity.class); // 버튼 누르면 objectdetection 시작
////                startActivity(intent); // startActivity 를 통해 액티비티 이동이 이루어짐,  intent = 컴포넌트를 실행하기 위해 시스템에 전달하는 메세지 객체
////            }
////        });
////    }
//
//    @Override
//    protected int getContentViewLayoutId() {
//
//        return R.layout.activity_object_detection;
//    }   // 이 클래스 자체는 CameraXActivity에서 일어나는 함수들을 정의하면서, object_detection 화면을 출력하기 위한 클래스?
//
//
////    @Override
////    protected TextureView getCameraPreviewTextureView() {
////
////        return
////    }
//
//    @Override
//    protected TextureView getCameraPreviewTextureView() {
//        Intent intent = getIntent();                // SubActivity에서 intent 값을 받아옴
//        String str = intent.getStringExtra("str");  // prac_fox, test_fox
//
//        resId = getResources().getIdentifier(str, "raw", getPackageName());
//        if (resId != 0) {
//            mediaPlayer = MediaPlayer.create(this, resId);
//            mediaPlayer.start();
//        }
////        mediaPlayer = MediaPlayer.create(this, R.raw.str);
////        mediaPlayer.start();
//
//        btn_play = (ImageButton) findViewById(R.id.btn_play);
//        btn_play.setOnClickListener(v -> {
//                    mediaPlayer = MediaPlayer.create(this, resId);
//                    mediaPlayer.start();
//        });
//
//        btn_stop = (ImageButton) findViewById(R.id.btn_stop);
//        btn_stop.setOnClickListener(v -> {
//                mediaPlayer.stop();
//                });
//
//        question = findViewById(R.id.question);    // activity_object_detection.xml의 question에 주기 위한 값
////        Intent intent = getIntent();                // SubActivity에서 intent 값을 받아옴
////        String str = intent.getStringExtra("str");  // 받아온 문자열을 저장
//        question.setText(str);                        // 받아온 문자열을 question에 넘겨줌
//
//        hint_letter = findViewById(R.id.hint_letter);
//
//        Button btn_hint = findViewById(R.id.hint);
//        btn_hint.setOnClickListener(v ->  {
//            hint_letter.setText(str.substring(5,6) + " _ _ ");
//        });
//
//
//
////        answer = findViewById(R.id.answer);    // activity_object_detection.xml의 question에 주기 위한 값
////        Intent intent2 = getIntent();                // SubActivity에서 intent 값을 받아옴
////        String str2 = intent2.getStringExtra("str");  // 받아온 문자열을 저장
////        answer.setText(str2);
//
//        //        icon = findViewById(R.id.diceView);    // activity_object_detection.xml의 question에 주기 위한 값
////        Intent intent3 = getIntent();                // SubActivity에서 intent 값을 받아옴
////        String icon_img = intent3.getStringExtra("image");  // 받아온 문자열을 저장
////        icon.setImageBitmap(icon_img);
//
//
////        Intent intent = getIntent();                // SubActivity에서 intent 값을 받아옴
////        String str = intent.getStringExtra("str");
//
//
//        center = (ImageView)findViewById(R.id.od_imageView);
//
//        String imageName = str;
//        int resourceId = getResources().getIdentifier(imageName, "drawable", getPackageName());
//        if (resourceId != 0) {
//            center.setImageResource(resourceId);
//        }
//
////        Intent intent3 = getIntent();                // SubActivity에서 intent 값을 받아옴
////        String str3 = intent3.getStringExtra("str");
//
//        dice = (ImageView)findViewById(R.id.od_diceView);
//
//        String diceName = str + "_dice";
//        int resourceId2 = getResources().getIdentifier(diceName, "drawable", getPackageName());
//        if (resourceId2 != 0) {
//            dice.setImageResource(resourceId2);
//        }
//
////        StringBuilder stringBuilder = new StringBuilder();
////        for (Object alp : result_alp) {
////            stringBuilder.append(alp).append("\n");
////        }
////        String textToDisplay = stringBuilder.toString();
////        // TextView에 문자열 설정
////        TextView textView = findViewById(R.id.detected);
////        textView.setText(textToDisplay);
//        TextView textView = findViewById(R.id.detected);
////        textToDisplay = "pig";
//        textView.setText(textToDisplay);
//
////        if ("fox_practice" == str) {
////            Intent intent2 = new Intent( ObjectDetectionsActivity.this, Correct.class);
////            intent2.putExtra("str", str);
////            startActivity(intent2);
////        }
//
//        prac_test = str.substring(0, 4);
////        btn_next = (Button) findViewById(R.id.btn_next);
////        btn_next.setOnClickListener(v -> {
////                    String correct = str.substring(5,8);
////                    String ans = "fox";
////                    ans_correct = prac_test + "_" + correct + "_"+ "correct";
////                    ans_incorrect = prac_test + "_" + correct + "_" + "incorrect";
////
////                    if (ans.equals(correct)) {
////                        Intent intent3 = new Intent( ObjectDetectionsActivity.this, Correct.class);
////                        intent3.putExtra("str",  ans_correct);
////                        startActivity(intent3);
////                    } else {
////                        Intent intent3 = new Intent(ObjectDetectionsActivity.this, Incorrect.class);
////                        intent3.putExtra("str", ans_incorrect);
////                        startActivity(intent3);
////                    }
////                }
////        );
//        correct = findViewById(R.id.btn_next);
////        btn_next.setOnClickListener(v -> {
//////        String correct = str.substring(5,8);
//////        String ans = "fox";
//////        ans_correct = prac_test + "_" + correct + "_"+ "correct";
//////        ans_incorrect = prac_test + "_" + correct + "_" + "incorrect";
//////            if (value == "correct") {
//////                Intent intent2 = new Intent(ObjectDetectionsActivity.this, Correct.class);
//////                startActivity(intent2);
//////            } else {
//////                Intent intent2 = new Intent(ObjectDetectionsActivity.this, Incorrect.class);
//////                startActivity(intent2);
//////            }
////        });
//
//
//
//        mResultView = findViewById(R.id.resultView);  //activity_object_detection.xml의 resultView 에 주기 위한 변수 선언
//        return ((ViewStub) findViewById(R.id.object_detection_texture_view_stub)) // activity_object_detection.xml
//                .inflate() // xml에 표기된 레이아웃들을 메모리에 객체화 시키는 행동
//                .findViewById(R.id.object_detection_texture_view); // texture_view.xml 에 있는 object_detection_texture_view 값에 넘겨줌?
//    }
//
//
//    @Override
//    protected void getIsEqual() {
//        value = isEqual;
//
//        answer = findViewById(R.id.answer);    // activity_object_detection.xml의 question에 주기 위한 값
////        Intent intent2 = getIntent();                // SubActivity에서 intent 값을 받아옴
////        String str2 = intent2.getStringExtra("str");  // 받아온 문자열을 저장
//        answer.setText(value);
//
////        correct = findViewById(R.id.btn_next);
////        btn_next.setOnClickListener(v -> {
//////        String correct = str.substring(5,8);
//////        String ans = "fox";
//////        ans_correct = prac_test + "_" + correct + "_"+ "correct";
//////        ans_incorrect = prac_test + "_" + correct + "_" + "incorrect";
////            if (value == "correct") {
////                Intent intent = new Intent(ObjectDetectionsActivity.this, Correct.class);
////                startActivity(intent);
////            } else {
////                Intent intent = new Intent(ObjectDetectionsActivity.this, Incorrect.class);
////                startActivity(intent);
////            }
////        });
//    }
//
//
//    @Override   // 화면에 결과를 표시하는 처리
//    protected void applyToUiAnalyzeImageResult(AnalysisResult result) {   // applyToUiAnalyzeImageResult 함수는 mResults 값을 mResultView에 넣어줌
//        mResultView.setResults(result.mResults);    // mResult에 result 값을 넘겨줌
//        mResultView.invalidate(); // 호출한 클라이언트 윈도우 화면을 무효화, 원하는 윈도우 화면을 강제 갱신
//    }  // 함수의 실행은 AbstractCameraXActivity에서 실행됨
//    // 카메라를 통해 이미지가 들어오면 그 이미지를 가지고 함수를 실행하는 듯 함
//
//
//
////    public void setResults(ArrayList<Result> results) {
////        mResults = results;
//
//
//    private Bitmap GetBinaryBitmap(Bitmap bitmap_src) {
//
//        Bitmap bitmap_new=bitmap_src.copy(bitmap_src.getConfig(), true);
//
//        for(int x=0; x<bitmap_new.getWidth(); x++) {
//            for(int y=0; y<bitmap_new.getHeight(); y++) {
//
//                int color=bitmap_new.getPixel(x, y);
//
//                color=GetNewColor(color);
//
//                bitmap_new.setPixel(x, y, color);
//            }
//        }
//        return bitmap_new;
//    }
//    private int GetNewColor(int c) {
//        int R = Color.red(c);
//        int G = Color.green(c);
//        int B = Color.blue(c);
//
//        if (R <= 100 & G <= 100 & B <= 100) {
//            return Color.BLACK;
//        }
//        else {
//            return Color.WHITE;
//        }
//    }
//
//
//    private Bitmap imgToBitmap(Image image) {
//            Image.Plane[] planes = image.getPlanes();
//            ByteBuffer yBuffer = planes[0].getBuffer();
//            ByteBuffer uBuffer = planes[1].getBuffer();
//            ByteBuffer vBuffer = planes[2].getBuffer();
//
//            int ySize = yBuffer.remaining();
//            int uSize = uBuffer.remaining();
//        int vSize = vBuffer.remaining();
//
//        byte[] nv21 = new byte[ySize + uSize + vSize];
//        yBuffer.get(nv21, 0, ySize);
//        vBuffer.get(nv21, ySize, vSize);
//        uBuffer.get(nv21, ySize + vSize, uSize);
//
//        YuvImage yuvImage = new YuvImage(nv21, ImageFormat.NV21, image.getWidth(), image.getHeight(), null);
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        yuvImage.compressToJpeg(new Rect(0, 0, yuvImage.getWidth(), yuvImage.getHeight()), 75, out);
//
//        byte[] imageBytes = out.toByteArray();
//        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//    }
//
//    @Override
//    @WorkerThread
//    @Nullable
//    protected AnalysisResult analyzeImage(ImageProxy image, int rotationDegrees) {
//        try {
//            if (mModule == null) {
//                mModule = LiteModuleLoader.load(SubActivity.assetFilePath(getApplicationContext(), "osmo.torchscript.ptl"));
//            }
//        } catch (IOException e) {
//            Log.e("Object Detection", "Error reading assets", e);
//            return null;
//        }
//        Bitmap bitmap = imgToBitmap(image.getImage());
//        // 이미지를 이진화하는 코드 추가 시작
//        //bitmap = GetBinaryBitmap(bitmap);
//        //bitmap = GetNewColor(bitmap);
//        // 이미지를 이진화하는 코드 추가 끝
//        Matrix matrix = new Matrix();
//        matrix.postRotate(90.0f);
//        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, PrePostProcessor.mInputWidth, PrePostProcessor.mInputHeight, true);
//        // 이미지를 가져옴
//
//        final Tensor inputTensor = TensorImageUtils.bitmapToFloat32Tensor(resizedBitmap, PrePostProcessor.NO_MEAN_RGB, PrePostProcessor.NO_STD_RGB);
//        IValue[] outputTuple = mModule.forward(IValue.from(inputTensor)).toTuple();
//        final Tensor outputTensor = outputTuple[0].toTensor();
//        final float[] outputs = outputTensor.getDataAsFloatArray();
//        // 이미지를 텐서화
//
//
//        float imgScaleX = (float)bitmap.getWidth() / PrePostProcessor.mInputWidth;
//        float imgScaleY = (float)bitmap.getHeight() / PrePostProcessor.mInputHeight;
//        float ivScaleX = (float)mResultView.getWidth() / bitmap.getWidth();
//        float ivScaleY = (float)mResultView.getHeight() / bitmap.getHeight();
//
//        final ArrayList<Result> results = PrePostProcessor.outputsToNMSPredictions(outputs, imgScaleX, imgScaleY, ivScaleX, ivScaleY, 0, 0);
//        result_alp = results;  // outputsToNMSPredictions 의 리턴값 return nonMaxSuppression(results, mNmsLimit, mThreshold); = selected
//        return new AnalysisResult(results);  //
//    }
//
//
//    @WorkerThread
//    @Nullable
//    protected ArrayList analyzeImage2(ImageProxy image, int rotationDegrees) {
//        try {
//            if (mModule == null) {
//                mModule = LiteModuleLoader.load(SubActivity.assetFilePath(getApplicationContext(), "osmo.torchscript.ptl"));
//            }
//        } catch (IOException e) {
//            Log.e("Object Detection", "Error reading assets", e);
//            return null;
//        }
//        Bitmap bitmap = imgToBitmap(image.getImage());
//        // 이미지를 이진화하는 코드 추가 시작
//        //bitmap = GetBinaryBitmap(bitmap);
//        //bitmap = GetNewColor(bitmap);
//        // 이미지를 이진화하는 코드 추가 끝
//        Matrix matrix = new Matrix();
//        matrix.postRotate(90.0f);
//        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, PrePostProcessor.mInputWidth, PrePostProcessor.mInputHeight, true);
//        // 이미지를 가져옴
//
//        final Tensor inputTensor = TensorImageUtils.bitmapToFloat32Tensor(resizedBitmap, PrePostProcessor.NO_MEAN_RGB, PrePostProcessor.NO_STD_RGB);
//        IValue[] outputTuple = mModule.forward(IValue.from(inputTensor)).toTuple();
//        final Tensor outputTensor = outputTuple[0].toTensor();
//        final float[] outputs = outputTensor.getDataAsFloatArray();
//        // 이미지를 텐서화
//
//
//        float imgScaleX = (float)bitmap.getWidth() / PrePostProcessor.mInputWidth;
//        float imgScaleY = (float)bitmap.getHeight() / PrePostProcessor.mInputHeight;
//        float ivScaleX = (float)mResultView.getWidth() / bitmap.getWidth();
//        float ivScaleY = (float)mResultView.getHeight() / bitmap.getHeight();
//
//        return PrePostProcessor.outputsToNMSPredictions(outputs, imgScaleX, imgScaleY, ivScaleX, ivScaleY, 0, 0);  // results
////        result_alp = results;  // outputsToNMSPredictions 의 리턴값 return nonMaxSuppression(results, mNmsLimit, mThreshold); = selected
////        return new AnalysisResult(results);  //
//    }
//
//
//
//}
//
//
////public class AnalysisResult {
////    private final ArrayList<Result> mResults;
////    public AnalysisResult(ArrayList<Result> results) {
////        mResults = results;
////}