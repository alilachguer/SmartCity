package com.example.ali.smartcity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ArticleReader extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_reader);

        WebView webView = (WebView) findViewById(R.id.webview);
        webView.setWebChromeClient(new WebChromeClient());

        Intent intent = getIntent();
        webView.loadUrl(intent.getStringExtra("url"));
    }
}
