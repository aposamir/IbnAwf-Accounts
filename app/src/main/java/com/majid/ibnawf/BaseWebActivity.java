package com.majid.ibnawf;

import android.app.*;
import android.os.*;
import android.webkit.*;
import android.widget.*;
import android.content.*;
import android.net.Uri;
import android.view.View;
import android.graphics.Bitmap;
import android.net.http.SslError;

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
    s.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);

    CookieManager cm = CookieManager.getInstance();
    cm.setAcceptCookie(true);
    cm.setAcceptThirdPartyCookies(w, true);

    w.setWebChromeClient(new WebChromeClient());
    w.setWebViewClient(new WebViewClient() {
      @Override public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        Uri u = request.getUrl();
        String scheme = u.getScheme();
        if ("http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme)) return false;
        try { startActivity(new Intent(Intent.ACTION_VIEW, u)); } catch (Exception ignored) {}
        return true;
      }
      @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Uri u = Uri.parse(url);
        String scheme = u.getScheme();
        if ("http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme)) return false;
        try { startActivity(new Intent(Intent.ACTION_VIEW, u)); } catch (Exception ignored) {}
        return true;
      }
      @Override public void onPageFinished(WebView view, String url) {
        CookieManager.getInstance().flush();
        super.onPageFinished(view, url);
      }
      @Override public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.cancel();
      }
    });

    // Try embedded WebView first. If this device/server combination closes the
    // connection, the error page offers a one-tap Chrome fallback.
    w.loadUrl(URL);
  }

  @Override public void onBackPressed() {
    WebView w = findViewById(R.id.web);
    if (w.canGoBack()) w.goBack(); else super.onBackPressed();
  }
}
