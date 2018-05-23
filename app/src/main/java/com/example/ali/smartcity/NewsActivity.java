package com.example.ali.smartcity;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ali.smartcity.data.Article;
import com.example.ali.smartcity.data.ForecastAdapter;
import com.example.ali.smartcity.data.NewsAdapter;
import com.google.android.gms.common.api.Result;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.VISIBLE;

public class NewsActivity extends AppCompatActivity {

    Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    TextView result;
    String query = "Sport";
    String lang = "eng";
    List<Article> articles = new ArrayList<Article>();
    RecyclerView newsList;
    RecyclerView.Adapter mAdapter;
    EventRegistry eventRegistry;

    String allArticlesURL;
    String articlesURL;

    static final String API_KEY = "cf2b2191-d9f3-49b4-b52d-67a4a12d82a3";
    static final String API_URL = "http://eventregistry.org/json/article?";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            firebaseUser = firebaseAuth.getCurrentUser();
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference().child("users");
        }

        RelativeLayout news = (RelativeLayout) findViewById(R.id.news_activity);
        toolbar = news.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.news);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        newsList = (RecyclerView) findViewById(R.id.news_list);
        //newsList.setHasFixedSize(true);
        newsList.setLayoutManager(new LinearLayoutManager(this));

        allArticlesURL = API_URL + "query=%7B%22%24query%22%3A%7B%22lang%22%3A%22" + lang
                + "%22%7D%7D&action=getArticles&resultType=articles&articlesSortBy=date&articlesCount=" +
                "100&articlesArticleBodyLen=-1&apiKey=" + API_KEY;
        articlesURL = API_URL + "query={%22%24query%22%3A{%22%24and%22%3A" +
                "[{%22conceptUri%22%3A{%22%24and%22%3A[%22http%3A%2F%2Fen.wikipedia.org%2Fwiki%2F" +
                query+"%22]}}%2C{%22lang%22%3A%22"+lang+"%22}]}}&action=getArticles&resultType=articles&articlesSortBy" +
                "=date&articlesCount=100&articlesArticleBodyLen=-1&apiKey=" + API_KEY;

        new EventRegistry("eng", true).execute();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.news_categories, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.item_english:
                lang = "eng";
                articles = new ArrayList<Article>();
                new EventRegistry(lang, true).execute();
                break;
            case R.id.item_french:
                lang = "fra";
                articles = new ArrayList<Article>();
                new EventRegistry(lang, true).execute();
                break;
            case R.id.item_all:
                articles = new ArrayList<Article>();
                new EventRegistry(lang, true).execute();
                break;
            case R.id.item_science:
                query = "Science";
                articles = new ArrayList<Article>();
                new EventRegistry(query, lang, false).execute();
                break;
            case R.id.item_sport:
                query = "Sport";
                articles = new ArrayList<Article>();
                new EventRegistry(query, lang, false).execute();
                break;
            case R.id.item_business:
                query = "Business";
                articles = new ArrayList<Article>();
                new EventRegistry(query, lang, false).execute();
                break;
            case R.id.item_technology:
                query = "Technology";
                articles = new ArrayList<Article>();
                new EventRegistry(query, lang, false).execute();
                break;
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    class EventRegistry extends AsyncTask<Void, Void, String>{
        String query;
        String lang;
        Boolean all;

        public EventRegistry(String lang, Boolean all){
            this.lang = lang;
            this.all = all;
        }

        public EventRegistry(String topic, String lang, Boolean all){
            this.lang = lang;
            this.all = all;
            this.query = topic;
        }

        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(Void... voids) {
            if(!isCancelled()){
                try {
                    URL url = new URL(API_URL + "query=%7B%22%24query%22%3A%7B%22lang%22%3A%22" + this.lang
                            + "%22%7D%7D&action=getArticles&resultType=articles&articlesSortBy=date&articlesCount=" +
                            "100&articlesArticleBodyLen=-1&apiKey=" + API_KEY);
                    if(!all)
                        url = new URL(API_URL + "query={%22%24query%22%3A{%22%24and%22%3A" +
                                "[{%22conceptUri%22%3A{%22%24and%22%3A[%22http%3A%2F%2Fen.wikipedia.org%2Fwiki%2F" +
                                this.query+"%22]}}%2C{%22lang%22%3A%22"+this.lang+"%22}]}}&action=getArticles&" +
                                "resultType=articles&articlesSortBy=date&articlesCount=100&articlesArticleBodyLen=-1&apiKey=" + API_KEY);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }
                        bufferedReader.close();
                        return stringBuilder.toString();
                    }
                    finally{
                        urlConnection.disconnect();
                    }
                }
                catch(Exception e) {
                    Log.e("ERROR", e.getMessage(), e);
                    return null;
                }
            }else {
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR";
            }
            Log.i("INFO", response);
            //result.setText(response);
            //articles = new ArrayList<Article>();
            try {
                JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
                JSONObject article = object.optJSONObject("articles");
                JSONArray array = article.getJSONArray("results");

                if(array != null){
                    for (int i=0; i< array.length(); i++){
                        Article art = new Article(array.getJSONObject(i).optString("title"),
                                array.getJSONObject(i).optString("body"),
                                array.getJSONObject(i).optString("url"),
                                array.getJSONObject(i).optString("date")
                                );
                        articles.add(art);
                    }
                }
                mAdapter = new NewsAdapter(articles, getBaseContext());
                newsList.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                //result.setText(titles.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getApplicationContext(), "canceelled!!", Toast.LENGTH_SHORT).show();
        }
    }
}
