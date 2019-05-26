package com.ys.demoredis.mapper;

import com.ys.demoredis.domain.UserScore;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface UserScoreMapper {

    @Insert("insert user_score (user_id,user_score,name) values(#{userId},#{userScore},#{name})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    public int insertUserScore(UserScore score);

}
