package com.majid.ibnawf;
import android.app.*; import android.os.*; import android.webkit.*;
public class IbnAwfApp extends Application {
 public void onCreate(){ super.onCreate(); if(Build.VERSION.SDK_INT>=28){ String p=Application.getProcessName(); int i=p.indexOf(':'); if(i>=0) WebView.setDataDirectorySuffix(p.substring(i+1)); } }
}
