package co.kr.exitobiz.Service.Mobile.impl;

import co.kr.exitobiz.Mappers.Mobile.UserMapper;
import co.kr.exitobiz.Service.Mobile.UserService;
import co.kr.exitobiz.Vo.usertableVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public String getUserToken(HashMap<String,String> params) {
        return userMapper.getUserToken(params);
    }

    @Override
    public List<Map<String, Object>> getUserTokenByLocation(HashMap<String, Object> params) {
        return userMapper.getUserTokenByLocation(params);
    }

    @Override
    public List<Map<String, usertableVo>> getUserTokenByLocationTest(List params){
        return userMapper.getUserTokenByLocationTest(params);
    }

}
