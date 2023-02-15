package co.kr.exitobiz.Service.WebApp;


import java.util.HashMap;

public interface AdminService {
    HashMap<String, Object> getComingMonthStats();

    HashMap<String, Object> getComingWeeksStats();

    HashMap<String, Object> getJoinMonthStats();

    HashMap<String, Object> getJoinWeeksStats();

    HashMap<String, Object> getComingTwoWeeks();

    HashMap<String, Object> getComingWeek();

    HashMap<String, Object> getJoinTwoWeeks();

    HashMap<String, Object> getJoinWeek();
}
