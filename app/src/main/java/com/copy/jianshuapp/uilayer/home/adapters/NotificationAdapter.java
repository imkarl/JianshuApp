package com.copy.jianshuapp.uilayer.home.adapters;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.copy.jianshuapp.R;
import com.copy.jianshuapp.uilayer.base.BaseRecyclerAdapter;
import com.copy.jianshuapp.uilayer.chat.ChatListActivity;
import com.copy.jianshuapp.uilayer.enums.NotificationType;
import com.copy.jianshuapp.uilayer.notify.NotifyDetailActivity;
import com.copy.jianshuapp.uilayer.social.SubmissionRequestActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 通知Adapter
 * @version imkarl 2017-04
 */
public class NotificationAdapter extends BaseRecyclerAdapter<NotificationAdapter.NotificationMenu> implements View.OnClickListener {

    public NotificationAdapter() {
        super(R.layout.notification_frag_normal_item, MENUS);
        enableLoadmore(false);
    }

    @Override
    protected void convert(BaseViewHolder holder, NotificationMenu entity) {
        holder.setTag(R.id.item_root, entity);

        holder.setImageResource(R.id.image_left, entity.icon);
        holder.setText(R.id.txt_head, entity.title);
        if (entity.hasSubtitle()) {
            holder.setVisible(R.id.txt_subhead, true);
            holder.setText(R.id.txt_subhead, entity.subtitle);
        } else {
            holder.setVisible(R.id.txt_subhead, false);
        }

        if (entity.unreadCount > 0) {
            holder.setVisible(R.id.txt_unread_count, true);
            holder.setText(R.id.txt_unread_count, (entity.unreadCount > 99 ? "99+" : String.valueOf(entity.unreadCount)));
        } else {
            holder.setVisible(R.id.txt_unread_count, false);
        }

        holder.setTag(R.id.item_root, holder.getLayoutPosition());
        holder.setOnClickListener(R.id.item_root, this);
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        NotificationMenu menu = getItem(position);
        switch (menu.type) {
            case COMMENT:
            case LIKE:
            case FOLLOW:
            case OTHER:
            case MONEY:
                NotifyDetailActivity.launch(menu.type);
                break;
            case MESSAGE:
                ChatListActivity.launch();
                break;
            case REQUEST:
                SubmissionRequestActivity.launch();
                break;
        }
    }

    private static final List<NotificationMenu> MENUS = new ArrayList<NotificationMenu>() {{
        add(new NotificationMenu(NotificationType.COMMENT, R.drawable.image_talk, R.string.ping_lun));
        add(new NotificationMenu(NotificationType.MESSAGE, R.drawable.image_message, R.string.jian_xin));
        add(new NotificationMenu(NotificationType.REQUEST, R.drawable.image_ask, R.string.tou_gao_qing_qiu, R.string.collection_submission_subtitle));
        add(new NotificationMenu(NotificationType.LIKE, R.drawable.image_like, R.string.like_and_star));
        add(new NotificationMenu(NotificationType.FOLLOW, R.drawable.image_guanzhu, R.string.guan_zhu));
        add(new NotificationMenu(NotificationType.MONEY, R.drawable.image_reward, R.string.reward));
        add(new NotificationMenu(NotificationType.OTHER, R.drawable.image_other, R.string.other_reminder, R.string.qi_ta_promote));
    }};

    static class NotificationMenu {
        private final NotificationType type;
        @DrawableRes
        private final int icon;
        @StringRes
        private final int title;

        @StringRes
        private int subtitle;
        private int unreadCount = 0;

        public NotificationMenu(NotificationType type, @DrawableRes int icon, @StringRes int title) {
            this.type = type;
            this.icon = icon;
            this.title = title;
        }

        public NotificationMenu(NotificationType type, @DrawableRes int icon, @StringRes int title, @StringRes int subtitle) {
            this.type = type;
            this.icon = icon;
            this.title = title;
            this.subtitle = subtitle;
        }

        public void setSubtitle(@StringRes int subtitle) {
            this.subtitle = subtitle;
        }

        public void setUnreadCount(int unreadCount) {
            this.unreadCount = unreadCount;
        }

        public boolean hasSubtitle() {
            return this.subtitle > 0;
        }
    }

}
