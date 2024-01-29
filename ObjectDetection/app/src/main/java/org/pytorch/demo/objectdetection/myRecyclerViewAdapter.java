package org.pytorch.demo.objectdetection;
import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
public class myRecyclerViewAdapter extends  RecyclerView.Adapter {

    /*
  어댑터의 동작원리 및 순서
  1.(getItemCount) 데이터 개수를 세어 어댑터가 만들어야 할 총 아이템 개수를 얻는다.
  2.(getItemViewType)[생략가능] 현재 itemview의 viewtype을 판단한다
  3.(onCreateViewHolder)viewtype에 맞는 뷰 홀더를 생성하여 onBindViewHolder에 전달한다.
  4.(onBindViewHolder)뷰홀더와 position을 받아 postion에 맞는 데이터를 뷰홀더의 뷰들에 바인딩한다.
  */

//    위의 3가지 메서드를 구현해야 합니다.
//
//    onCreateViewHolder : 뷰홀더를 생성(레이아웃 생성)
//    onBindViewHolder : 뷰홀더가 재활용될 때 실행되는 메서드
//    getItemCount : 아이템 개수를 조회
    String TAG = "RecyclerViewAdapter";

    //리사이클러뷰에 넣을 데이터 리스트
    ArrayList<DataModel> dataModels;
    Context context;
    // Item의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    // 직전에 클릭됐던 Item의 position
    private int prePosition = -1;

    //===== [Click 이벤트 구현을 위해 추가된 코드] ==========================
    // OnItemClickListener 인터페이스 선언
    public interface OnItemClickListener {
        void onItemClicked(int position, String data);
    }
    public interface OnItemClickListener2 {
        void onItemClicked2();
    }
    // OnItemClickListener 참조 변수 선언
    private OnItemClickListener itemClickListener;

    // OnItemClickListener 전달 메소드
    public void setOnItemClickListener (OnItemClickListener listener) {
        itemClickListener = listener;
    }

    //생성자를 통하여 데이터 리스트 context를 받음
    public myRecyclerViewAdapter(Context context, ArrayList<DataModel> dataModels) {
        this.dataModels = dataModels;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        //데이터 리스트의 크기를 전달해주어야 함
        //3) getItemCount() : 총 데이터의 갯수를 반환하도록 합니다.
        return dataModels.size();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        //1) onCreateViewHolder() : ViewHolder 객체를 생성하고 초기화 합니다.
        //자신이 만든 itemview를 inflate한 다음 뷰홀더 생성
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemview, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        //===== [Click 이벤트 구현을 위해 추가된 코드] =====================
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = "";
                int position = viewHolder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    data = viewHolder.getTextView().getText().toString();
                }
                itemClickListener.onItemClicked(position, data);
            }
        });
        //==================================================================

        //생선된 뷰홀더를 리턴하여 onBindViewHolder에 전달한다.
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder");
        // 2) onBindViewHolder() : 데이터를 가져와 ViewHolder안의 내용을 채워줍니다.
        MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.textView.setText(dataModels.get(position).getTitle());
        myViewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, position + "번째 텍스트 뷰 클릭", Toast.LENGTH_SHORT).show();
            }
        });

        myViewHolder.imageView.setImageResource(dataModels.get(position).image_path);
//        String text = dataModels.get(position);
//        holder.textView.setText(text);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        ImageView imageView2;
        LinearLayout linearlayout;
        OnViewHolderItemClickListener onViewHolderItemClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textview);
            imageView = itemView.findViewById(R.id.imageview);
            imageView2 = itemView.findViewById(R.id.imageview2);
            linearlayout = itemView.findViewById(R.id.linearlayout);

            linearlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onViewHolderItemClickListener.onViewHolderItemClick();
                }
            });
        }

        public TextView getTextView() {
            return textView;
        }

        public void setTextView(TextView textView) {
            this.textView = textView;
        }
    }
}
