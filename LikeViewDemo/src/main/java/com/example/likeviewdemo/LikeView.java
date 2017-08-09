package com.example.likeviewdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by zhangjinbo on 17-8-9.
 */

public class LikeView extends LinearLayout implements Animator.AnimatorListener {

    private AnimationDrawable mAnimLike, mAnimDislike;
    private Paint mPaint;
    private int defaultGravity = Gravity.CENTER_HORIZONTAL;
    private int like = 10, dislike = 20;
    private float fLike, fDis;
    private ImageView imageLike;
    private TextView likeNum;
    private int defaultTextColor = Color.WHITE;
    private TextView likeText;
    private String defaultLike = "喜欢";
    private ImageView imageDis;
    private TextView disNum;
    private TextView disText;
    private String defaultDis = "无感";
    private LinearLayout likeBack;
    private LinearLayout disBack;
    private int defaultSize = dip2px(getContext(), 25);
    private LinearLayout likeAll;
    private LinearLayout disAll;
    private int defaultBottom = 70;
    private int dividerMargin = 20;
    private int type;
    private ValueAnimator animatorBack;
    private boolean isClose;
    private String defaultShadow = "#7F484848";

    private int dip2px(Context context, int dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public LikeView(Context context) {
        this(context, null);
    }

    public LikeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LikeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        bindListener();
    }

