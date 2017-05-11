package com.copy.jianshuapp.uilayer.home.fragments;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ValueAnimator;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.copy.jianshuapp.R;
import com.copy.jianshuapp.common.AppUtils;
import com.copy.jianshuapp.common.DisplayInfo;
import com.copy.jianshuapp.common.LogUtils;
import com.copy.jianshuapp.common.ObjectUtils;
import com.copy.jianshuapp.exception.ExceptionUtils;
import com.copy.jianshuapp.modellayer.model.TrendArticle;
import com.copy.jianshuapp.modellayer.model.TrendBanner;
import com.copy.jianshuapp.modellayer.model.TrendCategory;
import com.copy.jianshuapp.modellayer.model.TrendSubject;
import com.copy.jianshuapp.modellayer.repository.TrendRepository;
import com.copy.jianshuapp.uilayer.base.BaseFragment;
import com.copy.jianshuapp.uilayer.base.BaseRecyclerAdapter;
import com.copy.jianshuapp.uilayer.browser.BrowserActivity;
import com.copy.jianshuapp.uilayer.home.adapters.TrendAdapter;
import com.copy.jianshuapp.uilayer.recomend.SpecialRecommendActivity;
import com.copy.jianshuapp.uilayer.search.SearchActivity;
import com.copy.jianshuapp.uilayer.subject.CollectionActivity;
import com.copy.jianshuapp.uilayer.trend.ArticleListActivity;
import com.copy.jianshuapp.uilayer.widget.HorizontalItemDecoration;
import com.copy.jianshuapp.uilayer.widget.JSSwipeRefreshLayout;
import com.copy.jianshuapp.uilayer.widget.UniversalDraweeView;
import com.copy.jianshuapp.uilayer.widget.adapter.ItemViewHelper;
import com.copy.jianshuapp.uilayer.widget.adapter.QuickPagerAdapter;
import com.copy.jianshuapp.uilayer.widget.indicator.CirclePageIndicator;
import com.copy.jianshuapp.uilayer.widget.viewpager.LoopViewPager;
import com.copy.jianshuapp.utils.ToastUtils;
import com.mcxtzhang.layoutmanager.flow.FlowLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import jianshu.api.MatchJianShuUrl;

/**
 * 趋势\发现
 * @version imkarl 2017-05
 */
