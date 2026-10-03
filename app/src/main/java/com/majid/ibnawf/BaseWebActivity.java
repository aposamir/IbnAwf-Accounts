package com.majid.ibnawf;

import android.app.*;
import android.os.*;
import android.webkit.*;
import android.widget.*;
import android.net.http.SslError;
import android.graphics.Bitmap;

public abstract class BaseWebActivity extends Activity {
  static final String URL = "https://ibn-awf.majid.cfd/app/";
  protected abstract String role();

  @Override public void onCreate(Bundle b) {
    super.onCreate(b);
    setContentView(R.layout.activity_web);
    ((TextView)findViewById(R.id.title)).setText(role());

    WebView w = findViewById(R.id.web);
    WebSettings s = w.getSettings();
    s.setJavaScriptEnabled(true);
    s.setDomStorageEnabled(true);
    s.setDatabaseEnabled(true);
    s.setAllowFileAccess(true);
    s.setAllowContentAccess(true);
    s.setLoadsImagesAutomatically(true);
    s.setUseWideViewPort(true);
    s.setLoadWithOverviewMode(false);
    s.setJavaScriptCanOpenWindowsAutomatically(true);
    s.setMediaPlaybackRequiresUserGesture(false);
    s.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
    s.setUserAgentString(s.getUserAgentString() + " Chrome/140.0.0.0 Mobile");

    CookieManager cm = CookieManager.getInstance();
    cm.setAcceptCookie(true);
    cm.setAcceptThirdPartyCookies(w, true);

    w.setWebChromeClient(new WebChromeClient());
    w.setWebViewClient(new WebViewClient() {
      @Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
      }
      @Override public void onPageFinished(WebView view, String url) {
        CookieManager.getInstance().flush();
        super.onPageFinished(view, url);
      }
      @Override public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.cancel();
      }
    });
    w.loadUrl(URL);
  }

  @Override public void onBackPressed() {
    WebView w = findViewById(R.id.web);
    if (w.canGoBack()) w.goBack(); else super.onBackPressed();
  }
}
