package com.boss.yobo1.first.fourth.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boss.yobo1.R;
import com.boss.yobo1.first.fourth.utils.UtilsMain;


/**
 * Created by YoBo on 2017/8/15.
 */

public class PageIndicator extends LinearLayout implements PageIndicatorInterface {

    private int tvBackgroud;
    private int tvColor;
    private float tvSize;
    private int tvLeft;
    private int tvRight;
    private int tvWidth;
    private int tvHeight;

    private int currentPage;

    private OnIndicatorItemClickListener listener;

    public PageIndicator(Context context) {
        super(context);
    }

    public PageIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.PageIndicator);
        tvBackgroud = t.getResourceId(R.styleable.PageIndicator_itemBackgroud, R.drawable.tab_selector);
        tvColor = t.getColor(R.styleable.PageIndicator_itemTvColor, Color.WHITE);
        tvWidth = UtilsMain.dip2px(context, t.getDimension(R.styleable.PageIndicator_itemWidth, 0));
        tvHeight = UtilsMain.dip2px(context, t.getDimension(R.styleable.PageIndicator_itemHeight, 0));
        tvSize = t.getDimension(R.styleable.PageIndicator_itemTvSize, 0);
        tvLeft = UtilsMain.dip2px(context, t.getDimension(R.styleable.PageIndicator_itemMarginLeft, 0));
        tvRight = UtilsMain.dip2px(context, t.getDimension(R.styleable.PageIndicator_itemMarginRight, 0));
        t.recycle();
    }

    public void setOnIndicatorClickListener(OnIndicatorItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void InitIndicatorItems(int itemsNumber) {

        removeAllViews();
        for (int i = 0; i < itemsNumber; i++) {
            TextView textView = new TextView(getContext());
            textView.setBackgroundResource(tvBackgroud);
            textView.setText(i + 1 + "");
            textView.setTextColor(tvColor);
            textView.setTextSize(tvSize);
            LayoutParams lp = new LayoutParams(tvWidth, tvHeight);
            lp.setMargins(tvLeft, 0, tvRight, 0);
            textView.setLayoutParams(lp);
            textView.setGravity(Gravity.CENTER);
            addView(textView);
            final int finalI = i;
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.IndicatorItemClick(finalI);
                    }
                }
            });

        }
    }

    @Override
    public void onPageSelected(int pageIndex) {
        TextView textView = (TextView) getChildAt(pageIndex);
        if (textView != null) {
            if (currentPage != pageIndex) {
                TextView beforeTv = (TextView) getChildAt(currentPage);
                beforeTv.setSelected(false);
                textView.setSelected(true);
                currentPage = pageIndex;
            } else {
                textView.setSelected(true);
            }
        }

    }

    public interface OnIndicatorItemClickListener {
        void IndicatorItemClick(int index);
    }


}
