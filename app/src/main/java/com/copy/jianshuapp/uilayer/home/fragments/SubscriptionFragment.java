package com.copy.jianshuapp.uilayer.home.fragments;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.copy.jianshuapp.R;
import com.copy.jianshuapp.common.AppUtils;
import com.copy.jianshuapp.common.DisplayInfo;
import com.copy.jianshuapp.common.LogUtils;
import com.copy.jianshuapp.common.ObjectUtils;
import com.copy.jianshuapp.exception.ExceptionUtils;
import com.copy.jianshuapp.modellayer.enums.SubscriptionType;
import com.copy.jianshuapp.modellayer.model.SubscriptionListEntity;
import com.copy.jianshuapp.modellayer.repository.SubscriptionRepository;
import com.copy.jianshuapp.uilayer.base.BaseFragment;
import com.copy.jianshuapp.uilayer.home.adapters.SubscriptionAdapter;
import com.copy.jianshuapp.uilayer.listeners.OnLoadmoreListener;
import com.copy.jianshuapp.uilayer.search.SearchActivity;
import com.copy.jianshuapp.uilayer.subscribe.add.AddSubscribeActivity;
import com.copy.jianshuapp.uilayer.widget.JSSwipeRefreshLayout;
import com.copy.jianshuapp.uilayer.widget.dialog.Alerts;
import com.copy.jianshuapp.utils.NetworkUtils;
import com.copy.jianshuapp.utils.ToastUtils;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * 订阅\关注
 * @version imkarl 2017-04
 */
public class SubscriptionFragment extends BaseFragment {

    private static final SparseArray<SubscriptionType> MENU_TYPES = new SparseArray<SubscriptionType>() {{
        put(R.string.all_follow, null);
        put(R.string.just_load_collection, SubscriptionType.collection);
        put(R.string.just_load_notebook, SubscriptionType.notebook);
        put(R.string.just_load_user, SubscriptionType.user);
        put(R.string.just_load_push, SubscriptionType.push);
    }};

    @Bind(R.id.view_status_height)
    View mViewStatusHeight;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.iv_title)
    ImageView mIvTitle;
    @Bind(R.id.title_wrapper)
    LinearLayout mTitleWrapper;
    @Bind(R.id.add_subscribe)
    ImageView mAddSubscribe;
    @Bind(R.id.iv_search)
    ImageView mIvSearch;
    @Bind(R.id.divider)
    View mDivider;
    @Bind(R.id.srl_content)
    JSSwipeRefreshLayout mSrlContent;
    @Bind(R.id.rv_content)
    RecyclerView mRvContent;
    @Bind(R.id.title_bar_root)
    LinearLayout mTitleBarRoot;

    private SubscriptionAdapter mAdapter;
    private int mCurrentSelected = 0;

    private Disposable mDisposable;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!NetworkUtils.isConnected()) {
            ToastUtils.show(R.string.network_exception_and_try_it_later);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_subscribe_main);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mViewStatusHeight.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayInfo.getStatusBarHeight()));
        }

        initView();
        bindLisnters();
        requestReload();
    }

    private void initView() {
        mAdapter = new SubscriptionAdapter();
        mAdapter.enableLoadmore(false);
        mRvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvContent.setAdapter(mAdapter);
        mSrlContent.setOnRefreshListener(this::reload);
        mAdapter.setOnLoadmoreListener(this::loadmore);
    }

    private void bindLisnters() {
        RxView.clicks(mTitleWrapper)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(it -> {
                    CharSequence[] itemArray = new CharSequence[MENU_TYPES.size()];
                    for (int i = 0; i < MENU_TYPES.size(); i++) {
                        int itemRes = MENU_TYPES.keyAt(i);
                        itemArray[i] = AppUtils.getContext().getString(itemRes);
                    }

                    Alerts.listOnAnchor(mTitleWrapper,
                            mCurrentSelected,
                            itemArray)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(select -> {
                                if (mCurrentSelected == select) {
                                    return;
                                }

                                mCurrentSelected = select;
                                mTitle.setText(itemArray[select]);
                                // 刷新列表数据
                                requestReload();
                            }, err -> {
                                //LogUtils.e(err.getMessage());
                            });
                });
        RxView.clicks(mAddSubscribe)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(it -> AddSubscribeActivity.launch());
        RxView.clicks(mIvSearch)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(it -> SearchActivity.lanuchFromHome());
    }

    private void requestReload() {
        mSrlContent.setRefreshing(true);
        reload();
    }

    private void reload() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            mDisposable = null;
        }

        SubscriptionType type = MENU_TYPES.valueAt(mCurrentSelected);
        mDisposable = SubscriptionRepository.list(type, -1)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .doOnTerminate(() -> {
                    mAdapter.showFeedView(mCurrentSelected == 0);
                    mSrlContent.setRefreshing(false);
                })
                .subscribe(data -> {
                    LogUtils.e(data);
                    mAdapter.setData(data.getSubscriptions());
                    mAdapter.notifyDataSetChanged();

                    if (ObjectUtils.isEmpty(data.getSubscriptions())) {
                        mAdapter.enableLoadmore(false);
                    } else {
                        mAdapter.enableLoadmore(true);
                    }
                }, err -> {
                    LogUtils.e(err);
                    ToastUtils.show(ExceptionUtils.getDescription(err));
                });
    }

    private void loadmore() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            mDisposable = null;
        }

        SubscriptionType type = MENU_TYPES.valueAt(mCurrentSelected);
        long lastUpdatedTime = -1;
        if (!ObjectUtils.isEmpty(mAdapter.getData())) {
            lastUpdatedTime = mAdapter.getData().get(mAdapter.getData().size() - 1).getLastUpdatedTime();
            lastUpdatedTime--;
        }
        mDisposable = SubscriptionRepository.list(type, lastUpdatedTime)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .doOnTerminate(() -> mAdapter.loadmoreComplete())
                .subscribe(data -> {
                    LogUtils.e(data);
                    mAdapter.addData(data.getSubscriptions());
                    mAdapter.notifyDataSetChanged();

                    if (ObjectUtils.isEmpty(data.getSubscriptions())) {
                        mAdapter.enableLoadmore(false);
                    }
                }, err -> {
                    LogUtils.e(err);
                    ToastUtils.show(ExceptionUtils.getDescription(err));
                });
    }

}
