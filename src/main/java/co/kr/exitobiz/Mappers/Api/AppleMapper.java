package co.kr.exitobiz.Mappers.Api;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface AppleMapper {
    public void login(Map<String, Object> params);

    Map<String, String> getUserInfo(Map<String, Object> params);
}
