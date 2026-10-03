package com.majid.ibnawf;
import android.app.Activity; import android.os.Bundle; import android.widget.TextView;
import org.mozilla.geckoview.GeckoRuntime; import org.mozilla.geckoview.GeckoSession; import org.mozilla.geckoview.GeckoView;
public abstract class BaseWebActivity extends Activity {
 private static GeckoRuntime runtime; private GeckoSession session;
 protected abstract String role(); protected String url(){return "https://ibn-awf.majid.cfd/app/";}
 @Override public void onCreate(Bundle state){super.onCreate(state);setContentView(R.layout.activity_web);((TextView)findViewById(R.id.title)).setText(role());GeckoView view=findViewById(R.id.gecko);session=new GeckoSession();session.setContentDelegate(new GeckoSession.ContentDelegate(){});if(runtime==null)runtime=GeckoRuntime.create(this);session.open(runtime);view.setSession(session);session.loadUri(url());}
 @Override public void onBackPressed(){if(session!=null)session.goBack();else super.onBackPressed();}
}