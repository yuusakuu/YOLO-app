package org.pytorch.demo.objectdetection;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderMove extends  RecyclerView.ViewHolder {

    TextView tv_movie_title;
    ImageView iv_movie;


    public ViewHolderMove(@NonNull View itemView) {
        super(itemView);

        iv_movie = itemView.findViewById(R.id.iv_movie);

//        itemView.setClickable(true);
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int pos = getAdapterPosition();
//                if (pos != RecyclerView.NO_POSITION) {
//                    Intent intent = new Intent(mContext.NewAct.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra("TEXT", list.get(pos));
//                    mContext.startActivity(intent);
//            }
//        });
//
//
    }

        public void onBind (DataMove data){
            iv_movie.setImageResource(data.getImage());
    }
}


