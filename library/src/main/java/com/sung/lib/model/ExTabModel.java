package com.sung.lib.model;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by sung on 2017-11-02.
 */

public class ExTabModel {

    private int mColor;

    private final Bitmap mIcon;
    private final Bitmap mSelectedIcon;
    private final Matrix mIconMatrix = new Matrix();

    private String mTitle = "";
    private String mBadgeTitle = "";
    private String mTempBadgeTitle = "";
    private float mBadgeFraction;

    private boolean mIsBadgeShowed;
    private boolean mIsBadgeUpdated;

    private final ValueAnimator mBadgeAnimator = new ValueAnimator();

    private float mInactiveIconScale;
    private float mActiveIconScaleBy;

    protected ExTabModel(final Builder builder) {
        mColor = builder.mColor;
        mIcon = builder.mIcon;
        mSelectedIcon = builder.mSelectedIcon;
        mTitle = builder.mTitle;
        mBadgeTitle = builder.mBadgeTitle;

        mBadgeAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(final Animator animation) {
                animation.removeListener(this);
                animation.addListener(this);
            }

            @Override
            public void onAnimationEnd(final Animator animation) {
                animation.removeListener(this);
                animation.addListener(this);

                // Detect whether we just update text and don`t reset show state
                if (!mIsBadgeUpdated) mIsBadgeShowed = !mIsBadgeShowed;
                else mIsBadgeUpdated = false;
            }

            @Override
            public void onAnimationRepeat(final Animator animation) {
                // Change title when we update and don`t see the title
                if (mIsBadgeUpdated) mBadgeTitle = mTempBadgeTitle;
            }
        });
    }

    public static class Builder {

        private final int mColor;
        private final Bitmap mIcon;
        private Bitmap mSelectedIcon;
        private String mTitle;
        private String mBadgeTitle;
        public Builder(final Drawable icon, final int color)
        {
            mColor = color;

            if (icon != null) {
                if (icon instanceof BitmapDrawable) mIcon = ((BitmapDrawable) icon).getBitmap();
                else {
                    mIcon = Bitmap.createBitmap(
                            icon.getIntrinsicWidth(),
                            icon.getIntrinsicHeight(),
                            Bitmap.Config.ARGB_8888
                    );
                    final Canvas canvas = new Canvas(mIcon);
                    icon.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                    icon.draw(canvas);
                }
            } else {
                mIcon = Bitmap.createBitmap(1, 1, Bitmap.Config.RGB_565);
            }
        }

        public Builder selectedIcon(final Drawable selectedIcon)
        {
            if (selectedIcon != null) {
                if (selectedIcon instanceof BitmapDrawable)
                    mSelectedIcon = ((BitmapDrawable) selectedIcon).getBitmap();
                else {
                    mSelectedIcon = Bitmap.createBitmap(
                            selectedIcon.getIntrinsicWidth(),
                            selectedIcon.getIntrinsicHeight(),
                            Bitmap.Config.ARGB_8888
                    );
                    final Canvas canvas = new Canvas(mSelectedIcon);
                    selectedIcon.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                    selectedIcon.draw(canvas);
                }
            } else mSelectedIcon = null;

            return this;
        }

        public Builder title(final String title)
        {
            mTitle = title;
            return this;
        }

        public Builder badgeTitle(final String title)
        {
            mBadgeTitle = title;
            return this;
        }

        public ExTabModel build()
        {
            return new ExTabModel(this);
        }
    }
}
