package com.copy.jianshuapp.modellayer.local.db.model;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

/**
 * 账户
 * @version imkarl 2017-03
 */
@Table("t_account")
public class Account {
    @PrimaryKey(AssignType.BY_MYSELF)
    @Column("id")
    private int id;
    private long token_expire;
    public String email;
    public String mobile_number;
    public String mobile_token;

//    public List<Accesses> accesses;
//    public int bookmarks_count;
//    public boolean default_subscription_push;
//    public int editable_collections_count;
//    public boolean enable_subscription_push;
//    public int followers_count;
//    public int following_count;
//    public String homepage;
//    public boolean is_followed_by_user;
//    public boolean is_newly_registered;
//    public boolean is_nickname_slug;
//    public int liked_notes_count;
//    public int notebooks_count;
//    public int notes_count;
//    public int owned_collections_count;
//    public int private_notes_count;
//    public int public_notes_count;
//    public List<Snses> public_snses;
//    public int subscribing_collections_count;
//    public int subscribing_notebooks_count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getToken_expire() {
        return token_expire;
    }

    public void setToken_expire(long token_expire) {
        this.token_expire = token_expire;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getMobile_token() {
        return mobile_token;
    }

    public void setMobile_token(String mobile_token) {
        this.mobile_token = mobile_token;
    }
}
