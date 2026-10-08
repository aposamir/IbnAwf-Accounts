package com.majid.testhosts;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public final class MainActivity extends Activity {
    private static final int BG = Color.rgb(10,23,41);
    private static final int GOLD = Color.rgb(222,191,130);
    private int dp(float n) { return (int)(getResources().getDisplayMetrics().density*n+0.5f); }

    @Override protected void onCreate(Bundle state) {
        super.onCreate(state);
        boolean nova = BuildConfig.FLAVOR.equals("nova");
        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.setBackgroundColor(BG);
        LinearLayout page = new LinearLayout(this);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setPadding(dp(22),dp(36),dp(22),dp(22));
        page.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        scroll.addView(page);

        TextView symbol = label("✦",36,GOLD);
        symbol.setGravity(Gravity.CENTER);
        page.addView(symbol);
        TextView title = label(nova ? "NOVA Restaurant OS" : "منشأة فراس عبد الرزاق كلكل",24,Color.WHITE);
        title.setGravity(Gravity.CENTER);
        page.addView(title);
        TextView subtitle = label("مركز الاختبار • جلسة مستقلة لكل دور",15,Color.rgb(169,184,203));
        subtitle.setPadding(0,dp(12),0,dp(26));
        subtitle.setGravity(Gravity.CENTER);
        page.addView(subtitle);
        if (nova) {
            addPair(page,"⚙  المدير",NovaAdminActivity.class,"▣  الجرسون الأول",NovaWaiter1Activity.class);
            addPair(page,"▣  الجرسون الثاني",NovaWaiter2Activity.class,"♨  المطبخ",NovaKitchenActivity.class);
            addPair(page,"☕  البوفيه",NovaBarActivity.class,"▤  المحاسب",NovaCashierActivity.class);
        } else {
            addPair(page,"◆  المالك",FirasOwnerActivity.class,"◇  الزبون",FirasCustomerActivity.class);
        }
        TextView description=label(
                nova ? "كل دور يفتح جلسة مستقلة تلقائيًا بحساب التجربة الخاص به." :
                       "لكل بوابة جلسة مستقلة. يُسجّل المالك والزبون الدخول أول مرة بحسابيهما الحقيقيين أو التجريبيين.",
                13,Color.rgb(167,180,199));
        description.setPadding(dp(12),dp(32),dp(12),0);
        description.setGravity(Gravity.CENTER);
        page.addView(description);
        setContentView(scroll);
    }
    private TextView label(String text,int size,int color) {
        TextView t=new TextView(this);t.setText(text);t.setTextColor(color);t.setTextSize(size);
        t.setGravity(Gravity.CENTER_VERTICAL);return t;
    }
    private void addPair(LinearLayout page,String a,Class<? extends Activity> ca,String b,Class<? extends Activity> cb) {
        LinearLayout row=new LinearLayout(this);row.setOrientation(LinearLayout.HORIZONTAL);
        row.setPadding(0,0,0,dp(12));page.addView(row);
        addRole(row,a,ca);addRole(row,b,cb);
    }
    private void addRole(LinearLayout row,String text,Class<? extends Activity> target) {
        Button b=new Button(this);b.setText(text);b.setTextSize(15);b.setTextColor(GOLD);
        b.setAllCaps(false);b.setMinHeight(dp(92));
        GradientDrawable bg=new GradientDrawable();
        bg.setColor(Color.rgb(25,44,68));bg.setCornerRadius(dp(15));
        bg.setStroke(dp(1),Color.rgb(58,77,101));b.setBackground(bg);
        LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(0,dp(96),1f);
        p.setMargins(dp(4),0,dp(4),0);row.addView(b,p);
        b.setOnClickListener(v->startActivity(new Intent(this,target)));
    }
}
