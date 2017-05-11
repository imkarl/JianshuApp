package com.copy.jianshuapp.modellayer.local.db;

import com.copy.jianshuapp.AppConfig;
import com.copy.jianshuapp.common.AppUtils;
import com.copy.jianshuapp.common.ObjectUtils;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;

import java.util.List;

/**
 * 登录账户Dao
 * @version imkarl 2017-03
 */
abstract class BaseDao {
    private static LiteOrm sLiteOrm;

    static LiteOrm orm() {
        if (sLiteOrm == null) {
            sLiteOrm = LiteOrm.newSingleInstance(AppUtils.getContext(), AppConfig.db.DB_NAME);
        }
        return sLiteOrm;
    }

    static <T> T queryOne(Class<T> clazz) {
        return queryOne(clazz, null);
    }
    static <T> T queryOne(Class<T> clazz, T defValue) {
        List<T> values = orm().query(clazz);
        if (!ObjectUtils.isEmpty(values)) {
            return values.get(0);
        }
        return defValue;
    }

    static <T> long count(Class<T> clazz) {
        long count = orm().queryCount(clazz);
        if (count < 0) {
            count = 0;
        }
        return count;
    }
    static <T> long count(QueryBuilder<T> query) {
        long count = orm().queryCount(query);
        if (count < 0) {
            count = 0;
        }
        return count;
    }

    static boolean save(Object value) {
        long id = orm().save(value);
        return id > 0;
    }

}
