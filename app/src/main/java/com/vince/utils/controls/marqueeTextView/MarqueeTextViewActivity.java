package com.vince.utils.controls.marqueeTextView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.vince.example.utils.R;

public class MarqueeTextViewActivity extends Activity {

    private MarqueeTextView mMqTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marquee_text_view);

        mMqTv = (MarqueeTextView)findViewById(R.id.menu_tree_title);
//        mMqTv.setSelected(true);
    }
}
