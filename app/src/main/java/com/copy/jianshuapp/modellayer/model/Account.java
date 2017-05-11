package com.copy.jianshuapp.modellayer.model;

import com.google.gson.annotations.SerializedName;
import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import java.util.List;

/**
 * 账户
 * @version imkarl 2017-04
 */
@Table("t_account")
public class Account {

    @PrimaryKey(AssignType.BY_MYSELF)
    @Column("id")
    private int id;
    private String email;
    @SerializedName("mobile_number")
    private String phone;
    private String nickname;
    private String slug;
    private String avatar;
    @SerializedName("mobile_token")
    private String token;
    private int subscriptions_count;
    private int notes_count;
    private int public_notes_count;
    private int private_notes_count;
    private int liked_notes_count;
    private int bookmarks_count;
    private boolean enable_subscription_push;
    private boolean default_subscription_push;
    private boolean is_nickname_slug;
    private boolean is_newly_registered;
    private List<Accesses> accesses;
    private List<Snses> public_snses;

    public class Accesses {
        public String email;
        public int id;
        public String name;
        public String provider;
        public List<Snses> snses;
        public String uid;
        public String username;
    }

    public class Snses {
        public String homepage;
        public int id;
        public boolean is_public;
        public String name;
        public String nickname;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", nickname='" + nickname + '\'' +
                ", slug='" + slug + '\'' +
                ", avatar='" + avatar + '\'' +
                ", token='" + token + '\'' +
                ", subscriptions_count=" + subscriptions_count +
                ", notes_count=" + notes_count +
                ", public_notes_count=" + public_notes_count +
                ", private_notes_count=" + private_notes_count +
                ", liked_notes_count=" + liked_notes_count +
                ", bookmarks_count=" + bookmarks_count +
                ", enable_subscription_push=" + enable_subscription_push +
                ", default_subscription_push=" + default_subscription_push +
                ", is_nickname_slug=" + is_nickname_slug +
                ", is_newly_registered=" + is_newly_registered +
                ", accesses=" + accesses +
                ", public_snses=" + public_snses +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean is_nickname_slug() {
        return is_nickname_slug;
    }

    public boolean is_newly_registered() {
        return is_newly_registered;
    }

    public int getSubscriptions_count() {
        return subscriptions_count;
    }

    public void setSubscriptions_count(int subscriptions_count) {
        this.subscriptions_count = subscriptions_count;
    }

    public int getNotes_count() {
        return notes_count;
    }

    public void setNotes_count(int notes_count) {
        this.notes_count = notes_count;
    }

    public int getPublic_notes_count() {
        return public_notes_count;
    }

    public void setPublic_notes_count(int public_notes_count) {
        this.public_notes_count = public_notes_count;
    }

    public int getPrivate_notes_count() {
        return private_notes_count;
    }

    public void setPrivate_notes_count(int private_notes_count) {
        this.private_notes_count = private_notes_count;
    }

    public int getLiked_notes_count() {
        return liked_notes_count;
    }

    public void setLiked_notes_count(int liked_notes_count) {
        this.liked_notes_count = liked_notes_count;
    }

    public int getBookmarks_count() {
        return bookmarks_count;
    }

    public void setBookmarks_count(int bookmarks_count) {
        this.bookmarks_count = bookmarks_count;
    }

    public boolean isEnable_subscription_push() {
        return enable_subscription_push;
    }

    public void setEnable_subscription_push(boolean enable_subscription_push) {
        this.enable_subscription_push = enable_subscription_push;
    }

    public boolean isDefault_subscription_push() {
        return default_subscription_push;
    }

    public void setDefault_subscription_push(boolean default_subscription_push) {
        this.default_subscription_push = default_subscription_push;
    }

    public boolean isIs_nickname_slug() {
        return is_nickname_slug;
    }

    public void setIs_nickname_slug(boolean is_nickname_slug) {
        this.is_nickname_slug = is_nickname_slug;
    }

    public boolean isIs_newly_registered() {
        return is_newly_registered;
    }

    public void setIs_newly_registered(boolean is_newly_registered) {
        this.is_newly_registered = is_newly_registered;
    }

    public List<Accesses> getAccesses() {
        return accesses;
    }

    public void setAccesses(List<Accesses> accesses) {
        this.accesses = accesses;
    }

    public List<Snses> getPublic_snses() {
        return public_snses;
    }

    public void setPublic_snses(List<Snses> public_snses) {
        this.public_snses = public_snses;
    }
}
