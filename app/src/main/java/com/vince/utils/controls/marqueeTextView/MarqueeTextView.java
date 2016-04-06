package com.vince.utils.controls.marqueeTextView;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * decription ：
 * author ： zhua
 * Created at 2016/4/6.
 */
public class MarqueeTextView extends TextView {

    public MarqueeTextView(Context con) {
        super(con);
    }
    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    public boolean isFocused() {
        return true;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction,
                                  Rect previouslyFocusedRect) {
      // super.onFocusChanged(focused,direction,previouslyFocusedRect);
    }
}
