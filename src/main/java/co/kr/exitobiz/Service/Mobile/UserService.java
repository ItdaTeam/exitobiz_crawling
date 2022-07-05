package co.kr.exitobiz.Service.Mobile;

import co.kr.exitobiz.Vo.usertableVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserService {

    public String getUserToken(HashMap<String,String> params);
    public List<Map<String, Object>> getUserTokenByLocation(HashMap<String, Object> params);
    public List<Map<String, usertableVo>> getUserTokenByLocationTest(List params);

}