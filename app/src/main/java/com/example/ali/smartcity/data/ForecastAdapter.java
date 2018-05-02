package com.example.ali.smartcity.data;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.ali.smartcity.R;

import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private List<Forecast> forecasts;
    private Context context;

    public ForecastAdapter(List<Forecast> forecasts, Context context) {
        this.forecasts = forecasts;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.forecast_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Forecast forecast = forecasts.get(position);

        holder.textViewDescription.setText(forecast.getDescription());

        holder.textViewDate.setText(forecast.getDate());
        holder.textViewDay.setText(forecast.getDay());
        holder.textViewHigh.setText(Integer.toString(forecast.getHigh()));
        holder.textViewLow.setText(Integer.toString(forecast.getLow()));
        int ressource = this.context.getResources().getIdentifier("drawable/icon_"+forecast.getCode(),
                null, this.context.getPackageName());

        Drawable weatherIconDrawable = this.context.getResources().getDrawable(ressource);
        holder.code.setImageDrawable(weatherIconDrawable);
    }

    @Override
    public int getItemCount() {
        return forecasts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewDay, textViewDate, textViewHigh, textViewLow, textViewDescription;
        public ImageView code;

        public ViewHolder(View itemView) {
            super(itemView);
            code = (ImageView) itemView.findViewById(R.id.forecast_item_code);
            textViewDate = (TextView) itemView.findViewById(R.id.forecast_item_date);
            textViewDay = (TextView) itemView.findViewById(R.id.forecast_item_day);
            textViewHigh = (TextView) itemView.findViewById(R.id.forecast_item_high);
            textViewLow = (TextView) itemView.findViewById(R.id.forecast_item_low);
            textViewDescription = (TextView) itemView.findViewById(R.id.forecast_item_desc);

        }
    }
}
