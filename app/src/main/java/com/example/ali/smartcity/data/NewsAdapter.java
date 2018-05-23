package com.example.ali.smartcity.data;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ali.smartcity.ArticleReader;
import com.example.ali.smartcity.NewsActivity;
import com.example.ali.smartcity.R;

import org.w3c.dom.Text;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsHolder> {

    private List<Article> articles;
    private Context context;

    public NewsAdapter(List<Article> articles, Context context) {
        this.articles = articles;
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
        final Article article = articles.get(position);
        holder.title.setText(article.getTitle());
        holder.body.setText(article.getBody());
        holder.date.setText(article.getDate());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void OnClick(View view, int position) {
                Intent intent = new Intent(context, ArticleReader.class);
                intent.putExtra("url", article.getUrl().toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

}
