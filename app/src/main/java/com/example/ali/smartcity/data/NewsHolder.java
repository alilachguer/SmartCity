package com.example.ali.smartcity.data;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.ali.smartcity.R;

public class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView title, body, date;
    ItemClickListener itemClickListener;

    public NewsHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.news_item_title);
        body = (TextView) itemView.findViewById(R.id.news_item_body);
        date = (TextView) itemView.findViewById(R.id.news_item_date);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.OnClick(view, getAdapterPosition());
    }
}
