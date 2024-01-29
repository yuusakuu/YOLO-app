// Copyright (c) 2020 Facebook, Inc. and its affiliates.
// All rights reserved.
//
// This source code is licensed under the BSD-style license found in the
// LICENSE file in the root directory of this source tree.

package org.pytorch.demo.objectdetection;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;


public class ResultView extends View {

    private final static int TEXT_X = 40; // 떨어진 위치?
    private final static int TEXT_Y = 35;
    private final static int TEXT_WIDTH = 260;
    private final static int TEXT_HEIGHT = 50;

    private Paint mPaintRectangle; // 변수선언? 자료형? 변수이름?
    private Paint mPaintText;
    private ArrayList<Result> mResults;
    private TextView detected;

    public ResultView(Context context) {
        super(context);
    } // 무슨의미?

    public ResultView(Context context, AttributeSet attrs){
        super(context, attrs);
        mPaintRectangle = new Paint();
        mPaintRectangle.setColor(Color.YELLOW);
        mPaintText = new Paint();
    }

    @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);

                if (mResults == null) return;
                for (Result result : mResults) { // for 문 돌면서 mResults 안의 요소를 result로 내옴
                    mPaintRectangle.setStrokeWidth(5);
                    mPaintRectangle.setStyle(Paint.Style.STROKE); //  stroke = 채우기 없음
            canvas.drawRect(result.rect.left, 1800 - result.rect.top, result.rect.left + 100, 1800 - (result.rect.top+ 100), mPaintRectangle);
//                    canvas.drawRect(result.rect.left, result.rect.top, result.rect.left, (result.rect.top), mPaintRectangle);
                    // canvas.drawRect 함수, (left, top, right, bottom, paint), rect.left = 사각형의 왼쪽 x 축을 가져옴,  result = 사각형 요소?

            Path mPath = new Path();
            RectF mRectF = new RectF(result.rect.left,result.rect.top, result.rect.left + TEXT_WIDTH,   result.rect.top - TEXT_HEIGHT);
            mPath.addRect(mRectF, Path.Direction.CW);
            mPaintText.setColor(Color.MAGENTA);
            canvas.drawPath(mPath, mPaintText);

            mPaintText.setColor(Color.MAGENTA); // 글자 색상
            mPaintText.setStrokeWidth(0);
            mPaintText.setStyle(Paint.Style.FILL); // 글자 채우기
            mPaintText.setTextSize(50);
            canvas.drawText(String.format("%s %.2f", PrePostProcessor.mClasses[result.classIndex], result.score), result.rect.left + TEXT_X, 1800 - (result.rect.top + TEXT_Y), mPaintText);
//            canvas.drawText(String.format("%s %.2f", PrePostProcessor.mClasses[result.classIndex], result.score), result.rect.left + TEXT_X, (result.rect.top + TEXT_Y), mPaintText);
            // PrePostProcessor.mClasses[result.classIndex]
        }
    }

    public void setResults(ArrayList<Result> results3) {  // results3 는 그냥 함수에 사용하듯 아무거나 사용해도 됨
        mResults = results3;
//        StringBuilder stringBuilder = new StringBuilder();
//        for (Result result : mResults) {
//            stringBuilder.append(result).append("\n");
//        }
//        String textToDisplay = stringBuilder.toString();
//
//        detected = findViewById(R.id.detected);
//        detected.setText(textToDisplay);
    }
//    public void viewResults {
//        mResults;
//    }
}
//
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
//        if (mResults == null) return;
//        for (Result result : mResults) { // for 문 돌면서 mResults 안의 요소를 result로 내옴
//            mPaintRectangle.setStrokeWidth(5);
//            mPaintRectangle.setStyle(Paint.Style.STROKE); //  stroke = 채우기 없음
//            canvas.drawRect(result.rect.left, 1800 - result.rect.top, result.rect.left + 100, 1800 - (result.rect.top+ 100), mPaintRectangle);
//            // canvas.drawRect 함수, (left, top, right, bottom, paint), rect.left = 사각형의 왼쪽 x 축을 가져옴
//            // result = 사각형 요소?
//
//            Path mPath = new Path();
//            RectF mRectF = new RectF(result.rect.left,result.rect.top, result.rect.left + TEXT_WIDTH,   result.rect.top - TEXT_HEIGHT);
//            mPath.addRect(mRectF, Path.Direction.CW);
//            mPaintText.setColor(Color.MAGENTA);
//            canvas.drawPath(mPath, mPaintText);
//
//            mPaintText.setColor(Color.MAGENTA); // 글자 색상
//            mPaintText.setStrokeWidth(0);
//            mPaintText.setStyle(Paint.Style.FILL); // 글자 채우기
//            mPaintText.setTextSize(50);
//            canvas.drawText(String.format("%s %.2f", PrePostProcessor.mClasses[result.classIndex], result.score), result.rect.left + TEXT_X, 1800 - (result.rect.top + TEXT_Y), mPaintText);
//            // PrePostProcessor.mClasses[result.classIndex] 가 출력되는 글자를 나타내고 있음
//        }
//    }
//
//    public void setResults(ArrayList<Result> results) {
//        mResults = results;
//    }  // setResults 함수는 arraylist에 mResults 값을 넣어주는 것?, ObjectDetectionActivity에서 setResults 가 활용되고 있음