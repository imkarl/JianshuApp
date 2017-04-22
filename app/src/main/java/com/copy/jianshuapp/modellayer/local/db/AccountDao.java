package com.copy.jianshuapp.modellayer.local.db;

import com.copy.jianshuapp.modellayer.model.Account;

/**
 * 登录账户Dao
 * @version imkarl 2017-03
 */
public class AccountDao extends BaseDao {
    private AccountDao() { }

    public static Account readLoginAccount() {
        return queryOne(Account.class);
    }

    public static boolean saveLoginAccount(Account account) {
        return save(account);
    }

}
