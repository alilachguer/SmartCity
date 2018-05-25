package com.example.ali.smartcity.data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ali.smartcity.ArticleReader;
import com.example.ali.smartcity.ChatRoom;
import com.example.ali.smartcity.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GroupeAdapter extends RecyclerView.Adapter<NewsHolder> {

    private List<Groupe> groupes;
    private Context context;

    public GroupeAdapter(List<Groupe> articles, Context context) {
        this.groupes = articles;
        this.context = context;
    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(final NewsHolder holder, int position) {
        final Groupe groupe = groupes.get(position);
        holder.title.setText(groupe.getNom());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void OnClick(View view, int position) {
                Intent intent = new Intent(context, ChatRoom.class);
                intent.putExtra("groupe", groupe.getNom());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return groupes.size();
    }


}
