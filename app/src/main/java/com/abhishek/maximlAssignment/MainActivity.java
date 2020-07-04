package com.abhishek.maximlAssignment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "Mainactivity";
    private WebView webView;
    //array of commonly used url shorteners
    String[] urlCheckers = new String[]{
            "goo.gl",
            "bit.ly",
            "t.co",
            "tinyurl",
            "youtu.be",
            "ow.ly",
            "w.wiki"
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new MyWebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String url = null;
        if (intent.getAction().equals(Intent.ACTION_PROCESS_TEXT)) {

            url = Objects.requireNonNull(intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)).toString();
            Log.d(TAG, "onCreate: " + url);
            //check if url is shortened

            if (checkUrl(url)) {
                //add https:// to the url then load url
            
                url = String.format("https://%s", url);
                webView.loadUrl(url);

            } else if (url.contains("http") || url.contains("https")) {
                //load url if http or https is present
                webView.loadUrl(url);

            } else {

                Toast.makeText(this, "Kindly check the url you've selected!", Toast.LENGTH_SHORT).show();

            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private boolean checkUrl(String url) {
        return Arrays.stream(urlCheckers).anyMatch(url::contains);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}