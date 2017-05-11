package com.copy.jianshuapp.modellayer.local.db;

/**
 * 文章详情Dao
 * @version imkarl 2017-05
 */
public class ArticleDetailDao extends BaseDao {
    private ArticleDetailDao() { }

    public static boolean isRead(int articleId) {
        if (articleId <= 0) {
            return false;
        }

        // TODO
        return false;
    }

}
