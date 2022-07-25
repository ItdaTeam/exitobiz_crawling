package co.kr.exitobiz.Mappers.Mobile;

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

}