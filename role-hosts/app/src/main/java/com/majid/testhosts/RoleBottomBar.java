package com.majid.testhosts;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Fixed bottom navigation shared by the home screen and every independently
 * sandboxed role WebView. A two-row layout shows every NOVA role on small
 * Android phones without tiny horizontal-scroll targets.
 */
final class RoleBottomBar {
    private static final int DEEP_TEAL = Color.rgb(9, 106, 100);
    private static final int INK = Color.rgb(50, 76, 80);
    private static final int GOLD = Color.rgb(207, 164, 76);
    private static final int PALE = Color.rgb(245, 248, 246);

    private RoleBottomBar() {}

    private static int dp(Activity activity, float n) {
        return (int)(activity.getResources().getDisplayMetrics().density * n + 0.5f);
    }

    static View create(Activity activity, Class<? extends Activity> active) {
        LinearLayout footer = new LinearLayout(activity);
        footer.setOrientation(LinearLayout.VERTICAL);
        footer.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        footer.setBackgroundColor(Color.WHITE);
        footer.setElevation(dp(activity, 10));
        footer.setPadding(dp(activity, 8), dp(activity, 7), dp(activity, 8), dp(activity, 7));

        if (BuildConfig.FLAVOR.equals("nova")) {
            // Two rows, not a hidden horizontally scrolling list: all 7 destinations visible.
            addRow(activity, footer, active, new Entry[]{
                new Entry("⌂", "الرئيسية", MainActivity.class),
                new Entry("⚙", "المدير", NovaAdminActivity.class),
                new Entry("▣", "جرسون ١", NovaWaiter1Activity.class),
                new Entry("▣", "جرسون ٢", NovaWaiter2Activity.class)
            });
            addRow(activity, footer, active, new Entry[]{
                new Entry("♨", "المطبخ", NovaKitchenActivity.class),
                new Entry("☕", "البوفيه", NovaBarActivity.class),
                new Entry("▤", "المحاسب", NovaCashierActivity.class)
            });
        } else {
            addRow(activity, footer, active, new Entry[]{
                new Entry("⌂", "الرئيسية", MainActivity.class),
                new Entry("◆", "المالك", FirasOwnerActivity.class),
                new Entry("◇", "الزبون", FirasCustomerActivity.class)
            });
        }
        return footer;
    }

    private static void addRow(Activity activity, LinearLayout footer,
                               Class<? extends Activity> active, Entry[] entries) {
        LinearLayout row = new LinearLayout(activity);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER);
        row.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        LinearLayout.LayoutParams rowParams =
            new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dp(activity, 55));
        footer.addView(row, rowParams);

        for (Entry entry : entries) {
            boolean selected = active.equals(entry.activity);
            LinearLayout tab = new LinearLayout(activity);
            tab.setOrientation(LinearLayout.VERTICAL);
            tab.setGravity(Gravity.CENTER);
            tab.setClickable(true);
            tab.setFocusable(true);
            tab.setContentDescription("فتح " + entry.label);
            tab.setPadding(dp(activity, 1), dp(activity, 3), dp(activity, 1), dp(activity, 3));

            GradientDrawable background = new GradientDrawable();
            background.setColor(selected ? DEEP_TEAL : PALE);
            background.setCornerRadius(dp(activity, 12));
            background.setStroke(dp(activity, 1),
                selected ? DEEP_TEAL : Color.rgb(232, 236, 235));
            tab.setBackground(background);
            tab.setElevation(selected ? dp(activity, 3) : 0);

            TextView glyph = new TextView(activity);
            glyph.setText(entry.icon);
            glyph.setTextSize(19);
            glyph.setGravity(Gravity.CENTER);
            glyph.setTextColor(selected ? GOLD : DEEP_TEAL);
            tab.addView(glyph, new LinearLayout.LayoutParams(-1, dp(activity, 24)));

            TextView name = new TextView(activity);
            name.setText(entry.label);
            name.setTextSize(10);
            name.setSingleLine(true);
            name.setGravity(Gravity.CENTER);
            name.setTextColor(selected ? Color.WHITE : INK);
            name.setTypeface(null, selected ? 1 : 0);
            tab.addView(name, new LinearLayout.LayoutParams(-1, dp(activity, 18)));

            LinearLayout.LayoutParams item =
                new LinearLayout.LayoutParams(0, dp(activity, 49), 1f);
            item.setMargins(dp(activity, 3), dp(activity, 2), dp(activity, 3), dp(activity, 2));
            row.addView(tab, item);
            tab.setOnClickListener(view -> {
                if (activity.getClass().equals(entry.activity)) return;
                Intent intent = new Intent(activity, entry.activity);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                activity.startActivity(intent);
            });
        }
    }

    private static final class Entry {
        final String icon;
        final String label;
        final Class<? extends Activity> activity;
        Entry(String icon, String label, Class<? extends Activity> activity) {
            this.icon = icon;
            this.label = label;
            this.activity = activity;
        }
    }
}