    private void bindListener() {
        imageDis.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 1;
                animBack(); //拉伸背景
                setVisibities(VISIBLE); //显示文字

                //切换背景色
                setBackgroundColor(Color.parseColor(defaultShadow));
                likeBack.setBackgroundResource(R.drawable.white_background);
                disBack.setBackgroundResource(R.drawable.yellow_background);

                imageLike.setBackgroundDrawable(null);
                imageLike.setBackgroundResource(R.drawable.animation_like);
                mAnimLike = (AnimationDrawable) imageLike.getBackground();
            }
        });

        imageLike.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 0;
                animBack(); //拉伸背景
                setVisibities(VISIBLE); //显示文字

                //切换背景色
                setBackgroundColor(Color.parseColor(defaultShadow));
                disBack.setBackgroundResource(R.drawable.white_background);
                likeBack.setBackgroundResource(R.drawable.yellow_background);

                imageDis.setBackgroundDrawable(null);
                imageDis.setBackgroundResource(R.drawable.animation_dislike);
                mAnimDislike = (AnimationDrawable) imageDis.getBackground();
            }
        });

    }

    //背景伸展动画
    private void animBack() {
        //动画执行中不能点击
        imageDis.setClickable(false);
        imageLike.setClickable(false);

        final int max = Math.max(like * 4, dislike * 4);

        animatorBack = ValueAnimator.ofInt(5, max);
        animatorBack.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int margin = (int) valueAnimator.getAnimatedValue();
                LayoutParams paramsLike = (LayoutParams) imageLike.getLayoutParams();
                paramsLike.bottomMargin = margin;

                if(margin <= like * 4) {
                    imageLike.setLayoutParams(paramsLike);
                }

                if(margin <= dislike * 4) {
                    imageDis.setLayoutParams(paramsLike);
                }
            }
        });

        isClose = false;
        animatorBack.addListener(this);
        animatorBack.setDuration(500);
        animatorBack.start();
    }

    public void setBackUp() {
        final int max = Math.max(like * 4, dislike * 4);
        animatorBack = ValueAnimator.ofInt(max, 5);
        animatorBack.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int margin = (int) valueAnimator.getAnimatedValue();
                LayoutParams paramsLike = (LayoutParams) imageLike.getLayoutParams();
                paramsLike.bottomMargin = margin;

                if(margin <= like * 4) {
                    imageLike.setLayoutParams(paramsLike);
                }

                if(margin <= dislike * 4) {
                    imageDis.setLayoutParams(paramsLike);
                }
            }
        });

        animatorBack.addListener(this);
        animatorBack.setDuration(500);
        animatorBack.start();
    }

    private void init() {
        this.removeAllViews();
        setOrientation(HORIZONTAL);
        setGravity(defaultGravity | Gravity.BOTTOM);
        setBackgroundColor(Color.TRANSPARENT);

        //设置百分比
        float count = like + dislike;
        fLike = like / count;
        fDis = dislike / count;
        like = (int) (fLike * 100);
        dislike = (int) (fDis * 100);

        //初始化图片
        imageLike = new ImageView(getContext());
        //添加动画资源，获得帧动画
        imageLike.setBackgroundResource(R.drawable.animation_like);
        mAnimLike = (AnimationDrawable) imageLike.getBackground();

        //初始化文字
        likeNum = new TextView(getContext());
        likeNum.setText(like + "%");
        likeNum.setTextColor(defaultTextColor);

        TextPaint likeNumPaint = likeNum.getPaint();
        likeNumPaint.setFakeBoldText(true);
        likeNum.setTextSize(20f);
        likeText = new TextView(getContext());
        likeText.setText(defaultLike);
        likeText.setTextColor(defaultTextColor);

        imageDis = new ImageView(getContext());
        imageDis.setBackgroundResource(R.drawable.animation_dislike);
        mAnimDislike = (AnimationDrawable) imageDis.getBackground();

        disNum = new TextView(getContext());
        disNum.setText(dislike  + "%");
        disNum.setTextColor(defaultTextColor);
        TextPaint disNumPaint = disNum.getPaint();
        disNumPaint.setFakeBoldText(true);
        disNum.setTextSize(20f);

        disText = new TextView(getContext());
        disText.setText(defaultDis);
        disText.setTextColor(defaultTextColor);

        //初始化布局
        likeBack = new LinearLayout(getContext());
        disBack = new LinearLayout(getContext());
        LayoutParams params2 = new LayoutParams(defaultSize, defaultSize);
        likeBack.addView(imageLike, params2);
        disBack.addView(imageDis, params2);

        disBack.setBackgroundResource(R.drawable.white_background);
        likeBack.setBackgroundResource(R.drawable.white_background);

        //单列总布局
        likeAll = new LinearLayout(getContext());
        disAll = new LinearLayout(getContext());
        likeAll.setOrientation(VERTICAL);
        disAll.setOrientation(VERTICAL);

        likeAll.setGravity(Gravity.CENTER_HORIZONTAL);
        disAll.setGravity(Gravity.CENTER_HORIZONTAL);

        likeAll.setBackgroundColor(Color.TRANSPARENT);
        disAll.setBackgroundColor(Color.TRANSPARENT);

        //添加文字图片放进一列
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,10,0,0);
        params.gravity = Gravity.CENTER;

        disAll.setGravity(Gravity.CENTER_HORIZONTAL);
        disAll.addView(disNum, params);
        disAll.addView(disText, params);
        disAll.addView(disBack, params);

        likeAll.setGravity(Gravity.RIGHT);
        likeAll.addView(likeNum, params);
        likeAll.addView(likeText, params);
        likeAll.addView(likeBack, params);

        //中间分割线
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundColor(Color.GRAY);

        LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(3, 80);
        params4.setMargins(dividerMargin, 10, dividerMargin, defaultBottom + 20);
        params.gravity = Gravity.BOTTOM;

        LayoutParams params3 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params3.setMargins(30,20,30,defaultBottom);
        params3.gravity = Gravity.BOTTOM;

        addView(disAll, params3);
        addView(imageView, params4);
        addView(likeAll, params3);

        setVisibities(INVISIBLE);
    }

    public void setVisibities(int v) {
        likeNum.setVisibility(v);
        disNum.setVisibility(v);
        likeText.setVisibility(v);
        disText.setVisibility(v);
    }

    @Override
    public void onAnimationStart(Animator animator) {

    }

    @Override
    public void onAnimationEnd(Animator animator) {
        //重置帧动画
        mAnimLike.stop();
        mAnimDislike.stop();

        //关闭时不执行帧动画
        if(isClose) {
            //收回后可点击
            imageDis.setClickable(true);
            imageLike.setClickable(true);
            //隐藏文字
            setVisibities(INVISIBLE);

            //恢复透明
            setBackgroundColor(Color.TRANSPARENT);
            return;
        }

        isClose = true;

        if(type == 0) {
            mAnimLike.start();
            objectY(imageLike);
        } else {
            mAnimDislike.start();
            objectX(imageDis);
        }
    }

    private void objectX(ImageView imageDis) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageDis, "translationX", 0.0f, -10.0f, 0.0f, 10.0f, 0.0f, -10.0f, 0.0f, 10,0f, 0.0f);
        animator.setRepeatMode(ObjectAnimator.RESTART);
        animator.setDuration(1500);
        animator.start();

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                setBackUp();
            }
        });
    }

    private void objectY(ImageView imageLike) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageLike, "translationY", 0.0f, -10.0f, 0.0f, 10.0f, 0.0f, -10.0f, 0.0f, 10.0f, 0.0f);
        animator.setRepeatMode(ObjectAnimator.RESTART);
        animator.setDuration(1500);
        animator.start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                setBackUp();
            }
        });
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }
}
