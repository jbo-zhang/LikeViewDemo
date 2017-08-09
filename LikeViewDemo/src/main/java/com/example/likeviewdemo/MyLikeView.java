package com.example.likeviewdemo;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by zhangjinbo on 17-8-9.
 */

public class MyLikeView extends LinearLayout {

    public int marginBottom;
    private ImageView mImageView;

    public MyLikeView(Context context) {
        this(context, null);
    }

    public MyLikeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyLikeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mImageView = new ImageView(getContext());
        mImageView.setImageResource(R.drawable.dislike_1);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundResource(R.drawable.yellow_background);
        addView(mImageView, params);

        mImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator animator = ObjectAnimator.ofInt(MyLikeView.this, "marginBottom", 0, 500);
                animator.setDuration(2000);
                animator.start();
            }
        });


    }

    public int getMarginBottom() {
        return marginBottom;

    }

    public void setMarginBottom(int marginBottom) {
        Log.d("9095", "marginBottom: " + marginBottom);
        this.marginBottom = marginBottom;
        LayoutParams layoutParams = (LayoutParams) mImageView.getLayoutParams();
        layoutParams.setMargins(0, 0, 0, marginBottom);
        mImageView.setLayoutParams(layoutParams);
    }


}
