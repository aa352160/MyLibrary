package com.jh352160.library.widget;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jh352160.library.R;

/**
 * Created by jh352160 on 2016/10/18.
 */

public class ScoreView extends LinearLayout {

    private Context context;
    private ImageView[] imageViews;
    private int star = 5, score = 0;
    private ScoreChangeListener mScoreChangeListener;
    private boolean haveHalf = false,enable=true;
    private int starSize=50;
    private @DrawableRes
    int fillStar = R.drawable.ic_score_red;
    private @DrawableRes
    int halfStar = R.drawable.ic_score_red;
    private @DrawableRes
    int emptyStar = R.drawable.ic_score_gray;

    public ScoreView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public ScoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        starSize=context.getResources().getDrawable(R.drawable.ic_score_gray).getMinimumHeight();
        initView();
    }

    private void initView() {
        imageViews = new ImageView[star];
        this.setOrientation(HORIZONTAL);
        for (int i = 0; i < imageViews.length; i++) {
            imageViews[i] = new ImageView(context);
            imageViews[i].setImageResource(emptyStar);
            imageViews[i].setLayoutParams(new ViewGroup.LayoutParams(starSize, starSize));
            this.addView(imageViews[i]);
        }
        if (enable) {
            setListener();
        }
    }

    private void setListener() {
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float num = getMeasuredWidth() * 1.0f / (star * 2);
                float offset = event.getX() / num;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //float offset=event.getX()/num;
                        refresh(offset >= star * 2 - 1 ? star : (int) offset / 2 + 1, ((int) offset) % 2 == 0);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //float offset=event.getX()/num;
                        if (!(offset < 0 || offset > star * 2)) {
                            refresh((int) offset / 2 + 1, ((int) offset) % 2 == 0);
                        }
                        break;
                }
                if (!(offset < 0 || offset > star * 2)) {
                    score = (int) (offset + 1);
                }

                if (mScoreChangeListener!=null) {
                    mScoreChangeListener.scoreChange();
                }
                return true;
            }
        });
    }

    /**
     * 设置点击监听
     */
    public void setScoreChangeListener(ScoreChangeListener mScoreChangeListener) {
        this.mScoreChangeListener = mScoreChangeListener;
    }

    /**
     * 获得当前分数
     */
    public float getScore() {
        if (haveHalf) {
            return score / 2.0f;
        } else {
            return (score + 1) / 2;
        }
    }

    /**
     * 设置分数
     */
    public void setScore(float score) {
        this.score=(int)(score*2);
        refresh(((int) score) % 2 == 0?(int)score+1:(int)score,((int) score) % 2 == 0);
    }

    public interface ScoreChangeListener {
        void scoreChange();
    }

    public void setHaveHalf(boolean haveHalf) {
        this.haveHalf = haveHalf;
    }

    public void setStar(int star) {
        this.star = star;
        this.removeAllViews();
        initView();
    }

    private void refresh(int position, boolean half) {
        for (int j = 0; j < imageViews.length; j++) {
            imageViews[j].setImageResource(emptyStar);
        }
        if (half) {
            for (int j = 0; j < position; j++) {
                imageViews[j].setImageResource(fillStar);
            }
            if (haveHalf) {
                imageViews[position - 1].setImageResource(halfStar);
            }
        } else {
            for (int j = 0; j < position; j++) {
                imageViews[j].setImageResource(fillStar);
            }
        }
    }

    /**
     * 设置星星的大小
     */
    public void setStarSize(int starSize){
        this.starSize=starSize;
        this.removeAllViews();
        initView();
    }

    /**
     * 设置是否可以操作，默认为true
     */
    public void isEnable(boolean enable){
        this.enable=enable;
        if (enable){
            setListener();
        }else {
            this.setOnTouchListener(null);
        }
    }

    public void setFillStar(@DrawableRes int fillStar) {
        this.fillStar = fillStar;
    }

    public void setHalfStar(@DrawableRes int halfStar) {
        this.halfStar = halfStar;
    }

    public void setEmptyStar(@DrawableRes int emptyStar) {
        this.emptyStar = emptyStar;
    }
}