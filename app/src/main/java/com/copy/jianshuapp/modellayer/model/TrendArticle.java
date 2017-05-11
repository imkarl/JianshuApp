package com.copy.jianshuapp.modellayer.model;

import com.google.gson.annotations.SerializedName;

/**
 * 趋势-文章
 * @version imkarl 2017-05
 */
public class TrendArticle {

    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("slug")
    private String slug;
    @SerializedName("desc")
    private String desc;
    @SerializedName("first_shared_at")
    private int publishTime;
    @SerializedName("last_compiled_at")
    private int lastEditTime;
    @SerializedName("list_image")
    private String image;
    @SerializedName("commentable")
    private boolean commented;
    @SerializedName("comments_count")
    private int commentCount;
    @SerializedName("likes_count")
    private int likeCount;
    @SerializedName("views_count")
    private int viewCount;
    @SerializedName("total_rewards_count")
    private int rewardsCount;
    @SerializedName("comment_updated_at")
    private int commentUpdatedTime;
    @SerializedName("has_video")
    private boolean hasVideo;
    @SerializedName("is_liked")
    private boolean liked;
    @SerializedName("notebook")
    private Notebook notebook;
    @SerializedName("vip_collection")
    private Subject subject;


//    public List<Collection> collections;
//    public String image_url;
//    public boolean isArticleUpdated;
//    public boolean is_bookmarked;
//    public boolean is_followed_by_user;
//    public boolean is_following_user;
//    public String mobile_content;
//    public List<Buyer> reward_buyers;
//    public int reward_default_amount;
//    public String reward_description;
//    public boolean rewardable;
//    public List<String> upload_image_urls;
//    public int visit_id;
//    public long wordage;

    @Override
    public String toString() {
        return "TrendArticle{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", slug='" + slug + '\'' +
                ", desc='" + desc + '\'' +
                ", publishTime=" + publishTime +
                ", lastEditTime=" + lastEditTime +
                ", image='" + image + '\'' +
                ", commented=" + commented +
                ", commentCount=" + commentCount +
                ", likeCount=" + likeCount +
                ", viewCount=" + viewCount +
                ", rewardsCount=" + rewardsCount +
                ", commentUpdatedTime=" + commentUpdatedTime +
                ", hasVideo=" + hasVideo +
                ", liked=" + liked +
                ", notebook=" + notebook +
                ", subject=" + subject +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(int publishTime) {
        this.publishTime = publishTime;
    }

    public int getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(int lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Notebook getNotebook() {
        return notebook;
    }

    public void setNotebook(Notebook notebook) {
        this.notebook = notebook;
    }

    public boolean isCommented() {
        return commented;
    }

    public void setCommented(boolean commented) {
        this.commented = commented;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getRewardsCount() {
        return rewardsCount;
    }

    public void setRewardsCount(int rewardsCount) {
        this.rewardsCount = rewardsCount;
    }

    public int getCommentUpdatedTime() {
        return commentUpdatedTime;
    }

    public void setCommentUpdatedTime(int commentUpdatedTime) {
        this.commentUpdatedTime = commentUpdatedTime;
    }

    public boolean isHasVideo() {
        return hasVideo;
    }

    public void setHasVideo(boolean hasVideo) {
        this.hasVideo = hasVideo;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public static class Notebook {
        private int id;
        private User user;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        @Override
        public String toString() {
            return "Notebook{" +
                    "id=" + id +
                    ", user=" + user +
                    '}';
        }

        public static class User {
            private int id;
            private String nickname;
            private String avatar;
            private SnsNicknames sns_nicknames;

            @Override
            public String toString() {
                return "User{" +
                        "id=" + id +
                        ", nickname='" + nickname + '\'' +
                        ", avatar='" + avatar + '\'' +
                        ", sns_nicknames=" + sns_nicknames +
                        '}';
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public SnsNicknames getSns_nicknames() {
                return sns_nicknames;
            }

            public void setSns_nicknames(SnsNicknames sns_nicknames) {
                this.sns_nicknames = sns_nicknames;
            }

            public static class SnsNicknames {
                private String weibo;

                @Override
                public String toString() {
                    return "SnsNicknames{" +
                            "weibo='" + weibo + '\'' +
                            '}';
                }

                public String getWeibo() {
                    return weibo;
                }

                public void setWeibo(String weibo) {
                    this.weibo = weibo;
                }
            }
        }
    }

    public static class Subject {
        private int id;
        private String slug;
        private String title;

        @Override
        public String toString() {
            return "Subject{" +
                    "id=" + id +
                    ", slug='" + slug + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
