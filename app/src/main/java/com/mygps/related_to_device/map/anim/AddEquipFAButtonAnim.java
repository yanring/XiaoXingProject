package com.mygps.related_to_device.map.anim;

import android.content.res.Resources;
import android.util.TypedValue;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * Created by 10397 on 2016/6/4.
 */

public class AddEquipFAButtonAnim extends Animation {
    private int mFromXType = ABSOLUTE;
    private int mToXType = ABSOLUTE;

    private int mFromYType = ABSOLUTE;
    private int mToYType = ABSOLUTE;

    /** @hide */
    protected float mFromXValue = 0.0f;
    /** @hide */
    protected float mToXValue = 0.0f;

    /** @hide */
    protected float mFromYValue = 0.0f;
    /** @hide */
    protected float mToYValue = 0.0f;

    /** @hide */
    protected float mFromXDelta;
    /** @hide */
    protected float mToXDelta;
    /** @hide */
    protected float mFromYDelta;
    /** @hide */
    protected float mToYDelta;



    private float mFromX;
    private float mToX;
    private float mFromY;
    private float mToY;

    private int mFromXData = 0;
    private int mToXData = 0;
    private int mFromYData = 0;
    private int mToYData = 0;

    private int mPivotXType = ABSOLUTE;
    private int mPivotYType = ABSOLUTE;
    private float mPivotXValue = 0.0f;
    private float mPivotYValue = 0.0f;

    private float mPivotX;
    private float mPivotY;

}
