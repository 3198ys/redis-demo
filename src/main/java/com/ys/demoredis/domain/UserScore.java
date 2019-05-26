package com.ys.demoredis.domain;

public class UserScore {
    private int id;
    private int userId;
    private Long userScore;
    private String name;


    public UserScore()
    {

    }
    public UserScore(int userId, Long userScore, String name) {
        this.userId = userId;
        this.userScore = userScore;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Long getUserScore() {
        return userScore;
    }

    public void setUserScore(Long userScore) {
        this.userScore = userScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
