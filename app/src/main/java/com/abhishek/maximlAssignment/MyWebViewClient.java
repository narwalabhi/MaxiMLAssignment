package com.abhishek.maximlAssignment;

import android.net.http.SslError;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MyWebViewClient extends WebViewClient {

    private static final String TAG = "MyWebViewClient";
    private boolean loadingFailed = false;

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        view.loadUrl(request.getUrl().toString());
        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        Log.d(TAG, "onPageFinished: " + loadingFailed);
        if (loadingFailed) {
            Toast.makeText(view.getContext(), "Check the url you've copied!", Toast.LENGTH_SHORT).show();
            loadingFailed = true;
        }
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        //set true if error received
        loadingFailed = true;
        Log.d(TAG, "onReceivedError: " + loadingFailed);
    }

}
