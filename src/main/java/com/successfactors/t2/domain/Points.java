package com.successfactors.t2.domain;

public class Points {
    private String userId;
    private Integer points;
    public Points(){
    }

    public Points(String userId, Integer points){
        this.userId = userId;
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
}
