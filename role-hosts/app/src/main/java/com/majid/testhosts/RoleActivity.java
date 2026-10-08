package com.majid.testhosts;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.net.Uri;

abstract class RoleActivity extends Activity {
    private WebView browser;
    private boolean roleSelected;
    protected abstract String roleId();
    protected abstract String roleTitle();
    private int dp(float v){return (int)(getResources().getDisplayMetrics().density*v+.5f);}
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout page=new LinearLayout(this);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        page.setBackgroundColor(Color.WHITE);

        LinearLayout bar=new LinearLayout(this);
        bar.setPadding(dp(8),dp(7),dp(8),dp(7));
        bar.setGravity(Gravity.CENTER_VERTICAL);
        bar.setBackgroundColor(Color.rgb(11,26,45));
        Button back=new Button(this);
        back.setText("‹ رجوع");back.setAllCaps(false);
        back.setOnClickListener(v->finish());
        bar.addView(back,new LinearLayout.LayoutParams(dp(80),dp(49)));
        TextView name=new TextView(this);
        name.setText(roleTitle());name.setTextSize(17);name.setTextColor(Color.WHITE);
        name.setGravity(Gravity.CENTER);
        bar.addView(name,new LinearLayout.LayoutParams(0,dp(49),1));
        Button reload=new Button(this);
        reload.setText("↻");reload.setTextSize(23);
        reload.setOnClickListener(v->browser.reload());
        bar.addView(reload,new LinearLayout.LayoutParams(dp(62),dp(49)));
        page.addView(bar);

        browser=new WebView(this);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.getSettings().setDomStorageEnabled(true);
        browser.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);
        browser.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        CookieManager.getInstance().setAcceptCookie(true);
        CookieManager.getInstance().setAcceptThirdPartyCookies(browser,false);
        browser.setWebChromeClient(new WebChromeClient());
        browser.setWebViewClient(new WebViewClient(){
            @Override public void onPageFinished(WebView view,String url){
                super.onPageFinished(view,url);
                selectRoleIfNeeded();
            }
            @Override public boolean shouldOverrideUrlLoading(WebView view,WebResourceRequest request){
                Uri uri=request.getUrl();
                String host=uri.getHost();
                String allowed=Uri.parse(BuildConfig.HOST_URL).getHost();
                if (host!=null && host.equalsIgnoreCase(allowed))return false;
                if (uri.getScheme()!=null && (uri.getScheme().equals("https")||uri.getScheme().equals("http")
                        ||uri.getScheme().equals("mailto")||uri.getScheme().equals("tel")||uri.getScheme().equals("whatsapp"))) {
                    try { startActivity(new Intent(Intent.ACTION_VIEW,uri)); }catch(Exception ignored){}
                }
                return true;
            }
        });
        page.addView(browser,new LinearLayout.LayoutParams(-1,0,1));
        page.addView(RoleBottomBar.create(this, getClass()));
        setContentView(page);
        browser.loadUrl(BuildConfig.HOST_URL);
    }

    private void selectRoleIfNeeded() {
        if(roleSelected)return;
        roleSelected=true;
        if(BuildConfig.FLAVOR.equals("nova")) {
            String id=roleId();
            // An approved, hardcoded role ID only; never interpolate untrusted text.
            String js="(function(){let n=0;const timer=setInterval(function(){"+
                "const b=document.querySelector('[data-demo=\\\""+id+"\\\"]');"+
                "if(b){clearInterval(timer);b.click()}else if(++n>80){clearInterval(timer)}"+
                "},350)})()";
            browser.evaluateJavascript(js,null);
        } else if(roleId().equals("owner")) {
            browser.evaluateJavascript("(function(){const b=document.getElementById('btnOwner');if(b)b.click()})()",null);
        }
    }
    @Override public void onBackPressed() {
        if(browser!=null&&browser.canGoBack())browser.goBack(); else super.onBackPressed();
    }
    @Override protected void onDestroy() {
        if(browser!=null){browser.destroy();browser=null;}
        super.onDestroy();
    }
}