public class TrendFragment extends BaseFragment {

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_view)
    JSSwipeRefreshLayout mSwipeRefreshView;
    @Bind(R.id.view_status_height)
    View mViewStatusHeight;
    @Bind(R.id.ll_search_mini)
    LinearLayout mLlSearchMini;
    @Bind(R.id.the_bottom_line)
    View mTheBottomLine;
    @Bind(R.id.img_dismiss)
    ImageView mImgDismiss;
    @Bind(R.id.relative_editor_exception)
    LinearLayout mRelativeEditorException;
    @Bind(R.id.tv_search)
    TextView mTvSearch;
    @Bind(R.id.ll_search_right)
    LinearLayout mLlSearchRight;
    @Bind(R.id.frame_root)
    FrameLayout mFrameRoot;

    View mFlBanner;
    LoopViewPager mViewpagerBanner;
    CirclePageIndicator mIndicatorBanner;
    RecyclerView mTagRecyclerView;
    RecyclerView mCardRecyclerView;
    View mTvTitleContribute;
    ImageView mIvTitle;
    TextView mTvTitleRight;

    View mIvPlaceCard;
    View mIvPlaceTag;

    private TrendAdapter mAdapter;
    private BaseRecyclerAdapter<TrendCategory> mAdapterCategory;
    private BaseRecyclerAdapter<TrendSubject> mAdapterSubject;

    private List<TrendBanner> mBanners;
    private List<TrendCategory> mCategorys;
    private List<TrendSubject> mSubjects;

    private int mPositionSubject;
    private Drawable mDrawableSearchBg;
    private Disposable mDisposable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_trend);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initSearchLayout();
        initRecyclerView();
        initHeaderView();

        mSwipeRefreshView.setRefreshing(true);
        reloadData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (mTvTitleRight != null) {
                mTvTitleRight.setVisibility(AppUtils.isLogin() ? View.VISIBLE : View.GONE);
            }
        }
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new TrendAdapter();
        mAdapter.enableLoadmore(false);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshView.setOnRefreshListener(this::reloadData);
        mAdapter.setOnLoadmoreListener(this::loadmore);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /** 参考高度 */
            private final float contrastHeight = DisplayInfo.getWidthPixels() / 2;
            private int currentY;
            /** 偏移高度 */
            private int offsetHeight;
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                currentY = currentY + dy;

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP || Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    offsetHeight = currentY + mLlSearchMini.getHeight();
                } else {
                    offsetHeight = (currentY + mLlSearchMini.getHeight()) + mViewStatusHeight.getHeight();
                }
                if (offsetHeight >= contrastHeight) {
                    // 全部可见
                    mTheBottomLine.setVisibility(View.VISIBLE);
                    mViewStatusHeight.setVisibility(View.INVISIBLE);
                    mLlSearchMini.setAlpha(1);
                    starSearchViewAnim(true);
                } else {
                    // 部分可见
                    mTheBottomLine.setVisibility(View.GONE);
                    mViewStatusHeight.setVisibility(View.VISIBLE);
                    if (currentY == 0) {
                        // 初始状态，背景完全透明
                        mLlSearchMini.setAlpha(0);
                        mViewStatusHeight.setAlpha(0);
                        starSearchViewAnim(false);
                    } else {
                        mLlSearchMini.setAlpha(((float) offsetHeight) / contrastHeight);
                        mViewStatusHeight.setAlpha(((float) offsetHeight) / contrastHeight);
                    }
                }
            }
        });
    }

    private boolean mLastIsZoom = false;
    private void starSearchViewAnim(final boolean zoom) {
        if (zoom == mLastIsZoom) {
            return;
        }

        final int maxWidth = DisplayInfo.getWidthPixels() - DisplayInfo.dp2px(30);
        ValueAnimator valueAnimator;
        if (zoom) {
            valueAnimator = (ValueAnimator) AnimatorInflater.loadAnimator(getContext(), R.animator.animator_expand);
        } else {
            valueAnimator = (ValueAnimator) AnimatorInflater.loadAnimator(getContext(), R.animator.animator_close);
        }
        valueAnimator.addUpdateListener(animator -> {
            int currentValue = (Integer) animator.getAnimatedValue();
            mLlSearchRight.getLayoutParams().width = (maxWidth * currentValue) / 100;
            if (mLlSearchRight.getLayoutParams().width > DisplayInfo.dp2px(114)) {
                mLlSearchRight.requestLayout();
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            public void onAnimationStart(Animator animator) {
                if (zoom) {
                    mTvSearch.setMaxEms(10);
                    mLlSearchRight.setBackground(mDrawableSearchBg);
                } else {
                    mTvSearch.setMaxEms(2);
                    mLlSearchRight.setBackgroundResource(R.drawable.shape_search_mini);
                }
            }
            public void onAnimationEnd(Animator animator) {
                if (zoom) {
                    mLlSearchRight.getLayoutParams().width = maxWidth;
                } else {
                    mLlSearchRight.getLayoutParams().width = DisplayInfo.dp2px(114);
                }
            }
            public void onAnimationCancel(Animator animator) {
            }
            public void onAnimationRepeat(Animator animator) {
            }
        });
        valueAnimator.start();
        mLastIsZoom = zoom;
    }

    private void initHeaderView() {
        View mHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.layout_banner, this.mRecyclerView, false);
        this.mFlBanner = mHeaderView.findViewById(R.id.fl_banner);
        this.mViewpagerBanner = (LoopViewPager) mHeaderView.findViewById(R.id.viewpager_banner);
        this.mIndicatorBanner = (CirclePageIndicator) mHeaderView.findViewById(R.id.indicator_banner);
        this.mCardRecyclerView = (RecyclerView) mHeaderView.findViewById(R.id.card_recycler);
        this.mTagRecyclerView = (RecyclerView) mHeaderView.findViewById(R.id.tag_recyclerview);
        this.mTvTitleContribute = mHeaderView.findViewById(R.id.tv_title_contribute);
        this.mIvTitle = (ImageView) mHeaderView.findViewById(R.id.iv_title);
        this.mTvTitleRight = (TextView) mHeaderView.findViewById(R.id.tv_title_right);

        this.mIvPlaceCard = mHeaderView.findViewById(R.id.iv_place_card);
        this.mIvPlaceTag = mHeaderView.findViewById(R.id.iv_place_tag);

        ViewGroup.LayoutParams lp = this.mViewpagerBanner.getLayoutParams();
        lp.height = DisplayInfo.getWidthPixels() / 2;
        this.mViewpagerBanner.setLayoutParams(lp);

        mTvTitleRight.setText("定制热门");
        mTvTitleRight.setOnClickListener(v -> {
            SpecialRecommendActivity.launch();
        });
        if (mTvTitleRight != null) {
            mTvTitleRight.setVisibility(AppUtils.isLogin() ? View.VISIBLE : View.GONE);
        }

        mCardRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mCardRecyclerView.addItemDecoration(new HorizontalItemDecoration(20));
        mAdapterCategory = new BaseRecyclerAdapter<TrendCategory>(R.layout.item_trend_categroy) {
            private View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag(R.id.global_tag);
                    TrendCategory entity = mCategorys.get(position);
                    ArticleListActivity.launch(entity);
                }
            };
            @Override
            protected void convert(BaseViewHolder holder, TrendCategory entity) {
                UniversalDraweeView draweeView = holder.getView(R.id.draweeview);
                draweeView.setImageURI(entity.getImage());
                draweeView.setTag(R.id.global_tag, holder.getLayoutPosition());
                draweeView.setOnClickListener(onClickListener);
            }
        };
        mCardRecyclerView.setAdapter(mAdapterCategory);

        mTagRecyclerView.setLayoutManager(new FlowLayoutManager() {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mAdapterSubject = new BaseRecyclerAdapter<TrendSubject>(R.layout.tag_recommend) {
            private View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag(R.id.global_tag);
                    TrendSubject entity = mSubjects.get(position);
                    CollectionActivity.launch(entity.getId(), "发现页专题推荐");
                }
            };
            @Override
            protected void convert(BaseViewHolder holder, TrendSubject entity) {
                holder.setText(R.id.text1, entity.getTitle());
                holder.setTag(R.id.text1, R.id.global_tag, holder.getLayoutPosition());
                holder.setOnClickListener(R.id.text1, onClickListener);
            }
        };
        mTagRecyclerView.setAdapter(mAdapterSubject);
        this.mAdapter.setHeaderView(mHeaderView);
    }

    private void initSearchLayout() {
        final int margin = DisplayInfo.dp2px(15);
        FrameLayout.LayoutParams llParams = (FrameLayout.LayoutParams) mLlSearchRight.getLayoutParams();
        llParams.gravity = Gravity.RIGHT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            llParams.setMargins(margin, DisplayInfo.getStatusBarHeight() + DisplayInfo.dp2px(3), margin, 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                this.mLlSearchMini.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        (DisplayInfo.dp2px(45) + (DisplayInfo.getStatusBarHeight() / 2)) + DisplayInfo.dp2px(5)));
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                this.mViewStatusHeight.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayInfo.getStatusBarHeight()));
                this.mLlSearchMini.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayInfo.dp2px(37)));
            }
        } else {
            this.mLlSearchMini.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayInfo.dp2px(43)));
            llParams.setMargins(margin, DisplayInfo.dp2px(8), margin, 0);
        }
        this.mLlSearchRight.setLayoutParams(llParams);
        mLlSearchRight.setOnClickListener(v -> SearchActivity.launch());

        TypedArray typedArray = getContext().obtainStyledAttributes(new int[]{R.attr.search_bg_home});
        this.mDrawableSearchBg = typedArray.getDrawable(0);
        typedArray.recycle();
    }

    private void reloadData() {
        if (ObjectUtils.isEmpty(mBanners)) {
            TrendRepository.listBanner()
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(bindToLifecycle())
                    .subscribe(it -> {
                        mBanners = it;

                        mViewpagerBanner.setAdapter(new QuickPagerAdapter<TrendBanner>(R.layout.item_image_draweeview, mBanners) {
                            private View.OnClickListener onClickListener = new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int position = (int) v.getTag(R.id.global_tag);
                                    TrendBanner entity = mBanners.get(position);
                                    if (MatchJianShuUrl.isUrl(entity.getLink())) {
                                        BrowserActivity.launch(entity.getLink());
                                    }
                                }
                            };
                            @Override
                            protected void convert(ItemViewHelper helper, TrendBanner entity) {
                                UniversalDraweeView imageView = helper.getView(R.id.imageView);
                                imageView.setImageURI(entity.getImage());
                                imageView.setTag(R.id.global_tag, helper.getPosition());
                                imageView.setOnClickListener(onClickListener);
                            }
                        });
                        mIndicatorBanner.setViewPager(mViewpagerBanner);
                    }, err -> {
                        LogUtils.e(err);
                    });
        }
        if (ObjectUtils.isEmpty(mCategorys)) {
            TrendRepository.listCategory()
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(bindToLifecycle())
                    .subscribe(it -> {
                        mCategorys = it;
                        LogUtils.e(it);

                        mCardRecyclerView.setVisibility(View.VISIBLE);
                        mIvPlaceCard.setVisibility(View.GONE);

                        mAdapterCategory.setData(mCategorys);
                        mAdapterCategory.notifyDataSetChanged();
                    }, err -> {
                        LogUtils.e(err);
                    });
        }
        if (ObjectUtils.isEmpty(mSubjects)) {
            TrendRepository.listSubject(1)
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(bindToLifecycle())
                    .subscribe(it -> {
                        mSubjects = it;
                        LogUtils.e(it);

                        mTagRecyclerView.setVisibility(View.VISIBLE);
                        mIvPlaceTag.setVisibility(View.GONE);

                        final int PAGE_SIZE_SUBJECT = 12;
                        mPositionSubject = 1;
                        mAdapterSubject.setData(mSubjects.subList(0, PAGE_SIZE_SUBJECT));
                        mAdapterSubject.notifyDataSetChanged();

                        mTvTitleContribute.setOnClickListener(v -> {
                            mPositionSubject++;
                            if (mPositionSubject > 4) {
                                mPositionSubject = 1;
                            }
                            mAdapterSubject.setData(mSubjects.subList((mPositionSubject - 1) * PAGE_SIZE_SUBJECT, mPositionSubject * PAGE_SIZE_SUBJECT));
                            mAdapterSubject.notifyDataSetChanged();

                            mIvTitle.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.anim_rotate));
                        });
                    }, err -> {
                        LogUtils.e(err);
                    });
        }

        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            mDisposable = null;
        }
        page = 1;
        TrendRepository.listArticle(page, null)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .doOnTerminate(() -> mSwipeRefreshView.setRefreshing(false))
                .subscribe(data -> {
                    LogUtils.e(data);
                    mAdapter.setData(data);
                    mAdapter.notifyDataSetChanged();

                    if (ObjectUtils.isEmpty(data)) {
                        mAdapter.enableLoadmore(false);
                    } else {
                        mAdapter.enableLoadmore(true);
                    }
                }, err -> {
                    LogUtils.e(err);
                    ToastUtils.show(ExceptionUtils.getDescription(err));
                });
    }

    private int page = 1;
    private void loadmore() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            mDisposable = null;
        }

        List<Integer> seenIds = new ArrayList<>();
        for (TrendArticle article : mAdapter.getData()) {
            seenIds.add(article.getId());
        }
        TrendRepository.listArticle(page+1, seenIds)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .doOnTerminate(() -> mAdapter.loadmoreComplete())
                .subscribe(data -> {
                    LogUtils.e(data);
                    page++;
                    mAdapter.addData(data);
                    mAdapter.notifyDataSetChanged();

                    if (ObjectUtils.isEmpty(data)) {
                        mAdapter.enableLoadmore(false);
                    }
                }, err -> {
                    LogUtils.e(err);
                    ToastUtils.show(ExceptionUtils.getDescription(err));
                });
    }

}
