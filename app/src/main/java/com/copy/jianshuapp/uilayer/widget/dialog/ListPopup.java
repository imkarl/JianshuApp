package com.copy.jianshuapp.uilayer.widget.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.chad.library.adapter.base.BaseViewHolder;
import com.copy.jianshuapp.R;
import com.copy.jianshuapp.common.ActivityLifcycleManager;
import com.copy.jianshuapp.common.DisplayInfo;
import com.copy.jianshuapp.uilayer.base.BaseRecyclerAdapter;
import com.copy.jianshuapp.uilayer.listeners.OnCancelListener;
import com.copy.jianshuapp.uilayer.listeners.OnItemClickListener;
import com.hitomi.cslibrary.CrazyShadow;
import com.hitomi.cslibrary.base.CrazyShadowDirection;
import com.labo.kaji.relativepopupwindow.RelativePopupWindow;

import java.util.Arrays;

/**
 * 列表PopupWindow
 * @version imkarl 2017-04
 */
public class ListPopup extends RelativePopupWindow {
    private RecyclerView mRecyclerView;
    private BaseRecyclerAdapter<CharSequence> mAdapter;
    private OnItemClickListener<CharSequence> mOnItemClickListener;
    private View.OnClickListener mOriginalOnItemClickListener = v -> {
        if (mOnItemClickListener == null) {
            return;
        }
        mOnItemClickListener.onItemClick((CharSequence) v.getTag());
    };
    private int mSelected = -1;

    public ListPopup(Context context) {
        mRecyclerView = (RecyclerView) LayoutInflater.from(context).inflate(R.layout.popup_list, null);
        setContentView(mRecyclerView);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new BaseRecyclerAdapter<CharSequence>(R.layout.item_black_drop_down_menu) {
            @Override
            protected void convert(BaseViewHolder holder, CharSequence data) {
                holder.setText(R.id.tv_name, data);
                holder.setTag(R.id.tv_name, data);
                holder.setOnClickListener(R.id.tv_name, mOriginalOnItemClickListener);

                if (holder.getLayoutPosition() == mSelected) {
                    holder.setTextColor(R.id.tv_name, context.getResources().getColor(R.color.theme_color));
                } else {
                    holder.setTextColor(R.id.tv_name, Color.GRAY);
                }
            }
        };
        mRecyclerView.setAdapter(mAdapter);

        // 添加阴影
        new CrazyShadow.Builder()
                .setContext(ActivityLifcycleManager.get().current())
                .setDirection(CrazyShadowDirection.ALL)
                .setShadowRadius(DisplayInfo.dp2px(3))
                .setCorner(DisplayInfo.dp2px(2))
                .setBaseShadowColor(Color.LTGRAY)
                .setBackground(Color.WHITE)
                .setImpl(CrazyShadow.IMPL_WRAP)
                .action(mRecyclerView);
        // 避免因添加阴影导致高度变小
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mRecyclerView.getLayoutParams().height = mRecyclerView.getHeight() + DisplayInfo.dp2px(6);
                mRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    public ListPopup setSelected(int selected) {
        if (selected >= 0) {
            mSelected = selected;
        }
        mAdapter.notifyDataSetChanged();
        return this;
    }

    public ListPopup items(CharSequence... items) {
        mAdapter.setNewData(Arrays.asList(items));
        return this;
    }

    public ListPopup itemsCallback(OnItemClickListener<CharSequence> listener) {
        mOnItemClickListener = listener;
        return this;
    }

    public ListPopup cancelListener(OnCancelListener listener) {
        setOnDismissListener(() -> {
            if (isCancel) {
                listener.onCancel();
            }
        });
        return this;
    }

    private boolean isCancel;
    @Override
    public void dismiss() {
        isCancel = true;
        super.dismiss();
    }
    public void close() {
        isCancel = false;
        super.dismiss();
    }

}
