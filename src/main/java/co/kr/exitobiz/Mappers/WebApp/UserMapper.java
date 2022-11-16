package co.kr.exitobiz.Mappers.WebApp;

import co.kr.exitobiz.Vo.usertableVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    public String getUserToken(HashMap<String,String> params);

    public List<Map<String, Object>> getUserTokenByLocation(HashMap<String, Object> params);
    public List<Map<String, usertableVo>> getUserTokenByLocationTest(List params);

    public String getUserTokenByKeyId(HashMap<String, String> params);

    public List<Map<String, Object>> getUserTokens();

    Map<String, Object> getUserInfo(Map<String, Object> params);

    Map<String, Object> getCompanyInfo(Map<String, Object> params);

    void updatePushSetting(Map<String, Object> params);

    Map<String, Object> getPushSetting(Map<String, Object> params);

    void withdraw(Map<String, Object> params);

    int checkNickname(Map<String, Object> params);

    void updateUserInfo(Map<String, Object> params);
}