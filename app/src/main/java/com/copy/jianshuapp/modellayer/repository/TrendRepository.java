package com.copy.jianshuapp.modellayer.repository;

import com.copy.jianshuapp.modellayer.model.TrendArticle;
import com.copy.jianshuapp.modellayer.model.TrendBanner;
import com.copy.jianshuapp.modellayer.model.TrendCategory;
import com.copy.jianshuapp.modellayer.model.TrendSubject;
import com.copy.jianshuapp.modellayer.remote.RemoteManager;
import com.copy.jianshuapp.modellayer.remote.api.TrendApi;

import java.util.List;

import io.reactivex.Observable;

/**
 * 趋势\动态Repository
 * @version imkarl 2017-05
 */
public final class TrendRepository extends Repository {
    private TrendRepository() {
    }

    private static final TrendApi api = RemoteManager.getInstance().createApi(TrendApi.class);

    public static Observable<List<TrendBanner>> listBanner() {
        return api.listBanner()
                .compose(process());
    }

    public static Observable<List<TrendCategory>> listCategory() {
        return api.listCategory()
                .compose(process());
    }

    public static Observable<List<TrendSubject>> listSubject(int page) {
        if (page <= 0) {
            page = 0;
        }
        return api.listSubject(page)
                .compose(process());
    }

    public static Observable<List<TrendArticle>> listArticle(int page, List<Integer> seenIds) {
        if (page <= 0) {
            page = 0;
        }
        return api.listArticle(page, seenIds)
                .compose(process());
    }

}
