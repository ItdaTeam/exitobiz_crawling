package co.kr.exitobiz.Mappers.WebApp;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface AdminMapper {
    HashMap<String, Object> getComingMonthStats();

    HashMap<String, Object> getComingWeeksStats();

    HashMap<String, Object> getJoinMonthStats();

    HashMap<String, Object> getJoinWeeksStats();

    HashMap<String, Object> getComingTwoWeeks();

    HashMap<String, Object> getComingWeek();

    HashMap<String, Object> getJoinTwoWeeks();

    HashMap<String, Object> getJoinWeek();
}
