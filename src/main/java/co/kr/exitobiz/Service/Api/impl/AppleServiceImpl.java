package co.kr.exitobiz.Service.Api.impl;

import co.kr.exitobiz.Mappers.Api.AppleMapper;
import co.kr.exitobiz.Service.Api.AppleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AppleServiceImpl implements AppleService {

    @Autowired
    AppleMapper appleMapper;


    @Override
    public Map<String, String> login(Map<String, Object> params) {

        return appleMapper.getUserInfo(params);
    }

}
