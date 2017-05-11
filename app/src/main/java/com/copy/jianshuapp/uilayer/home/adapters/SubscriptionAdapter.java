package com.copy.jianshuapp.uilayer.home.adapters;

import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.chad.library.adapter.base.BaseViewHolder;
import com.copy.jianshuapp.R;
import com.copy.jianshuapp.common.ActivityLifcycleManager;
import com.copy.jianshuapp.common.AppUtils;
import com.copy.jianshuapp.common.DisplayInfo;
import com.copy.jianshuapp.common.LogUtils;
import com.copy.jianshuapp.modellayer.enums.SubscriptionType;
import com.copy.jianshuapp.modellayer.model.SubscriptionListEntity;
import com.copy.jianshuapp.uilayer.base.BaseRecyclerAdapter;
import com.copy.jianshuapp.uilayer.friend.circle.FriendCircleActivity;
import com.copy.jianshuapp.uilayer.notebook.NotebookActivity;
import com.copy.jianshuapp.uilayer.subject.CollectionActivity;
import com.copy.jianshuapp.uilayer.subscribe.add.AddSubscribeActivity;
import com.copy.jianshuapp.uilayer.user.UserPushingDetailActivity;
import com.copy.jianshuapp.uilayer.widget.ClickableSpanNoUnderLine;
import com.copy.jianshuapp.uilayer.widget.UniversalDraweeView;
import com.copy.jianshuapp.uilayer.widget.dialog.Alerts;
import com.copy.jianshuapp.utils.BitmapUtils;
import com.copy.jianshuapp.utils.ImageUtils;
import com.copy.jianshuapp.utils.ShortcutUtils;
import com.copy.jianshuapp.utils.ThrottleUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 订阅Adapter
 * @version imkarl 2017-04
 */
public class SubscriptionAdapter extends BaseRecyclerAdapter<SubscriptionListEntity.Subscription> implements OnClickListener {
    private static final int LAYOUT_ID_FEED = R.layout.item_subscription_feed;
    private static final int LAYOUT_ID_FOOTER = R.layout.item_subscribe_recyclerview_end;
    private static final int LAYOUT_ID_CONTENT = R.layout.item_following_pushing;

    private View mFeedView;
    private View mFooterView;

    public SubscriptionAdapter() {
        super(LAYOUT_ID_CONTENT);
        // R.layout.item_following_pushing_header;
    }

    @Override
    protected void convert(BaseViewHolder holder, SubscriptionListEntity.Subscription entity) {
        View itemView = holder.getConvertView();
        itemView.setTag(entity);
        itemView.setOnClickListener(this);
        if (entity.getSubscriptionType() == null || entity.getSubscriptionType() == SubscriptionType.notebook) {
            itemView.setLongClickable(false);
        } else {
            itemView.setLongClickable(true);
            final String userId = entity.getSource_identity().split(":")[0];
            itemView.setOnLongClickListener(v -> {
                showAddToDesktop(entity.getSubscriptionType(), userId, entity.getImage(), entity.getName());
                return true;
            });
        }


        TextView tvName = holder.getView(R.id.tv_name);
        tvName.setMaxWidth(DisplayInfo.getWidthPixels() / 2);

        holder.setText(R.id.tv_name, entity.getName());
        holder.setText(R.id.tv_desc, entity.getNewNoteTitle());

        // 未读数
        int unreadCount = entity.getUnreadCount();
        if (unreadCount == 0) {
            holder.setVisible(R.id.tv_unread, false);
            holder.setVisible(R.id.tv_notify, false);
        } else {
            holder.setVisible(R.id.tv_unread, true);
            holder.setVisible(R.id.tv_notify, true);

            String txt = unreadCount > 99 ? "99+" : String.valueOf(unreadCount);
            holder.setText(R.id.tv_unread, String.format(AppUtils.getContext().getString(R.string.count_article_update), txt));
        }


        UniversalDraweeView icon = holder.getView(R.id.avatar);
        SubscriptionType subscriptionType = entity.getSubscriptionType();
        if (subscriptionType != null) {
            switch (subscriptionType) {
                case user:
                    icon.setCircle(true);
                    icon.setImageURI(entity.getImage());
                    break;
                case notebook:
                    icon.setCircle(false);
                    icon.setCornerRadiusRes(R.dimen.dp_4);
                    icon.setImageURI(ImageUtils.parseUri(R.drawable.wj_image));
                    break;
                default:
                    icon.setCircle(false);
                    icon.setCornerRadiusRes(R.dimen.dp_4);
                    icon.setImageURI(entity.getImage());
                    break;
            }
        }
    }

