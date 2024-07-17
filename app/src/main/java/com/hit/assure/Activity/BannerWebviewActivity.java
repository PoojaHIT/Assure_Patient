package com.hit.assure.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.hit.assure.R;

import java.util.Objects;

public class BannerWebviewActivity extends AppCompatActivity {

    private WebView m_webview;
    private ProgressDialog progressDialog;
    String strWebURL = "https://multitutor.in/home/quiz_page/2/";
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_webview);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        showProgressDialog();
        intia();
    }

    private void intia() {

        m_webview = findViewById(R.id.m_webview);

        if (getIntent() != null){
            url = getIntent().getStringExtra("url");
        }

        startWebView(url);

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void startWebView(String webURL) {
        m_webview.getSettings().setDomStorageEnabled(true);
        m_webview.getSettings().setLoadsImagesAutomatically(true);
        m_webview.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            m_webview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        m_webview.setWebChromeClient(new WebChromeClient());
        m_webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if (url.startsWith("tel:") || url.startsWith("whatsapp:")) {
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse(url));
//                    startActivity(intent);
//                    return true;
//                }
                return false;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Toast.makeText(BannerWebviewActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }

            public void onPageFinished(WebView view, String url) {
                hideProgressDialog();
                view.loadUrl("javascript:window.HTMLOUT.processHTML(document.getElementsByTagName('html')[0].innerHTML);");
            }
        });

        m_webview.loadUrl(webURL);
        m_webview.canGoBack();
        m_webview.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getAction() == MotionEvent.ACTION_UP
                        && m_webview.canGoBack()) {
                    m_webview.goBack();
                    return true;
                }
                return false;
            }
        });
    }






    private void showProgressDialog() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
            progressDialog.setContentView(R.layout.item_loader);
            Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    private void hideProgressDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}