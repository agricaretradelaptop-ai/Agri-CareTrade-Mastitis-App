package com.agricaretrade.mastitis;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends Activity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new WebView(this);
        setContentView(webView);
        configureWebView();
        if (savedInstanceState == null) {
            webView.loadUrl("file:///android_asset/index.html");
        } else {
            webView.restoreState(savedInstanceState);
        }
    }

    private void configureWebView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(false);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        settings.setTextZoom(100);
        webView.addJavascriptInterface(new AndroidBridge(), "AgriAndroid");
        webView.setWebViewClient(new WebViewClient());
    }

    private void printCurrentPage() {
        PrintManager printManager = (PrintManager) getSystemService(PRINT_SERVICE);
        String jobName = "Agri CareTrade Mastitis Result";
        printManager.print(jobName, webView.createPrintDocumentAdapter(jobName), new PrintAttributes.Builder().build());
    }

    private void sendEmail(String to, String subject, String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + Uri.encode(to)));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No email app found.", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareText(String subject, String text) {
        Intent send = new Intent(Intent.ACTION_SEND);
        send.setType("text/plain");
        send.putExtra(Intent.EXTRA_SUBJECT, subject);
        send.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(send, "Share mastitis result"));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        webView.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        webView.evaluateJavascript("(function(){var a=document.querySelector('.screen.active');if(!a)return 'unknown';return a.id;})()", value -> {
            if (value != null && !value.equals("\"home\"") && !value.equals("null")) {
                webView.evaluateJavascript("show('home')", null);
            } else {
                MainActivity.super.onBackPressed();
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.removeJavascriptInterface("AgriAndroid");
            webView.destroy();
        }
        super.onDestroy();
    }

    public class AndroidBridge {
        @JavascriptInterface
        public void printPage() {
            runOnUiThread(() -> printCurrentPage());
        }

        @JavascriptInterface
        public void openEmail(String to, String subject, String body) {
            runOnUiThread(() -> sendEmail(to, subject, body));
        }

        @JavascriptInterface
        public void shareText(String subject, String text) {
            runOnUiThread(() -> shareText(subject, text));
        }
    }
}
