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
//import android.util.Log;
//import android.view.TextureView;
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
//import android.widget.TextView;
//
//
//public class ObjectDetectionActivity extends AbstractCameraXActivity<ObjectDetectionActivity.AnalysisResult> {
//    private Module mModule = null;
//    private ResultView mResultView;
//    private Button btn_next;
//    private TextView correct;
//
//    static class AnalysisResult {
//        private final ArrayList<Result> mResults;
//
//        public AnalysisResult(ArrayList<Result> results) {
//            mResults = results;
//        }
//    }
//
//    @Override
//    protected int getContentViewLayoutId() {
//        return R.layout.activity_object_second_detection;
//    }
//
//    @Override
//    protected TextureView getCameraPreviewTextureView() {
//        mResultView = findViewById(R.id.resultView2);  //findviewbyid = 리소스 id를 통해서 레이아웃에 있는 뷰 객체 중 일치하는 뷰를 가져오는 함수
//        return ((ViewStub) findViewById(R.id.object_detection_texture_view_stub2)) // texture view 의 id?  stub 은 어디서 온건가?
//                .inflate() // xml에 표기된 레이아웃들을 메모리에 객체화 시키는 행동
//                .findViewById(R.id.object_detection_texture_view);
//    }
//
//    @Override
//    protected void getIsEqual() {
//        String value = isEqual;
//        correct = findViewById(R.id.btn_next);
////        btn_next.setOnClickListener(v -> {
//////        String correct = str.substring(5,8);
//////        String ans = "fox";
//////        ans_correct = prac_test + "_" + correct + "_"+ "correct";
//////        ans_incorrect = prac_test + "_" + correct + "_" + "incorrect";
////            if (value == "correct") {
////                Intent intent = new Intent(ObjectDetectionActivity.this, Correct.class);
////                startActivity(intent);
////            } else {
////                Intent intent = new Intent(ObjectDetectionActivity.this, Incorrect.class);
////                startActivity(intent);
////            }
////        });
//    }
//
//    @Override
//    protected void applyToUiAnalyzeImageResult(AnalysisResult result) {
//        mResultView.setResults(result.mResults);
//        mResultView.invalidate(); // 호출한 클라이언트 윈도우 화면을 무효화, 원하는 윈도우 화면을 강제 갱신
//    }
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
//
//            }
//
//        }
//
//        return bitmap_new;
//
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
//        Image.Plane[] planes = image.getPlanes();
//        ByteBuffer yBuffer = planes[0].getBuffer();
//        ByteBuffer uBuffer = planes[1].getBuffer();
//        ByteBuffer vBuffer = planes[2].getBuffer();
//
//        int ySize = yBuffer.remaining();
//        int uSize = uBuffer.remaining();
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
//
//        final Tensor inputTensor = TensorImageUtils.bitmapToFloat32Tensor(resizedBitmap, PrePostProcessor.NO_MEAN_RGB, PrePostProcessor.NO_STD_RGB);
//        IValue[] outputTuple = mModule.forward(IValue.from(inputTensor)).toTuple();
//        final Tensor outputTensor = outputTuple[0].toTensor();
//        final float[] outputs = outputTensor.getDataAsFloatArray();
//
//        float imgScaleX = (float)bitmap.getWidth() / PrePostProcessor.mInputWidth;
//        float imgScaleY = (float)bitmap.getHeight() / PrePostProcessor.mInputHeight;
//        float ivScaleX = (float)mResultView.getWidth() / bitmap.getWidth();
//        float ivScaleY = (float)mResultView.getHeight() / bitmap.getHeight();
//
//        final ArrayList<Result> results = PrePostProcessor.outputsToNMSPredictions(outputs, imgScaleX, imgScaleY, ivScaleX, ivScaleY, 0, 0);
//        return new AnalysisResult(results);
//    }
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
//}
