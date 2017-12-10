package com.copy.jianshuapp.modellayer.remote.api.mock;

import com.copy.jianshuapp.modellayer.model.TrendArticle;
import com.copy.jianshuapp.modellayer.model.TrendBanner;
import com.copy.jianshuapp.modellayer.model.TrendCategory;
import com.copy.jianshuapp.modellayer.model.TrendSubject;
import com.copy.jianshuapp.modellayer.remote.api.TrendApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class MockTrendApi implements TrendApi {
    @Override
    public Observable<List<TrendBanner>> listBanner() {
        List<TrendBanner> banners = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TrendBanner banner = new TrendBanner();
            banner.setId(i);
            banner.setKey("key-"+i);
            banner.setName("name-"+i);
            banner.setPosition(i);
            banner.setStartTime(30*60*1000);
            banner.setEndTtime(60*60*1000);
            banner.setImage("xxx.jpg");
            banner.setLink("http://xxx");
            banners.add(banner);
        }
        return Observable.just(banners);
    }

    @Override
    public Observable<List<TrendCategory>> listCategory() {
        List<TrendCategory> categories = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TrendCategory category = new TrendCategory();
            category.setId(i);
            category.setName("name-"+i);
            category.setPosition(i);
            category.setImage("xxx.jpg");
            categories.add(category);
        }
        return Observable.just(categories);
    }

    @Override
    public Observable<List<TrendSubject>> listSubject(int page) {
        List<TrendSubject> subjects = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TrendSubject subject = new TrendSubject();
            subject.setId(i);
            subject.setTitle("title-"+i);
            subjects.add(subject);
        }
        return Observable.just(subjects);
    }

    @Override
    public Observable<List<TrendArticle>> listArticle(int page, List<Integer> seenIds) {
        List<TrendArticle> subjects = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TrendArticle article = new TrendArticle();
            article.setId(i);
            article.setTitle("title-"+i);
            article.setSlug("slug-"+i);
            article.setDesc("desc-"+i);
            article.setPublishTime(24*60*60*1000);
            article.setLastEditTime(3*24*60*60*1000);
            article.setCommentUpdatedTime(8*24*60*60*1000);
            article.setImage("xxx.jpg");
            article.setCommentCount(i*3);
            article.setLikeCount(i*4);
            article.setViewCount(i*7);
            article.setRewardsCount(i*10);
            subjects.add(article);
        }
        return Observable.just(subjects);
    }
}
