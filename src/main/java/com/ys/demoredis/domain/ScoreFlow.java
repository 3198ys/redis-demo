package com.ys.demoredis.domain;

import java.io.Serializable;

public class ScoreFlow implements Serializable {
    private static final long serialVersionUID = -4415438719697624459L;


    private int id;
    private Long score;
    private String userId;
    private String userName;


    public ScoreFlow(Long score, String userId, String userName) {
        this.score = score;
        this.userId = userId;
        this.userName = userName;
    }

    public ScoreFlow()
    {

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
