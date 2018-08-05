package com.successfactors.t2.domain;

public class RankingItem {
    private String userId;
    private String nickname;
    private String avatarUrl;
    private Integer points;
    public RankingItem(){
    }

    public RankingItem(String userId, String nickname, String avatarUrl, Integer points){
        this.userId = userId;
        this.nickname = nickname;
        this.avatarUrl = avatarUrl;
        this.points = points;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
