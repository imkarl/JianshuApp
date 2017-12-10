package com.copy.jianshuapp.modellayer.remote.api;

import com.copy.jianshuapp.modellayer.model.TrendArticle;
import com.copy.jianshuapp.modellayer.model.TrendBanner;
import com.copy.jianshuapp.modellayer.model.TrendCategory;
import com.copy.jianshuapp.modellayer.model.TrendSubject;

import java.util.List;

import io.reactivex.Observable;

/**
 * 趋势\动态API
 * @author imkarl
 */
public interface TrendApi {

    Observable<List<TrendBanner>> listBanner();

    Observable<List<TrendCategory>> listCategory();

    Observable<List<TrendSubject>> listSubject(int page);

    Observable<List<TrendArticle>> listArticle(int page, List<Integer> seenIds);

}
