package com.majid.testhosts;

import android.app.Application;
import android.os.Build;
import android.webkit.WebView;

/**
 * Android WebView cookies/storage are isolated by process. Each role Activity
 * has its own android:process and an independent WebView data directory.
 */
public final class HostApp extends Application {
    @Override public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            String process = Application.getProcessName();
            int separator = process.lastIndexOf(':');
            if (separator >= 0 && separator < process.length() - 1) {
                WebView.setDataDirectorySuffix(process.substring(separator + 1));
            }
        }
    }
}
