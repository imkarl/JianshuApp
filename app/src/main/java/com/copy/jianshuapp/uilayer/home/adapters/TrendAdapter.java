package com.copy.jianshuapp.uilayer.home.adapters;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.copy.jianshuapp.R;
import com.copy.jianshuapp.common.AppUtils;
import com.copy.jianshuapp.common.DisplayInfo;
import com.copy.jianshuapp.modellayer.local.db.ArticleDetailDao;
import com.copy.jianshuapp.modellayer.model.TrendArticle;
import com.copy.jianshuapp.uilayer.article.detail.ArticleDetailActivity;
import com.copy.jianshuapp.uilayer.base.BaseRecyclerAdapter;
import com.copy.jianshuapp.uilayer.subject.CollectionActivity;
import com.copy.jianshuapp.uilayer.user.UserCenterActivity;
import com.copy.jianshuapp.uilayer.widget.UniversalDraweeView;
import com.copy.jianshuapp.utils.ImageUtils;
import com.copy.jianshuapp.utils.TimeFormatUtils;

/**
 * 趋势Adapter
 * @version imkarl 2017-05
 */
public class TrendAdapter extends BaseRecyclerAdapter<TrendArticle> implements View.OnClickListener {

    private final int mAvatarSize = DisplayInfo.dp2px(26);
    private final Drawable mDefaultVideoIcon = ContextCompat.getDrawable(AppUtils.getContext(), R.drawable.img_article_video);

    public TrendAdapter() {
        super(R.layout.item_article_list_with_tag);
    }

    @Override
    protected void convert(BaseViewHolder holder, TrendArticle entity) {
        holder.setText(R.id.last_compiled_time, formatTime(entity.getPublishTime()));
        holder.setText(R.id.collection_tag, getSubjectTitle(entity));

        String avatarUrl = getAvatar(entity);
        if (!TextUtils.isEmpty(avatarUrl)) {
            UniversalDraweeView draweeViewAuthorAvatar = holder.getView(R.id.author_avatar);
            draweeViewAuthorAvatar.setImageURI(avatarUrl);
        }

        TextView tvAuthorName = holder.getView(R.id.author_name);
        TextView tvTitle = holder.getView(R.id.title);
        TextView tvExtraInfo = holder.getView(R.id.extra_info);
        UniversalDraweeView draweeViewImage = holder.getView(R.id.image);
        RelativeLayout rlAvatarName = holder.getView(R.id.avatar_name);
        FrameLayout flCollectionTagContainer = holder.getView(R.id.collection_tag_container);

        boolean shouldSetSelected = ArticleDetailDao.isRead(entity.getId());
        String userNickname = getNickname(entity);
        if (!TextUtils.isEmpty(userNickname)) {
            tvAuthorName.setText(userNickname);
            tvAuthorName.setSelected(shouldSetSelected);
        }
        tvTitle.setText(entity.getTitle());
        tvTitle.setSelected(shouldSetSelected);
        tvExtraInfo.setText(getExtraInfo(entity));
        tvExtraInfo.setCompoundDrawablesWithIntrinsicBounds(entity.isHasVideo() ? mDefaultVideoIcon : null, null, null, null);
        if (TextUtils.isEmpty(entity.getImage())) {
            draweeViewImage.setVisibility(View.GONE);
        } else {
            draweeViewImage.setVisibility(View.VISIBLE);
            String thumbUrl = ImageUtils.format(entity.getImage(), DisplayInfo.dp2px(80));
            draweeViewImage.setImageURI(thumbUrl);
        }

        rlAvatarName.setTag(holder.getLayoutPosition());
        rlAvatarName.setOnClickListener(this);

        flCollectionTagContainer.setTag(holder.getLayoutPosition());
        flCollectionTagContainer.setOnClickListener(this);
        if (TextUtils.isEmpty(getSubjectTitle(entity))) {
            flCollectionTagContainer.setVisibility(View.GONE);
        } else {
            flCollectionTagContainer.setVisibility(View.VISIBLE);
        }

        holder.setTag(R.id.root_view, holder.getLayoutPosition());
        holder.setOnClickListener(R.id.root_view, this);
    }


    private String formatTime(long timeInSeconds) {
        String formatString;
        if (TimeFormatUtils.isSameYear(timeInSeconds)) {
            formatString = "MM.dd HH:mm";
        } else {
            formatString = "yyyy.MM.dd HH:mm";
        }
        return TimeFormatUtils.formatDuration(1000 * timeInSeconds, formatString);
    }

    private String getExtraInfo(TrendArticle acticle) {
        int rewardCount = acticle.getRewardsCount();
        if (acticle.isCommented()) {
            if (rewardCount > 0) {
                return String.format(AppUtils.getContext().getString(R.string.raw_extra_info_with_reward),
                        acticle.getViewCount(), acticle.getCommentCount(), acticle.getLikeCount(), rewardCount);
            } else {
                return String.format(AppUtils.getContext().getString(R.string.raw_extra_info),
                        acticle.getViewCount(), acticle.getCommentCount(), acticle.getLikeCount());
            }
        } else if (rewardCount > 0) {
            return String.format(AppUtils.getContext().getString(R.string.raw_extra_info2_with_reward),
                    acticle.getViewCount(), acticle.getLikeCount(), rewardCount);
        } else {
            return String.format(AppUtils.getContext().getString(R.string.raw_extra_info2),
                    acticle.getViewCount(), acticle.getLikeCount());
        }
    }

    private String getAvatar(TrendArticle acticle) {
        if (acticle == null || acticle.getNotebook() == null || acticle.getNotebook().getUser() == null) {
            return "";
        }
        return ImageUtils.format(acticle.getNotebook().getUser().getAvatar(), mAvatarSize, mAvatarSize);
    }
    private String getNickname(TrendArticle acticle) {
        if (acticle == null || acticle.getNotebook() == null || acticle.getNotebook().getUser() == null) {
            return "";
        }
        return acticle.getNotebook().getUser().getNickname();
    }
    private String getSubjectTitle(TrendArticle acticle) {
        if (acticle == null || acticle.getSubject() == null) {
            return "";
        }
        return acticle.getSubject().getTitle();
    }


    public void onClick(View v) {
        TrendArticle entity = getItem((int) v.getTag());
        switch (v.getId()) {
            case R.id.avatar_name:
                UserCenterActivity.launch(entity.getNotebook().getId());
                break;
            case R.id.collection_tag_container:
                CollectionActivity.launch(entity.getSubject().getId(), entity.getSubject().getTitle());
                break;
            case R.id.root_view:
                ArticleDetailActivity.launch(entity.getId());
                break;
            default:
                break;
        }
    }

}
