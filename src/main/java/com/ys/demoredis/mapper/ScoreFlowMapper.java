package com.ys.demoredis.mapper;

import com.ys.demoredis.domain.ScoreFlow;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ScoreFlowMapper {


    @Insert("insert into score_flow (score,user_id,user_name) values(#{score},#{userId},#{userName})")
    public int insertScoreFlow(ScoreFlow flow);
}
