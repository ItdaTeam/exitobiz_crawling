package co.kr.exitobiz.Mappers.Cms;

import co.kr.exitobiz.Vo.Cms.StaffVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface LoginMapper {
    public StaffVo getPassword(StaffVo vo);

    public void updateLoginTime(StaffVo vo);

    public String getUserToken(HashMap<String, String> params);
}
