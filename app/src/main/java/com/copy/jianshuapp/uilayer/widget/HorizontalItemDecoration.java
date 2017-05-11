package com.copy.jianshuapp.uilayer.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Dimension;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.copy.jianshuapp.R;
import com.copy.jianshuapp.common.AppUtils;

/**
 * 横向ItemDecoration
 * @version imkarl 2017-05
 */
public class HorizontalItemDecoration extends RecyclerView.ItemDecoration {
    private Context mContext;
    @Dimension private int space;

    public HorizontalItemDecoration(@Dimension int space) {
        this.mContext = AppUtils.getContext();
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.left = this.mContext.getResources().getDimensionPixelSize(R.dimen.little_banner_padding_left);
        }
        if (parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount()) {
            outRect.right = this.mContext.getResources().getDimensionPixelOffset(R.dimen.little_banner_padding_left);
        } else {
            outRect.right = this.space;
        }
    }
}
