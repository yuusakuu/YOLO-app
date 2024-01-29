package org.pytorch.demo.objectdetection;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerVierAdapterMove extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // adapter에 들어갈 list 입니다.
    private ArrayList<DataMove> listData = new ArrayList<>();
    private Context mContext;

//    public RecyclerVIewAdapter(Context context.ArrayList<String> list) {
//        this.list = list;
//        this.mContext = context
//    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alpha, parent, false);
        return new ViewHolderMove(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderMove)holder).onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    void addItem(DataMove data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }
}
