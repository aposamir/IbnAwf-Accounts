package com.majid.ibnawf;
import android.app.*; import android.os.*; import android.webkit.*; import android.widget.*;
public abstract class BaseWebActivity extends Activity {
  static final String URL="https://ibn-awf.majid.cfd/app/";
  protected abstract String role();
  public void onCreate(Bundle b){ super.onCreate(b); setContentView(R.layout.activity_web); ((TextView)findViewById(R.id.title)).setText(role());
    WebView w=findViewById(R.id.web); WebSettings s=w.getSettings(); s.setJavaScriptEnabled(true); s.setDomStorageEnabled(true); s.setDatabaseEnabled(true); s.setAllowFileAccess(false); s.setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);
    CookieManager.getInstance().setAcceptCookie(true); CookieManager.getInstance().setAcceptThirdPartyCookies(w,true);
    w.setWebViewClient(new WebViewClient()); w.setWebChromeClient(new WebChromeClient()); w.loadUrl(URL);
  }
  @Override public void onBackPressed(){ WebView w=findViewById(R.id.web); if(w.canGoBack()) w.goBack(); else super.onBackPressed(); }
}
