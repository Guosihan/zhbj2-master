package com.example.gsh.zhbj2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.webkit.WebView;

/**
 * Created by gsh on 2017/5/22.
 */
public class ReadActivity extends AppCompatActivity {
    public static   final  String URL_EXTRA = "URL_EXTRA";
    private String url;
    private WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        webView= (WebView) findViewById(R.id.webview);
        url = getIntent().getStringExtra(URL_EXTRA);
        if(url != null && url.length()>0){
            webView.loadUrl(url);
            setFullScreen();
        }
    }
    private void setFullScreen() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
