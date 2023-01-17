package co.kr.exitobiz.Service.WebApp.impl;

import co.kr.exitobiz.Mappers.WebApp.UserMapper;
import co.kr.exitobiz.Service.WebApp.UserService;
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
    public List<Map<String, Object>> getUserToken(HashMap<String,String> params) {
        return userMapper.getUserToken(params);
    }

    @Override
    public List<Map<String, Object>> getUserTokenByLocation(HashMap<String, Object> params) {
        return userMapper.getUserTokenByLocation(params);
    }

    @Override
    public List<Map<String, Object>> getUserTokenByLocationTest(HashMap<String, Object> params){
        return userMapper.getUserTokenByLocationTest(params);
    }


    /*
     * 개별푸쉬를 위한 keyId 유형별 토큰 가져오기
     */
    @Override
    public List<Map<String, Object>> getUserTokenByKeyId(HashMap<String, String> params) {
        return userMapper.getUserTokenByKeyId(params);
    }

    @Override
    public List<Map<String, Object>> getUserTokens() {
        return userMapper.getUserTokens();

    }

    @Override
    public Map<String, Object> getUserInfo(Map<String, Object> params) {
        return userMapper.getUserInfo(params);
    }

    @Override
    public Map<String, Object> getCompanyInfo(Map<String, Object> params) {
        return userMapper.getCompanyInfo(params);
    }

    @Override
    public void updatePushSetting(Map<String, Object> params) {
        userMapper.updatePushSetting(params);
    }

    @Override
    public Map<String, Object> getPushSetting(Map<String, Object> params) {
        return userMapper.getPushSetting(params);
    }

    @Override
    public void withdraw(Map<String, Object> params) {
        userMapper.withdraw(params);
    }

    @Override
    public void updateReSignIn(Map<String, Object> params) {
        userMapper.updateReSignIn(params);
    }


    @Override
    public int checkNickname(Map<String, Object> params) {
        return userMapper.checkNickname(params);
    }

    @Override
    public void updateUserInfo(Map<String, Object> params) {
        userMapper.updateUserInfo(params);
    }

    @Override
    public void updateCompanyInfo(Map<String, Object> params) {
        userMapper.updateCompanyInfo(params);
    }

}