    public void showFeedView(boolean show) {
        if (show) {
            // 显示FeedView
            if (mFeedView == null) {
                mFeedView = View.inflate(ActivityLifcycleManager.get().current(), LAYOUT_ID_FEED, null);
                mFeedView.setOnClickListener(v -> {
                    if (ThrottleUtils.valid(v)) {
                        FriendCircleActivity.launch();
                    }
                });
            }

            setHeaderView(mFeedView);
        } else {
            setHeaderView(null);
        }
    }

    @Override
    public void enableLoadmore(boolean enable) {
        if (isEnableLoadmore() == enable) {
            return;
        }

        super.enableLoadmore(enable);

        if (!enable) {
            // 显示footer
            if (mFooterView == null) {
                mFooterView = View.inflate(ActivityLifcycleManager.get().current(), LAYOUT_ID_FOOTER, null);
                TextView tvEnd = (TextView) mFooterView.findViewById(R.id.txt_end);
                tvEnd.setMovementMethod(LinkMovementMethod.getInstance());
                String txtEnd = AppUtils.getContext().getString(R.string.to_discover_more_zuthor_and_collection);
                SpannableStringBuilder ssb = new SpannableStringBuilder(txtEnd);
                int startIndex = txtEnd.indexOf("更");
                int endIndex = txtEnd.length();
                if (startIndex > -1) {
                    ssb.setSpan(new ClickableSpanNoUnderLine() {
                        public void onClick(View widget) {
                            if (ThrottleUtils.valid(widget)) {
                                AddSubscribeActivity.launch();
                            }
                        }
                    }, startIndex, endIndex, 0);
                    tvEnd.setText(ssb, BufferType.SPANNABLE);
                }
            }

            if (mFooterView.getParent() == null) {
                addFooterView(mFooterView);
            }
        } else {
            removeFooterView(mFooterView);
        }
    }

    private void showAddToDesktop(SubscriptionType type, String id, String imgUrl, String name) {
        Alerts.list(null, AppUtils.getContext().getString(R.string.add_to_desktop))
                .flatMap(it -> {
                    return ImageUtils.loadImage(ImageUtils.parseUri(imgUrl), 128, 128)
                            .map(bitmap -> {
                                RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(AppUtils.getContext().getResources(), bitmap);
                                drawable.setCornerRadius(20);
                                return (Drawable)drawable;
                            })
                            .onErrorReturnItem(AppUtils.getContext().getResources().getDrawable(R.drawable.jianshu_icon))
                            .map(BitmapUtils::toBitmap);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bitmap -> {
                    ShortcutUtils.addShortcut(type, id, name, bitmap);
                }, err -> {
                    LogUtils.e(err);
                });
    }

    @Override
    public void onClick(View v) {
        if (ThrottleUtils.valid(v)) {
            switch (v.getId()) {
                case R.id.subscribe_root:
                    SubscriptionListEntity.Subscription entity = (SubscriptionListEntity.Subscription) v.getTag();

                    boolean hasUnread = entity.getUnreadCount() > 0;
                    boolean consumed = false;
                    switch (entity.getSubscriptionType()) {
                        case user:
                            consumed = true;
                            UserPushingDetailActivity.launch(entity.getSource_identity().split(":")[0], entity.getName(), hasUnread);
                            break;
                        case notebook:
                            consumed = true;
                            NotebookActivity.launch(entity.getSource_identity().split(":")[0], entity.getSource_identity(), hasUnread);
                            break;
                        case collection:
                            consumed = true;
                            CollectionActivity.launch(entity.getSource_identity().split(":")[0], entity.getSource_identity(), hasUnread);
                            break;
                    }
                    if (consumed) {
                        entity.setUnreadCount(0);
                        notifyDataSetChanged();
                    }
                    break;
            }
        }
    }

}
