package com.example.ali.smartcity.data;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ali.smartcity.ArticleReader;
import com.example.ali.smartcity.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AdsAdapter extends RecyclerView.Adapter<AdsAdapter.PubHolder>{

    private List<Pub> pubs;
    private Context context;

    public AdsAdapter(FragmentActivity activity, ArrayList<Pub> pubs) {
        this.pubs = pubs;
        this.context = activity;
    }

    @Override
    public PubHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pub_item, parent, false);
        return new PubHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PubHolder holder, int position) {
        final Pub pub = pubs.get(position);
        holder.title.setText(pub.getNom());
        holder.desc.setText(pub.getDescription());
        new DownloadImageTask(holder.image).execute(pub.getImage());

    }

    @Override
    public int getItemCount() {
        return pubs.size();
    }

    public class PubHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title, desc;
        ImageView image;
        ItemClickListener itemClickListener;

        public PubHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.pub_title);
            image = (ImageView) itemView.findViewById(R.id.pub_image);
            desc = (TextView) itemView.findViewById(R.id.pub_desc);

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

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
