package co.kr.exitobiz.Mappers.WebApp;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    public List<Map<String, Object>> getUserToken(HashMap<String,String> params);

    public List<Map<String, Object>> getUserTokenByLocation(HashMap<String, Object> params);
    public List<Map<String, Object>> getUserTokenByLocationTest(HashMap<String, Object> params);

    public List<Map<String, Object>> getUserTokenByKeyId(HashMap<String, String> params);

    public List<Map<String, Object>> getUserTokens();

    Map<String, Object> getUserInfo(Map<String, Object> params);

    Map<String, Object> getCompanyInfo(Map<String, Object> params);

    void updatePushSetting(Map<String, Object> params);

    Map<String, Object> getPushSetting(Map<String, Object> params);

    void withdraw(Map<String, Object> params);

    void updateReSignIn(Map<String, Object> params);

    int checkNickname(Map<String, Object> params);

    void updateUserInfo(Map<String, Object> params);

    void updateCompanyInfo(Map<String, Object> params);

    public List<Map<String, Object>> checkAppVer();

    void insertLogin(Map<String, Object> params);

    void insertUserSmartDeviceInfo(Map<String, Object> params);

    void updateMobileInfo(Map<String, Object> params);

    void delUserToken(Map<String, Object> params);
}