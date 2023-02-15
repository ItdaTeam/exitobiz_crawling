package co.kr.exitobiz.Service.WebApp.impl;

import co.kr.exitobiz.Mappers.WebApp.AdminMapper;
import co.kr.exitobiz.Service.WebApp.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Override
    public HashMap<String, Object> getComingMonthStats() {
        return adminMapper.getComingMonthStats();
    }

    @Override
    public HashMap<String, Object> getComingWeeksStats() {
        return adminMapper.getComingWeeksStats();
    }

    @Override
    public HashMap<String, Object> getJoinMonthStats() {
        return adminMapper.getJoinMonthStats();
    }

    @Override
    public HashMap<String, Object> getJoinWeeksStats() {
        return adminMapper.getJoinWeeksStats();
    }

    @Override
    public HashMap<String, Object> getComingTwoWeeks() {
        return adminMapper.getComingTwoWeeks();
    }

    @Override
    public HashMap<String, Object> getComingWeek() {
        return adminMapper.getComingWeek();
    }

    @Override
    public HashMap<String, Object> getJoinTwoWeeks() {
        return adminMapper.getJoinTwoWeeks();
    }

    @Override
    public HashMap<String, Object> getJoinWeek() {
        return adminMapper.getJoinWeek();
    }
}
